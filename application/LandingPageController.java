package application;

import javafx.event.ActionEvent;

import java.io.IOException;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;

//This class is in charge of switching to the LogInScreen.fxml or ChoosePrice.fxml depending on what button the user presses
public class LandingPageController {

	// changes to login screen on login button press, with the previous scene set as
	// LandingPage.fxml
	@FXML
	private void changeToLogIn(ActionEvent event) throws IOException {
		SceneHandler.setPreviousScene("LandingPage.fxml");
		Parent logInParent = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
		Scene logInScene = new Scene(logInParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(logInScene);
		window.show();
	}

	// changes to choose price on get started button press, with the previous scene
	// set as LandingPage.fxml
	@FXML
	private void changeToChoosePrice(ActionEvent event) throws IOException {
		SceneHandler.setPreviousScene("LandingPage.fxml");
		Parent choosePriceParent = FXMLLoader.load(getClass().getResource("ChoosePrice.fxml"));
		Scene choosePriceScene = new Scene(choosePriceParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(choosePriceScene);
		window.show();
	}

	// ----------------------------------------------------------------------------------------
	// Handles back and home buttons
	@FXML
	private Button bBack;

	@FXML
	private Button bHome;

	@FXML
	private void back(ActionEvent event) throws IOException {
		SceneHandler s1 = new SceneHandler();
		s1.backToPrevious(event);
	}

	@FXML
	private void home(ActionEvent event) throws IOException {
		SceneHandler s2 = new SceneHandler();
		s2.backToHome(event);
	}
}
