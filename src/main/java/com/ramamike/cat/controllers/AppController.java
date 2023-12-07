package com.ramamike.cat.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.ramamike.cat.constants.Constants;
import com.ramamike.cat.models.Model;
import com.ramamike.cat.views.Views;

import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;

public class AppController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	public BorderPane app_parent;

	@FXML
	void initialize() {
		Model.getInstance().getViewFactory().getAppSelectedMenuItem()
			.addListener((observableValue, oldVal, newVal) -> {
				switch (newVal) {
					case TOP:
						{
							app_parent.setCenter(Model.getInstance().getViewFactory().getTopView());
						}
						break;
					default : {
						app_parent.setCenter(Model.getInstance().getViewFactory().getCapView());
					}
			}
		});
	}
}
