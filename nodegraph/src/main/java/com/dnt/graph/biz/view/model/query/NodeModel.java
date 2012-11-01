package com.dnt.graph.biz.view.model.query;

public class NodeModel {
	private String nodeId;
	private String hostName;
	private String createDate;
	private String fromHost;
	private String toHost;

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getFromHost() {
		return fromHost;
	}

	public void setFromHost(String fromHost) {
		this.fromHost = fromHost;
	}

	public String getToHost() {
		return toHost;
	}

	public void setToHost(String toHost) {
		this.toHost = toHost;
	}
}
