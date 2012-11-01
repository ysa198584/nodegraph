package com.dnt.graph.biz.view.util;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.dnt.graph.biz.view.constants.GraphProperty;

public class GraphDbUtil{

	public static final String db_path=GraphProperty.DB_PATH;
	@Autowired
	private static GraphDatabaseService graphDb;
	
	private  GraphDbUtil(){}
	public static GraphDatabaseService getSingletonGraphDb(){
		 if(graphDb==null){
			 graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(db_path);
			 registerShutdownHook(graphDb);
		 }
		 
		return graphDb;
	}
	public static void clearDb(String dbPath) {
		try {
			FileUtils.deleteRecursively(new File(dbPath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	public static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}
	public static void main(String[] args) {
		getSingletonGraphDb().shutdown();
	}
}
