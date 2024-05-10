package org.eustrosoft;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.eustrosoft.dto.QRDto;
import org.eustrosoft.dto.QREustrosoftParams;
import org.jfree.graphics2d.svg.SVGGraphics2D;
import org.jfree.graphics2d.svg.ViewBox;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class QRGeneratorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        try {
            req.setCharacterEncoding("UTF-8");
            resp.setStatus(200);

            QRDto params = QRParamFactory.getRightDto(req);
            if (params == null) {
                throw new Exception("Could not parse request");
            }
            BufferedImage qrImage = getQRImage(params);

            if (params.getFileType().equals(FileType.SVG)) {
                resp.setContentType(FileType.getContentType(params.getFileType()));
                PrintWriter writer = resp.getWriter();
                writer.println("<?xml\n" +
                        "version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                        "<!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">");
                writer.println(getQRCodeSvg(qrImage, params.getX()));
            } else {
                resp.setContentType(FileType.getContentType(params.getFileType()));
                ImageIO.write(qrImage, params.getFileType().getType(), resp.getOutputStream());
            }
        } catch (Exception ex) {
            PrintWriter writer = resp.getWriter();
            writer.println(ex.getMessage());
            resp.setStatus(400);
            writer.flush();
        }
    }

    private BufferedImage getQRImage(QRDto params) throws WriterException {
        Map<EncodeHintType, String> hintMap = new HashMap<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, params.getCorrectionLevel().toString());
        if (!(params instanceof QREustrosoftParams)) {
            hintMap.put(EncodeHintType.CHARACTER_SET, StandardCharsets.UTF_8.toString());
        }
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix byteMatrix = qrCodeWriter.encode(
                params.getText(), BarcodeFormat.QR_CODE, params.getX(), params.getX(), hintMap
        );

        int matrixWidth = byteMatrix.getWidth();

        BufferedImage image = new BufferedImage(matrixWidth, matrixWidth, BufferedImage.TYPE_INT_RGB);
        image.createGraphics();
        Graphics2D graphics = (Graphics2D) image.getGraphics();
        graphics.setColor(params.getBackgroundColor());
        graphics.fillRect(0, 0, matrixWidth, matrixWidth);

        graphics.setColor(params.getColor());

        for (int i = 0; i < matrixWidth; i++) {
            for (int j = 0; j < matrixWidth; j++) {
                if (byteMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        return image;
    }

    private static String getQRCodeSvg(BufferedImage image, int width) {
        SVGGraphics2D g2 = new SVGGraphics2D(width, width);
        g2.drawImage(image, 0, 0, width, width, null);
        ViewBox viewBox = new ViewBox(0, 0, width, width);
        return g2.getSVGElement(null, true, viewBox, null, null);
    }
}
