package application;

import java.io.IOException;
import java.util.Stack;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

//This class handles all back and home buttons - a stack holds the order of scenes 
public class SceneHandler {

	private static Stack<String> previousScene = new Stack<>();;

	static void setPreviousScene(String PS) {
		previousScene.push(PS);
	}

	Stack<String> getPreviousScene() {
		return previousScene;
	}

	// switches back to the previous scene
	void backToPrevious(ActionEvent event) throws IOException {
		if (getPreviousScene().isEmpty()) {
			Alert cantGoBack = new Alert(Alert.AlertType.ERROR);
			cantGoBack.setTitle("Error");
			cantGoBack.setContentText("Can not go further back");
			cantGoBack.show();
		}

		else {
			Parent previousParent = FXMLLoader.load(getClass().getResource(getPreviousScene().pop()));
			Scene previousScene = new Scene(previousParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(previousScene);
			window.show();
		}
	}

	// goes to the respective home screen
	public void backToHome(ActionEvent event) throws IOException {
		User u1 = new User();
		if (u1.isLoggedIn()) {
			if (u1.isAdmin) {
				Parent homeParent = FXMLLoader.load(getClass().getResource("AdminLists.fxml"));
				Scene homeScene = new Scene(homeParent);

				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(homeScene);
				window.show();
			} else {
				Parent homeParent = FXMLLoader.load(getClass().getResource("UserLists.fxml"));
				Scene homeScene = new Scene(homeParent);

				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(homeScene);
				window.show();
			}
		} else {
			Parent homeParent = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
			Scene homeScene = new Scene(homeParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(homeScene);
			window.show();
		}
	}

}
