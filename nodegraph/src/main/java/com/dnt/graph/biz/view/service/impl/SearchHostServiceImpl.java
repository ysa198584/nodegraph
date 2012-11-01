package com.dnt.graph.biz.view.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.dnt.graph.biz.view.constants.IPHostMap;
import com.dnt.graph.biz.view.service.SearchHostService;

public class SearchHostServiceImpl implements SearchHostService{
	public  List<String> queryStart(String startWord){
		List<String> soruceList=IPHostMap.hostList;
		List<String> list=new ArrayList<String>();
		for(int i=0;i<soruceList.size();i++){
			if(soruceList.get(i).startsWith(startWord)){
				list.add(soruceList.get(i));
			}
		}
		return list;
	}
}
