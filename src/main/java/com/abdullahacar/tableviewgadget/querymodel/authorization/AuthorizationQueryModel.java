/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.querymodel.authorization;

/**
 *
 * @author ABDULLAH ACAR
 */
public class AuthorizationQueryModel {

    String tokenKey;
    String hashCode;

    public String getTokenKey() {
        return tokenKey;
    }

    public void setTokenKey(String tokenKey) {
        this.tokenKey = tokenKey;
    }

    public String getHashCode() {
        return hashCode;
    }

    public void setHashCode(String hashCode) {
        this.hashCode = hashCode;
    }

    public static class Builder {

        private String tokenKey;
        private String hashCode;

        private Builder() {
        }

        public Builder tokenKey(final String value) {
            this.tokenKey = value;
            return this;
        }

        public Builder hashCode(final String value) {
            this.hashCode = value;
            return this;
        }

        public AuthorizationQueryModel build() {
            return new AuthorizationQueryModel(tokenKey, hashCode);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private AuthorizationQueryModel(final String tokenKey, final String hashCode) {
        this.tokenKey = tokenKey;
        this.hashCode = hashCode;
    }

    
    
}
