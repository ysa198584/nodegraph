package com.dnt.graph.biz.view.service;

import java.util.List;

import com.dnt.graph.biz.common.exception.FileOperatorException;
import com.dnt.graph.biz.view.model.d3graph.D3DataModel;
import com.dnt.graph.biz.view.model.neovigatorgraph.NodeRelationship;
import com.dnt.graph.biz.view.model.neovigatorgraph.ViewTableModel;

public interface GraphNodeService {

	/**
	 * 
	 * @param date
	 *            时间
	 * @param hostIp
	 *            主机IP
	 * @param depth
	 *            指定主机IP的网络关系的层数
	 * @return
	 */
	public D3DataModel findNodesByHost(String date, String host, int depth);

	/**
	 * 
	 * @param date
	 *            时间
	 * @param hostIp
	 *            主机IP
	 * @param depth
	 *            指定主机IP的网络关系的层数
	 * @return
	 */
	public NodeRelationship findNodeRelationshipsByHost(String date,
			long nodeId, int depth);

	/**
	 * 这里是只查询某个节点的下一层的节点关系，包括指向它的节点和它指向的节点关系,不会查询本节点周围节点之间的关系。
	 * 
	 * @param nodeId
	 * @return
	 */
	public NodeRelationship findNodeRelationshipsByHostLevelOne(Long nodeId,
			String primaryKey);

	/**
	 * 这里是根据两个主机HOST来查询它们间的详细端口情况
	 * 
	 * @param fromHost
	 * @param toHost
	 * @return
	 */
	public List<ViewTableModel> getDetailNodePortRelation(String date,String fromHost,String toHost)throws FileOperatorException;

}
