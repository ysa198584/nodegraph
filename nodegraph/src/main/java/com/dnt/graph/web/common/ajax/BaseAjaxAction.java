package com.dnt.graph.web.common.ajax;

import com.dnt.graph.biz.common.util.JSONUtil;
import com.dnt.graph.web.common.model.AjaxWritterModel;

public class BaseAjaxAction {
	private AjaxWritterModel<Object> ajaxModel=new AjaxWritterModel<Object>();
	private String writeJson="";
	public AjaxWritterModel<Object> getAjaxModel() {
		return ajaxModel;
	}
	public void setAjaxModel(AjaxWritterModel<Object> ajaxModel) {
		this.ajaxModel = ajaxModel;
	}
	public String getWriteJson() {
		return writeJson;
	}
	public void setWriteJson(String writeJson) {
		this.writeJson = writeJson;
	}
	public  void toJson(Object obj){
		ajaxModel.setData(obj);
		writeJson=JSONUtil.bean2json(ajaxModel);
	}
}
