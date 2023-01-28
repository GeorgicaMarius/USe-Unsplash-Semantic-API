export interface Photo {
  photoId: string;
  photoUrl: string;
  photoDescription: string;
  photoKeywords: string;
  statsDownloads: number;
  statsViews: number;
  exifCameraMake: string;
  exifCameraModel: string;
  exifIso: string;
  exifApertureValue: string;
  exifFocalLength: string;
  exifExposureTime: string;
  photoImageUrl: string;
  photoSubmittedAt: number;
  photoWidth: number;
  photoHeight: number;
  photoLocationCity: string;
  photoLocationCountry: string;
  photoLocationLongitude: string;
  photoLocationLatitude: string;
  photoLocationName: string;
  photographerLastName: string;
  photographerFirstName: string;
  photographerUsername: string;
}
