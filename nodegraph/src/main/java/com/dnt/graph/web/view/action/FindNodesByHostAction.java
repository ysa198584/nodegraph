package com.dnt.graph.web.view.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import com.dnt.graph.biz.view.service.GraphNodeService;

public class FindNodesByHostAction implements Controller{
	@Autowired
	private GraphNodeService graphNodeService;
	
	private String helloWorld;      //该属性用于获取配置文件中的helloWorld属性
	private String viewPage; 
	@Override
	public ModelAndView handleRequest(HttpServletRequest request,HttpServletResponse response){
		Map<String,String> model = new HashMap<String,String>();
        model.put("helloWorld", getHelloWorld());    
        return new ModelAndView(getViewPage(),model);
	}
	public String getHelloWorld() {
		return helloWorld;
	}
	public void setHelloWorld(String helloWorld) {
		this.helloWorld = helloWorld;
	}
	public String getViewPage() {
		return viewPage;
	}
	public void setViewPage(String viewPage) {
		this.viewPage = viewPage;
	}
	
}
