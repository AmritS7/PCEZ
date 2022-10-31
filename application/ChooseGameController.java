package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.stage.Stage;

//This class is in charge of ChooseGames.fxml, and lets the user pick up to three games to then calculate the recommended build
public class ChooseGameController implements Initializable{

	private static int tier;

	private static int sum = 0;

	public static void setTier(int t) {
		tier = t;
	}

	private static int counter = 0;

	@FXML
	private Button bSubmit;

	@FXML
	private CheckBox cbApexLegends;

	@FXML
	private CheckBox cbCOD;

	@FXML
	private CheckBox cbCSGO;

	@FXML
	private CheckBox cbFIIFA;

	@FXML
	private CheckBox cbFortnite;

	@FXML
	private CheckBox cbForza;

	@FXML
	private CheckBox cbGTA;

	@FXML
	private CheckBox cbLOL;

	@FXML
	private CheckBox cbMinecraft;

	
	// all of these methods handle each button click and increment the sum values
	// which is then passed onto the algorithm to calculate the recommended build
	@FXML
	private void handleMinecraft(ActionEvent event) throws IOException {
		if (cbMinecraft.isSelected()) {
			sum += 1;
			counter++;
			if (counter >= 3) {
				changeToListInfo(event);
			}
		}
	}

	@FXML
	private void handleCSGO(ActionEvent event) throws IOException {
		if (cbCSGO.isSelected()) {
			sum += 2;
			counter++;
			if (counter >= 3) {
				changeToListInfo(event);
			}
		}
	}

	@FXML
	private void handleLOL(ActionEvent event) throws IOException {
		if (cbLOL.isSelected()) {
			sum += 3;
			counter++;
			if (counter >= 3) {
				changeToListInfo(event);
			}
		}
	}

	@FXML
	private void handleApex(ActionEvent event) throws IOException {
		if (cbApexLegends.isSelected()) {
			sum += 4;
			counter++;
			if (counter >= 3) {
				changeToListInfo(event);
			}
		}
	}

	@FXML
	private void handleFIFA(ActionEvent event) throws IOException {
		if (cbFIIFA.isSelected()) {
			sum += 5;
			counter++;
			if (counter >= 3) {
				changeToListInfo(event);
			}
		}
	}

	@FXML
	private void handleFortnite(ActionEvent event) throws IOException {
		if (cbFortnite.isSelected()) {
			sum += 6;
			counter++;
			if (counter >= 3) {
				changeToListInfo(event);
			}
		}
	}

	@FXML
	private void handleCOD(ActionEvent event) throws IOException {
		if (cbCOD.isSelected()) {
			sum += 7;
			counter++;
			if (counter >= 3) {
				changeToListInfo(event);
			}
		}
	}

	@FXML
	private void handleForza(ActionEvent event) throws IOException {
		if (cbForza.isSelected()) {
			sum += 8;
			counter++;
			if (counter >= 3) {
				changeToListInfo(event);
			}
		}
	}

	@FXML
	private void handleGTA(ActionEvent event) throws IOException {
		if (cbGTA.isSelected()) {
			sum += 9;
			counter++;
			if (counter >= 3) {
				changeToListInfo(event);
			}
		}
	}

	// changes to next scene, passing on the value
	@FXML
	private void changeToListInfo(ActionEvent event) throws IOException {

		List.lastLID = (sum / counter) + tier;
		ChooseBuildController.initialLID = (sum / counter) + tier;

		SceneHandler.setPreviousScene("ChooseGames.fxml");
		Parent chooseBuildParent = FXMLLoader.load(getClass().getResource("ChooseBuild.fxml"));
		Scene chooseBuildScene = new Scene(chooseBuildParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(chooseBuildScene);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		counter = 0;
		sum = 0;
	}

}
