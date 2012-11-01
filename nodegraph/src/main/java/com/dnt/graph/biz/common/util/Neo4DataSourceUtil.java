package com.dnt.graph.biz.common.util;

import java.io.File;
import java.io.IOException;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.impl.util.FileUtils;

public class Neo4DataSourceUtil {

	public static void clearDb(String dbPath) {
		try {
			FileUtils.deleteRecursively(new File(dbPath));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void shutDown(GraphDatabaseService graphDb) {
		System.out.println();
		System.out.println("Shutting down database ...");
		graphDb.shutdown();
	}

	public static void registerShutdownHook(final GraphDatabaseService graphDb) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				graphDb.shutdown();
			}
		});
	}

	public static GraphDatabaseService getGraphDb(String dbPath) {
		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(dbPath);
		registerShutdownHook(graphDb);
		return graphDb;
	}
}
