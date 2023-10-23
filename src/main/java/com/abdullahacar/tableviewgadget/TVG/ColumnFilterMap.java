/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.TVG;

import javafx.scene.control.TableColumn;

import java.util.List;
import java.util.Map;


public class ColumnFilterMap {

    String key;
    TableColumn column;
    FilterType type;
    List comboList;
    List<String> queryFields;
    Object defaultValue; 
    Map<Object, String> idMap;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public TableColumn getColumn() {
        return column;
    }

    public void setColumn(TableColumn column) {
        this.column = column;
    }

    public FilterType getType() {
        return type;
    }

    public void setType(FilterType type) {
        this.type = type;
    }

    public List getComboList() {
        return comboList;
    }

    public void setComboList(List comboList) {
        this.comboList = comboList;
    }

    public List<String> getQueryFields() {
        return queryFields;
    }

    public void setQueryFields(List<String> queryFields) {
        this.queryFields = queryFields;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Map<Object, String> getIdMap() {
        return idMap;
    }

    public void setIdMap(Map<Object, String> idMap) {
        this.idMap = idMap;
    }

    public static class Builder {

        private String key;
        private TableColumn column;
        private FilterType type;
        private List comboList;
        private List<String> queryFields;
        private Object defaultValue;
        private Map<Object,String> idMap;

        private Builder() {
        }

        public Builder key(final String value) {
            this.key = value;
            return this;
        }

        public Builder column(final TableColumn value) {
            this.column = value;
            return this;
        }

        public Builder type(final FilterType value) {
            this.type = value;
            return this;
        }

        public Builder comboList(final List value) {
            this.comboList = value;
            return this;
        }

        public Builder queryFields(final List<String> value) {
            this.queryFields = value;
            return this;
        }

        public Builder defaultValue(final Object value) {
            this.defaultValue = value;
            return this;
        }

        public Builder idMap(final Map<Object,String> value) {
            this.idMap = value;
            return this;
        }

        public ColumnFilterMap build() {
            return new ColumnFilterMap(key, column, type, comboList, queryFields, defaultValue, idMap);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private ColumnFilterMap(final String key, final TableColumn column, final FilterType type, final List comboList, final List<String> queryFields, final Object defaultValue, final Map<Object, String> idMap) {
        this.key = key;
        this.column = column;
        this.type = type;
        this.comboList = comboList;
        this.queryFields = queryFields;
        this.defaultValue = defaultValue;
        this.idMap = idMap;
    }

    
    
}
