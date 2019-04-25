package com.strumski.playground;

import com.strumski.playground.lucene.*;
import com.strumski.playground.model.Location;
import org.apache.lucene.index.IndexWriter;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static junit.framework.TestCase.*;

public class ApplicationTest {

    @Test
    public void testIndexing() {
        LuceneComponent luceneComponent = DaggerLuceneComponent.builder().directoryModule(new DirectoryModule("/var/tmp/lucene/unittests/")).build();
        IndexWriter indexWriter = luceneComponent.buildWriteIndex();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("data/")).getFile());
        try {
            LuceneIndexer.indexPath(indexWriter, file.getAbsolutePath());
            assertEquals(2, indexWriter.numRamDocs());
            indexWriter.close();
            List<Location> res = LuceneSearcher.findAllLocations(luceneComponent.buildSearchIndex());
            res.forEach(System.out::println);
            assertEquals(2, res.size());
        } catch (IOException e) {
            fail();
        }
    }
}
