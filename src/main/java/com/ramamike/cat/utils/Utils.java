package com.ramamike.cat.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ramamike.cat.constants.Constants;
import com.ramamike.cat.models.config.Configuration;
import com.ramamike.cat.service.SortDUT;
import com.ramamike.cat.views.Views;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Utils {
	
	private static final Logger log = LoggerFactory.getLogger(SortDUT.class);

	public static void showExceptionDialog(Throwable throwable) {

		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Dialog");
		alert.setHeaderText("Thrown Exception");
		alert.setContentText("DevLaunch has thrown an exception.");

		StringWriter sw = new StringWriter();
		PrintWriter pw = new PrintWriter(sw);
		throwable.printStackTrace(pw);
		String exceptionText = sw.toString();

		Label label = new Label("The exception was:");

		TextArea textArea = new TextArea(exceptionText);
		textArea.setEditable(false);
		textArea.setWrapText(true);

		textArea.setMaxWidth(Double.MAX_VALUE);
		textArea.setMaxHeight(Double.MAX_VALUE);
		GridPane.setVgrow(textArea, Priority.ALWAYS);
		GridPane.setHgrow(textArea, Priority.ALWAYS);

		GridPane expContent = new GridPane();
		expContent.setMaxWidth(Double.MAX_VALUE);
		expContent.add(label, 0, 0);
		expContent.add(textArea, 0, 1);

		alert.getDialogPane().setExpandableContent(expContent);

		alert.showAndWait();
	}

	public static void showAlert(String message) {

		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Dialog");
		alert.setHeaderText("Information");
		alert.setContentText(message);
		alert.showAndWait();
	}

	public static AnchorPane getAchorPaneFormFXML(Object thisClass, Enum<? extends Views> viewName) {
		AnchorPane anchorPane = new AnchorPane();
		try {
			anchorPane = FXMLLoader.load(thisClass.getClass().getResource("/fxml/" + viewName + ".fxml"));
		} catch (IOException | NullPointerException e) {
			showExceptionDialog(e);
		}
		return anchorPane;
	}

	public static File chooseFile(String initialDir) {

		Stage stage = new Stage();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setInitialDirectory(new File(initialDir));

		return fileChooser.showOpenDialog(stage);

	}

	public static List<String> getData(File file) {
		List<String> data = new ArrayList<>();

		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			showExceptionDialog(e);
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(fis));
		try {
			String line;
			while ((line = br.readLine()) != null) {
				data.add(line.trim());
			}
		} catch (IOException e) {
			showExceptionDialog(e);
		}
		return data;
	}

	public static void saveData(String data, String name) {

		FileOutputStream outputStream = null;
		try {
			outputStream = new FileOutputStream(name);
		} catch (FileNotFoundException e) {
			log.error(e.getMessage());
		}

		byte[] strToBytes = data.getBytes();
		try {
			outputStream.write(strToBytes);
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				outputStream.close();
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}

	}

	public static <R> R parse(List<String> rawData, String start, String end, R r, Function<String, R> f) {
		int size = rawData.size();
		for (int i = 0; i <= size; i++) {
			String row = rawData.get(i);
			if (row.contains(start)) {
				i++;
				while (i <= size && !(row = rawData.get(i)).contains(end)) {
					f.apply(row);
					i++;
				}
				return r;
			}
		}
		return r;
	}

}
