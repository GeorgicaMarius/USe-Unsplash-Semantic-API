package ro.wade.useapi.models;

public class DbpediaCountryQueryDto {

    public String countryName;
    public String countryCurrency;
    public Integer countryPopulationTotal;
    public String countryTimeZone;
    public String countryWebsite;
    public Double countryLat;
    public Double countryLong;
    public String countryOfficialLanguage;
    public String countryCapital;
    public String countryGovernmentType;

    @Override
    public String toString() {
        return "DbpediaCountryQueryDto{" +
                "countryName='" + countryName + '\'' +
                ", countryCurrency='" + countryCurrency + '\'' +
                ", countryPopulationTotal=" + countryPopulationTotal +
                ", countryTimeZone='" + countryTimeZone + '\'' +
                ", countryWebsite='" + countryWebsite + '\'' +
                ", countryLat=" + countryLat +
                ", countryLong=" + countryLong +
                ", countryOfficialLanguage='" + countryOfficialLanguage + '\'' +
                ", countryCapital='" + countryCapital + '\'' +
                ", countryGovernmentType='" + countryGovernmentType + '\'' +
                '}';
    }
}
