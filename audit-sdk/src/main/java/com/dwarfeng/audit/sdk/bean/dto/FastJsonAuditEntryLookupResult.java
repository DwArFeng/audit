package com.dwarfeng.audit.sdk.bean.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditCategory;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditEntry;
import com.dwarfeng.audit.sdk.bean.entity.FastJsonAuditEntryProperty;
import com.dwarfeng.audit.stack.bean.dto.AuditEntryLookupResult;
import com.dwarfeng.audit.stack.bean.entity.AuditEntryProperty;
import com.dwarfeng.subgrade.stack.bean.dto.Dto;

import java.util.*;
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
                            f -> f.stream().map(Data::of).collect(Collectors.toList())
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
    private List<Data> data;

    public FastJsonAuditEntryLookupResult() {
    }

    public FastJsonAuditEntryLookupResult(
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
        return "FastJsonAuditEntryLookupResult{" +
                "currentPage=" + currentPage +
                ", totalPages=" + totalPages +
                ", rows=" + rows +
                ", count=" + count +
                ", data=" + data +
                '}';
    }

    /**
     * FastJson 审计条目查询详细数据。
     *
     * <p>
     * 该类封装审计条目、所属审计类别以及按属性 ID 索引的审计条目属性。
     *
     * @author DwArFeng
     * @since 1.1.0
     */
    public static class Data implements Dto {

        private static final long serialVersionUID = 819734003421714189L;

        public static Data of(AuditEntryLookupResult.Data data) {
            if (Objects.isNull(data)) {
                return null;
            } else {
                Map<String, FastJsonAuditEntryProperty> propertyMap = null;
                if (Objects.nonNull(data.getAuditEntryPropertyMap())) {
                    propertyMap = new LinkedHashMap<>();
                    for (Map.Entry<String, AuditEntryProperty> entry : data.getAuditEntryPropertyMap().entrySet()) {
                        propertyMap.put(entry.getKey(), FastJsonAuditEntryProperty.of(entry.getValue()));
                    }
                }
                return new Data(
                        FastJsonAuditEntry.of(data.getAuditEntry()),
                        FastJsonAuditCategory.of(data.getAuditCategory()),
                        propertyMap
                );
            }
        }

        @JSONField(name = "audit_entry", ordinal = 1)
        private FastJsonAuditEntry auditEntry;

        @JSONField(name = "audit_category", ordinal = 2)
        private FastJsonAuditCategory auditCategory;

        @JSONField(name = "audit_entry_property_map", ordinal = 3)
        private Map<String, FastJsonAuditEntryProperty> auditEntryPropertyMap;

        public Data() {
        }

        public Data(
                FastJsonAuditEntry auditEntry,
                FastJsonAuditCategory auditCategory,
                Map<String, FastJsonAuditEntryProperty> auditEntryPropertyMap
        ) {
            this.auditEntry = auditEntry;
            this.auditCategory = auditCategory;
            this.auditEntryPropertyMap = auditEntryPropertyMap;
        }

        public FastJsonAuditEntry getAuditEntry() {
            return auditEntry;
        }

        public void setAuditEntry(FastJsonAuditEntry auditEntry) {
            this.auditEntry = auditEntry;
        }

        public FastJsonAuditCategory getAuditCategory() {
            return auditCategory;
        }

        public void setAuditCategory(FastJsonAuditCategory auditCategory) {
            this.auditCategory = auditCategory;
        }

        public Map<String, FastJsonAuditEntryProperty> getAuditEntryPropertyMap() {
            return auditEntryPropertyMap;
        }

        public void setAuditEntryPropertyMap(Map<String, FastJsonAuditEntryProperty> auditEntryPropertyMap) {
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
