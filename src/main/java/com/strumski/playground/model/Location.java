package com.strumski.playground.model;

/*
 * POJO class for a location of interest
 *
 * @author igur
 */
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.strumski.playground.lucene.DirectoryModule;
import com.strumski.playground.lucene.DocumentFields;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LatLonDocValuesField;
import org.apache.lucene.document.LatLonPoint;
import org.apache.lucene.document.StoredField;

import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public final class Location {
    private String name;
    private String address;
    private String city;
    private Double longitude;
    private Double latitude;

    public Location() {}

    public Location(Document d) {
        this.name = d.get(DocumentFields.NAME_FIELD);
        this.address = d.get(DocumentFields.ADDRESS_FIELD);
        this.city = d.get(DocumentFields.CITY_FIELD);
        this.latitude = (Double) d.getField(DocumentFields.LATITUDE).numericValue();
        this.longitude = (Double) d.getField(DocumentFields.LONGITUDE).numericValue();
    }


    public String getAddress() {
        return address;
    }
    public String getName() {
        return name;
    }
    public String getCity() {
        return city;
    }
    public Double getLongitude() {
        return longitude;
    }
    public Double getLatitude() {
        return latitude;
    }


    @Override
    public String toString() {
        return "Location{" +
                "name=" + name +
                ", address=" + address +
                ", city=" + city +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(name, location.name) &&
                Objects.equals(address, location.address) &&
                Objects.equals(city, location.city) &&
                Objects.equals(longitude, location.longitude) &&
                Objects.equals(latitude, location.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, city, longitude, latitude);
    }
}
