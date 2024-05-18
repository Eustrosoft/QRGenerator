package org.eustrosoft.dto;

public class QREmailParams extends QRDefaultParams {
    private String email;
    private String subject;

    public QREmailParams(String basicUrl, String email, String subject, String text) {
        super(generateEmailMessage(email, subject, text));
        this.email = email;
        this.subject = subject;
    }

    public static QRDto fromStrings(
            String basicUrl, String email, String subject, String text, String fileType,
            String x, String correctionLevel, String color, String backgroundColor
    ) throws Exception {
        QREmailParams params = new QREmailParams(basicUrl, email, subject, text);
        params.setColor(color);
        params.setCorrectionLevel(correctionLevel);
        params.setX(x);
        params.setBackgroundColor(backgroundColor);
        params.setFileType(fileType);
        return params;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    private static String generateEmailMessage(String email, String subject, String text) {
        return String.format("mailto:%s?subject=%s&body=%s", email, subject, text);
    }
}
