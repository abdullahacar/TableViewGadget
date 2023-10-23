/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.querymodel.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class QueryModel {

    @Getter
    protected Date createdDateStart;
    @Getter
    protected Date createdDateEnd;
    protected int skip, limit;
    protected List<String> fields;
    protected List<String> _ids;
    protected List<SortModel> sort;
    protected String _id, ownerId, projectId, clientId, franchiseId, deliveryType, paymentId;
    protected InModel inModel;
    protected NinModel ninModel;
    protected List<InModel> inModels;
    protected List<NinModel> ninModels;
    protected List<OrModel> orModels;
    protected List<ExistsModel> existsModels;
    protected List<BetweenDatesModel> betweenDatesModels;
    protected BetweenDatesModel betweenDatesModel;
    protected boolean test;
    protected boolean withCount;
    protected boolean authorizationInactive;

    public List<ExistsModel> getExistsModels() {
        if (this.existsModels == null) {
            this.existsModels = new ArrayList<>();
        }
        return existsModels;
    }

    public List<BetweenDatesModel> getBetweenDatesModels() {
        if (this.betweenDatesModels == null) {
            this.betweenDatesModels = new ArrayList<>();
        }
        return betweenDatesModels;
    }

    public void setBetweenDatesModels(List<BetweenDatesModel> betweenDatesModels) {
        this.betweenDatesModels = betweenDatesModels;
    }

    public void setExistsModels(List<ExistsModel> existsModels) {
        this.existsModels = existsModels;
    }

    public List<OrModel> getOrModels() {
        if (this.orModels == null) {
            this.orModels = new ArrayList<>();
        }
        return orModels;
    }

    public boolean isAuthorizationInactive() {
        return authorizationInactive;
    }

    public void setAuthorizationInactive(boolean authorizationInactive) {
        this.authorizationInactive = authorizationInactive;
    }

    public void setOrModels(List<OrModel> orModels) {
        this.orModels = orModels;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public List<String> getFields() {
        return fields;
    }

    public void setFields(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getIds() {
        return _ids;
    }

    public void setIds(List<String> _ids) {
        this._ids = _ids;
    }

    public List<SortModel> getSort() {
        return sort;
    }

    public void setSort(List<SortModel> sort) {
        this.sort = sort;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public InModel getInModel() {
        return inModel;
    }

    public void setInModel(InModel inModel) {
        this.inModel = inModel;
    }

    public NinModel getNinModel() {
        return ninModel;
    }

    public void setNinModel(NinModel ninModel) {
        this.ninModel = ninModel;
    }

    public List<InModel> getInModels() {
        if (this.inModels == null) {
            this.inModels = new ArrayList<>();
        }
        return inModels;
    }

    public void setInModels(List<InModel> inModels) {
        this.inModels = inModels;
    }

    public List<NinModel> getNinModels() {
        if (this.ninModels == null) {
            this.ninModels = new ArrayList<>();
        }
        return ninModels;
    }

    public void setNinModels(List<NinModel> ninModels) {
        this.ninModels = ninModels;
    }

    public BetweenDatesModel getBetweenDatesModel() {
        return betweenDatesModel;
    }

    public void setBetweenDatesModel(BetweenDatesModel betweenDatesModel) {
        this.betweenDatesModel = betweenDatesModel;
    }

    public String getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(String franchiseId) {
        this.franchiseId = franchiseId;
    }

    public boolean isWithCount() {
        return withCount;
    }

    public void setWithCount(boolean withCount) {
        this.withCount = withCount;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public void setCreatedDateStart(Date createdDateStart) {
        this.createdDateStart = createdDateStart;
    }

    public void setCreatedDateEnd(Date createdDateEnd) {
        this.createdDateEnd = createdDateEnd;
    }
}
