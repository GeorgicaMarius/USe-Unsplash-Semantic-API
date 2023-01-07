package org.example.models;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import org.example.converters.CustomDateConverter;

import java.util.Date;

public class PhotoBean {
    @CsvBindByName(column = "photo_id")
    public String photoId;

    @CsvBindByName(column = "photo_url")
    public String photoUrl;

    @CsvBindByName(column = "photo_image_url")
    public String photoImageUrl;

    @CsvCustomBindByName(column = "photo_submitted_at", converter = CustomDateConverter.class)
    public Date photoSubmittedAt;

    @CsvBindByName(column = "photo_featured")
    public String photoFeatured;

    @CsvBindByName(column = "photo_width")
    public Integer photoWidth;

    @CsvBindByName(column = "photo_height")
    public Integer photoHeight;

    @CsvBindByName(column = "photo_aspect_ratio")
    public Double photoAspectRatio;

    @CsvBindByName(column = "photo_description")
    public String photoDescription;

    @CsvBindByName(column = "photographer_username")
    public String photographerUsername;

    @CsvBindByName(column = "photographer_first_name")
    public String photographerFirstName;

    @CsvBindByName(column = "photographer_last_name")
    public String photographerLastName;

    @CsvBindByName(column = "exif_camera_make")
    public String exifCameraMake;

    @CsvBindByName(column = "exif_camera_model")
    public String exifCameraModel;

    @CsvBindByName(column = "exif_iso")
    public Integer exifIso;

    @CsvBindByName(column = "exif_aperture_value")
    public String exifApertureValue;

    @CsvBindByName(column = "exif_focal_length")
    public String exifFocalLength;

    @CsvBindByName(column = "exif_exposure_time")
    public String exifExposureTime;

    @CsvBindByName(column = "photo_location_name")
    public String photoLocationName;

    @CsvBindByName(column = "photo_location_latitude")
    public String photoLocationLatitude;

    @CsvBindByName(column = "photo_location_longitude")
    public String photoLocationLongitude;

    @CsvBindByName(column = "photo_location_country")
    public String photoLocationCountry;

    @CsvBindByName(column = "photo_location_city")
    public String photoLocationCity;

    @CsvBindByName(column = "stats_views")
    public Integer statsViews;

    @CsvBindByName(column = "stats_downloads")
    public Integer statsDownloads;

    @CsvBindByName(column = "ai_description")
    public String aiDescription;

    @CsvBindByName(column = "ai_primary_landmark_name")
    public String aiPrimaryLandmarkName;

    @CsvBindByName(column = "ai_primary_landmark_latitude")
    public String aiPrimaryLandmarkLatitude;

    @CsvBindByName(column = "ai_primary_landmark_longitude")
    public String aiPrimaryLandmarkLongitude;

    @CsvBindByName(column = "ai_primary_landmark_confidence")
    public String aiPrimaryLandmarkConfidence;

    @CsvBindByName(column = "blur_hash")
    public String blurHash;

    @Override
    public String toString() {
        return "PhotoBean{" + "\n" +
                "\tphotoId='" + photoId + '\'' + "\n" +
                "\tphotoUrl='" + photoUrl + '\'' + "\n" +
                "\tphotoImageUrl='" + photoImageUrl + '\'' + "\n" +
                "\tphotoSubmittedAt=" + photoSubmittedAt + "\n" +
                "\tphotoFeatured='" + photoFeatured + '\'' + "\n" +
                "\tphotoWidth=" + photoWidth + "\n" +
                "\tphotoHeight=" + photoHeight + "\n" +
                "\tphotoAspectRatio=" + photoAspectRatio + "\n" +
                "\tphotoDescription='" + photoDescription + '\'' + "\n" +
                "\tphotographerUsername='" + photographerUsername + '\'' + "\n" +
                "\tphotographerFirstName='" + photographerFirstName + '\'' + "\n" +
                "\tphotographerLastName='" + photographerLastName + '\'' + "\n" +
                "\texifCameraMake='" + exifCameraMake + '\'' + "\n" +
                "\texifCameraModel='" + exifCameraModel + '\'' + "\n" +
                "\texifIso=" + exifIso + "\n" +
                "\texifApertureValue='" + exifApertureValue + '\'' + "\n" +
                "\texifFocalLength='" + exifFocalLength + '\'' + "\n" +
                "\texifExposureTime='" + exifExposureTime + '\'' + "\n" +
                "\tphotoLocationName='" + photoLocationName + '\'' + "\n" +
                "\tphotoLocationLatitude='" + photoLocationLatitude + '\'' + "\n" +
                "\tphotoLocationLongitude='" + photoLocationLongitude + '\'' + "\n" +
                "\tphotoLocationCountry='" + photoLocationCountry + '\'' + "\n" +
                "\tphotoLocationCity='" + photoLocationCity + '\'' + "\n" +
                "\tstatsViews=" + statsViews + "\n" +
                "\tstatsDownloads=" + statsDownloads + "\n" +
                "\taiDescription='" + aiDescription + '\'' + "\n" +
                "\taiPrimaryLandmarkName='" + aiPrimaryLandmarkName + '\'' + "\n" +
                "\taiPrimaryLandmarkLatitude='" + aiPrimaryLandmarkLatitude + '\'' + "\n" +
                "\taiPrimaryLandmarkLongitude='" + aiPrimaryLandmarkLongitude + '\'' + "\n" +
                "\taiPrimaryLandmarkConfidence='" + aiPrimaryLandmarkConfidence + '\'' + "\n" +
                "\tblurHash='" + blurHash + '\'' + "\n" +
                '}';
    }
}
