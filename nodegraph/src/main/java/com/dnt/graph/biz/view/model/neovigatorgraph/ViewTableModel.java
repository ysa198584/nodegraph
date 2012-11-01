package com.dnt.graph.biz.view.model.neovigatorgraph;

public class ViewTableModel {
	private String chinaName;
	private String relationship;
	private String fromHost;
	private String toHost;
	private String fromPort;
	private String toPort;
	
	public String getChinaName() {
		return chinaName;
	}
	public void setChinaName(String chinaName) {
		this.chinaName = chinaName;
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
	public String getFromPort() {
		return fromPort;
	}
	public void setFromPort(String fromPort) {
		this.fromPort = fromPort;
	}
	public String getToPort() {
		return toPort;
	}
	public void setToPort(String toPort) {
		this.toPort = toPort;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	
}
