package com.strumski.playground.lucene;

import dagger.Component;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.search.IndexSearcher;

@Component(modules = {LuceneModule.class, DirectoryModule.class})
public interface LuceneComponent {
    IndexWriter buildWriteIndex();
    IndexReader buildReadIndex();
    IndexSearcher buildSearchIndex();
}
