package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

//This class is in charge of the change part view of the code when they decide to change a part (ChangePart.fxml)
public class ChangePartController implements Initializable {

	private static HashMap<Integer, String> changesToBeMade = new HashMap<Integer, String>();

	private HashMap<Button, Integer> buttonChanges = new HashMap<Button, Integer>();
	private String partIdentifier = "none";
	private static String currPartName;
	private int listID = 0;

	@FXML
	private VBox vbChanges;
	

	@FXML
	private Button bList;

	@FXML
	public ChoiceBox<String> cbPartType;

	@FXML
	private ChoiceBox<String> cbPartName;

	private String[] partTypes = { "CPU", "GPU", "Motherboard", "Ram", "Cooler", "Storage", "Power Supply", "Case" };

	// at start, adds all part types and part names into the choiceboxes
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cbPartType.getItems().addAll(partTypes);
		cbPartType.setOnAction(this::getPartType);
		cbPartName.setOnAction(this::saveChanges);
	}

	// sets identifier for setPartName table so we can display other parts that also
	// have the same identifier (ex. other CPUS)
	public void getPartType(ActionEvent evene) {
		String myType = cbPartType.getValue();
		if (myType.equals("CPU")) {
			setPI("c");
		} else if (myType.equals("GPU")) {
			setPI("g");
		} else if (myType.equals("MotherBoard")) {
			setPI("m");
		} else if (myType.equals("Ram")) {
			setPI("r");
		} else if (myType.equals("Cooler")) {
			setPI("o");
		} else if (myType.equals("Storage")) {
			setPI("s");
		} else if (myType.equals("Power Supply")) {
			setPI("p");
		} else if (myType.equals("Case")) {
			setPI("a");
		}

		setPartNameTable();

	}

	// adds all other parts of the same type into the checkbox for selection
	private void setPartNameTable() {
		String partIdentifier = getPI();
		ArrayList<String> partNames = new ArrayList<String>();
		partNames.clear();

		try {
			Connection con = DBFunctions.getConnection();
			PreparedStatement findParts = con
					.prepareStatement("SELECT part_id, part_name FROM parts WHERE identifier = ?");
			findParts.setString(1, partIdentifier);

			ResultSet currentList = findParts.executeQuery();
			while (currentList.next()) {
				partNames.add(currentList.getString("part_name"));
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		cbPartName.getItems().clear();
		cbPartName.getItems().addAll(partNames);

	}

	// displays any changes made underneath the table
	public void saveChanges(ActionEvent event) {
		String partName = cbPartName.getValue();

		try {
			String type = "none";
			Connection con = DBFunctions.getConnection();
			PreparedStatement findParts = con
					.prepareStatement("SELECT part_id, identifier FROM parts WHERE part_name = ?");
			findParts.setString(1, partName);

			ResultSet currentList = findParts.executeQuery();
			while (currentList.next()) {
				changesToBeMade.put(currentList.getInt("part_id"), currentList.getString("identifier"));

				String prevIdent = currentList.getString("identifier");
				if (prevIdent.equals("c")) {
					type = "CPU";
				} else if (prevIdent.equals("g")) {
					type = "GPU";
				} else if (prevIdent.equals("m")) {
					type = "Motherboard";
				} else if (prevIdent.equals("r")) {
					type = "Ram";
				} else if (prevIdent.equals("o")) {
					type = "Cooler";
				} else if (prevIdent.equals("s")) {
					type = "Storage";
				} else if (prevIdent.equals("p")) {
					type = "Power Supply";
				} else if (prevIdent.equals("a")) {
					type = "Case";
				}

				vbChanges.getChildren().add(new Label(type + " -> " + partName));
				Button b1 = new Button("View Part Info");
				buttonChanges.put(b1, currentList.getInt("part_id"));
				b1.setOnAction(arg0 -> {
					try {
						int pID = buttonChanges.get(arg0.getSource());
						PartInfoController.setPID(pID);
						changeToPartInfo(arg0);

					} catch (IOException e) {
						e.printStackTrace();
					}
				});
				vbChanges.getChildren().add(b1);

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	// changes scenes
	private void changeToPartInfo(ActionEvent event) throws IOException {
		SceneHandler.setPreviousScene("ChangePart.fxml");
		Parent partInfoParent = FXMLLoader.load(getClass().getResource("PartInfo.fxml"));
		Scene partInfoScene = new Scene(partInfoParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(partInfoScene);
		window.show();

	}

	private void setPI(String ident) {
		this.partIdentifier = ident;
	}

	// when save is clicked, goes back to list contents with the changed parts
	@FXML
	public void backToList(ActionEvent event) throws Exception {

		List.saveChanges(changesToBeMade);
		List l1 = new List(List.lastLID);

		SceneHandler.setPreviousScene("ChangePart.fxml");
		Parent listInfoParent = FXMLLoader.load(getClass().getResource("ListInfo.fxml"));
		Scene listInfoScene = new Scene(listInfoParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(listInfoScene);
		window.show();

	}

	private String getPI() {
		return this.partIdentifier;
	}

	public static void setCurrPartName(String string) {
		currPartName = string;
	}

	private String getCurrPartName() {
		return currPartName;
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
