package org.openmrs.module.appmanager.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;

import org.apache.ant.compress.taskdefs.Unzip;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.openmrs.api.AdministrationService;
import org.openmrs.module.appmanager.AppConstants;
import org.openmrs.module.appmanager.AppException;
import org.openmrs.util.OpenmrsUtil;
import org.openmrs.web.WebUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


public class AppManagerServiceImpl implements AppManagerService {
	
	private final Logger logger = LoggerFactory.getLogger(AppManagerServiceImpl.class);
	
	@Autowired
	AdministrationService administrationService;

	@Autowired
	ServletContext servletContext;
	
	@Override
    public File install(InputStream appInputStream, String filename) {
		
		filename = WebUtil.stripFilename(filename);
	    
        File folder = getAppRepository();
		
		// check if app filename is already loaded
		if (OpenmrsUtil.folderContains(folder, filename))
			throw new AppException(filename + " is already associated with a loaded app.");
		
		File appFile = new File(folder.getAbsolutePath(), filename);
		
		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(appFile);
			OpenmrsUtil.copyFile(appInputStream, outputStream);
		}
		catch (FileNotFoundException e) {
			throw new AppException("Can't create an app file for " + filename, e);
		}
		catch (IOException e) {
			throw new AppException("Can't create an app file for " + filename, e);
		}
		finally {
			IOUtils.closeQuietly(appInputStream);
			IOUtils.closeQuietly(outputStream);
		}
		
		return appFile;
    }
	 
	@Override
    public File start(File appFile) {
		String appName = appFile.getName().substring(0, appFile.getName().lastIndexOf('.'));
		
		String destFolderPath = servletContext.getRealPath("") + "/" + AppConstants.RUNNING_APPS_FOLDER + "/" + appName;
		File destFolder = new File(destFolderPath);
		
		Unzip unzip = new Unzip();
		unzip.setSrc(appFile);
		unzip.setDest(destFolder);
		unzip.execute();
		
		return destFolder;
    }
	
	@Override
	public boolean stop(String appName) {
		boolean isDeleted = false;
		String destFolderPath = servletContext.getRealPath("") + "/" + AppConstants.RUNNING_APPS_FOLDER + "/" + appName;
		File destFolder = new File(destFolderPath);
		try {
	        FileUtils.deleteDirectory(destFolder);
	        isDeleted = true;
        }
        catch (IOException e) {
        	throw new AppException("Unable to stop the '" + appName + "' app", e);
        }
		return isDeleted;
	}

	@Override
    public boolean uninstall(String appName) {
		File appRepository = getAppRepository();
		File appFile = new File(appRepository.getAbsolutePath(), appName + AppConstants.APP_ARCHIVE_EXTENSION);
		
		if (appFile.exists() && ! appFile.delete()) {
			logger.error("Failed to delete the " + appFile.getAbsolutePath());
			throw new AppException("Unable to uninstall the '" + appName + "' app");
		}
		return ! appFile.exists();
    }

	@Override
    public File getAppRepository() {
		
		String folderName = administrationService.getGlobalProperty(AppConstants.REPOSITORY_FOLDER_PROPERTY,
			AppConstants.REPOSITORY_FOLDER_PROPERTY_DEFAULT);
		
		// try to load the repository folder straight away.
		File folder = new File(folderName);
		
		// if the property wasn't a full path already, assume it was intended to be a folder in the
		// application directory
		if (!folder.exists()) {
			folder = new File(OpenmrsUtil.getApplicationDataDirectory(), folderName);
		}
		
		// now create the apps folder if it doesn't exist
		if (!folder.exists()) {
			logger.warn("App repository " + folder.getAbsolutePath() + " doesn't exist.  Creating directories now.");
			folder.mkdirs();
		}
		
		if (!folder.isDirectory())
			throw new AppException("App repository is not a directory at: " + folder.getAbsolutePath());
		
		return folder;
	}
	
	
    public static void main(String[] args) {
	    File f = new File("/Users/NameFILIP/Dropbox/TensesTable.png");
	    System.out.println(f.exists());
	    System.out.println(f.delete());
	    System.out.println(f.exists());
    }
}
