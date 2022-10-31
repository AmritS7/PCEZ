package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Optional;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/*
 * This class is in charge of the Table when you are logged in as Admin
 */
public class AdminListController implements Initializable {

	private double MSRP = 0;
	private String CPUName;
	private String GPUName;
	private int counter;

	@FXML
	private Button bParts;

	@FXML
	private Button bSwitch;

	@FXML
	private HBox hBChart;

	@FXML
	private TableColumn<List, Integer> lID;
	@FXML
	private TableColumn<List, Double> perc;

	@FXML
	private TableColumn<List, Double> tPrice;

	@FXML
	private TableColumn<List, String> cpuName;

	@FXML
	private TableColumn<List, String> gpuName;

	@FXML
	private TableView<List> table;

	private LinkedHashMap<Integer, Integer> lStats = new LinkedHashMap<Integer, Integer>();

	// Adds all values to the table from the observableList
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		lID.setCellValueFactory(new PropertyValueFactory<>("listID"));
		tPrice.setCellValueFactory(new PropertyValueFactory<>("MSRP"));
		cpuName.setCellValueFactory(new PropertyValueFactory<>("CPUName"));
		gpuName.setCellValueFactory(new PropertyValueFactory<>("GPUName"));
		perc.setCellValueFactory(new PropertyValueFactory<>("counter"));

		try {
			table.setItems(getLists());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Iterates through lists 1-27 and gets the respective counters, MSRP, cpuName,
	// and gpuName, and adds to an observable list - which is returned
	public ObservableList<List> getLists() throws Exception {
		ObservableList<List> lists = FXCollections.observableArrayList();

		for (int i = 1; i <= 27; i++) {
			try {
				MSRP = 0;
				Connection con = DBFunctions.getConnection();
				PreparedStatement findCounter = con.prepareStatement("SELECT list_counter FROM list WHERE list_id = ?");
				findCounter.setInt(1, i);
				ResultSet count = findCounter.executeQuery();
				while (count.next()) {
					counter = count.getInt("list_counter");
				}

				PreparedStatement findParts = con.prepareStatement("SELECT * FROM list_parts WHERE list_id = ?");
				findParts.setInt(1, i);

				ResultSet current = findParts.executeQuery();

				while (current.next()) {
					PreparedStatement partInfo = con.prepareStatement("SELECT * FROM parts WHERE part_id = ?");
					partInfo.setInt(1, current.getInt("part_id"));

					ResultSet currentPart = partInfo.executeQuery();

					while (currentPart.next()) {
						MSRP += currentPart.getDouble("MSRP");
						if (currentPart.getString("identifier").equals("c")) {
							CPUName = currentPart.getString("part_name");
						}
						if (currentPart.getString("identifier").equals("g")) {
							GPUName = currentPart.getString("part_name");
						}

					}

				}
				List l = new List(i, MSRP, CPUName, GPUName, counter);
				lists.add(l);
				lStats.put(l.getListID(), l.getCounter());

			} catch (Exception e) {

				e.printStackTrace();
			}
		}

		return lists;
	}

	// Switches scenes to the bar chart for lists on button press with the previous
	// scene set as Admin Lists
	@FXML
	public void switchToBar(ActionEvent event) throws IOException {
		AdminListBarController.setStats(lStats);

		SceneHandler.setPreviousScene("AdminLists.fxml");
		Parent aLBParent = FXMLLoader.load(getClass().getResource("AdminListBar.fxml"));
		Scene aLBScene = new Scene(aLBParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(aLBScene);
		window.show();
	}

	// Switches scenes to the Admin Part Table on button press with the previous
	// scene set as Admin Lists
	@FXML
	private void switchToParts(ActionEvent event) throws IOException {
		SceneHandler.setPreviousScene("AdminLists.fxml");
		Parent aPParent = FXMLLoader.load(getClass().getResource("AdminPart.fxml"));
		Scene aPScene = new Scene(aPParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(aPScene);
		window.show();
	}

	// Logs the admin out - with an alert 
	@FXML
	private void logOut(ActionEvent event) throws IOException {
		Alert loggedOut = new Alert(Alert.AlertType.INFORMATION);
		loggedOut.setTitle("Log Out");
		loggedOut.setContentText("You have successfully logged out");
		Optional<ButtonType> result = loggedOut.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
		User u1 = new User();
		u1.setLoggedIn(false);
		User.login_id = 0;
		Parent homeParent = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
		Scene homeScene = new Scene(homeParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(homeScene);
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
