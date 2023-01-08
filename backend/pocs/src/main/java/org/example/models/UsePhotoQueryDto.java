package org.example.models;

public class UsePhotoQueryDto {

    public String photoId;
    public String photoUrl;
    public String photoDescription;
    public String photoKeywords;
    public Integer statsDownloads;
    public Integer statsViews;
    public String exifCameraMake;
    public String exifCameraModel;
    public String exifIso;
    public String exifApertureValue;
    public String exifFocalLength;
    public String exifExposureTime;
    public String photoImageUrl;
    public Long photoSubmittedAt;
    public Integer photoWidth;
    public Integer photoHeight;
    public String photoLocationCity;
    public String photoLocationCountry;
    public String photoLocationLongitude;
    public String photoLocationLatitude;
    public String photoLocationName;
    public String photographerLastName;
    public String photographerFirstName;
    public String photographerUsername;

    @Override
    public String toString() {
        return "PhotoQueryDto{" + "\n" +
                "\tphotoId='" + photoId + '\'' + "\n" +
                "\tphotoUrl='" + photoUrl + '\'' + "\n" +
                "\tphotoDescription='" + photoDescription + '\'' + "\n" +
                "\tphotoKeywords='" + photoKeywords + '\'' + "\n" +
                "\tstatsDownloads=" + statsDownloads + "\n" +
                "\tstatsViews=" + statsViews + "\n" +
                "\texifCameraMake='" + exifCameraMake + '\'' + "\n" +
                "\texifCameraModel='" + exifCameraModel + '\'' + "\n" +
                "\texifIso='" + exifIso + '\'' + "\n" +
                "\texifApertureValue='" + exifApertureValue + '\'' + "\n" +
                "\texifFocalLength='" + exifFocalLength + '\'' + "\n" +
                "\texifExposureTime='" + exifExposureTime + '\'' + "\n" +
                "\tphotoImageUrl='" + photoImageUrl + '\'' + "\n" +
                "\tphotoSubmittedAt=" + photoSubmittedAt + "\n" +
                "\tphotoWidth=" + photoWidth + "\n" +
                "\tphotoHeight=" + photoHeight + "\n" +
                "\tphotoLocationCity='" + photoLocationCity + '\'' + "\n" +
                "\tphotoLocationCountry='" + photoLocationCountry + '\'' + "\n" +
                "\tphotoLocationLongitude='" + photoLocationLongitude + '\'' + "\n" +
                "\tphotoLocationLatitude='" + photoLocationLatitude + '\'' + "\n" +
                "\tphotoLocationName='" + photoLocationName + '\'' + "\n" +
                "\tphotographerLastName='" + photographerLastName + '\'' + "\n" +
                "\tphotographerFirstName='" + photographerFirstName + '\'' + "\n" +
                "\tphotographerUsername='" + photographerUsername + '\'' + "\n" +
                '}';
    }
}
