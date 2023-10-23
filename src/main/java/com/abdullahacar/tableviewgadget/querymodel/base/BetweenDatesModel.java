/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.querymodel.base;

import java.util.Date;

/**
 *
 * @author ABDULLAH ACAR
 */
public class BetweenDatesModel {
    
    String fieldName;
    Date startDate, endDate;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public static class Builder {

        private String fieldName;
        private Date startDate;
        private Date endDate;

        private Builder() {
        }

        public Builder fieldName(final String value) {
            this.fieldName = value;
            return this;
        }

        public Builder startDate(final Date value) {
            this.startDate = value;
            return this;
        }

        public Builder endDate(final Date value) {
            this.endDate = value;
            return this;
        }

        public BetweenDatesModel build() {
            return new BetweenDatesModel(fieldName, startDate, endDate);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private BetweenDatesModel(final String fieldName, final Date startDate, final Date endDate) {
        this.fieldName = fieldName;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    
    
    
}
