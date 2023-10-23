/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abdullahacar.tableviewgadget.TVG;

import javafx.beans.property.BooleanProperty;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;

/**
 *
 * @author abdullahacar
 */
public class TVGBuilderParameter {

    ScrollPane scrollPane;
    TableView tableView;
    ITVGFilterHandler filterhandler;
    FilterableColumnsUserData userData;
    BooleanProperty triggerFilterProperty;

    public TableView getTableView() {
        return tableView;
    }

    public void setTableView(TableView tableView) {
        this.tableView = tableView;
    }

    public ITVGFilterHandler getFilterhandler() {
        return filterhandler;
    }

    public void setFilterhandler(TVGFilterHandler filterhandler) {
        this.filterhandler = filterhandler;
    }

    public FilterableColumnsUserData getUserData() {
        return userData;
    }

    public void setUserData(FilterableColumnsUserData userData) {
        this.userData = userData;
    }

    public BooleanProperty getTriggerFilterProperty() {
        return triggerFilterProperty;
    }

    public void setTriggerFilterProperty(BooleanProperty triggerFilterProperty) {
        this.triggerFilterProperty = triggerFilterProperty;
    }

    public ScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public static class Builder {

        private ScrollPane scrollPane;
        private TableView tableView;
        private ITVGFilterHandler filterhandler;
        private FilterableColumnsUserData userData;
        private BooleanProperty triggerFilterProperty;

        private Builder() {
        }

        public Builder scrollPane(final ScrollPane value) {
            this.scrollPane = value;
            return this;
        }

        public Builder tableView(final TableView value) {
            this.tableView = value;
            return this;
        }

        public Builder filterhandler(final ITVGFilterHandler value) {
            this.filterhandler = value;
            return this;
        }

        public Builder userData(final FilterableColumnsUserData value) {
            this.userData = value;
            return this;
        }

        public Builder triggerFilterProperty(final BooleanProperty value) {
            this.triggerFilterProperty = value;
            return this;
        }

        public TVGBuilderParameter build() {
            return new TVGBuilderParameter(tableView, filterhandler, userData, triggerFilterProperty, scrollPane);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private TVGBuilderParameter(final TableView tableView, final ITVGFilterHandler filterhandler, final FilterableColumnsUserData userData, final BooleanProperty triggerFilterProperty, final ScrollPane scrollPane) {
        this.tableView = tableView;
        this.filterhandler = filterhandler;
        this.userData = userData;
        this.triggerFilterProperty = triggerFilterProperty;
        this.scrollPane = scrollPane;
    }

}
