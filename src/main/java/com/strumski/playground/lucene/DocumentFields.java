package com.strumski.playground.lucene;

public final class DocumentFields {

    private DocumentFields() {
        throw new AssertionError("No instances allowed");
    }

    public static final String NAME_FIELD = "name";
    public static final String ADDRESS_FIELD = "address";
    public static final String CITY_FIELD = "city";
    public static final String LOCATION_TAG = "latlon";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String KEY = "key";

}
