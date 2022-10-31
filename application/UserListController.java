package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

//In charge of UserLists.fxml 

public class UserListController implements Initializable {

	@FXML
	private TableView<List> tableView;

	ObservableList<List> lists = FXCollections.observableArrayList();

	public void changeScene(Stage window, int listID) throws IOException {

		List.lastLID = listID;

		SceneHandler.setPreviousScene("UserLists.fxml");
		Parent listInfoParent = FXMLLoader.load(getClass().getResource("ListInfo.fxml"));
		Scene listInfoScene = new Scene(listInfoParent);

		window.setScene(listInfoScene);
		window.show();
	}

	// sets table contents equal to what is present in the db
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		TableColumn listName = new TableColumn("List Name");
		TableColumn action = new TableColumn("View");
		tableView.getColumns().addAll(listName, action);

		ArrayList<User> UserLists = new ArrayList<User>();
		User u1 = new User();

		UserLists = u1.getUserLists(u1.login_id);

		for (int i = 0; i < UserLists.size(); i++) {

			Button b1 = new Button();
			lists.add(new List(UserLists.get(i).getListName(), b1, UserLists.get(i).getListID()));

		}

		listName.setCellValueFactory(new PropertyValueFactory<List, String>("listName"));

		action.setCellValueFactory(new PropertyValueFactory<List, String>("button"));

		tableView.setItems(lists);
	}

	@FXML
	private Button bNewList;

	// moves to choose price scene, with user lists as previous
	@FXML
	void goToGS(ActionEvent event) throws IOException {
		SceneHandler.setPreviousScene("UserLists.fxml");
		Parent choosePriceParent = FXMLLoader.load(getClass().getResource("ChoosePrice.fxml"));
		Scene choosePriceScene = new Scene(choosePriceParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(choosePriceScene);
		window.show();
	}

	// logs out the user
	@FXML
	private void logOut(ActionEvent event) throws IOException {
		User u1 = new User();
		u1.setLoggedIn(false);
		Parent homeParent = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
		Scene homeScene = new Scene(homeParent);
		User.login_id = 0;
		List.lastLID=0;

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(homeScene);
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

	@FXML
	private Button bLogOut;

}
