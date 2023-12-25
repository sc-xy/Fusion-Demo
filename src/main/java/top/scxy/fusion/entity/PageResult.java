package top.scxy.fusion.entity;

import java.io.Serializable;
import java.util.List;

public class PageResult<T> implements Serializable {
    private Integer pageNum;
    private Integer pageSize;
    private Integer totalCount;
    private List<T> data;
    public PageResult(Integer pageNum, Integer pageSize, Integer totalCount, List<T> data) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.totalCount = totalCount;
        this.data = data;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
