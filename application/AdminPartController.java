package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
import javafx.stage.Stage;

public class AdminPartController implements Initializable {

	@FXML
	private TableColumn<Part, Integer> CPUCounter;

	@FXML
	private TableColumn<Part, Integer> CPUID;

	@FXML
	private TableColumn<Part, String> CPUName;

	@FXML
	private TableColumn<Part, Double> CPUPrice;

	@FXML
	private TableColumn<Part, Integer> CaseCounter;

	@FXML
	private TableColumn<Part, Integer> CaseID;

	@FXML
	private TableColumn<Part, String> CaseName;

	@FXML
	private TableColumn<Part, Double> CasePrice;

	@FXML
	private TableColumn<Part, Integer> CoolerCounter;

	@FXML
	private TableColumn<Part, Integer> CoolerID;

	@FXML
	private TableColumn<Part, String> CoolerName;

	@FXML
	private TableColumn<Part, Double> CoolerPrice;

	@FXML
	private TableColumn<Part, Integer> GPUCounter;

	@FXML
	private TableColumn<Part, Integer> GPUID;

	@FXML
	private TableColumn<Part, String> GPUName;

	@FXML
	private TableColumn<Part, Double> GPUPrice;

	@FXML
	private TableColumn<Part, Integer> MOBOCounter;

	@FXML
	private TableColumn<Part, Integer> MOBOID;

	@FXML
	private TableColumn<Part, String> MOBOName;

	@FXML
	private TableColumn<Part, Double> MOBOPrice;

	@FXML
	private TableColumn<Part, Integer> PSUCounter;

	@FXML
	private TableColumn<Part, Integer> PSUID;

	@FXML
	private TableColumn<Part, String> PSUName;

	@FXML
	private TableColumn<Part, Double> PSUPrice;

	@FXML
	private TableColumn<Part, Integer> RamCounter;

	@FXML
	private TableColumn<Part, Integer> RamID;

	@FXML
	private TableColumn<Part, String> RamName;

	@FXML
	private TableColumn<Part, Double> RamPrice;

	@FXML
	private TableColumn<Part, Integer> StorageCounter;

	@FXML
	private TableColumn<Part, Integer> StorageID;

	@FXML
	private TableColumn<Part, String> StorageName;

	@FXML
	private TableColumn<Part, Double> StoragePrice;

	@FXML
	private Button bBack;

	@FXML
	private Button bHome;

	@FXML
	private Button changeToListStats;

	@FXML
	private TableView<Part> tCPU;

	@FXML
	private TableView<Part> tCase;

	@FXML
	private TableView<Part> tCooler;

	@FXML
	private TableView<Part> tGPU;

	@FXML
	private TableView<Part> tMOBO;

	@FXML
	private TableView<Part> tPSU;

	@FXML
	private TableView<Part> tRam;

	@FXML
	private TableView<Part> tStorage;

	private double MSRP = 0;
	private String partName = "";
	private int counter = 0;
	private int PID;

	// Iterates through all parts and gets the respective counters, MSRP, partName,
	// and adds to an observable list - which is returned
	public ObservableList<Part> getParts(String i) throws Exception {
		ObservableList<Part> CPUS = FXCollections.observableArrayList();

		Connection con = DBFunctions.getConnection();
		PreparedStatement findCPUS = con.prepareStatement("SELECT * FROM parts WHERE identifier = ?");
		findCPUS.setString(1, i);

		ResultSet current = findCPUS.executeQuery();

		while (current.next()) {
			MSRP = current.getDouble("msrp");
			partName = current.getString("part_name");
			PID = current.getInt("part_id");
			counter = current.getInt("part_counter");

			Part p = new Part(PID, partName, MSRP, counter);
			CPUS.add(p);
		}
		return CPUS;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {

		CPUID.setCellValueFactory(new PropertyValueFactory<>("part_id"));
		CPUPrice.setCellValueFactory(new PropertyValueFactory<>("partMSRP"));
		CPUName.setCellValueFactory(new PropertyValueFactory<>("partName"));
		CPUCounter.setCellValueFactory(new PropertyValueFactory<>("counter"));

		GPUID.setCellValueFactory(new PropertyValueFactory<>("part_id"));
		GPUPrice.setCellValueFactory(new PropertyValueFactory<>("partMSRP"));
		GPUName.setCellValueFactory(new PropertyValueFactory<>("partName"));
		GPUCounter.setCellValueFactory(new PropertyValueFactory<>("counter"));

		MOBOID.setCellValueFactory(new PropertyValueFactory<>("part_id"));
		MOBOPrice.setCellValueFactory(new PropertyValueFactory<>("partMSRP"));
		MOBOName.setCellValueFactory(new PropertyValueFactory<>("partName"));
		MOBOCounter.setCellValueFactory(new PropertyValueFactory<>("counter"));

		CPUID.setCellValueFactory(new PropertyValueFactory<>("part_id"));
		CPUPrice.setCellValueFactory(new PropertyValueFactory<>("partMSRP"));
		CPUName.setCellValueFactory(new PropertyValueFactory<>("partName"));
		CPUCounter.setCellValueFactory(new PropertyValueFactory<>("counter"));

		RamID.setCellValueFactory(new PropertyValueFactory<>("part_id"));
		RamPrice.setCellValueFactory(new PropertyValueFactory<>("partMSRP"));
		RamName.setCellValueFactory(new PropertyValueFactory<>("partName"));
		RamCounter.setCellValueFactory(new PropertyValueFactory<>("counter"));

		CoolerID.setCellValueFactory(new PropertyValueFactory<>("part_id"));
		CoolerPrice.setCellValueFactory(new PropertyValueFactory<>("partMSRP"));
		CoolerName.setCellValueFactory(new PropertyValueFactory<>("partName"));
		CoolerCounter.setCellValueFactory(new PropertyValueFactory<>("counter"));

		StorageID.setCellValueFactory(new PropertyValueFactory<>("part_id"));
		StoragePrice.setCellValueFactory(new PropertyValueFactory<>("partMSRP"));
		StorageName.setCellValueFactory(new PropertyValueFactory<>("partName"));
		StorageCounter.setCellValueFactory(new PropertyValueFactory<>("counter"));

		PSUID.setCellValueFactory(new PropertyValueFactory<>("part_id"));
		PSUPrice.setCellValueFactory(new PropertyValueFactory<>("partMSRP"));
		PSUName.setCellValueFactory(new PropertyValueFactory<>("partName"));
		PSUCounter.setCellValueFactory(new PropertyValueFactory<>("counter"));

		CaseID.setCellValueFactory(new PropertyValueFactory<>("part_id"));
		CasePrice.setCellValueFactory(new PropertyValueFactory<>("partMSRP"));
		CaseName.setCellValueFactory(new PropertyValueFactory<>("partName"));
		CaseCounter.setCellValueFactory(new PropertyValueFactory<>("counter"));

		try {
			tCPU.setItems(getParts("c"));
			tGPU.setItems(getParts("g"));
			tMOBO.setItems(getParts("m"));
			tRam.setItems(getParts("r"));
			tCooler.setItems(getParts("o"));
			tStorage.setItems(getParts("s"));
			tPSU.setItems(getParts("p"));
			tCase.setItems(getParts("a"));

		} catch (Exception e) {
			e.printStackTrace();
		}
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
			Parent homeParent = FXMLLoader.load(getClass().getResource("LandingPage.fxml"));
			Scene homeScene = new Scene(homeParent);

			Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
			window.setScene(homeScene);
			window.show();
		}
	}

	// Switches scenes to the Admin List Table on button press with the previous
	// scene set as Admin Parts
	@FXML
	private void switchToLists(ActionEvent event) throws IOException {
		SceneHandler.setPreviousScene("AdminPart.fxml");
		Parent aPParent = FXMLLoader.load(getClass().getResource("AdminLists.fxml"));
		Scene aPScene = new Scene(aPParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(aPScene);
		window.show();
	}

	// ----------------------------------------------------------------------------------------
	// Handles back and home buttons
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
