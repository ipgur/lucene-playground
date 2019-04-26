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

import com.strumski.playground.model.Location;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.LatLonPoint;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class LuceneSearcher {

    public static List<Location> searchAllLocations(IndexSearcher indexSearcher) throws IOException {
        Query query = new MatchAllDocsQuery();
        List<Document> documents = executeQuery(indexSearcher, query, Integer.MAX_VALUE);
        List<Location> locations = documents.stream().map(Location::new).collect(Collectors.toList());
        indexSearcher.getIndexReader().close();
        return locations;
    }

    public static List<Location> searchLocationsWithinDistance(IndexSearcher indexSearcher, String name) throws IOException {
        Query query = new TermQuery(new Term(DocumentFields.NAME_FIELD, name));
        List<Document> documents = executeQuery(indexSearcher, query, Integer.MAX_VALUE);
        List<Location> locations = documents.stream().map(Location::new).collect(Collectors.toList());
        indexSearcher.getIndexReader().close();
        return locations;
    }

    public static List<Location> searchLocationsWithinDistance(IndexSearcher indexSearcher,
                                                             double latitude, double longitude, double distance) throws IOException {
        Query query = LatLonPoint.newDistanceQuery(DocumentFields.LOCATION_TAG, latitude, longitude, distance);
        List<Document> documents = executeQuery(indexSearcher, query, Integer.MAX_VALUE);
        List<Location> locations = documents.stream().map(Location::new).collect(Collectors.toList());
        indexSearcher.getIndexReader().close();
        return locations;
    }

    private static List<Document> executeQuery(IndexSearcher searcher, Query query, Integer maxResults) throws IOException, IOException {
        TopDocs topDocs = searcher.search(query, maxResults);

        return Arrays.stream(topDocs.scoreDocs)
                .map(scoreDoc -> toDocument(scoreDoc, searcher))
                .collect(Collectors.toList());
    }

    private static Document toDocument(ScoreDoc scoreDoc, IndexSearcher searcher) {
        try {
            return searcher.doc(scoreDoc.doc);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
