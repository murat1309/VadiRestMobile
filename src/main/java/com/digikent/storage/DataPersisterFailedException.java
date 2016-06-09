package com.digikent.storage;

import java.io.IOException;


public class DataPersisterFailedException extends Exception {
	private static final long serialVersionUID = 1L;

	public DataPersisterFailedException(IOException e) {
		super(e);
	}

}
