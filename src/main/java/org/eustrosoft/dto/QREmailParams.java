package org.eustrosoft.dto;

import javax.servlet.http.HttpServletRequest;

import static org.eustrosoft.Constants.EMPTY;
import static org.eustrosoft.Constants.PARAM_EMAIL;
import static org.eustrosoft.Constants.PARAM_SUBJECT;
import static org.eustrosoft.Constants.PARAM_TEXT;
import static org.eustrosoft.util.Util.getOrDefault;

public class QREmailParams extends QRDefaultParams {
    private String email;
    private String subject;

    public static QREmailParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) {
        return new QREmailParams(
                getOrDefault(request, PARAM_EMAIL, EMPTY),
                getOrDefault(request, PARAM_SUBJECT, EMPTY),
                getOrDefault(request, PARAM_TEXT, EMPTY),
                imageSettings
        );
    }

    public QREmailParams(String email, String subject, String text, QRImageSettings imageSettings) {
        super(generateEmailMessage(email, subject, text), imageSettings);
        this.email = email;
        this.subject = subject;
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
