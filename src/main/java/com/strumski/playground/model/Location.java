/*
 * Copyright 2019 igur.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.strumski.playground.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.strumski.playground.lucene.DocumentFields;
import org.apache.lucene.document.Document;

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
    public int hashCode() {
        return Objects.hash(name, address, city, longitude, latitude);
    }
}
