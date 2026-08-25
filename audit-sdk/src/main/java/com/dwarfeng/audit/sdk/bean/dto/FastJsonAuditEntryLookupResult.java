package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditEntry;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * FastJson 审计条目查询结果。
 *
 * @author DwArFeng
 * @since 1.0.0-beta
 */
public class FastJsonAuditEntryLookupResult implements Dto {

    private static final long serialVersionUID = 5398031619624302611L;

    public static FastJsonAuditEntryLookupResult of(AuditEntryLookupResult auditEntryLookupResult) {
        if (Objects.isNull(auditEntryLookupResult)) {
            return null;
        } else {
            return new FastJsonAuditEntryLookupResult(
                    auditEntryLookupResult.getCurrentPage(),
                    auditEntryLookupResult.getTotalPages(),
                    auditEntryLookupResult.getRows(),
                    auditEntryLookupResult.getCount(),
                    Optional.ofNullable(auditEntryLookupResult.getData()).map(
                            f -> f.stream().map(FastJsonAuditEntry::of).collect(Collectors.toList())
                    ).orElse(null)
            );
        }
    }

    /**
     * 当前页数，从 0 开始计数。
     */
    @JSONField(name = "current_page", ordinal = 1)
    private int currentPage;

    /**
     * 总页数。
     */
    @JSONField(name = "total_pages", ordinal = 2)
    private int totalPages;

    /**
     * 每页行数。
     */
    @JSONField(name = "rows", ordinal = 3)
    private int rows;

    /**
     * 总记录数。
     */
    @JSONField(name = "count", ordinal = 4)
    private long count;

    /**
     * 当前页数据。
     */
    @JSONField(name = "data", ordinal = 5)
    private List<FastJsonAuditEntry> data;

    public FastJsonAuditEntryLookupResult() {
    }

    public FastJsonAuditEntryLookupResult(
            int currentPage, int totalPages, int rows, long count, List<FastJsonAuditEntry> data
    ) {
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

    public List<FastJsonAuditEntry> getData() {
        return data;
    }

    public void setData(List<FastJsonAuditEntry> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "FastJsonAuditEntryLookupResult{" +
                "currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", rows=" + rows +
                ", count=" + count +
                ", data=" + data +
                '}';
    }
}
