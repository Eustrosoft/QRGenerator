package org.eustrosoft;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.servlet.http.HttpServletRequest;
import org.eustrosoft.dto.QRDto;
import org.eustrosoft.dto.QRDefaultParams;
import org.eustrosoft.dto.QREustrosoftParams;

import static org.eustrosoft.Constants.*;

public final class QRParamFactory {

    public static QRDto getRightDto(final HttpServletRequest req) throws Exception {
        String q = req.getParameter(PARAM_Q);
        String text = req.getParameter(PARAM_TEXT);

        if (q != null) {
            String p = req.getParameter(PARAM_P);
            String d = req.getParameter(PARAM_D);
            String site = req.getParameter(PARAM_SITE);
            return QREustrosoftParams.fromStrings(
                    WebParams.getString(req, WebParams.BASIC_REDIRECT_URL),
                    q, p, d, site,
                    getOrDefault(req, PARAM_FILE_TYPE, FileType.PNG.getType()),
                    getOrDefault(req, PARAM_X, Default.IMAGE_SIZE),
                    getOrDefault(req, PARAM_CORRECTION_LEVEL, ErrorCorrectionLevel.L.name()),
                    getOrDefault(req, PARAM_COLOR, Default.COLOR),
                    getOrDefault(req, PARAM_BACKGROUND, Default.BACKGROUND)
            );
        }
        if (text != null) {
            String color = req.getParameter(PARAM_COLOR);
            String background = req.getParameter(PARAM_BACKGROUND);
            String fileType = req.getParameter(PARAM_FILE_TYPE);
            String x = req.getParameter(PARAM_X);
            String correctionLevel = req.getParameter(PARAM_CORRECTION_LEVEL);

            return QRDefaultParams.fromStrings(
                    text, color, background,
                    fileType, x, correctionLevel
            );
        }
        return null;
    }

    public static <T> T getOrDefault(HttpServletRequest request, String param, T defaultValue) {
        if (request == null) {
            return null;
        }
        String parameter = request.getParameter(param);
        if (parameter == null) {
            return defaultValue;
        }
        return (T) parameter;
    }
}
