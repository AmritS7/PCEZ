package application;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/*
 * This class is in charge of the BarChart when you are logged in under Admin
 */
public class AdminListBarController implements Initializable {

	private static LinkedHashMap<Integer, Integer> stats = new LinkedHashMap<Integer, Integer>();

	@FXML
	private BarChart<?, ?> LSBar;

	@FXML
	private CategoryAxis xLID;

	@FXML
	private NumberAxis yClicks;

	// at start, iterates through a hashmap which contains a set of data - the
	// list_id, and the respective counter
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		XYChart.Series set1 = new XYChart.Series<>();

		Set<Integer> keys = stats.keySet();
		for (int element : keys) {
			String el = String.valueOf(element);
			set1.getData().add(new XYChart.Data(el, stats.get(element)));
		}

		LSBar.getData().addAll(set1);

	}

	// Before changing scenes, stats is set by AdminListController, so at
	// initialization, we can loop through and initialize the bar chart
	public static void setStats(LinkedHashMap<Integer, Integer> lStats) {
		stats = lStats;
	}

	@FXML
	private Button bSwitch;

	// Switches scenes to the pie chart for lists on button press with the previous
	// scene set as Admin List Bar
	@FXML
	public void switchToPie(ActionEvent event) throws IOException {
		AdminListPieController.setStats(stats);

		SceneHandler.setPreviousScene("AdminListBar.fxml");
		Parent aLPParent = FXMLLoader.load(getClass().getResource("AdminListPie.fxml"));
		Scene aLPScene = new Scene(aLPParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(aLPScene);
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
