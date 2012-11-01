package com.dnt.graph.biz.view.service;

import java.io.IOException;

import com.dnt.graph.biz.common.exception.FileOperatorException;

public interface FileService {
	/**
	 * 提取原始文件的相关操作，包括原始数据转成节点、关系、以及历史数据的保存,这个是通过定时任务去做的
	 * 
	 * @throws IOException
	 */
	public void saveNodeAndRelation() throws FileOperatorException;

}
