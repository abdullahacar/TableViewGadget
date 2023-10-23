package com.abdullahacar.tableviewgadget.cellValueFactories;

import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.util.Callback;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormattedDateValueFactory<EntityType> implements Callback<CellDataFeatures<EntityType, String>, ObservableValue<String>> {
	private String getterName;
	private SimpleDateFormat formatter;

	    private String property;

    public FormattedDateValueFactory() {
    }

//    public FormattedDateValueFactory(@NamedArg("property") String property) {
//        this.property = property;
//    }
	
	public FormattedDateValueFactory(String fieldName, String dateFormatPattern) {
		this.getterName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1, fieldName.length());
		this.formatter = new SimpleDateFormat(dateFormatPattern);
	}

	public ObservableValue<String> call(CellDataFeatures<EntityType, String> features) {
		try {
			EntityType entity = features.getValue();
			Method m = entity.getClass().getMethod(getterName);
			Date date = (Date) m.invoke(entity);
			String formattedDate = formatter.format(date);
			return new SimpleObservableValue<String>(formattedDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }
    
}
