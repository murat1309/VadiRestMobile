/**
 *
 */
package com.digikent.storage;

import java.io.IOException;
import java.io.InputStream;

public interface DataPersister {

	void persist(String identifier, byte[] data, boolean readonly) throws DataPersisterFailedException;

    InputStream readFileContent(String identifier) throws IOException;
}
