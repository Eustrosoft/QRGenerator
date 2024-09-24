package org.eustrosoft.qr.dto;

/**
 * QR Interface for needed params to generate qr image
 */
public interface QRDto {
    String getText();
    QRImageSettings getImageSettings();
    String getFileName();
}
