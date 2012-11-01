package com.dnt.graph.web.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class SecurityInteceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,Object arg2) throws Exception {
		boolean isPass=true;
		/**
		 * 这里做权限校验
		 String url=request.getRequestURI();
		if(StringUtils.equals(url, "/graph/host/info.htm")){
			response.sendRedirect(WebConstants.NO_PERMISSION_PATH);
		}
		else{
			isPass=true;
		}
		*/
		return isPass;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,Object arg2, ModelAndView view) throws Exception {
		/**
		 * 这里在最后优化的时候，对于AJAX的请求可以放在这里来做
		String url=request.getRequestURI();
		if(url.endsWith(".dox")){
			response.setContentType("text/html;charset=utf-8");
			 PrintWriter pw = null;
		        try {
		        	Object result=view.getModelMap().get("result");
		        	AjaxWritterModel<Object> model=new AjaxWritterModel<Object>();
		        	model.setData(result);
		        	pw = response.getWriter();
		            String json = JSONUtil.bean2json(model);
		            pw.write(json);
		        } catch (IOException e) {
		            throw new RuntimeException(e);
		        } finally {
		            if (pw != null) {
		                pw.close();
		            }
		        }
		}
		 */
	}

	@Override
	public void afterCompletion(HttpServletRequest request,	HttpServletResponse response, Object arg2, Exception arg3)throws Exception {

	}
}
