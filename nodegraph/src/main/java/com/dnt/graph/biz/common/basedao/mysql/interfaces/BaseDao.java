package com.dnt.graph.biz.common.basedao.mysql.interfaces;

import java.util.List;

import com.dnt.graph.biz.common.paging.PaginatedResult;
import com.dnt.graph.biz.common.paging.Paginator;

/**
 * BaseDao
 * 
 * @author yangshuan
 * @param <P>
 * @param <V>
 * @param <T>
 */
public interface BaseDao {

    public <T> List<T> getList(String sqlName, Object params);

    public <T> List<T> getList(String sqlName);

    public int count(String sqlName);

    public <T> T getObject(String sqlName);

    public <T> T getObject(String sqlName, Object params);

    public int count(String sqlName, Object params);

    public <T> T insertObject(String sqlName, Object obj);

    public boolean updateObject(String sqlName, Object params);

    public boolean deleteObject(String sqlName, Object parameters);

    public <T> void insertList(String sqlName, List<T> parameters);

    public <T> void updateList(String sqlName, List<T> parameters);

    public <T> void deleteList(String sqlName, List<T> parameters);

    public <P, V> PaginatedResult<P> getPageList(String sqlName, Paginator<V> paginator);

    public <P, V> PaginatedResult<P> getPageList(String sqlName, V parameter, int start, int limit);
}
