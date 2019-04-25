package com.strumski.playground.lucene;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strumski.playground.model.Location;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public final class LuceneIndexer {

    private static final Logger logger = LoggerFactory.getLogger(LuceneIndexer.class);
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void indexPath(IndexWriter indexWriter, String path) throws IOException {
        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.filter(Files::isRegularFile).forEach(path1 -> LuceneIndexer.indexFile(indexWriter, path1));
        }
    }

    private static void indexFile(IndexWriter indexWriter, Path path) {
        try {
            InputStream stream = new FileInputStream(path.toFile());
            Location location = mapper.readValue(stream, Location.class);
            addDocument(indexWriter, location);
            logger.info("Successfully indexed " + path);
        } catch (IOException e) {
            logger.error("Couldn't index file", e);
        }
    }

    private static void addDocument(IndexWriter indexWriter, Location location) throws IOException {
        Document doc = new Document();
        doc.add(new StoredField(DocumentFields.NAME_FIELD, location.getName()));
        doc.add(new StringField(DocumentFields.KEY, Integer.toString(location.hashCode()), Field.Store.YES));
        doc.add(new StoredField(DocumentFields.ADDRESS_FIELD, location.getAddress()));
        doc.add(new StringField(DocumentFields.CITY_FIELD, location.getCity(), Field.Store.YES));
        doc.add(new LatLonPoint(DocumentFields.LOCATION_TAG, location.getLatitude(), location.getLongitude()));
        doc.add(new StoredField(DocumentFields.LATITUDE, location.getLatitude()));
        doc.add(new StoredField(DocumentFields.LONGITUDE, location.getLongitude()));
        indexWriter.updateDocument(new Term(DocumentFields.KEY, Integer.toString(location.hashCode())), doc);
    }
}

