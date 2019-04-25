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
