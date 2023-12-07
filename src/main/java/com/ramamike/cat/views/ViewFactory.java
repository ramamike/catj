package com.ramamike.cat.views;

import com.ramamike.cat.constants.Constants;
import com.ramamike.cat.utils.Utils;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class ViewFactory {
	private final ObjectProperty<Views> appSelectedMenuItem;
    private AnchorPane capView;
    private AnchorPane topView;
    
    public ViewFactory() {
		this.appSelectedMenuItem = new SimpleObjectProperty<>();
    	
    }
    
    public ObjectProperty<Views> getAppSelectedMenuItem() {
    	return appSelectedMenuItem;
    }
    
    public void showApp() {
	try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/App.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle(Constants.TITLE);
            stage.getIcons().add(new Image(String.valueOf(getClass().getResource("/train_logo.jpg"))));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            Utils.showExceptionDialog(e);
        }
    }
    
    public AnchorPane getCapView() {
	if(capView==null) {
		capView = Utils.getAchorPaneFormFXML(this, Views.CAP); 
	}
	return capView;
    }
    
    public AnchorPane getTopView() {
	if(topView==null) {
		topView = Utils.getAchorPaneFormFXML(this, Views.TOP); 
	}
	return topView;
    }
    
}
