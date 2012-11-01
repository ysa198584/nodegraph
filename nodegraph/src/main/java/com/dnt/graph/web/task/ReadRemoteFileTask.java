package com.dnt.graph.web.task;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dnt.graph.biz.common.exception.FileOperatorException;
import com.dnt.graph.biz.view.service.FileService;

public class ReadRemoteFileTask {
	private final static Log logger = LogFactory.getLog((ReadRemoteFileTask.class));
	@Autowired
	private FileService fileService;

	public void readRemoteFile() {
		try {
			fileService.saveNodeAndRelation();
		} catch (FileOperatorException e) {
			logger.error(e.getErrorMessage());
			e.printStackTrace();
		}
	}
}
