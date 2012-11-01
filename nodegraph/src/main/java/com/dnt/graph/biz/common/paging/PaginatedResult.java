/**
 * Project: common.dal File Created at 2011-1-7 $Id$ Copyright 1999-2100 Alibaba.com Corporation Limited. All rights
 * reserved. This software is the confidential and proprietary information of Alibaba Company.
 * ("Confidential Information"). You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into with Alibaba.com.
 */
package com.dnt.graph.biz.common.paging;

import java.util.ArrayList;
import java.util.List;

/**
 * PaginatedResult 分页结果
 * 
 * @author jianjiang.fangjj
 */
public class PaginatedResult<T> {

    private int     count;
    private List<T> data = new ArrayList<T>();

    /**
     * @return the counta
     */
    public int getCount() {
        return count;
    }

    /**
     * @param count the count to set
     */
    public void setCount(int count) {
        this.count = count;
    }

    /**
     * @return the data
     */
    public List<T> getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(List<T> data) {
        this.data = data;
    }
}
