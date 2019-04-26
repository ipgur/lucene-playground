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

        LuceneSearcher.searchAllLocations(luceneComponent.buildSearchIndex()).forEach(System.out::println);
        LuceneSearcher.searchLocationsWithinDistance(luceneComponent.buildSearchIndex(), "bla").forEach(System.out::println);
        LuceneSearcher.searchLocationsWithinDistance(luceneComponent.buildSearchIndex(), "hermes").forEach(System.out::println);
        LuceneSearcher.searchLocationsWithinDistance(luceneComponent.buildSearchIndex(),
                49.861208, 8.659239, 500).forEach(System.out::println);

    }
}
