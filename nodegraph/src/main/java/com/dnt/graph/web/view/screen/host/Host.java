package com.dnt.graph.web.view.screen.host;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dnt.graph.biz.view.dataobject.HostInfoEntity;
import com.dnt.graph.biz.view.service.HostService;
import com.dnt.graph.web.common.ajax.BaseAjaxAction;
@Controller
@RequestMapping("/host")
public class Host extends BaseAjaxAction{
	@Autowired
	private HostService hostService;
	@RequestMapping("/info.htm")
	public String info(String hostName,Model attributeModel){
		attributeModel.addAttribute("hostname",hostName);
		HostInfoEntity entity=hostService.getHost(hostName);
		attributeModel.addAttribute("host",entity);
        return "/host/info";
	}
	@RequestMapping("/statusChart.htm")
	public String statusChart(String hostName,Model attributeModel){
		String json=hostService.getHostStateData(hostName);
		attributeModel.addAttribute("datajson",json);
		attributeModel.addAttribute("hostname",hostName);
        return "/host/statusChart";
	}
	
}
