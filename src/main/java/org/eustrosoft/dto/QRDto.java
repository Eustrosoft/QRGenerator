package org.eustrosoft.dto;

import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import javax.servlet.http.HttpServletRequest;
import org.eustrosoft.FileType;

import java.awt.Color;

/**
 * QR Interface for needed params to generate qr image
 */
public interface QRDto {
    String getText();
    QRImageSettings getImageSettings();
}
