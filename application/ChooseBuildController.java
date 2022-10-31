package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

//This class is in charge of displaying the three recommended builds based on the algorithm (ChooseBuild.fxml)
public class ChooseBuildController implements Initializable {

	@FXML
	private Button bCBuild;

	@FXML
	private Button bNBuild;

	@FXML
	private Button bPBuild;

	@FXML
	private Label lCBCPU;

	@FXML
	private Label lCBGPU;

	@FXML
	private Label lCBPrice;

	@FXML
	private Label lNBCPU;

	@FXML
	private Label lNBGPU;

	@FXML
	private Label lNBPrice;

	@FXML
	private Label lPBCPU;

	@FXML
	private Label lPBGPU;

	@FXML
	private Label lPBPrice;

	private static int listID;
	public static int initialLID;

	// sets the labels equal to the content of the three different lists
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		listID = initialLID;
		try {
			List l1 = new List(listID);
			lCBCPU.setText(l1.getCPUName());
			lCBGPU.setText(l1.getGPUName());
			lCBPrice.setText("$" + l1.getMSRP());

			List l2 = new List(listID - 1);
			lPBCPU.setText(l2.getCPUName());
			lPBGPU.setText(l2.getGPUName());
			lPBPrice.setText("$" + l2.getMSRP());

			List l3 = new List(listID + 1);
			lNBCPU.setText(l3.getCPUName());
			lNBGPU.setText(l3.getGPUName());
			lNBPrice.setText("$" + l3.getMSRP());

		} catch (Exception e1) {
			e1.printStackTrace();
		}

	}

//changes scenes to middle list
	@FXML
	private void changeToCListInfo(ActionEvent event) throws Exception {
		List.lastLID = listID;

		Connection con = DBFunctions.getConnection();
		PreparedStatement getListCounter = con.prepareStatement("SELECT list_counter FROM list WHERE list_id=?");
		getListCounter.setInt(1, listID);

		ResultSet listStats = getListCounter.executeQuery();
		while (listStats.next()) {
			int currCounter = listStats.getInt("list_counter");
			currCounter++;

			PreparedStatement updateListCounter = con
					.prepareStatement("UPDATE list SET list_counter = ? WHERE list_id = ?");
			updateListCounter.setInt(1, currCounter);
			updateListCounter.setInt(2, listID);

			updateListCounter.executeUpdate();
		}

		SceneHandler.setPreviousScene("ChooseBuild.fxml");
		Parent listInfoParent = FXMLLoader.load(getClass().getResource("ListInfo.fxml"));
		Scene listInfoScene = new Scene(listInfoParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(listInfoScene);
		window.show();

	}

//changes scenes to left list
	@FXML
	private void changeToPListInfo(ActionEvent event) throws Exception {
		if(listID-1 == 0) {
			Alert nullList = new Alert(Alert.AlertType.ERROR);
			nullList.setTitle("Null List");
			nullList.setContentText("The selected list does not exist, please select a different one");
			nullList.show();
		}
		else {
		List.lastLID = listID - 1;

		Connection con = DBFunctions.getConnection();
		PreparedStatement getListCounter = con.prepareStatement("SELECT list_counter FROM list WHERE list_id=?");
		getListCounter.setInt(1, listID);

		ResultSet listStats = getListCounter.executeQuery();
		while (listStats.next()) {
			int currCounter = listStats.getInt("list_counter");
			currCounter++;

			PreparedStatement updateListCounter = con
					.prepareStatement("UPDATE list SET list_counter = ? WHERE list_id = ?");
			updateListCounter.setInt(1, currCounter);
			updateListCounter.setInt(2, listID);

			updateListCounter.executeUpdate();
		}

		SceneHandler.setPreviousScene("ChooseBuild.fxml");
		Parent listInfoParent = FXMLLoader.load(getClass().getResource("ListInfo.fxml"));
		Scene listInfoScene = new Scene(listInfoParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(listInfoScene);
		window.show();
		}
	}

	// changes scenes to right list
	@FXML
	private void changeToNListInfo(ActionEvent event) throws Exception {
		if(listID+1 == 28) {
			Alert nullList = new Alert(Alert.AlertType.ERROR);
			nullList.setTitle("Null List");
			nullList.setContentText("The selected list does not exist, please select a different one");
			nullList.show();
		}
		else {
		List.lastLID = listID + 1;

		Connection con = DBFunctions.getConnection();
		PreparedStatement getListCounter = con.prepareStatement("SELECT list_counter FROM list WHERE list_id=?");
		getListCounter.setInt(1, listID);

		ResultSet listStats = getListCounter.executeQuery();
		while (listStats.next()) {
			int currCounter = listStats.getInt("list_counter");
			currCounter++;

			PreparedStatement updateListCounter = con
					.prepareStatement("UPDATE list SET list_counter = ? WHERE list_id = ?");
			updateListCounter.setInt(1, currCounter);
			updateListCounter.setInt(2, listID);

			updateListCounter.executeUpdate();
		}

		SceneHandler.setPreviousScene("ChooseBuild.fxml");
		Parent listInfoParent = FXMLLoader.load(getClass().getResource("ListInfo.fxml"));
		Scene listInfoScene = new Scene(listInfoParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(listInfoScene);
		window.show();
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
