/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.abdullahacar.tableviewgadget.TVG;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/**
 *
 * @author abdullahacar
 */
public class ButtonContainer {

    Button hideButton;
    Button selectAllButton;
    Button clearButton;
    HBox container;

    public Button getHideButton() {
        return hideButton;
    }

    public void setHideButton(Button hideButton) {
        this.hideButton = hideButton;
    }

    public Button getSelectAllButton() {
        return selectAllButton;
    }

    public void setSelectAllButton(Button selectAllButton) {
        this.selectAllButton = selectAllButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public void setClearButton(Button clearButton) {
        this.clearButton = clearButton;
    }

    public HBox getContainer() {
        return container;
    }

    public void setContainer(HBox container) {
        this.container = container;
    }

    public static class Builder {

        private Button hideButton;
        private Button selectAllButton;
        private Button clearButton;
        private HBox container;

        private Builder() {
        }

        public Builder hideButton(final Button value) {
            this.hideButton = value;
            return this;
        }

        public Builder selectAllButton(final Button value) {
            this.selectAllButton = value;
            return this;
        }

        public Builder clearButton(final Button value) {
            this.clearButton = value;
            return this;
        }

        public Builder container(final HBox value) {
            this.container = value;
            return this;
        }

        public ButtonContainer build() {
            return new ButtonContainer(hideButton, selectAllButton, clearButton, container);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private ButtonContainer(final Button hideButton, final Button selectAllButton, final Button clearButton, final HBox container) {
        this.hideButton = hideButton;
        this.selectAllButton = selectAllButton;
        this.clearButton = clearButton;
        this.container = container;
    }

    
    
}
