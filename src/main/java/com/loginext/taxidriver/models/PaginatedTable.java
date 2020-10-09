package com.loginext.taxidriver.models;

import java.math.BigInteger;
import java.util.List;

public class PaginatedTable {

    public BigInteger getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigInteger totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }

    BigInteger totalCount;
    Integer pageNumber;
    Integer pageSize;

    List<?> data;

}
