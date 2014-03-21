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
package org.openmrs.module.appmanager.web.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.ant.compress.taskdefs.Unzip;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.module.ModuleUtil;
import org.openmrs.web.WebUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * The main controller.
 */
@Controller
public class AppManagerManageController {
	
	@Autowired
	ServletContext servletContext;
	
	protected final Log log = LogFactory.getLog(getClass());
	
	@RequestMapping(value = "/module/appmanager/manage", method = RequestMethod.GET)
	public void manage(ModelMap model) {
		String realPath = servletContext.getRealPath("");
		
		File appFolder = new File(realPath + "apps/");
		String[] appList = appFolder.list();
		
		model.addAttribute("appList", appList);
	}
	
	@RequestMapping(value = "/module/appmanager/manage", method = RequestMethod.POST)
	public void upload(ModelMap model, HttpServletRequest request) {
		
		String realPath = servletContext.getRealPath("");
		
		InputStream inputStream = null;
		try {
			if (request instanceof MultipartHttpServletRequest) {
				
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				MultipartFile multipartModuleFile = multipartRequest.getFile("appFile");
				if (multipartModuleFile != null && !multipartModuleFile.isEmpty()) {
					String filename = WebUtil.stripFilename(multipartModuleFile.getOriginalFilename());
					
					inputStream = multipartModuleFile.getInputStream();
					File appFile = ModuleUtil.insertModuleFile(inputStream, filename);
					String dest = realPath + "apps/" + filename.substring(0, filename.lastIndexOf('.'));
					Unzip unzip = new Unzip();
					unzip.setSrc(appFile);
					unzip.setDest(new File(dest));
					unzip.execute();
				}
			}
		}
		catch (IOException e) {
			log.error("Error generated", e);
		}
		finally {
			IOUtils.closeQuietly(inputStream);
		}
		
	}
}
