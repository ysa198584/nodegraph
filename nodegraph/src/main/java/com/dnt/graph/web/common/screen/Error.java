package com.dnt.graph.web.common.screen;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error")
public class Error {
	@RequestMapping("/global.htm")
	public String global(String hostName,Model attributeModel){
		attributeModel.addAttribute("errorinfo","errorglobal");
        return "/error/global";
	}
	@RequestMapping("/noPermission.htm")
	public String noPermission(String hostName,Model attributeModel){
		attributeModel.addAttribute("errorinfo","errornoPermission");
        return "/error/noPermission";
	}
	@RequestMapping("/operateError.htm")
	public String operateErro(String hostName,Model attributeModel){
		attributeModel.addAttribute("errorinfo","erroroperateError");
        return "/error/operateError";
	}
}
