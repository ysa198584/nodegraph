package com.dnt.graph.biz.view.util;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.dnt.graph.biz.view.dataobject.HostInfoEntity;

public class RemoteDataUtil {

	public static HostInfoEntity getHost(String hostName) {
		HostInfoEntity host = new HostInfoEntity();
		host.setApplicationType("zmcc");
		host.setChinaName("充值机");
		host.setFullName("9000/800/rp4440");
		host.setCpuFrequency("999");
		host.setCpuModel("hp");
		host.setCpuNumber(4);
		host.setIp("10.70.195.144");
		host.setMemory(8386560);
		host.setMakeCompany("HP");
		host.setName("appsvr1");
		host.setSerialNumber("PA RISC");
		host.setSystemType("hpux B.11.11");
		/**
		String dataUrl = "";
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(dataUrl);
		try {
			HttpResponse response = httpClient.execute(httppost);
			String content = EntityUtils.toString( response.getEntity() );
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		return host;
	}
	public static String getHostStateData(String hostName) {
		String dataUrl = "http://infoboard.zj.chinamobile.com:8080/nodes/1/grahp_to_json.json";
		String content ="";
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(dataUrl);
			HttpResponse response = httpClient.execute(httppost);
			content = EntityUtils.toString( response.getEntity() );
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	public static void main(String[] args) {
		System.out.println(getHostStateData(""));
	}
}
