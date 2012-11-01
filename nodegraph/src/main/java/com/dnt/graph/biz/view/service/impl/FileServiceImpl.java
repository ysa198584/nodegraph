package com.dnt.graph.biz.view.service.impl;

import com.dnt.graph.biz.common.exception.FileOperatorException;
import com.dnt.graph.biz.common.file.FileInit;
import com.dnt.graph.biz.view.service.FileService;

public class FileServiceImpl implements FileService {

	@Override
	public void saveNodeAndRelation() throws FileOperatorException {
		try {
			FileInit.loadRemoteFile();
		} catch (FileOperatorException e) {
			throw e;
		}
	}
}
