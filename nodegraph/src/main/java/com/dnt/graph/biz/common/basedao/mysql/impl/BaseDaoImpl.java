package com.dnt.graph.biz.common.basedao.mysql.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.dnt.graph.biz.common.basedao.mysql.interfaces.BaseDao;
import com.dnt.graph.biz.common.paging.PaginatedResult;
import com.dnt.graph.biz.common.paging.Paginator;
import com.ibatis.sqlmap.client.SqlMapExecutor;

/**
 * BaseDaoImpl
 * 
 * @author ting.weit 2011-1-20
 * @alitalkID weitingjava
 */
public class BaseDaoImpl extends SqlMapClientDaoSupport implements BaseDao {

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getList(String sqlName, Object params) {
        List<T> list = this.getSqlMapClientTemplate().queryForList(sqlName, params);
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> getList(String sqlName) {
        List<T> list = this.getSqlMapClientTemplate().queryForList(sqlName);
        return list;
    }

    @Override
    public int count(String sqlName) {
        return (Integer) this.getSqlMapClientTemplate().queryForObject(sqlName);
    }

    @Override
    public int count(String sqlName, Object params) {
        return (Integer) this.getSqlMapClientTemplate().queryForObject(sqlName, params);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getObject(String sqlName) {
        T obj = (T) this.getSqlMapClientTemplate().queryForObject(sqlName);
        return obj;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T getObject(String sqlName, Object params) {
        T obj = (T) this.getSqlMapClientTemplate().queryForObject(sqlName, params);
        return obj;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T insertObject(String sqlName, Object obj) {
        return (T) this.getSqlMapClientTemplate().insert(sqlName, obj);
    }

    @Override
    public boolean updateObject(String sqlName, Object params) {
        int result = this.getSqlMapClientTemplate().update(sqlName, params);
        return result > 0;
    }

    @Override
    public boolean deleteObject(String sqlName, Object parameters) {
        int result = this.getSqlMapClientTemplate().delete(sqlName, parameters);
        return result > 0;
    }

    @Override
    public <T> void insertList(final String sqlName, final List<T> parameters) {
        this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

            @Override
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                if (parameters == null || parameters.isEmpty()) {
                    throw new IllegalArgumentException("parameters can not be empty");
                }
                executor.startBatch();

                for (Object parameter : parameters) {
                    executor.insert(sqlName, parameter);
                }

                executor.executeBatch();
                return null;
            }
        });
    }

    @Override
    public <T> void updateList(final String sqlName, final List<T> parameters) {
        this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

            @Override
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                if (parameters == null || parameters.isEmpty()) {
                    throw new IllegalArgumentException("parameters can not be empty");
                }
                executor.startBatch();

                for (Object parameter : parameters) {
                    executor.update(sqlName, parameter);
                }

                executor.executeBatch();
                return null;
            }
        });
    }

    @Override
    public <T> void deleteList(final String sqlName, final List<T> parameters) {
        this.getSqlMapClientTemplate().execute(new SqlMapClientCallback() {

            @Override
            public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
                if (parameters == null || parameters.isEmpty()) {
                    throw new IllegalArgumentException("parameters can not be empty");
                }
                executor.startBatch();

                for (Object parameter : parameters) {
                    executor.delete(sqlName, parameter);
                }

                executor.executeBatch();
                return null;
            }
        });
    }

    @Override
    public <P, V> PaginatedResult<P> getPageList(String sqlName, Paginator<V> paginator) {
        int count = this.count(sqlName + "Count", paginator);
        List<P> list = this.getList(sqlName, paginator);
        PaginatedResult<P> pageResult = new PaginatedResult<P>();
        pageResult.setData(list);
        pageResult.setCount(count);
        return pageResult;
    }

    @Override
    public <P, V> PaginatedResult<P> getPageList(String sqlName, V parameter, int start, int limit) {
        Paginator<V> extPaginator = new Paginator<V>(start, limit);
        return this.getPageList(sqlName, extPaginator);
    }
}
