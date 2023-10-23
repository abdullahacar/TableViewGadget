/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.abdullahacar.tableviewgadget.cellValueFactories;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;
import org.apache.commons.beanutils.NestedNullException;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nehir
 */
public class FormattedPropertyValueFactory implements Callback<CellDataFeatures<Object, Object>, ObservableValue<Object>> {

    private String property;
    private String format = "";

    public FormattedPropertyValueFactory() {
    }

    public ObservableValue<Object> call(CellDataFeatures<Object, Object> param) {
        Object value = param.getValue();
        Object objectValue = null;
        try {
            objectValue = getNestedPropertyIfExists(value, property);
            if (objectValue instanceof Date) {
                if (!format.isEmpty()) {
                    Date date = (Date) objectValue;
                    objectValue = new SimpleDateFormat(format).format(date);
                }
            } else if (objectValue instanceof Double) {
                if (!format.isEmpty()) {
                    Double doubleNumber = (Double) objectValue;
                    objectValue = new DecimalFormat(format, new DecimalFormatSymbols(Locale.US)).format(doubleNumber);
                }
            } else if (objectValue instanceof Integer) {
                if (!format.isEmpty()) {
                    int doubleNumber = (int) objectValue;
                    objectValue = new DecimalFormat(format).format(doubleNumber);
                }
            }

        } catch (Exception e) {
            System.out.println("Error in NestedPropertyValueFactory");
        }
        return new SimpleObjectProperty(objectValue);
    }

    public static Object getNestedPropertyIfExists(Object bean, String name) {
        try {
            return PropertyUtils.getNestedProperty(bean, name);
        } catch (NestedNullException e) {
            // Do nothing
        } catch (IllegalAccessException ex) {
            Logger.getLogger(FormattedPropertyValueFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(FormattedPropertyValueFactory.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(FormattedPropertyValueFactory.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
