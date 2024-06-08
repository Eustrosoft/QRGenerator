package org.eustrosoft.dto;

import javax.servlet.http.HttpServletRequest;

import static org.eustrosoft.Constants.EMPTY;
import static org.eustrosoft.Constants.MAP_URL_FORMAT;
import static org.eustrosoft.Constants.PARAM_ENCRYPTION;
import static org.eustrosoft.Constants.PARAM_LATITUDE;
import static org.eustrosoft.Constants.PARAM_LONGITUDE;
import static org.eustrosoft.Constants.PARAM_PASSWORD;
import static org.eustrosoft.Constants.PARAM_SSID;
import static org.eustrosoft.util.Util.getOrDefault;

public class QRLocationParams extends QRDefaultParams {
    private Float longitude;
    private Float latitude;

    public static QRLocationParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) throws Exception {
        return new QRLocationParams(
                getOrDefault(request, PARAM_LONGITUDE, 0.0f),
                getOrDefault(request, PARAM_LATITUDE, 0.0f),
                imageSettings
        );
    }

    public QRLocationParams(
            Float longitude, Float latitude,
            QRImageSettings imageSettings
    ) {
        super(generateLocation(longitude, latitude), imageSettings);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private static String generateLocation(Float longitude, Float latitude) {
        if (longitude == null) {
            longitude = 0.0f;
        }
        if (latitude == null) {
            latitude = 0.0f;
        }
        return String.format(MAP_URL_FORMAT, longitude, latitude);
    }

    public Float getLongitude() {
        return longitude;
    }

    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }

    public Float getLatitude() {
        return latitude;
    }

    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
}
