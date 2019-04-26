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
