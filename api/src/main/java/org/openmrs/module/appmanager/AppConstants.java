package org.openmrs.module.appmanager;

/**
 * Constants used by the app system in openmrs
 */
public class AppConstants {
	
	/**
	 * Name of the global property that will tell the system where to look for apps to load. Can
	 * be either relative or absolute
	 */
	public static final String REPOSITORY_FOLDER_PROPERTY = "appmanager.repositoryFolder";
	
	/**
	 * Default name of the folder that holds the currently loaded apps for the system
	 * 
	 * @see #REPOSITORY_FOLDER_PROPERTY
	 */
	public static final String REPOSITORY_FOLDER_PROPERTY_DEFAULT = "apps";
	
	
	public static final String RUNNING_APPS_FOLDER = "apps";
	
	public static final String APP_ARCHIVE_EXTENSION = ".zip";
}
