package com.strumski.playground.lucene;

import dagger.Module;
import dagger.Provides;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

import java.io.IOException;

@Module
final class LuceneModule {
    @Provides
    Analyzer providesAnalyser() {
        return new StandardAnalyzer();
    }

    @Provides
    IndexWriterConfig provideIndexConfig(Analyzer analyzer) {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
        return  indexWriterConfig;
    }

    @Provides
    IndexWriter provideIndexWriter(Directory index, IndexWriterConfig indexWriterConfig) {
        try {
            return new IndexWriter(index, indexWriterConfig);
        } catch (IOException e) {
            throw new RuntimeException("couldn't crete an index writer", e);
        }
    }

    @Provides
    IndexReader provideIndexReader(Directory index) {
        try {
            return DirectoryReader.open(index);
        } catch (IOException e) {
            throw new RuntimeException("couldn't crete an index reader: " + e.getMessage());
        }
    }

    @Provides
    IndexSearcher provideIndexSearcher(IndexReader reader) {
        return new IndexSearcher(reader);
    }
}
