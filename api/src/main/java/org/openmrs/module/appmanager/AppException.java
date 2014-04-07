/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.appmanager;


/**
 * Represents often fatal errors that occur within the appmanager package
 * 
 * @version 1.0
 */
public class AppException extends RuntimeException {
	
    private static final long serialVersionUID = 150417256149835806L;

	public AppException(String message) {
		super(message);
	}
	
	public AppException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public AppException(String message, String appName) {
		super(message + " App: " + appName);
	}
	
	public AppException(String message, String appName, Throwable cause) {
		super(message + " App: " + appName, cause);
	}
	
}

