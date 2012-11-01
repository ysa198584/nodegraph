package com.dnt.graph.biz.view.dataobject;

public class HostInfoEntity {
	private String id;
	private String name;
	private String chinaName;
	private String fullName;
	private String ip;
	private String SerialNumber;
	private	String cpuFrequency;
	private String cpuModel;
	private int cpuNumber;
	private long Memory;
	private String applicationType;
	private String makeCompany;
	private String systemType;
	private String onlineTime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getChinaName() {
		return chinaName;
	}
	public void setChinaName(String chinaName) {
		this.chinaName = chinaName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getSerialNumber() {
		return SerialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		SerialNumber = serialNumber;
	}
	public String getCpuFrequency() {
		return cpuFrequency;
	}
	public void setCpuFrequency(String cpuFrequency) {
		this.cpuFrequency = cpuFrequency;
	}
	public String getCpuModel() {
		return cpuModel;
	}
	public void setCpuModel(String cpuModel) {
		this.cpuModel = cpuModel;
	}
	public int getCpuNumber() {
		return cpuNumber;
	}
	public void setCpuNumber(int cpuNumber) {
		this.cpuNumber = cpuNumber;
	}
	public long getMemory() {
		return Memory;
	}
	public void setMemory(long memory) {
		Memory = memory;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public String getMakeCompany() {
		return makeCompany;
	}
	public void setMakeCompany(String makeCompany) {
		this.makeCompany = makeCompany;
	}
	public String getSystemType() {
		return systemType;
	}
	public void setSystemType(String systemType) {
		this.systemType = systemType;
	}
	public String getOnlineTime() {
		return onlineTime;
	}
	public void setOnlineTime(String onlineTime) {
		this.onlineTime = onlineTime;
	}
	
}
