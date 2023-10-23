/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.querymodel.base;

import java.util.List;

/**
 *
 * @author ABDULLAH ACAR
 */
public class NinModel {

    String fieldName;
    List<String> values;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public static class Builder {

        private String fieldName;
        private List<String> values;

        private Builder() {
        }

        public Builder fieldName(final String value) {
            this.fieldName = value;
            return this;
        }

        public Builder values(final List<String> value) {
            this.values = value;
            return this;
        }

        public NinModel build() {
            return new NinModel(fieldName, values);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private NinModel(final String fieldName, final List<String> values) {
        this.fieldName = fieldName;
        this.values = values;
    }

}
