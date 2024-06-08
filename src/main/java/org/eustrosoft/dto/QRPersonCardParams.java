package org.eustrosoft.dto;

import javax.servlet.http.HttpServletRequest;

import static org.eustrosoft.Constants.*;
import static org.eustrosoft.util.Util.getOrDefault;

public class QRPersonCardParams extends QRDefaultParams {

    private String firstName;
    private String lastName;
    private String organization;
    private String title;
    private String email;
    private String mobilePhone;
    private String fax;
    private String street;
    private String city;
    private String region;
    private String postcode;
    private String country;
    private String url;

    public static QRPersonCardParams fromRequest(
            HttpServletRequest request,
            QRImageSettings imageSettings
    ) throws Exception {
        return new QRPersonCardParams(
                getOrDefault(request, PARAM_PHONE, EMPTY),
                getOrDefault(request, PARAM_FIRST_NAME, EMPTY),
                getOrDefault(request, PARAM_LAST_NAME, EMPTY),
                getOrDefault(request, PARAM_ORGANIZATION, EMPTY),
                getOrDefault(request, PARAM_TITLE, EMPTY),
                getOrDefault(request, PARAM_EMAIL, EMPTY),
                getOrDefault(request, PARAM_MOBILE_PHONE, EMPTY),
                getOrDefault(request, PARAM_FAX, EMPTY),
                getOrDefault(request, PARAM_STREET, EMPTY),
                getOrDefault(request, PARAM_CITY, EMPTY),
                getOrDefault(request, PARAM_REGION, EMPTY),
                getOrDefault(request, PARAM_POSTCODE, EMPTY),
                getOrDefault(request, PARAM_COUNTRY, EMPTY),
                getOrDefault(request, PARAM_URL, EMPTY),
                imageSettings
        );
    }

    public QRPersonCardParams(String phone, String firstName, String lastName, String organization,
                              String title, String email, String mobilePhone, String fax, String street, String city,
                              String region, String postcode, String country, String url, QRImageSettings imageSettings) {
        super(generatePCard(
                        phone, firstName, lastName, organization, title, email, mobilePhone,
                        fax, street, city, region, postcode, country, url
                ), imageSettings
        );
        this.firstName = firstName;
        this.lastName = lastName;
        this.organization = organization;
        this.title = title;
        this.email = email;
        this.mobilePhone = mobilePhone;
        this.fax = fax;
        this.street = street;
        this.city = city;
        this.region = region;
        this.postcode = postcode;
        this.country = country;
        this.url = url;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public static String generatePCard(String phone, String firstName, String lastName, String organization,
                                       String title, String email, String mobilePhone, String fax, String street, String city,
                                       String region, String postcode, String country, String url) {
        return String.format(
                "BEGIN:VCARD\n" +
                        "VERSION:3.0\n" +
                        "N:%s;%s\n" +
                        "FN:%s %s\n" +
                        "TITLE:%s\n" +
                        "ORG:%s\n" +
                        "URL:%s\n" +
                        "EMAIL;TYPE=INTERNET:%s\n" +
                        "TEL;TYPE=voice,home,pref:%s\n" +
                        "TEL;TYPE=voice,cell,pref:%s\n" +
                        "TEL;TYPE=fax,home,pref:%s\n" +
                        "ADR:;;%s;%s;%s;%s;%s\n" +
                        "END:VCARD",
                lastName, firstName, firstName, lastName, title, organization, url, email, phone,
                mobilePhone, fax, street, city, region, postcode, country
        );
    }
}
