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
