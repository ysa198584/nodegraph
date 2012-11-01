package com.dnt.graph.web.view.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dnt.graph.biz.common.exception.FileOperatorException;
import com.dnt.graph.biz.view.model.d3graph.D3DataModel;
import com.dnt.graph.biz.view.model.neovigatorgraph.NodeRelationship;
import com.dnt.graph.biz.view.model.neovigatorgraph.ViewTableModel;
import com.dnt.graph.biz.view.model.query.NodeModel;
import com.dnt.graph.biz.view.service.GraphNodeService;
import com.dnt.graph.biz.view.service.SearchHostService;
import com.dnt.graph.web.common.ajax.BaseAjaxAction;
import com.dnt.graph.web.common.constants.WebConstants;

@Controller
@RequestMapping("/rela")
public class RelationshipAjaxAction extends BaseAjaxAction{
	
	private final static Log logger = LogFactory.getLog((RelationshipAjaxAction.class));
	@Autowired
	private GraphNodeService graphNodeService;
	@Autowired
	private SearchHostService searchHostService;
	@RequestMapping("/queryD3Relation.dox")
	@ResponseBody
	public String  queryD3Relation(NodeModel queryModel,Model attributeModel)  {
		D3DataModel d3model=graphNodeService.findNodesByHost(queryModel.getCreateDate(),queryModel.getHostName(), WebConstants.D3_NODE_DEPTH);
		toJson(d3model);
		return getWriteJson();
	}
	@RequestMapping("/queryRelation.dox")	
	@ResponseBody	
	public String  queryRelation(NodeModel queryModel,Model attributeModel)  {
		String	id=queryModel.getNodeId();
		String hostName=queryModel.getHostName();
		String createDate=queryModel.getCreateDate();
		Long nodeId=null;
		if(null!=id){
		   nodeId=Long.parseLong(id);
		}
		NodeRelationship nodeRelationship=graphNodeService.findNodeRelationshipsByHostLevelOne(nodeId,hostName+createDate);
		toJson(nodeRelationship);
		return getWriteJson();
	}
	@RequestMapping("/queryNodeDetail.dox")	
	@ResponseBody	
	public String  queryNodeDetail(NodeModel queryModel,HttpServletResponse response,Model attributeModel) throws IOException  {
		String fromHost=queryModel.getFromHost();
		String toHost=queryModel.getToHost();
		String queryDate=queryModel.getCreateDate();
		try{
			List<ViewTableModel> list=graphNodeService.getDetailNodePortRelation(queryDate,fromHost, toHost);
			toJson(list);
			return getWriteJson();
		}
		catch(FileOperatorException e){
			e.printStackTrace();
			logger.error(e.getErrorMessage());
			attributeModel.addAttribute("errorMessage",e.getErrorMessage());
			response.sendRedirect(WebConstants.OPERATOR_ERROR_HTML);
		}
		return null;
	}
	@RequestMapping("/autoSearchHost.dox")
	@ResponseBody
	public String  autoSearchHost(HttpServletRequest request,Model attributeModel)  {
		String inputWord=request.getParameter("inputWord");
		List<String> list=searchHostService.queryStart(inputWord);
		toJson(list);
		return getWriteJson();
	}
	
}
