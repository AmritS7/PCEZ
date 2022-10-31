package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

//This class is in  charge of ListInfo.fxml
public class ListInfoController implements Initializable {

	@FXML
	private Button bCPU;

	@FXML
	private Button bCase;

	@FXML
	private Button bCooler;

	@FXML
	private Button bGPU;

	@FXML
	private Button bMOBO;

	@FXML
	private Button bPSU;

	@FXML
	private Button bRam;

	@FXML
	private Button bStorage;

	@FXML
	private Label lCPU;

	@FXML
	private Label lCase;

	@FXML
	private Label lCooler;

	@FXML
	private Label lGPU;

	@FXML
	private Label lMOBO;

	@FXML
	private Label lPSU;

	@FXML
	private Label lPrice;

	@FXML
	private Label lRam;

	@FXML
	private Label lStorage;

	private static int ListID;

	private static int CPUID;

	// creates a list and sets the labels in the view equal to that lists contents
	// and price
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		try {
			List list = new List(List.lastLID);
			ListID = List.lastLID;

			try {
				lCPU.setText(list.getCPUName());
				setCPUID(list.getCPUID());
				setCPUCounter(list.getCPUCounter());
				lGPU.setText(list.getGPUName());
				setGPUID(list.getGPUID());
				setGPUCounter(list.getGPUCounter());
				lMOBO.setText(list.getMOBOName());
				setMOBOID(list.getMOBOID());
				setMOBOCounter(list.getMOBOCounter());
				lRam.setText(list.getRamName());
				setRamID(list.getRamID());
				setRamCounter(list.getRamCounter());
				lCooler.setText(list.getCoolerName());
				setCoolerID(list.getCoolerID());
				setCoolerCounter(list.getCoolerCounter());
				lStorage.setText(list.getStorageName());
				setStorageID(list.getStorageID());
				setStorageCounter(list.getStorageCounter());
				lPSU.setText(list.getPSUName());
				setPSUID(list.getPSUID());
				setPSUCounter(list.getPSUCounter());
				lCase.setText(list.getCaseName());
				setCaseID(list.getCaseID());
				setCaseCounter(list.getCaseCounter());
				lPrice.setText("$" + list.getMSRP());
			} catch (Exception e) {
			}
		}

		catch (Exception e1) {

		}
	}

	// on button press, moves onto the partInfo.fxml scene, displaying the info of
	// the selected part
	@FXML
	private void buttonPress(ActionEvent event) throws IOException {

		if (event.getSource().equals(bCPU)) {
			PartInfoController.setPID(CPUID);
		} else if (event.getSource().equals(bGPU)) {
			PartInfoController.setPID(GPUID);
		} else if (event.getSource().equals(bMOBO)) {
			PartInfoController.setPID(MOBOID);
		} else if (event.getSource().equals(bRam)) {
			PartInfoController.setPID(RamID);
		} else if (event.getSource().equals(bCooler)) {
			PartInfoController.setPID(CoolerID);
		} else if (event.getSource().equals(bStorage)) {
			PartInfoController.setPID(StorageID);
		} else if (event.getSource().equals(bPSU)) {
			PartInfoController.setPID(PSUID);
		} else if (event.getSource().equals(bCase)) {
			PartInfoController.setPID(CaseID);
		}

		SceneHandler.setPreviousScene("ListInfo.fxml");
		Parent partInfoParent = FXMLLoader.load(getClass().getResource("PartInfo.fxml"));
		Scene partInfoScene = new Scene(partInfoParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(partInfoScene);
		window.show();

	}

	// if user is not logged in, prompts user to do so
	@FXML
	private void saveList(ActionEvent event) {
		try {

			User u1 = new User();

			if (u1.isLoggedIn() == false) {

				Alert notLoggedIn = new Alert(Alert.AlertType.CONFIRMATION);
				notLoggedIn.setTitle("Not Logged In");
				notLoggedIn.setContentText("You are not logged in to save, please log in or register now!");
				Optional<ButtonType> result = notLoggedIn.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					SceneHandler.setPreviousScene("ListInfo.fxml");
					Parent logInParent = FXMLLoader.load(getClass().getResource("LogInScreen.fxml"));
					Scene logInScene = new Scene(logInParent);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(logInScene);
					window.show();

					LogInController.setLID(ListID);

				}
			} else {
				SceneHandler.setPreviousScene("ListInfo.fxml");
				Parent saveListParent = FXMLLoader.load(getClass().getResource("SaveList.fxml"));
				Scene saveListScene = new Scene(saveListParent);

				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(saveListScene);
				window.show();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	// saves a new list with the entered name, if it already exists, asks to
	// override or else saves as a new list in the db
	@FXML
	public void savePress(ActionEvent event) {

		int sameListID = 0;

		try {

			String lName = tfLName.getText();
			Connection con = DBFunctions.getConnection();
			PreparedStatement findList = con.prepareStatement("SELECT * FROM list WHERE login_id = ?");
			findList.setInt(1, LogInController.login_id);

			ResultSet userLists = findList.executeQuery();
			while (userLists.next()) {
				if (userLists.getString("list_name").equals(lName)) {
					sameListID = userLists.getInt("list_id");
				}
			}
			if (sameListID != 0) {
				Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
				alert.setTitle("Save Confirmation");
				alert.setContentText(
						"A list with the same name " + lName + " exists. Are you sure you want to override it?");
				Optional<ButtonType> result = alert.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {

					PreparedStatement updateCPU = con
							.prepareStatement("UPDATE list_parts SET part_id = ? WHERE list_id = ? AND part_id between 1 AND 9");
					updateCPU.setInt(1, sameListID);
					updateCPU.setInt(2, getCPUID());
					updateCPU.executeUpdate();

					PreparedStatement updateGPU = con
							.prepareStatement("UPDATE list_parts SET part_id = ? WHERE list_id = ? AND part_id between 10 AND 18");
					updateGPU.setInt(1, sameListID);
					updateGPU.setInt(2, getGPUID());
					updateGPU.executeUpdate();

					PreparedStatement updateMOBO = con
							.prepareStatement("UPDATE list_parts SET part_id = ? WHERE list_id = ? AND part_id between 19 AND 23");
					updateMOBO.setInt(1, sameListID);
					updateMOBO.setInt(2, getMOBOID());
					updateMOBO.executeUpdate();

					PreparedStatement updateRAM = con
							.prepareStatement("UPDATE list_parts SET part_id = ? WHERE list_id = ? AND part_id between 24 AND 28");
					updateRAM.setInt(1, sameListID);
					updateRAM.setInt(2, getRamID());
					updateRAM.executeUpdate();

					PreparedStatement updateCooler = con
							.prepareStatement("UPDATE list_parts SET part_id = ? WHERE list_id = ? AND part_id between 29 AND 33");
					updateCooler.setInt(1, sameListID);
					updateCooler.setInt(2, getCoolerID());
					updateCooler.executeUpdate();

					PreparedStatement updateStorage = con
							.prepareStatement("UPDATE list_parts SET part_id = ? WHERE list_id = ? AND part_id between 34 AND 38");
					updateStorage.setInt(1, sameListID);
					updateStorage.setInt(2, getStorageID());
					updateStorage.executeUpdate();

					PreparedStatement updatePSU = con
							.prepareStatement("UPDATE list_parts SET part_id = ? WHERE list_id = ? AND part_id between 39 AND 43");
					updatePSU.setInt(1, sameListID);
					updatePSU.setInt(2, getPSUID());
					updatePSU.executeUpdate();

					PreparedStatement updateCase = con
							.prepareStatement("UPDATE list_parts SET part_id = ? WHERE list_id = ? AND part_id between 43 AND 48");
					updateCase.setInt(1, sameListID);
					updateCase.setInt(2, getCaseID());
					updateCase.executeUpdate();

					Alert done = new Alert(Alert.AlertType.CONFIRMATION);
					done.setTitle("List Saved");
					done.setContentText("The list has successfully been saved!");
					Optional<ButtonType> result2 = done.showAndWait();
					if (result2.isPresent() && result2.get() == ButtonType.OK) {
						SceneHandler.setPreviousScene("ListInfo.fxml");
						Parent logInParent = FXMLLoader.load(getClass().getResource("UserLists.fxml"));
						Scene logInScene = new Scene(logInParent);

						Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
						window.setScene(logInScene);
						window.show();
					}

				}
			}

			else {
				int mLID = 0;
				PreparedStatement findMaxListID = con.prepareStatement("SELECT MAX(list_id) FROM list_parts");
				ResultSet MaxLID = findMaxListID.executeQuery();

				if (MaxLID.next()) {
					mLID = MaxLID.getInt(1);
					mLID++;
				}

				PreparedStatement saveNewList = con
						.prepareStatement("INSERT INTO list (list_id, login_id, list_name) VALUES ('" + mLID + "', '"
								+ User.login_id + "', '" + lName + "')");
				saveNewList.executeUpdate();

				PreparedStatement saveCPU = con.prepareStatement(
						"INSERT INTO list_parts (list_id, part_id) VALUES ('" + mLID + "', '" + getCPUID() + "')");
				saveCPU.executeUpdate();
				PreparedStatement updateCPUCounter = con
						.prepareStatement("UPDATE parts SET part_counter = ? WHERE part_id =?");
				updateCPUCounter.setInt(1, getCPUCounter() + 1);
				updateCPUCounter.setInt(2, getCPUID());
				updateCPUCounter.executeUpdate();

				PreparedStatement saveGPU = con.prepareStatement(
						"INSERT INTO list_parts (list_id, part_id) VALUES ('" + mLID + "', '" + getGPUID() + "')");
				saveGPU.executeUpdate();
				PreparedStatement updateGPUCounter = con
						.prepareStatement("UPDATE parts SET part_counter = ? WHERE part_id =?");
				updateGPUCounter.setInt(1, getGPUCounter() + 1);
				updateGPUCounter.setInt(2, getGPUID());
				updateGPUCounter.executeUpdate();

				PreparedStatement saveMOBO = con.prepareStatement(
						"INSERT INTO list_parts (list_id, part_id) VALUES ('" + mLID + "', '" + getMOBOID() + "')");
				saveMOBO.executeUpdate();
				PreparedStatement updateMOBOCounter = con
						.prepareStatement("UPDATE parts SET part_counter = ? WHERE part_id =?");
				updateMOBOCounter.setInt(1, getMOBOCounter() + 1);
				updateMOBOCounter.setInt(2, getMOBOID());
				updateMOBOCounter.executeUpdate();

				PreparedStatement saveRam = con.prepareStatement(
						"INSERT INTO list_parts (list_id, part_id) VALUES ('" + mLID + "', '" + getRamID() + "')");
				saveRam.executeUpdate();
				PreparedStatement updateRamCounter = con
						.prepareStatement("UPDATE parts SET part_counter = ? WHERE part_id =?");
				updateRamCounter.setInt(1, getRamCounter() + 1);
				updateRamCounter.setInt(2, getRamID());
				updateRamCounter.executeUpdate();

				PreparedStatement saveCooler = con.prepareStatement(
						"INSERT INTO list_parts (list_id, part_id) VALUES ('" + mLID + "', '" + getCoolerID() + "')");
				saveCooler.executeUpdate();
				PreparedStatement updateCoolerCounter = con
						.prepareStatement("UPDATE parts SET part_counter = ? WHERE part_id =?");
				updateCoolerCounter.setInt(1, getCoolerCounter() + 1);
				updateCoolerCounter.setInt(2, getCoolerID());
				updateCoolerCounter.executeUpdate();

				PreparedStatement saveStorage = con.prepareStatement(
						"INSERT INTO list_parts (list_id, part_id) VALUES ('" + mLID + "', '" + getStorageID() + "')");
				saveStorage.executeUpdate();
				PreparedStatement updateStorageCounter = con
						.prepareStatement("UPDATE parts SET part_counter = ? WHERE part_id =?");
				updateStorageCounter.setInt(1, getStorageCounter() + 1);
				updateStorageCounter.setInt(2, getStorageID());
				updateStorageCounter.executeUpdate();

				PreparedStatement savePSU = con.prepareStatement(
						"INSERT INTO list_parts (list_id, part_id) VALUES ('" + mLID + "', '" + getPSUID() + "')");
				savePSU.executeUpdate();
				PreparedStatement updatePSUCounter = con
						.prepareStatement("UPDATE parts SET part_counter = ? WHERE part_id =?");
				updatePSUCounter.setInt(1, getPSUCounter() + 1);
				updatePSUCounter.setInt(2, getPSUID());
				updatePSUCounter.executeUpdate();

				PreparedStatement saveCase = con.prepareStatement(
						"INSERT INTO list_parts (list_id, part_id) VALUES ('" + mLID + "', '" + getCaseID() + "')");
				saveCase.executeUpdate();
				PreparedStatement updateCaseCounter = con
						.prepareStatement("UPDATE parts SET part_counter = ? WHERE part_id =?");
				updateCaseCounter.setInt(1, getCaseCounter() + 1);
				updateCaseCounter.setInt(2, getCaseID());
				updateCaseCounter.executeUpdate();

				Alert done = new Alert(Alert.AlertType.CONFIRMATION);
				done.setTitle("List Saved");
				done.setContentText("The list has successfully been saved!");
				Optional<ButtonType> result2 = done.showAndWait();
				if (result2.isPresent() && result2.get() == ButtonType.OK) {
					SceneHandler.setPreviousScene("ListInfo.fxml");
					Parent logInParent = FXMLLoader.load(getClass().getResource("UserLists.fxml"));
					Scene logInScene = new Scene(logInParent);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(logInScene);
					window.show();
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// deletes list, if not logged in
	@FXML
	private void delete(ActionEvent event) throws Exception {
		if (User.login_id == 0) {
			Alert notLoggedIn = new Alert(Alert.AlertType.ERROR);
			notLoggedIn.setTitle("Not Logged In");
			notLoggedIn.setContentText("You are not logged in, so you can not delete this list!");
			notLoggedIn.show();
		} else {
			Connection con = DBFunctions.getConnection();

			if (ListID <= 27) {
				Alert dontOwnList = new Alert(Alert.AlertType.ERROR);
				dontOwnList.setTitle("Not Savved");
				dontOwnList.setContentText("You do not have this list saved. You can not delete it!");
				dontOwnList.show();
			}

			else {
				PreparedStatement deleteList = con.prepareStatement("DELETE FROM list WHERE list_id = ?");
				deleteList.setInt(1, ListID);
				deleteList.executeUpdate();
				PreparedStatement deleteListParts = con.prepareStatement("DELETE FROM list_parts WHERE list_id = ?");
				deleteListParts.setInt(1, ListID);
				deleteListParts.executeUpdate();

				Alert deleted = new Alert(Alert.AlertType.INFORMATION);
				deleted.setTitle("Delete Confirmation");
				deleted.setContentText("The list has been successfully deleted!");
				Optional<ButtonType> result = deleted.showAndWait();
				if (result.isPresent() && result.get() == ButtonType.OK) {
					Parent uListParent = FXMLLoader.load(getClass().getResource("UserLists.fxml"));
					Scene uListScene = new Scene(uListParent);

					Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
					window.setScene(uListScene);
					window.show();
				}
			}
		}
	}

	private static void setCPUID(int PartID) {
		CPUID = PartID;
	}

	private static int getCPUID() {
		return CPUID;
	}

	private static int GPUID;

	private static void setGPUID(int PartID) {
		GPUID = PartID;
	}

	private static int getGPUID() {
		return GPUID;
	}

	private static int MOBOID;

	private static void setMOBOID(int PartID) {
		MOBOID = PartID;
	}

	private static int getMOBOID() {
		return MOBOID;
	}

	private static int RamID;

	private static void setRamID(int PartID) {
		RamID = PartID;
	}

	private static int getRamID() {
		return RamID;
	}

	private static int CoolerID;

	private static void setCoolerID(int PartID) {
		CoolerID = PartID;
	}

	private static int getCoolerID() {
		return CoolerID;
	}

	private static int StorageID;

	private static void setStorageID(int PartID) {
		StorageID = PartID;
	}

	private static int getStorageID() {
		return StorageID;
	}

	private static int PSUID;

	private static void setPSUID(int PartID) {
		PSUID = PartID;
	}

	private static int getPSUID() {
		return PSUID;
	}

	private static int CaseID;

	private static void setCaseID(int PartID) {
		CaseID = PartID;
	}

	private static int getCaseID() {
		return CaseID;
	}

	private static int CPUCounter;

	private static void setCPUCounter(int CPUC) {
		CPUCounter = CPUC;
	}

	private static int getCPUCounter() {
		return CPUCounter;
	}

	private static int GPUCounter;

	private static void setGPUCounter(int GPUC) {
		GPUCounter = GPUC;
	}

	private static int getGPUCounter() {
		return GPUCounter;
	}

	private static int MOBOCounter;

	private static void setMOBOCounter(int MOBOC) {
		MOBOCounter = MOBOC;
	}

	private static int getMOBOCounter() {
		return MOBOCounter;
	}

	private static int RamCounter;

	private static void setRamCounter(int RamC) {
		RamCounter = RamC;
	}

	private static int getRamCounter() {
		return RamCounter;
	}

	private static int CoolerCounter;

	private static void setCoolerCounter(int CoolerC) {
		CoolerCounter = CoolerC;
	}

	private static int getCoolerCounter() {
		return CoolerCounter;
	}

	private static int StorageCounter;

	private static void setStorageCounter(int StorageC) {
		StorageCounter = StorageC;
	}

	private static int getStorageCounter() {
		return StorageCounter;
	}

	private static int PSUCounter;

	private static void setPSUCounter(int PSUC) {
		PSUCounter = PSUC;
	}

	private static int getPSUCounter() {
		return PSUCounter;
	}

	private static int CaseCounter;

	private static void setCaseCounter(int CaseC) {
		CaseCounter = CaseC;
	}

	private static int getCaseCounter() {
		return CaseCounter;
	}

	@FXML
	private Button bDelete;

	@FXML
	private Button bSaveList;

	@FXML
	private Button bSave;

	@FXML
	private TextField tfLName;

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
