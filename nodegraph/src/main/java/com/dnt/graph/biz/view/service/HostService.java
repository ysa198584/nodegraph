package com.dnt.graph.biz.view.service;

import com.dnt.graph.biz.view.dataobject.HostInfoEntity;

public interface HostService {
	/**
	 * 根据主机名字查询主机的相关信息
	 * 
	 * @param hostName
	 * @return
	 */
	public HostInfoEntity getHost(String hostName);
	/**
	 * 根据主机名字来查询主机当前运行的相关参数
	 * @param hostName
	 * @return	JSON字符串
	 */
	public String getHostStateData(String hostName);
}
