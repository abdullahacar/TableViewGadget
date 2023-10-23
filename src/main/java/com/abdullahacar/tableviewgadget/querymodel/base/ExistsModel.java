/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abdullahacar.tableviewgadget.querymodel.base;

/**
 *
 * @author abdullahacar
 */
public class ExistsModel {
    
    String fieldName;
    boolean exists;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    public static class Builder {

        private String fieldName;
        private boolean exists;

        private Builder() {
        }

        public Builder fieldName(final String value) {
            this.fieldName = value;
            return this;
        }

        public Builder exists(final boolean value) {
            this.exists = value;
            return this;
        }

        public ExistsModel build() {
            return new ExistsModel(fieldName, exists);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private ExistsModel(final String fieldName, final boolean exists) {
        this.fieldName = fieldName;
        this.exists = exists;
    }
    
    
    
}
