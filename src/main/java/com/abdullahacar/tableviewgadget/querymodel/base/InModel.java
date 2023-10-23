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
public class InModel {

    String fieldName;
    List values;
    boolean id;
    boolean bool;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public List getValues() {
        return values;
    }

    public void setValues(List values) {
        this.values = values;
    }

    public boolean isId() {
        return id;
    }

    public void setId(boolean id) {
        this.id = id;
    }

    public boolean isBool() {
        return bool;
    }

    public void setBool(boolean bool) {
        this.bool = bool;
    }

    public static class Builder {

        private String fieldName;
        private List values;
        private boolean id;
        private boolean bool;

        private Builder() {
        }

        public Builder fieldName(final String value) {
            this.fieldName = value;
            return this;
        }

        public Builder values(final List value) {
            this.values = value;
            return this;
        }

        public Builder id(final boolean value) {
            this.id = value;
            return this;
        }

        public Builder bool(final boolean value) {
            this.bool = value;
            return this;
        }

        public InModel build() {
            return new InModel(fieldName, values, id, bool);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private InModel(final String fieldName, final List values, final boolean id, final boolean bool) {
        this.fieldName = fieldName;
        this.values = values;
        this.id = id;
        this.bool = bool;
    }

    

}
