package com.ramamike.cat.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.ramamike.cat.service.SortDUT;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CapController {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private Button sortDut_btn;

	@FXML
	void initialize() {
		sortDut_btn.setOnAction(e -> new SortDUT().sort());
	}
}
