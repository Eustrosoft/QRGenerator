package org.eustrosoft.qr.dto;

import javax.servlet.http.HttpServletRequest;
import org.eustrosoft.qr.Constants;
import org.eustrosoft.qr.WebParams;

import static org.eustrosoft.qr.Constants.FORMAT_DISTANCE;
import static org.eustrosoft.qr.Constants.FORMAT_LATITUDE;
import static org.eustrosoft.qr.Constants.FORMAT_LONGITUDE;
import static org.eustrosoft.qr.Constants.PARAM_DISTANCE;
import static org.eustrosoft.qr.Constants.PARAM_LATITUDE;
import static org.eustrosoft.qr.Constants.PARAM_LONGITUDE;
import static org.eustrosoft.qr.util.Util.getOrDefaultExtract;

public class QRLocationParams extends QRDefaultParams {
    private Float longitude;
    private Float latitude;
    private Integer distance;

    public static QRLocationParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) throws Exception {
        return new QRLocationParams(
                request,
                getOrDefaultExtract(
                        request, PARAM_LONGITUDE,
                        WebParams.getFloat(request, WebParams.DEFAULT_MAP_LONGITUDE, Constants.Default.LONGITUDE),
                        Float::parseFloat
                ),
                getOrDefaultExtract(
                        request, PARAM_LATITUDE,
                        WebParams.getFloat(request, WebParams.DEFAULT_MAP_LATITUDE, Constants.Default.LATITUDE),
                        Float::parseFloat
                ),
                getOrDefaultExtract(
                        request, PARAM_DISTANCE,
                        WebParams.getInteger(request, WebParams.DEFAULT_MAP_DISTANCE, Constants.Default.DISTANCE),
                        Integer::parseInt
                ),
                imageSettings
        );
    }

    public QRLocationParams(
            HttpServletRequest request, Float longitude,
            Float latitude, Integer distance,
            QRImageSettings imageSettings
    ) {
        super(generateLocation(request, longitude, latitude, distance), imageSettings);
        this.latitude = latitude;
        this.longitude = longitude;
        this.distance = distance;
    }

    private static String generateLocation(
            HttpServletRequest request, Float longitude,
            Float latitude, Integer distance
    ) {
        String mapFormat = WebParams.getString(
                request, WebParams.DEFAULT_MAP_API, Constants.Default.MAP_URL_FORMAT
        );
        mapFormat = mapFormat.replace(FORMAT_LATITUDE, latitude.toString());
        mapFormat = mapFormat.replace(FORMAT_LONGITUDE, longitude.toString());
        mapFormat = mapFormat.replace(FORMAT_DISTANCE, distance.toString());
        return mapFormat;
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
