package com.dnt.graph.biz.view.model.d3graph;

import java.util.ArrayList;
import java.util.List;

public class D3DataModel {
	private List<NodeModel> nodes=new ArrayList<NodeModel>();
	private List<LinkModel> links=new ArrayList<LinkModel>();
	public List<NodeModel> getNodes() {
		return nodes;
	}
	public void setNodes(List<NodeModel> nodes) {
		this.nodes = nodes;
	}
	public List<LinkModel> getLinks() {
		return links;
	}
	public void setLinks(List<LinkModel> links) {
		this.links = links;
	}
}
