package com.dnt.graph.biz.common.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Filter;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.Scorer;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import com.dnt.graph.biz.common.constants.FileDirConstants;
import com.dnt.graph.biz.view.model.neovigatorgraph.ViewTableModel;

/**
 * 文件索引类
 * 
 * @author ysa
 * 
 */
public class FileIndexer {
	private static IndexWriter indexWriter;
	private static Analyzer luceneAnalyzer = new StandardAnalyzer(
			Version.LUCENE_35);
	private static File indexFile;
	private static ArrayList<Thread> threadList = new ArrayList<Thread>();

	public static void createIndex(String fileRootDir, String indexDir)
			throws IOException {
		indexFile = new File(indexDir);
		List<File> fileList = new ArrayList<File>();
		traversalFile(fileRootDir, fileList);
		indexWriter = getInstance();
		for (int i = 0; i < fileList.size(); i++) {
			fileToWriter(indexWriter, fileList.get(i));
		}
		indexWriter.close();
	}

	public static void traversalFile(String fileRootDir, List<File> fileList)
			throws IOException {
		File file = new File(fileRootDir);
		File[] subFile = file.listFiles();
		for (int i = 0; i < subFile.length; i++) {
			if (subFile[i].isDirectory()) {
				traversalFile(subFile[i].getAbsolutePath(), fileList);
			} else {
				fileList.add(subFile[i]);
			}
		}
	}

	public static void fileToWriter(IndexWriter indexWriter, File file)
			throws IOException {
		List<String> lineList = FileUtils.readLines(file);
		for (String s : lineList) {
			String array[] = s.split("\\|");
			indexWriter
					.addDocument(createDocument(array[0], array[1], array[2]));
		}
	}

	public static Document createDocument(String id, String title,
			String content) {
		Document doc = new Document();
		// Field.Index.NO 表示不索引
		// Field.Index.ANALYZED 表示分词且索引
		// Field.Index.NOT_ANALYZED 表示不分词且索引
		doc.add(new Field("id", id, Field.Store.YES, Field.Index.NOT_ANALYZED));
		doc.add(new Field("title", title, Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("content", content, Field.Store.YES,
				Field.Index.ANALYZED));
		return doc;
	}

	private static IndexWriter getInstance() throws IOException {
		synchronized (threadList) {
			if (indexWriter == null) {
				if (!indexFile.exists()) {
					indexFile.mkdir();
				}
				Directory dir = FSDirectory.open(indexFile);
				if (IndexWriter.isLocked(dir)) {// 判断目录是否已经锁住
					IndexWriter.unlock(dir);
				}
				IndexWriterConfig writerConfig = new IndexWriterConfig(
						Version.LUCENE_35, luceneAnalyzer);
				indexWriter = new IndexWriter(dir, writerConfig);
			}
			if (!threadList.contains(Thread.currentThread())) {
				threadList.add(Thread.currentThread());
			}
			return indexWriter;
		}
	}

	private static ViewTableModel documentToObject(Document document) {

		return null;
	}

	/**
	 * 搜索 IndexSearcher 用来在索引库中进行查询
	 * */

	/**
	 * 搜索
	 * 
	 * @param where
	 *            搜索条件
	 * @param after
	 *            分页时要用到，不分页时为null
	 */
	public void search(String queryString, ScoreDoc after) {
		IndexSearcher isearcher = null;
		Directory directory = null;
		try {
			directory = FSDirectory.open(indexFile);
			// 创建索引搜索器 且只读
			IndexReader reader = IndexReader.open(directory, true);
			isearcher = new IndexSearcher(reader);
			QueryParser parser = new QueryParser(Version.LUCENE_35, null,luceneAnalyzer);
			Query query = parser.parse(queryString);
			
			// lucene3.5深度分页，每页显示10条记录
			TopDocs topDocs = isearcher.searchAfter(after, query, 10);
//			isearcher.search(weight, filter, collector)search(query, 10, Sort.INDEXORDER.setSort(new SortField("id",SortField.STRING)));
			ScoreDoc[] hits = topDocs.scoreDocs;
			for (ScoreDoc scoreDoc : hits) {
				Document hitDoc = isearcher.doc(scoreDoc.doc);
				String id = hitDoc.get("id");
				String title = hitDoc.get("title");
				String content = hitDoc.get("content");
				float score = scoreDoc.score;
				if (title == null) {
					title = hitDoc.get("title");
				}
				if (content == null) {
					content = hitDoc.get("content");
				}
				System.out.println("doc:" + scoreDoc.doc + "    score:" + score	+ "   id:" + id + "   title:" + title + "    content:"	+ content);
			}

		} catch (Exception e) {
			throw new RuntimeException(e);
		} finally {
			try {
				isearcher.close();
				directory.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	// 查询分页
	public List<ViewTableModel> findNowPageInfo(String searchTxt, Integer nowPage,Integer pageSize) {
		// 声明返回值
		List<ViewTableModel> list = new ArrayList<ViewTableModel>();
		// 创建索引的查询对象
		IndexSearcher indexSearcher = null;
		// 创建索引的读取对象
		IndexReader indexReader = null;
		try {
			// 读取的文件
			indexReader = IndexReader.open(FSDirectory.open(indexFile));
			// 查找读取的文件
			indexSearcher = new IndexSearcher(indexReader);
			// 查询的解析器对象
			QueryParser parser = new QueryParser(Version.LUCENE_35, "title",luceneAnalyzer);
			// 查询解析器对象调用解析的方法得到一个查询的对象
			Query query = parser.parse(searchTxt);
			// 执行查询
			TopDocs topDocs = indexSearcher.search(query, (nowPage + 1)	* pageSize);
			for (int i = 0; i < topDocs.totalHits; i++) {
				Document doc = indexSearcher.doc(topDocs.scoreDocs[i].doc);
				doc.get("title");
				ViewTableModel model = new ViewTableModel();
				list.add(model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (indexSearcher != null) {
				try {
					indexSearcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (indexReader != null) {
				try {
					indexReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	public static void main(String[] args) throws Exception {
		// FileInit.init();

	}
}
