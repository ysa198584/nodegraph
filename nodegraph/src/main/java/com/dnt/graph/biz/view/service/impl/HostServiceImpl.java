package com.dnt.graph.biz.view.service.impl;

import com.dnt.graph.biz.view.dataobject.HostInfoEntity;
import com.dnt.graph.biz.view.service.HostService;
import com.dnt.graph.biz.view.util.RemoteDataUtil;

public class HostServiceImpl implements HostService {

	@Override
	public HostInfoEntity getHost(String hostName) {
		return RemoteDataUtil.getHost(hostName);
	}

	@Override
	public String getHostStateData(String hostName) {
		return RemoteDataUtil.getHostStateData(hostName);
	}
	
}
