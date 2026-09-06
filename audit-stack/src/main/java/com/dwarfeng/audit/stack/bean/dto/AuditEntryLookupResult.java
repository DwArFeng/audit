package com.dwarfeng.audit.stack.bean.dto;

import com.dwarfeng.audit.stack.bean.entity.AuditCategory;
import com.dwarfeng.audit.stack.bean.entity.AuditEntry;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.List;
import java.util.Map;

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
    private List<Data> data;

    public AuditEntryLookupResult() {
    }

    public AuditEntryLookupResult(
            int currentPage, int totalPages, int rows, long count, List<Data> data
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

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
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

    /**
     * 审计条目查询详细数据。
     *
     * <p>
     * 该类封装审计条目、所属审计类别以及按属性 ID 索引的审计条目属性。
     *
     * @author DwArFeng
     * @since 1.1.0
     */
    public static class Data implements Dto {

        private static final long serialVersionUID = -4721631729630383300L;

        private AuditEntry auditEntry;
        private AuditCategory auditCategory;
        private Map<String, AuditEntryProperty> auditEntryPropertyMap;

        public Data() {
        }

        public Data(
                AuditEntry auditEntry,
                AuditCategory auditCategory,
                Map<String, AuditEntryProperty> auditEntryPropertyMap
        ) {
            this.auditEntry = auditEntry;
            this.auditCategory = auditCategory;
            this.auditEntryPropertyMap = auditEntryPropertyMap;
        }

        public AuditEntry getAuditEntry() {
            return auditEntry;
        }

        public void setAuditEntry(AuditEntry auditEntry) {
            this.auditEntry = auditEntry;
        }

        public AuditCategory getAuditCategory() {
            return auditCategory;
        }

        public void setAuditCategory(AuditCategory auditCategory) {
            this.auditCategory = auditCategory;
        }

        public Map<String, AuditEntryProperty> getAuditEntryPropertyMap() {
            return auditEntryPropertyMap;
        }

        public void setAuditEntryPropertyMap(Map<String, AuditEntryProperty> auditEntryPropertyMap) {
            this.auditEntryPropertyMap = auditEntryPropertyMap;
        }

        @Override
        public String toString() {
            return "Data{" +
                    "auditEntry=" + auditEntry +
                    ", auditCategory=" + auditCategory +
                    ", auditEntryPropertyMap=" + auditEntryPropertyMap +
                    '}';
        }
    }
}
