package com.strumski.playground;

import com.strumski.playground.lucene.*;
import org.apache.lucene.index.IndexWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Objects;


public class Application {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws IOException {

        LuceneComponent luceneComponent = DaggerLuceneComponent.builder().directoryModule(new DirectoryModule("/var/tmp/bla")).build();
        IndexWriter indexWriter = luceneComponent.buildWriteIndex();
        ClassLoader classLoader = Application.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("data/")).getFile());
        LuceneIndexer.indexPath(indexWriter, file.getAbsolutePath());
        indexWriter.close();

        logger.info("------------- New search: finalAll ---------------");
        LuceneSearcher.findAllLocations(luceneComponent.buildSearchIndex()).forEach(System.out::println);
        logger.info("------------- New search: find all locations with name bla ---------------");
        LuceneSearcher.findLocationsByName(luceneComponent.buildSearchIndex(), "bla").forEach(System.out::println);;
        logger.info("------------- New search: find all locations with name hermes ---------------");
        LuceneSearcher.findLocationsByName(luceneComponent.buildSearchIndex(), "hermes").forEach(System.out::println);;
        logger.info("------------- New search: find all locations within given range ---------------");
        LuceneSearcher.findLocationsWithinDistance(luceneComponent.buildSearchIndex(),
                49.861208, 8.659239, 500).forEach(System.out::println);;

    }
}
