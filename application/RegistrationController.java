package application;

import java.io.IOException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//This class is in charge of Registration.fxml 
public class RegistrationController {

	@FXML
	private Button bSubmit;

	@FXML
	private PasswordField pfPassword;

	@FXML
	private PasswordField pfPassword2;

	@FXML
	private TextField tfUsername;

	@FXML
	private Label lResponse;

	// creates a new account in the db if the passwords are the same and the
	// username is not taken
	@FXML
	private void createAccount(ActionEvent event) throws IOException {
		String u = tfUsername.getText();
		String p = pfPassword.getText();
		String p2 = pfPassword2.getText();

		if (!p.equals(p2)) {
			Alert nM = new Alert(Alert.AlertType.ERROR);
			nM.setTitle("Password error");
			nM.setContentText("The passwords do not match, please try again");
			nM.show();
		}

		else {
			User u1 = new User();
			boolean completed = u1.getUsers(u, p, p);

			if (completed == true) {
				Alert complete = new Alert(Alert.AlertType.INFORMATION);
				complete.setTitle("Success");
				complete.setContentText("Your account has been successfully registered!");

				Optional<ButtonType> result = complete.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					SceneHandler.setPreviousScene("RegistrationScreen.fxml");
					Parent logInParent = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
					Scene logInScene = new Scene(logInParent);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(logInScene);
					window.show();
				}
			} else if (completed == false) {
				Alert exists = new Alert(Alert.AlertType.ERROR);
				exists.setTitle("Username Exists");
				exists.setContentText("This username already exists please try again");
				exists.show();
			}
		}
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
