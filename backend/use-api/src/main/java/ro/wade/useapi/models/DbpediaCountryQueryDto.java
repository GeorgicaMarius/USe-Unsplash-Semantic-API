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
        return "DbpediaCountryQueryDto{" + "\n" +
                "\tcountryName='" + countryName + '\'' + "\n" +
                "\tcountryCurrency='" + countryCurrency + '\'' + "\n" +
                "\tcountryPopulationTotal=" + countryPopulationTotal + "\n" +
                "\tcountryTimeZone='" + countryTimeZone + '\'' + "\n" +
                "\tcountryWebsite='" + countryWebsite + '\'' + "\n" +
                "\tcountryLat=" + countryLat + "\n" +
                "\tcountryLong=" + countryLong + "\n" +
                "\tcountryOfficialLanguage='" + countryOfficialLanguage + '\'' + "\n" +
                "\tcountryCapital='" + countryCapital + '\'' + "\n" +
                "\tcountryGovernmentType='" + countryGovernmentType + '\'' + "\n" +
                '}';
    }
}
