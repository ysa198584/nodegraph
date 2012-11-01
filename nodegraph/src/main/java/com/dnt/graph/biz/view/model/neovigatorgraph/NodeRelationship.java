package com.dnt.graph.biz.view.model.neovigatorgraph;

import java.util.ArrayList;
import java.util.List;

public class NodeRelationship {
	private List<BaseModel> attributes=new ArrayList<BaseModel>();
	private String name;
	private String id;
	private String chinaName;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public List<BaseModel> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<BaseModel> attributes) {
		this.attributes = attributes;
	}
	public String getChinaName() {
		return chinaName;
	}
	public void setChinaName(String chinaName) {
		this.chinaName = chinaName;
	}

}
