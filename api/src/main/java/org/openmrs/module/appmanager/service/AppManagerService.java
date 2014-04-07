package org.openmrs.module.appmanager.service;

import java.io.File;
import java.io.InputStream;


public interface AppManagerService {
	
	/**
	 * Add the <code>inputStream</code> as a file in the apps repository
	 * 
	 * @param inputStream <code>InputStream</code> to load
	 * @return filename String of the file's name of the stream
	 */
	File install(InputStream appInputStream, String filename);
	
	File start(File appFile);
	
	boolean stop(String appName);

	boolean uninstall(String appName);
	
	/**
	 * Gets the folder where apps are stored. AppException is thrown on errors
	 * 
	 * @return folder containing apps
	 */
	File getAppRepository();
	
}
