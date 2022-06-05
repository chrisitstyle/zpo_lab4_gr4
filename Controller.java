package application;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {

	@FXML
	TextField txtField;

	@FXML
	VBox objectParams;

	@FXML
	TextArea textConsole;

	Field[] Fields;
	List<Object> listOfObjects;
	Class<?> cl;
	Object obj;
	Object value;

	@FXML
	void initialize() {

		listOfObjects = new ArrayList<>();
	}

	/*
	Metoda tworz¹ca obiekt
	 */
	@FXML
	void createObject() {
		textConsole.clear();
		String pathName = txtField.getText();

		try {
			cl = Class.forName(pathName);
			Fields = cl.getDeclaredFields();
			Constructor<?> cons = cl.getDeclaredConstructors()[0];
			cons.setAccessible(true);
			obj = cons.newInstance();
			listOfObjects.add(obj);
			objectParams.getChildren().clear();
			for (Field field : cl.getDeclaredFields()) {
				HBox item = new HBox();
				String paramName = field.getName().toString();
				//ustawienie dostêpu
				field.setAccessible(true);


				if (paramName.equals("text")) {
					TextArea textBox = new TextArea();
					textBox.setMaxWidth(300);
					textBox.setMinWidth(300);
					textBox.setId(paramName);
					String value;
					String getter = "get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                    Method m = obj.getClass().getDeclaredMethod(getter);
                    value = m.invoke(obj).toString();
					textBox.setText(value);
					item.getChildren().add(textBox);
				} else {
					TextField textBox = new TextField();
					textBox.setId(paramName);
					textBox.setMaxWidth(300);
					textBox.setMinWidth(300);
					String value;
					String getter = "get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                    Method m = obj.getClass().getDeclaredMethod(getter);
                    value = m.invoke(obj).toString();
					textBox.setText(value);
					item.getChildren().add(textBox);
				}
				Label label = new Label();
				label.setText("<- " + field.getName());
				item.getChildren().add(label);
				objectParams.getChildren().add(item);
			}

		} catch (ClassNotFoundException e) {
			displayConsoleMessage("Nie ma klasy o takiej nazwie: "+pathName+"...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	ZAPISYWANIE ZMIAN
	 */
	@FXML
	void saveChanges() {
		textConsole.clear();
		for (Field field : Fields) {
			if (field.getName().toString().equals("text")) {
				TextArea item = (TextArea) objectParams.lookup("#" + field.getName().toString());
				value = item.getText();
				try {
					String setter = "set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                    Method m = obj.getClass().getDeclaredMethod(setter,field.getType());
                    m.invoke(obj, value);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {
				TextField item = (TextField) objectParams.lookup("#" + field.getName().toString());
				boolean correct = true;
				try {
					switch(field.getType().toString()) {
					case "class java.lang.String":
						value = item.getText();
						break;
					case "class java.lang.Integer":
						value = Integer.valueOf(item.getText());
						break;
					case "int":
						value = Integer.parseInt(item.getText());
						break;
					case "short":
						value = Short.parseShort(item.getText());
						break;
					case "byte":
						value = Byte.parseByte(item.getText());
						break;
					case "long":
						value = Long.parseLong(item.getText());
						break;
					case "float":
						value = Float.parseFloat(item.getText());
						break;
					case "double":
						value = Double.parseDouble(item.getText());
						break;
					case "boolean":
						value = Boolean.parseBoolean(item.getText());
						break;
					case "char":
						value = item.getText().charAt(0);
						break;
					}
				} catch (Exception e) {
					displayConsoleMessage("Typu " + field.getName().toString() + " nie mo¿na zmieniæ");
					correct = false;
				}
				if (correct) {
					try {
						String setter = "set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
	                    Method m = obj.getClass().getDeclaredMethod(setter,field.getType());
	                    m.invoke(obj, value);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		displayConsoleMessage("Obiekt " + obj.getClass().getName().toString() + " pomyœlnie zosta³ zmodyfikowany");
		displayObject(obj);
	}

	void displayConsoleMessage(String msg) {

		textConsole.appendText(msg + "\n");
	}
/*
Metoda, która wyœwietla obiekt
 */
	void displayObject(Object obj) {
		textConsole.appendText(obj.getClass().getSimpleName() + " Obiekt \n");

		for (Field item : obj.getClass().getDeclaredFields()) {

			try {
				textConsole.appendText(item.getName().toString() + " = " + item.get(obj) + "\n");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}