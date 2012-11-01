package com.dnt.graph.biz.common.file;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;

import com.dnt.graph.biz.common.constants.Errors;
import com.dnt.graph.biz.common.constants.FileDirConstants;
import com.dnt.graph.biz.common.exception.FileOperatorException;
import com.dnt.graph.biz.common.util.DateUtil;
import com.dnt.graph.biz.common.util.Neo4DataSourceUtil;
import com.dnt.graph.biz.view.constants.GraphProperty;
import com.dnt.graph.biz.view.constants.IPHostMap;
import com.dnt.graph.biz.view.constants.RelTypes;
import com.dnt.graph.biz.view.dataobject.NodeEntity;
import com.dnt.graph.biz.view.util.GraphDbUtil;
public class FileInit {
	private static GraphDatabaseService graphDb;
	private static Index<Node> nodeIndex;
	
	public static void  init() throws FileOperatorException {
		try {
			String date = DateUtil.formatDateTimeNoLine(new Date());
			List<String> list = new ArrayList<String>();
			File file=new File(FileDirConstants.SOURCE_FILE_PATH);
			List<String> sourceList = FileUtils.readLines(file);
			Map<String, Integer> relationsNumMap = new HashMap<String, Integer>();
			Map<String, NodeEntity> fromHostObjectMap = new HashMap<String, NodeEntity>();
			Map<String, List<NodeEntity>> relationsMap = new HashMap<String, List<NodeEntity>>();
			for (int i = 0; i < sourceList.size(); i++) {
				String lineData = sourceList.get(i);
				// 过滤空串、同一个IP之间的连接、*号IP
				if (!StringUtils.isEmpty(lineData)	&& !StringUtils.contains(lineData, "127.0.0.1")	&& !StringUtils.contains(lineData, "*")) {
						list.add(ipTransformHost(lineData, date));
				}
			}
			Collections.sort(list);
			for (int i = 0; i < list.size(); i++) {
				String lineData = list.get(i);
				String[] lineArray = lineData.split("\\|");
				String fromHost = lineArray[0];
				String toHost = lineArray[2];
				// 如果两台主机的host相同则不建立关系
				if (!StringUtils.equals(fromHost, toHost)) {
					String fromPort = lineArray[1];
					String toPort = lineArray[3];
					String key = fromHost + toHost;
					if (fromHostObjectMap.containsKey(fromHost)) {
						boolean b = relationsNumMap.containsKey(key);
						if (b) {
							relationsNumMap.put(key,relationsNumMap.get(key) + 1);
						} else {
							relationsNumMap.put(key, 1);
							NodeEntity object = new NodeEntity();
							object.setCreateDate(date);
							object.setHost(toHost);
							object.setPort(toPort);
							relationsMap.get(fromHost).add(object);
						}
					} else {
						relationsNumMap.put(key, 1);
						NodeEntity keyObject = new NodeEntity();
						keyObject.setCreateDate(date);
						keyObject.setHost(fromHost);
						keyObject.setPort(fromPort);
						fromHostObjectMap.put(fromHost, keyObject);

						NodeEntity object = new NodeEntity();
						object.setCreateDate(date);
						object.setHost(toHost);
						object.setPort(toPort);
						List<NodeEntity> objectList = new ArrayList<NodeEntity>();
						objectList.add(object);
						relationsMap.put(fromHost, objectList);
					}
				}
				createFile(lineData, date, fromHost, toHost);
			}
			create(relationsNumMap,fromHostObjectMap,relationsMap);//将节点和关系入graphDB库,将原始进行拆分和组装写入文件中
			/**
			 * 下面不用建索引文件，直接在前台通过拼装文件路径就行了
			 */
//			FileIndexer.createIndex(FileDirConstants.FILE_DIR,FileDirConstants.FILE_INDEX_DIR);//为生成的新的数据文件创建索引
			
		} catch (IOException e) {
			throw new FileOperatorException(Errors.FILE_NOT_FIND_ERROR.getCode(),Errors.FILE_NOT_FIND_ERROR.getMessage(),FileDirConstants.SOURCE_FILE_PATH);
		}
	}
	public static void create(Map<String, Integer> relationsNumMap,Map<String, NodeEntity> fromHostObjectMap,Map<String, List<NodeEntity>> relationsMap){
			Neo4DataSourceUtil.clearDb(GraphProperty.DB_PATH);
			graphDb = GraphDbUtil.getSingletonGraphDb();
			nodeIndex = graphDb.index().forNodes("nodes");
			Transaction tx = graphDb.beginTx();
			try{
				Iterator<String> iterator = fromHostObjectMap.keySet().iterator();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					NodeEntity fromObject = fromHostObjectMap.get(key);
					Node fromNode=createAndIndexNode(fromObject);
					List<NodeEntity> list = relationsMap.get(key);
					for (int i = 0; i < list.size(); i++) {
						NodeEntity object = list.get(i);
						Node toNode = createAndIndexNode(object);
						String relationsNumKey=fromObject.getHost()+object.getHost();
						Integer linksNumber=relationsNumMap.get(relationsNumKey);
						fromNode.createRelationshipTo(toNode, RelTypes.LINK).setProperty(GraphProperty.LINK_NUMBER,String.valueOf(linksNumber));
					}
				}
				tx.success();
			}
			finally{
				tx.finish();
			}
		}

	private static Node createAndIndexNode(NodeEntity object) {
		IndexHits<Node> nodes = nodeIndex.query(GraphProperty.PRIMARY_KEY, object.getHost()+object.getCreateDate());//这里的查询有问题，应该还有一个过滤条件是date.
		Node node=null;
		if (nodes.size() == 0) {
			node = graphDb.createNode();
			node.setProperty(GraphProperty.HOST, object.getHost());
			node.setProperty(GraphProperty.CREATE_DATE, object.getCreateDate());
			node.setProperty(GraphProperty.PRIMARY_KEY, object.getHost()+object.getCreateDate());
			nodeIndex.add(node, GraphProperty.HOST, object.getHost());
			nodeIndex.add(node, GraphProperty.CREATE_DATE, object.getCreateDate());
			nodeIndex.add(node, GraphProperty.PRIMARY_KEY, object.getHost()+object.getCreateDate());
		}
		else{
			node=nodes.getSingle();
		}
		return node;
	}
	private static String ipTransformHost(String s, String date)throws IOException {
		String[] array = s.split("\\|");
		String fromIp = getIP(array[0]);
		String toIp = getIP(array[1]);
		String fromHost = IPHostMap.map.get(fromIp);
		if (null == fromHost) {
			fromHost = GraphProperty.NO_HOST_NAME;
		}
		String toHost = IPHostMap.map.get(toIp);
		if (null == toHost) {
			toHost = GraphProperty.NO_HOST_NAME;
		}
		String temp = array[0];
		if (fromHost.compareTo(toHost) > 0) {
			array[0] = array[1];
			array[1] = temp;

			array[0] = array[0].replace(toIp, toHost);
			array[1] = array[1].replace(fromIp, fromHost);
		} else {
			array[0] = array[0].replace(fromIp, fromHost);
			array[1] = array[1].replace(toIp, toHost);
		}
		StringBuffer buffer = new StringBuffer("");
		for (int i = 0; i < array.length; i++) {
			if (i == array.length - 1) {
				buffer.append(array[i]);
			} else {
				buffer.append(array[i]).append("|");
			}
		}
		return buffer.toString().replaceAll("\\.", "\\|");
	}
	private static String getIP(String s) {
		StringBuffer buffer = new StringBuffer("");
		String array[] = s.split("\\.");
		for (int i = 0; i < array.length - 1; i++) {
			if (i == array.length - 2) {
				buffer.append(array[i]);
			} else {
				buffer.append(array[i]).append(".");
			}
		}
		return buffer.toString();
	}

	private static void createFile(String lineData, String date,String fromHost, String toHost) throws IOException {
		String dir = FileDirConstants.FILE_DIR + "/" + date + "/" + fromHost + "/" + toHost + "/";
		File file = new File(dir);
		if (!file.exists()) {
			file.mkdirs();
		}
		file = new File(dir + FileDirConstants.SOURCE_FILE_NAME);
		if (!file.exists()) {
			file.createNewFile();
		}
		BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
		List<String> list = FileUtils.readLines(file);
		if (list.size() == 0) {
			writer.append(lineData);
		} else {
			writer.append("\r\n" + lineData);
		}
		writer.flush();
		writer.close();
	}
	public static void loadRemoteFile() throws FileOperatorException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpHost httpHost=new HttpHost("10.70.1181.217",8080);
		HttpGet  httpGet=new HttpGet("/c/netstat.txt");
		HttpResponse response=null;
		FileOutputStream output=null;
		InputStream input=null;
		try {
			response=httpClient.execute(httpHost, httpGet);
			HttpEntity entity=response.getEntity();
			File storeFile=new File(FileDirConstants.SOURCE_FILE_PATH);
			if(!storeFile.exists()){
				storeFile.createNewFile();
			}
			output=new FileOutputStream(storeFile);
			input=entity.getContent();
			byte b[]=new byte[1024*1024];
			int read;
			while((read=input.read(b))!=-1){
				output.write(b, 0, read);
			}
			init();
		} 
		catch (IOException e) {
			throw new FileOperatorException(Errors.LOAD_REMOTE_FILE_FAILURE.getCode(),Errors.LOAD_REMOTE_FILE_FAILURE.getMessage());
		}
		finally{
			try{
				if(output!=null){
					output.flush();
					output.close();
				}
				if(input!=null){
					input.close();
				}
			}
			catch(IOException e){
			throw new FileOperatorException(Errors.LOAD_REMOTE_FILE_FAILURE.getCode(),Errors.LOAD_REMOTE_FILE_FAILURE.getMessage());	
			}
			
		}
	}
	public static void main(String[] args) {
		try {
			init();
		} catch (FileOperatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
