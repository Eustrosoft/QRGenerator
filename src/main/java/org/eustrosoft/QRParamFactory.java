package org.eustrosoft;

import javax.servlet.http.HttpServletRequest;
import org.eustrosoft.dto.QRDefaultParams;
import org.eustrosoft.dto.QRDto;
import org.eustrosoft.dto.QREmailParams;
import org.eustrosoft.dto.QREustrosoftParams;
import org.eustrosoft.dto.QRImageSettings;
import org.eustrosoft.dto.QRLocationParams;
import org.eustrosoft.dto.QRPersonCardParams;
import org.eustrosoft.dto.QRPhoneNumberParams;
import org.eustrosoft.dto.QRSmsParams;
import org.eustrosoft.dto.QRUrlParams;
import org.eustrosoft.dto.QRWiFiParams;

import static org.eustrosoft.Constants.PARAM_D;
import static org.eustrosoft.Constants.PARAM_P;
import static org.eustrosoft.Constants.PARAM_Q;
import static org.eustrosoft.Constants.PARAM_SITE;
import static org.eustrosoft.Constants.PARAM_TYPE;
import static org.eustrosoft.Constants.QRType;

public final class QRParamFactory {

    public static QRDto getRightDto(final HttpServletRequest req) throws Exception {
        String q = req.getParameter(PARAM_Q);
        String type = req.getParameter(PARAM_TYPE);

        if (q == null && type == null) {
            throw new IllegalArgumentException("Use 'Q' or 'type' to determine the type of generated QR code");
        }

        if (q != null) {
            String p = req.getParameter(PARAM_P);
            String d = req.getParameter(PARAM_D);
            String site = req.getParameter(PARAM_SITE);
            return new QREustrosoftParams(
                    q, p, d, site,
                    QRImageSettings.extractFromRequest(req)
            );
        } else {
            QRType qrType = QRType.fromString(type);
            QRImageSettings imageSettings = QRImageSettings.extractFromRequest(req);
            switch (qrType) {
                case TEXT:
                    return QRDefaultParams.fromRequest(req, imageSettings);
                case URL:
                    return QRUrlParams.fromRequest(req, imageSettings);
                case PHONE:
                    return QRPhoneNumberParams.fromRequest(req, imageSettings);
                case EMAIL:
                    return QREmailParams.fromRequest(req, imageSettings);
                case SMS:
                    return QRSmsParams.fromRequest(req, imageSettings);
                case CONTACT:
                    return QRPersonCardParams.fromRequest(req, imageSettings);
                case WIFI:
                    return QRWiFiParams.fromRequest(req, imageSettings);
                case LOCATION:
                    return QRLocationParams.fromRequest(req, imageSettings);
                default:
                    return null;
            }
        }
    }
}
