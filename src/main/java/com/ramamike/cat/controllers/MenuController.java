package com.ramamike.cat.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import com.ramamike.cat.constants.Constants;
import com.ramamike.cat.models.Model;
import com.ramamike.cat.views.Views;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MenuController {
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button CAP_btn;

    @FXML
    private Button DDS_btn;

    @FXML
    private Button TOP_btn;

    @FXML
    private Button help_btn;

    @FXML
    void initialize() {
    	addListeners();
    }
    
    private void addListeners() {
    	CAP_btn.setOnAction(e-> onCap());
    	TOP_btn.setOnAction(e-> onTop());
    }
    
    private void onCap() {
    	Model.getInstance().getViewFactory().getAppSelectedMenuItem().set(Views.CAP);
    }
    
    
    private void onTop() {
    	Model.getInstance().getViewFactory().getAppSelectedMenuItem().set(Views.TOP);
    }
}
