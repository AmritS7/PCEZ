package application;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/*
 * This class is in charge of the Pie Chart when you are logged in as Admin
 */
public class AdminListPieController implements Initializable {
	@FXML
	private PieChart LSPie;

	@FXML
	private Button bSwitch;

	private static LinkedHashMap<Integer, Integer> stats = new LinkedHashMap<Integer, Integer>();

	// at start, iterates through a hashmap which contains a set of data - the
	// list_id, and the respective counter
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

		Set<Integer> keys = stats.keySet();
		for (int element : keys) {
			String el = String.valueOf(element);
			pieChartData.add(new PieChart.Data(el, stats.get(element)));
		}

		LSPie.setData(pieChartData);
	}

	// Before changing scenes, stats is set by AdminListController, so at
	// initialization, we can loop through and initialize the bar chart
	public static void setStats(LinkedHashMap<Integer, Integer> lStats) {
		stats = lStats;
	}

	// Switches scenes to the table on button press with the previous
	// scene set as Admin List Pie
	@FXML
	public void switchToList(ActionEvent event) throws IOException {
		AdminListPieController.setStats(stats);

		SceneHandler.setPreviousScene("AdminListPie.fxml");
		Parent aLLParent = FXMLLoader.load(getClass().getResource("AdminLists.fxml"));
		Scene aLLScene = new Scene(aLLParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(aLLScene);
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
