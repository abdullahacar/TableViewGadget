/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abdullahacar.tableviewgadget.querymodel.base;

/**
 *
 * @author abdullahacar
 */

public class SortModel {

    String fieldName;
    int order;

    public static class Builder {

        private String fieldName;
        private int order;

        private Builder() {
        }

        public Builder fieldName(final String value) {
            this.fieldName = value;
            return this;
        }

        public Builder order(final int value) {
            this.order = value;
            return this;
        }

        public SortModel build() {
            return new SortModel(fieldName, order);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private SortModel(final String fieldName, final int order) {
        this.fieldName = fieldName;
        this.order = order;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

}
