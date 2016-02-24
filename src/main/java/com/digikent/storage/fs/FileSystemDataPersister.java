package com.digikent.storage.fs;


import com.digikent.storage.DataPersister;
import com.digikent.storage.DataPersisterFailedException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class FileSystemDataPersister implements DataPersister {
    private static final Logger LOG = LoggerFactory.getLogger(FileSystemDataPersister.class);

    private static final String ROOT_PATH = System.getProperty("user.home") + "/.kep";


    @Override
    public void persist( String identifier, byte[] data, boolean readonly)
            throws DataPersisterFailedException {
        try {
            LOG.debug("Saving {} to the file system path {}", identifier, ROOT_PATH);
            Path path = Paths.get(ROOT_PATH, identifier);
            FileOutputStream outputStream = FileUtils.openOutputStream(path.toFile());
            IOUtils.write(data, outputStream);
            IOUtils.closeQuietly(outputStream);

            if (readonly) {
                path.toFile().setReadOnly();

            }

        } catch (IOException e) {
            LOG.warn("persisting data with fileName {} has failed", identifier);
            throw new DataPersisterFailedException(e);
        }
    }


    @Override
    public InputStream readFileContent(String identifier) throws IOException {
        LOG.debug("Open {}  the file from system path {}", identifier, ROOT_PATH);
        Path path = Paths.get(ROOT_PATH, identifier);
        return FileUtils.openInputStream(path.toFile());
    }
}
