/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abdullahacar.tableviewgadget.querymodel.base;

import java.util.List;

/**
 *
 * @author abdullahacar
 */
public class OrModel {

    List<String> fieldNames;
    List<String> values;

    public List<String> getFieldNames() {
        return fieldNames;
    }

    public List<String> getValues() {
        return values;
    }

    public static class Builder {

        private List<String> fieldNames;
        private List<String> values;

        private Builder() {
        }

        public Builder fieldNames(final List<String> value) {
            this.fieldNames = value;
            return this;
        }

        public Builder values(final List<String> value) {
            this.values = value;
            return this;
        }

        public OrModel build() {
            return new OrModel(fieldNames, values);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private OrModel(final List<String> fieldNames, final List<String> values) {
        this.fieldNames = fieldNames;
        this.values = values;
    }

     
}
