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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.MMapDirectory;

import java.io.IOException;
import java.nio.file.Paths;

@Module
public final class DirectoryModule {
    private final String directory;

    public DirectoryModule(String directory) {
        this.directory = directory;
    }

    @Provides
    Directory providesDirectory() {
        try {
            return new MMapDirectory(Paths.get(directory));
        } catch (IOException e) {
            throw new RuntimeException("Couldn't provide a directory: ", e);
        }
    }
}
