package com.strumski.playground;

import com.strumski.playground.lucene.*;
import com.strumski.playground.model.Location;
import org.apache.lucene.index.IndexWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.fail;


public class SearchLocationsTest {

    private final LuceneComponent luceneComponent = DaggerLuceneComponent.builder().directoryModule(
            new DirectoryModule("/var/tmp/lucene/unittests/")).build();

    @Before
    public void indexPath() {
        try (IndexWriter indexWriter = luceneComponent.buildWriteIndex()) {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(Objects.requireNonNull(classLoader.getResource("data/")).getFile());
            LuceneIndexer.indexPath(indexWriter, file.getAbsolutePath());
        } catch (IOException e) {
            fail();
        }
    }


    @Test
    public void testSearchAllLocations() {
        try {
            List<Location> res = LuceneSearcher.searchAllLocations(luceneComponent.buildSearchIndex());
            assertEquals(2, res.size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testSearchWitinDistance() {
        try {
            List<Location> res = LuceneSearcher.searchLocationsWithinDistance(luceneComponent.buildSearchIndex(),
                    49.861208, 8.659239, 650);
            assertEquals(1, res.size());
        } catch (IOException e) {
            fail();
        }
    }

    @Test
    public void testSearchByName() {
        try {
            List<Location> res = LuceneSearcher.searchLocationsWithinDistance(luceneComponent.buildSearchIndex(), "hermes");
            assertEquals(1, res.size());
            res = LuceneSearcher.searchLocationsWithinDistance(luceneComponent.buildSearchIndex(), "dhl");
            assertEquals(1, res.size());
            res = LuceneSearcher.searchLocationsWithinDistance(luceneComponent.buildSearchIndex(), "bla");
            assertEquals(0, res.size());
        } catch (IOException e) {
            fail();
        }
    }
}
