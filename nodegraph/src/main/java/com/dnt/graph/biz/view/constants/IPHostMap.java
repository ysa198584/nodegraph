package com.dnt.graph.biz.view.constants;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class IPHostMap {
	public static Map<String,String> map=new HashMap<String,String>();
	public static Map<String,String> chinaNameMap=new HashMap<String,String>();
	public static List<String> hostList=new ArrayList<String>();
	private final static Log logger = LogFactory.getLog((IPHostMap.class));
	private static String filePath="E:/textfile/IPhost.txt";
	static	{
		try {
			chinaNameMap.put("zjjacsb1", "充值接口机");
			chinaNameMap.put("zjnacsb2", "充值接口机");
			chinaNameMap.put("zjnacsc1", "充值接口机");
			chinaNameMap.put("zjdacsc2", "充值接口机");
			chinaNameMap.put("pc-zjjwcrm12", "CRM全业务系统Socket服务器");
			chinaNameMap.put("pc-zjdwcrm12", "CRM全业务系统Socket服务器");
			chinaNameMap.put("zjbjcz01", "充值数据库");
			List<String> list = FileUtils.readLines(new File(filePath));
			for (int i = 0; i < list.size(); i++) {
				String lineData = list.get(i);
				if(!StringUtils.isEmpty(lineData)){
					String array[]=lineData.split("\\|");
					hostList.add(array[1]);
					map.put(array[0],array[1]);
				}
			}
		} catch (IOException e) {
			logger.error("read file is error !");
			e.printStackTrace();
		}
//				map.put("10.70.4.111", "xhsdhostA");
//				map.put("10.70.36.51", "xhsdhostB");
//				map.put("10.70.213.211", "xhsdhostB");
//				map.put("10.70.212.22", "xhsdhostC");
//				map.put("10.70.213.190", "xhsdhostC");
//				map.put("10.70.213.133", "xhsdhostC");
//				map.put("10.70.209.168", "xhsdhostC");
//				map.put("10.70.244.166", "xhsdhostA");
//				map.put("10.70.213.167", "xhsdhostA");
//				map.put("10.70.213.182", "xhsdhostA");
//				map.put("192.168.0.2", "xhsdhostE");
//				map.put("192.168.0.1", "xhsdhostE");
//				map.put("192.168.0.3", "xhsdhostE");
//				map.put("192.168.0.4", "xhsdhostE");
//				map.put("192.168.0.6", "xhsdhostE");
//				map.put("192.168.0.10", "xhsdhostF");
//				map.put("192.168.0.11", "xhsdhostF");
//				map.put("192.168.0.109", "xhsdhostF");
		
		
		
			}
}
