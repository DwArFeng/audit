package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.ArrayList;
import java.util.List;

/**
 * 审计条目查询结果。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class AuditEntryLookupResult implements Dto {

    private static final long serialVersionUID = 8936264986635070808L;

    /**
     * 当前页数，从 0 开始计数。
     */
    private int currentPage;

    /**
     * 总页数。
     */
    private int totalPages;

    /**
     * 每页行数。
     */
    private int rows;

    /**
     * 总记录数。
     */
    private long count;

    /**
     * 当前页数据。
     */
    private List<AuditEntry> data = new ArrayList<>();

    public AuditEntryLookupResult() {
    }

    public AuditEntryLookupResult(int currentPage, int totalPages, int rows, long count, List<AuditEntry> data) {
        this.currentPage = currentPage;
        this.totalPages = totalPages;
        this.rows = rows;
        this.count = count;
        this.data = data;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public List<AuditEntry> getData() {
        return data;
    }

    public void setData(List<AuditEntry> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "AuditEntryLookupResult{" +
                "currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", rows=" + rows +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
