package com.dnt.graph.biz.view.model.d3graph;

public class LinkModel {
	private long id;
	private long source;
	private long target;
	private String color;
	private String type;
	private String linkNumberColor; 
	private String fromhost;
	private String tohost;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getSource() {
		return source;
	}
	public void setSource(long source) {
		this.source = source;
	}
	public long getTarget() {
		return target;
	}
	public void setTarget(long target) {
		this.target = target;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getFromhost() {
		return fromhost;
	}
	public void setFromhost(String fromhost) {
		this.fromhost = fromhost;
	}
	public String getTohost() {
		return tohost;
	}
	public void setTohost(String tohost) {
		this.tohost = tohost;
	}
	public String getLinkNumberColor() {
		return linkNumberColor;
	}
	public void setLinkNumberColor(String linkNumberColor) {
		this.linkNumberColor = linkNumberColor;
	}

}
