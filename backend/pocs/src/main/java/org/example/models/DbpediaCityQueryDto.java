package org.example.models;

public class DbpediaCityQueryDto {

    public String cityName;
    public String cityAbstract;
    public String cityCountry;
    public String cityAreaCode;
    public Integer cityPopulationTotal;
    public Integer cityPopulationAsOf;
    public String cityPostalCode;
    public String cityTimeZone;
    public String cityType;
    public String cityWebsite;
    public Double cityLat;
    public Double cityLong;

    @Override
    public String toString() {
        return "DbpediaCityQueryDto{" + "\n" +
                "\tcityName='" + cityName + '\'' + "\n" +
                "\tcityAbstract='" + cityAbstract + '\'' + "\n" +
                "\tcityCountry='" + cityCountry + '\'' + "\n" +
                "\tcityAreaCode='" + cityAreaCode + '\'' + "\n" +
                "\tcityPopulationTotal=" + cityPopulationTotal + "\n" +
                "\tcityPopulationAsOf=" + cityPopulationAsOf + "\n" +
                "\tcityPostalCode='" + cityPostalCode + '\'' + "\n" +
                "\tcityTimeZone='" + cityTimeZone + '\'' + "\n" +
                "\tcityType='" + cityType + '\'' + "\n" +
                "\tcityWebsite='" + cityWebsite + '\'' + "\n" +
                "\tcityLat=" + cityLat + "\n" +
                "\tcityLong=" + cityLong + "\n" +
                '}';
    }
}
