package com.dnt.graph.biz.view.model.neovigatorgraph;

import java.util.ArrayList;
import java.util.List;

public class BaseModel {
	private String id;
	private String name;
	private List<Model> values=new ArrayList<Model>();
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
	public List<Model> getValues() {
		return values;
	}
	public void setValues(List<Model> values) {
		this.values = values;
	}
	
}
