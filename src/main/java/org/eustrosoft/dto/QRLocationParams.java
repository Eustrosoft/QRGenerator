package org.eustrosoft.dto;

import javax.servlet.http.HttpServletRequest;
import org.eustrosoft.Constants;
import org.eustrosoft.WebParams;

import java.net.URI;

import static org.eustrosoft.Constants.MAP_URL_FORMAT;
import static org.eustrosoft.Constants.PARAM_DISTANCE;
import static org.eustrosoft.Constants.PARAM_LATITUDE;
import static org.eustrosoft.Constants.PARAM_LONGITUDE;
import static org.eustrosoft.util.Util.getOrDefault;
import static org.eustrosoft.util.Util.getOrDefaultExtract;

public class QRLocationParams extends QRDefaultParams {
    private Float longitude;
    private Float latitude;
    private Integer distance;

    public static QRLocationParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) throws Exception {
        return new QRLocationParams(
                WebParams.getFloat(
                        request, WebParams.DEFAULT_MAP_LONGITUDE,
                        getOrDefaultExtract(request, PARAM_LONGITUDE, Constants.Default.LONGITUDE, Float::parseFloat)
                ),
                WebParams.getFloat(
                        request, WebParams.DEFAULT_MAP_LATITUDE,
                        getOrDefaultExtract(request, PARAM_LATITUDE, Constants.Default.LATITUDE, Float::parseFloat)
                ),
                WebParams.getInteger(
                        request, WebParams.DEFAULT_MAP_DISTANCE,
                        getOrDefaultExtract(request, PARAM_DISTANCE, Constants.Default.DISTANCE, Integer::parseInt)
                ),
                imageSettings
        );
    }

    public QRLocationParams(
            Float longitude, Float latitude, Integer distance,
            QRImageSettings imageSettings
    ) {
        super(generateLocation(longitude, latitude, distance), imageSettings);
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    private static String generateLocation(Float longitude, Float latitude, Integer distance) {
        String coordFormat = longitude + "%2C" + latitude;
        return String.format(MAP_URL_FORMAT, coordFormat, distance);
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

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }
}
