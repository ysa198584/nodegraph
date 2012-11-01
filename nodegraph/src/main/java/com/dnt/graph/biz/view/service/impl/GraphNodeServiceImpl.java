package com.dnt.graph.biz.view.service.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;

import com.dnt.graph.biz.common.constants.Errors;
import com.dnt.graph.biz.common.constants.FileDirConstants;
import com.dnt.graph.biz.common.exception.FileOperatorException;
import com.dnt.graph.biz.view.constants.GraphProperty;
import com.dnt.graph.biz.view.constants.IPHostMap;
import com.dnt.graph.biz.view.constants.RelTypes;
import com.dnt.graph.biz.view.model.d3graph.D3DataModel;
import com.dnt.graph.biz.view.model.d3graph.LinkModel;
import com.dnt.graph.biz.view.model.d3graph.NodeModel;
import com.dnt.graph.biz.view.model.neovigatorgraph.BaseModel;
import com.dnt.graph.biz.view.model.neovigatorgraph.Model;
import com.dnt.graph.biz.view.model.neovigatorgraph.NodeRelationship;
import com.dnt.graph.biz.view.model.neovigatorgraph.ViewTableModel;
import com.dnt.graph.biz.view.service.GraphNodeService;
import com.dnt.graph.biz.view.util.GraphDbUtil;

public class GraphNodeServiceImpl implements GraphNodeService {
	private  final GraphDatabaseService graphDb=GraphDbUtil.getSingletonGraphDb();
	private  Index<Node> nodeIndex=graphDb.index().forNodes("nodes");
	
	public D3DataModel findNodesByHost(String date,String host, int depth) {
		String primaryKey=host+date;
    	TraversalDescription traversalDescription = Traversal.description().breadthFirst().relationships( RelTypes.LINK,Direction.BOTH).evaluator( Evaluators.toDepth( depth ) ).uniqueness( Uniqueness.RELATIONSHIP_RECENT );
    	return makeModel(primaryKey,traversalDescription);
	}
	public D3DataModel makeModel(String primaryKey,TraversalDescription traversalDescription){
		Map<Long,Long> map=new HashMap<Long,Long>();
		D3DataModel model=new D3DataModel();
		List<String> nodeIdList=new ArrayList<String>();
		Node querynode= nodeIndex.query(GraphProperty.PRIMARY_KEY,primaryKey).getSingle();
		long i=0;
		long j=0;
		if(null==querynode){
			return model;
		}
    	for ( Relationship relationship : traversalDescription.traverse( querynode ).relationships() )
    	{
    		Node startNode=relationship.getStartNode();
    		String id=String.valueOf(startNode.getId());
    		if(!nodeIdList.contains(id)){
    			map.put(startNode.getId(), j);
    			NodeModel node=new NodeModel();
    			if(i%10==0){
    				node.setFillColor(GraphProperty.EXCEPTION_CIRCLE_FILL_COLOR);
    				node.setFontColor(GraphProperty.EXCEPTION_FONT_COLOR);
    			}
    			else{
    				node.setFillColor(GraphProperty.CIRCLE_FILL_COLOR);
    				node.setFontColor(GraphProperty.FONT_COLOR);
    			}
    			node.setName((String) startNode.getProperty(GraphProperty.HOST));
    			node.setId(j);
    			model.getNodes().add(node);
    			nodeIdList.add(id);
    			j++;
    		}
    		Node endNode=relationship.getEndNode();
    		id=String.valueOf(endNode.getId());
    		if(!nodeIdList.contains(id)){
    			map.put(endNode.getId(), j);
    			NodeModel node=new NodeModel();
    			if(i%10==0){
    				node.setFillColor(GraphProperty.EXCEPTION_CIRCLE_FILL_COLOR);
    				node.setFontColor(GraphProperty.EXCEPTION_FONT_COLOR);
    			}
    			else{
    				node.setFillColor(GraphProperty.CIRCLE_FILL_COLOR);
    				node.setFontColor(GraphProperty.FONT_COLOR);
    			}
    			node.setName((String) endNode.getProperty(GraphProperty.HOST));
    			node.setId(j);
    			model.getNodes().add(node);
    			nodeIdList.add(id);
    			j++;
    		}
    		LinkModel link=new LinkModel();
    		link.setId(i);
    		if(i%10==0){
        		link.setColor(GraphProperty.EXCEPTION_LINE_COLOR);
    		}
    		else{
        		link.setColor(GraphProperty.LINE_COLOR);
    		}
    		link.setFromhost((String) startNode.getProperty(GraphProperty.HOST));
    		link.setTohost((String) endNode.getProperty(GraphProperty.HOST));
    		link.setSource(map.get(startNode.getId()));
    		link.setTarget(map.get(endNode.getId()));
    		link.setType((String) relationship.getProperty(GraphProperty.LINK_NUMBER));
    		link.setLinkNumberColor(GraphProperty.LINK_NUMBER_COLOR);
//    		if(i%8==0){
        		model.getLinks().add(link);
//    		}
    		i++;
    	}
    	return model;
	}
	public NodeRelationship findNodeRelationshipsByHost(String date,long nodeId, int depth) {
		//String primaryKey=hostIp+date;
    	
    	NodeRelationship nodeRelationship=new NodeRelationship();
    	Node querynode= graphDb.getNodeById(nodeId);
		Transaction tx = graphDb.beginTx();
		try{
			nodeRelationship.setId(String.valueOf(querynode.getId()));
			nodeRelationship.setName((String) querynode.getProperty(GraphProperty.HOST)); 
			TraversalDescription traversalDescription = Traversal.description().breadthFirst().relationships( RelTypes.LINK,Direction.OUTGOING).evaluator( Evaluators.toDepth( depth ) ).uniqueness( Uniqueness.RELATIONSHIP_RECENT );
			for ( Relationship relationship : traversalDescription.traverse( querynode ).relationships() )
			{
				BaseModel baseModel=new BaseModel();
				Node endNode=relationship.getEndNode();
				String id=String.valueOf(endNode.getId());
				Model model=new Model();
				model.setName((String) endNode.getProperty(GraphProperty.HOST));
				model.setId(id);
				baseModel.getValues().add(model);
				nodeRelationship.getAttributes().add(baseModel);
			}
			tx.success();
    	}
		finally{
			tx.finish();
		}
		return nodeRelationship;
	}
	public NodeRelationship findNodeRelationshipsByHostLevelOne(Long nodeId,String primaryKey) {
    	NodeRelationship nodeRelationship=new NodeRelationship();
    	Node queryNode= null;
		Transaction tx = graphDb.beginTx();
		try{
			if(null==nodeId){
				queryNode=nodeIndex.query(GraphProperty.PRIMARY_KEY, primaryKey).getSingle();
			}
			else{
				queryNode=graphDb.getNodeById(nodeId);
			}
			nodeRelationship.setId(String.valueOf(queryNode.getId()));
			String host=(String) queryNode.getProperty(GraphProperty.HOST);
			nodeRelationship.setName(host); 
			String chinaName=IPHostMap.chinaNameMap.get((String) queryNode.getProperty(GraphProperty.HOST));
			if(null==chinaName){
				nodeRelationship.setChinaName(host); 
			}
			else{
				nodeRelationship.setChinaName(host+"("+chinaName+")"); 
			}
//			BaseModel baseModel=new BaseModel();
			// 查询由这个节点指向下一层节点的关系
			TraversalDescription traversalDescription = Traversal.description().breadthFirst().relationships( RelTypes.LINK,Direction.OUTGOING).evaluator( Evaluators.toDepth( 1 ) );
			for ( Relationship relationship : traversalDescription.traverse( queryNode ).relationships() )
			{
				BaseModel baseModel=new BaseModel();
				Node endNode=relationship.getEndNode();
				baseModel.setId(relationship.getType().name());
				baseModel.setName((String) relationship.getProperty(GraphProperty.LINK_NUMBER));
				String id=String.valueOf(endNode.getId());
				Model model=new Model();
				model.setName((String) endNode.getProperty(GraphProperty.HOST));
				model.setId(id);
				baseModel.getValues().add(model);
				nodeRelationship.getAttributes().add(baseModel);
			}
			// 查询由上层节点指向本节点的关系
			traversalDescription = Traversal.description().breadthFirst().relationships( RelTypes.LINK,Direction.INCOMING).evaluator( Evaluators.toDepth( 1 ) );
			for ( Relationship relationship : traversalDescription.traverse( queryNode ).relationships() )
			{
				BaseModel baseModel=new BaseModel();
				Node startNode=relationship.getStartNode();
				baseModel.setId(relationship.getType().name());
				baseModel.setName((String) relationship.getProperty(GraphProperty.LINK_NUMBER));
				String id=String.valueOf(startNode.getId());
				Model model=new Model();
				model.setName((String) startNode.getProperty(GraphProperty.HOST));
				model.setId(id);
				baseModel.getValues().add(model);
				nodeRelationship.getAttributes().add(baseModel);
			}
//			nodeRelationship.getAttributes().add(baseModel);
			tx.success();
    	}
		finally{
			tx.finish();
		}
		return nodeRelationship;
	}
	public List<ViewTableModel> getDetailNodePortRelation(String date,String fromHost,String toHost) throws FileOperatorException{
		List<ViewTableModel> list=new ArrayList<ViewTableModel>();
		String dir = FileDirConstants.FILE_DIR + "/" + date + "/" + fromHost + "/" + toHost + "/";
		File fileDir=new File(dir);
		if(!fileDir.exists()){
			throw new FileOperatorException(Errors.FILE_PATH_ERROR.getCode(),Errors.FILE_PATH_ERROR.getMessage(),dir);
		}
		File file = new File(dir + FileDirConstants.SOURCE_FILE_NAME);
		if(!file.exists()){
			throw new FileOperatorException(Errors.FILE_NOT_FIND_ERROR.getCode(),Errors.FILE_NOT_FIND_ERROR.getMessage(),dir + FileDirConstants.SOURCE_FILE_NAME);
		}
		List<String> lineDataList;
		try {
			lineDataList = FileUtils.readLines(file);
		} catch (IOException e) {
			throw new FileOperatorException(Errors.READ_FILE_ERROR.getCode(),Errors.READ_FILE_ERROR.getMessage(),dir + FileDirConstants.SOURCE_FILE_NAME);
		}
		for(String line:lineDataList){
			String array[]=line.split("\\|");
			ViewTableModel model=new ViewTableModel();
			model.setFromHost(array[0]);
			model.setFromPort(array[1]);
			model.setToHost(array[2]);
			model.setToPort(array[3]);
			model.setRelationship(array[4]);
			String chinaName=IPHostMap.chinaNameMap.get(model.getFromHost());
			if(null==chinaName){
				chinaName=model.getFromHost(); 
			}
			model.setChinaName(chinaName);
			list.add(model);
		}
		return list;		
	}
}
