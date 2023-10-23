/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abdullahacar.tableviewgadget.TVG;

import java.util.List; 

/**
 *
 * @author abdullahacar
 */
public class FilterableColumnsUserData {

    List<ColumnFilterMap> list;

    public List<ColumnFilterMap> getList() {
        return list;
    }

    public void setList(List<ColumnFilterMap> list) {
        this.list = list;
    }

    public static class Builder {

        private List<ColumnFilterMap> list;

        private Builder() {
        }

        public Builder list(final List<ColumnFilterMap> value) {
            this.list = value;
            return this;
        }

        public FilterableColumnsUserData build() {
            return new FilterableColumnsUserData(list);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private FilterableColumnsUserData(final List<ColumnFilterMap> list) {
        this.list = list;
    }

}
