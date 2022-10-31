package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

//In charge of LogIn.fxml - Checks if the users info matches within the db, or if they click on Register - moves to registration scene - still need to edit to include password hashing 
public class LogInController {

	static int login_id;
	private static int LID;
	static boolean isAdmin;

	public static void setLID(int listID) {
		LID = listID;
	}

	public static int getLID() {
		return LID;
	}

	@FXML
	private PasswordField password;

	@FXML
	private TextField username;

	// checks if login is correct - if so moves on to next scene
	@FXML
	private void checkLogIn(ActionEvent event) throws IOException {
		String enteredUsername = username.getText();
		String eneteredPassword = password.getText();

		User u1 = new User();
		login_id = u1.getLoginId(enteredUsername, eneteredPassword);
		Boolean isAdmin = u1.isAdmin;

		if (login_id == 0) { // Alerts
			Alert wP = new Alert(Alert.AlertType.ERROR);
			wP.setTitle("Wrong Password");
			wP.setContentText("The password you've entered is incorrect please try again");
			wP.show();
		} else if (login_id == -1) {
			Alert DNE = new Alert(Alert.AlertType.ERROR);
			DNE.setTitle("Account not found");
			DNE.setContentText(
					"The username you've entered does not exist in the database please register or try again!");
			DNE.show();
		}

		else {


				if (isAdmin == true) {
					SceneHandler.setPreviousScene("LogInScreen.fxml");
					Parent adminParent = FXMLLoader.load(getClass().getResource("AdminLists.fxml"));
					Scene adminListScene = new Scene(adminParent);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(adminListScene);
					window.show();
				}

				else if (getLID() != 0) {
					switchBack(event);
				}
				
				else {

					SceneHandler.setPreviousScene("LogInScreen.fxml");
					Parent userListsParent = FXMLLoader.load(getClass().getResource("UserLists.fxml"));
					Scene userListScene = new Scene(userListsParent);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(userListScene);
					window.show();
				}
			}
		}
	

	// If user already picked a scene - moves back to that list
	void switchBack(ActionEvent event) throws IOException {

		List.lastLID = LID;
		SceneHandler.setPreviousScene("LogInScreen.fxml");
		Parent prevListParent = FXMLLoader.load(getClass().getResource("ListInfo.fxml"));
		Scene prevListScene = new Scene(prevListParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(prevListScene);
		window.show();
	}

	// changes to registration scene, setting previous scene to loginscreen.fxml
	@FXML
	private void changeToRegistration(ActionEvent event) throws IOException {
		SceneHandler.setPreviousScene("LogInScreen.fxml");
		Parent registrationParent = FXMLLoader.load(getClass().getResource("RegistrationScreen.fxml"));
		Scene resgistrationScene = new Scene(registrationParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(resgistrationScene);
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
