package com.dnt.graph.web.common.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.dnt.graph.web.common.constants.WebConstants;

public class GraphHandlerExceptionResolver implements HandlerExceptionResolver{
	private static Logger logger = Logger.getLogger(GraphHandlerExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request,HttpServletResponse response, Object handler, Exception ex) {
		String url = request.getRequestURI();
        String param =request.getQueryString();
        if (param != null &&param.length() > 0) {
                  url += ("?" +param);
        }
        logger.error("异常跳转: "+ url, ex);
        return new ModelAndView(WebConstants.GLOBAL_ERROR_VM_PATH).addObject("exceptiontype","全局异常");
	}

}
