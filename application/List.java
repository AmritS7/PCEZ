package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

//This class serves as the backend of the list processes, including creating lists, switching scenes, and making changes
public class List {

	public SimpleStringProperty listName;
	private Button button;

	private static HashMap<Integer, String> changesToBeMade;
	private int listID;
	private int CPUCounter;
	private int CPUID;
	private String CPUName;
	private int GPUCounter;
	private int GPUID;
	private String GPUName;
	private int MOBOCounter;
	private int MOBOID;
	private String MOBOName;
	private int RamCounter;
	private int RamID;
	private String RamName;
	private int CoolerCounter;
	private int CoolerID;
	private String CoolerName;
	private int StorageCounter;
	private int StorageID;
	private String StorageName;
	private int PSUCounter;
	private int PSUID;
	private String PSUName;
	private int CaseCounter;
	private int CaseID;
	private String CaseName;
	private static int counter;

	public static int lastLID = 0;

	private double MSRP;

	// creates list
	public List(String listName, Button button, int listID) {
		this.listName = new SimpleStringProperty(listName);
		this.button = new Button("View List");
		this.setListID(listID);
		this.button.setOnMouseClicked(event -> {
			try {
				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				UserListController userListController = new UserListController();
				userListController.changeScene(window, listID);

			} catch (IOException e) {

				e.printStackTrace();
			}
		});
	}

	// creates list in a different form - from getting the ListID and sets
	// respective part names and values
	public List(int lID) throws Exception {


		setMSRP(0);
		Connection con = DBFunctions.getConnection();
		PreparedStatement findParts = con.prepareStatement("SELECT * FROM list_parts WHERE list_id = ?");
		findParts.setInt(1, lID);

		ResultSet currentList = findParts.executeQuery();
		while (currentList.next()) {

			Part p1 = new Part(currentList.getInt("part_id"));
			setMSRP(getMSRP() + p1.getPartMSRP());

			String ident = p1.getPartIdentifier();

			if (ident.equals("c")) {
				this.CPUName = p1.getPartName();
				this.CPUID = p1.getPart_id();
				this.CPUCounter = p1.getCounter();

			} else if (ident.equals("g")) {
				this.GPUName = p1.getPartName();
				this.GPUID = p1.getPart_id();
				this.GPUCounter = p1.getCounter();

			} else if (ident.equals("m")) {
				this.MOBOName = p1.getPartName();
				this.MOBOID = p1.getPart_id();
				this.MOBOCounter = p1.getCounter();

			} else if (ident.equals("r")) {
				this.RamName = p1.getPartName();
				this.RamID = p1.getPart_id();
				this.RamCounter = p1.getCounter();

			} else if (ident.equals("o")) {
				this.CoolerName = p1.getPartName();
				this.CoolerID = p1.getPart_id();
				this.CoolerCounter = p1.getCounter();

			} else if (ident.equals("s")) {
				this.StorageName = p1.getPartName();
				this.StorageID = p1.getPart_id();
				this.StorageCounter = p1.getCounter();

			} else if (ident.equals("p")) {
				this.PSUName = p1.getPartName();
				this.PSUID = p1.getPart_id();
				this.PSUCounter = p1.getCounter();

			} else if (ident.equals("a")) {
				this.CaseName = p1.getPartName();
				this.CaseID = p1.getPart_id();
				this.CaseCounter = p1.getCounter();
			}

		}

		if (changesToBeMade != null) {
			applyChanges(getChanges());

		}
		listID = lID;
		lastLID = lID;

	}

	// default constructor
	public List() {

	}

	// creates a list using only lid, msrp, cpuname,gpuname and counter - for
	// AdminList use
	public List(int lID, double mSRP2, String cPUName2, String gPUName2, int counter2) {
		this.listID = lID;
		this.MSRP = mSRP2;
		this.CPUName = cPUName2;
		this.GPUName = gPUName2;
		this.counter = counter2;
	}

	// Gets the contents of a list from the list ID and stores them in an ArrayList
	// to be used by a controller
	public ArrayList<Part> getListInfo(int listID) {

		ArrayList<Integer> partIDS = new ArrayList<Integer>();
		ArrayList<Part> listContents = new ArrayList<Part>();

		try {
			Connection con = DBFunctions.getConnection();
			PreparedStatement findParts = con.prepareStatement("SELECT part_id FROM list_parts WHERE list_id = ?");
			findParts.setInt(1, listID);

			ResultSet currentList = findParts.executeQuery();

			int i = 0;

			while (currentList.next()) {
				partIDS.add(i, currentList.getInt("part_id"));
				i++;

			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		for (int i = 0; i < partIDS.size(); i++) {
			Part p1 = new Part(partIDS.get(i));
			listContents.add(p1);

		}

		return listContents;

	}

	// At the creation of list, if the hashmap is not empty, then sets these new
	// changes so user can view the modified list
	public void applyChanges(HashMap<Integer, String> changesToBeMade) {
		for (Entry<Integer, String> entry : changesToBeMade.entrySet()) {
			Integer key = entry.getKey();
			String value = entry.getValue();
			Part p1 = new Part(key);
			if (value.equals("c")) {
				this.CPUName = p1.getPartName();
				CPUID = p1.getPart_id();
			} else if (value.equals("g")) {
				this.GPUName = p1.getPartName();
				GPUID = p1.getPart_id();
			} else if (value.equals("m")) {
				this.setMOBOName(p1.getPartName());
				setMOBOID(p1.getPart_id());
			} else if (value.equals("r")) {
				this.setRamName(p1.getPartName());
				setRamID(p1.getPart_id());
			} else if (value.equals("o")) {
				this.setCoolerName(p1.getPartName());
				setCoolerID(p1.getPart_id());
			} else if (value.equals("s")) {
				this.setStorageName(p1.getPartName());
				setStorageID(p1.getPart_id());
			} else if (value.equals("p")) {
				this.setPSUName(p1.getPartName());
				setPSUID(p1.getPart_id());
			} else if (value.equals("a")) {
				this.setCaseName(p1.getPartName());
				setCaseID(p1.getPart_id());
			}

		}
	}

	public int getMOBOID() {
		return MOBOID;
	}

	public void setMOBOID(int mOBOID) {
		this.MOBOID = mOBOID;
	}

	public String getMOBOName() {
		return MOBOName;
	}

	public void setMOBOName(String mOBOName) {
		this.MOBOName = mOBOName;
	}

	public int getRamID() {
		return RamID;
	}

	public void setRamID(int ramID) {
		this.RamID = ramID;
	}

	public String getRamName() {
		return RamName;
	}

	public void setRamName(String ramName) {
		this.RamName = ramName;
	}

	public int getCoolerID() {
		return CoolerID;
	}

	public void setCoolerID(int coolerID) {
		this.CoolerID = coolerID;
	}

	public String getCoolerName() {
		return CoolerName;
	}

	public void setCoolerName(String coolerName) {
		this.CoolerName = coolerName;
	}

	public int getStorageID() {
		return StorageID;
	}

	public void setStorageID(int storageID) {
		this.StorageID = storageID;
	}

	public String getStorageName() {
		return StorageName;
	}

	public void setStorageName(String storageName) {
		this.StorageName = storageName;
	}

	public int getPSUID() {
		return PSUID;
	}

	public void setPSUID(int pSUID) {
		this.PSUID = pSUID;
	}

	public String getPSUName() {
		return PSUName;
	}

	public void setPSUName(String pSUName) {
		this.PSUName = pSUName;
	}

	public int getCaseID() {
		return CaseID;
	}

	public void setCaseID(int caseID) {
		this.CaseID = caseID;
	}

	public String getCaseName() {
		return CaseName;
	}

	public void setCaseName(String caseName) {
		this.CaseName = caseName;
	}

	public String getListName() {
		return (listName.get());
	}

	public void SetListName(String listName) {
		this.listName = new SimpleStringProperty(listName);
	}

	public Button getButton() {
		return (this.button);
	}

	public void setButton(Button button) {
		this.button = button;
	}

	public int getListID() {
		return listID;
	}

	public void setListID(int lID) {
		listID = lID;
	}

	public int getCPUID() {
		return CPUID;
	}

	public String getCPUName() {
		return CPUName;
	}

	public int getGPUID() {
		return GPUID;
	}

	public String getGPUName() {
		return GPUName;
	}

	public double getMSRP() {
		return MSRP;
	}

	public void setMSRP(double mSRP) {
		this.MSRP = mSRP;
	}

	public static void saveChanges(HashMap<Integer, String> changes) {
		changesToBeMade = changes;
	}

	public HashMap<Integer, String> getChanges() {
		return changesToBeMade;
	}

	public int getCounter() throws Exception {
		Connection con = DBFunctions.getConnection();
		PreparedStatement findCounter = con.prepareStatement("SELECT list_counter FROM list WHERE list_id = ?");
		findCounter.setInt(1, this.listID);
		ResultSet curr = findCounter.executeQuery();
		while (curr.next()) {
			counter = curr.getInt("list_counter");
		}
		return counter;
	}

	public static void setCounter(int counter) {
		List.counter = counter;
	}

	public int getCPUCounter() {
		return CPUCounter;
	}

	public void setCPUCounter(int cPUCounter) {
		CPUCounter = cPUCounter;
	}

	public int getGPUCounter() {
		return GPUCounter;
	}

	public void setGPUCounter(int gPUCounter) {
		GPUCounter = gPUCounter;
	}

	public int getMOBOCounter() {
		return MOBOCounter;
	}

	public void setMOBOCounter(int mOBOCounter) {
		MOBOCounter = mOBOCounter;
	}

	public int getRamCounter() {
		return RamCounter;
	}

	public void setRamCounter(int ramCounter) {
		RamCounter = ramCounter;
	}

	public int getCoolerCounter() {
		return CoolerCounter;
	}

	public void setCoolerCounter(int coolerCounter) {
		CoolerCounter = coolerCounter;
	}

	public int getStorageCounter() {
		return StorageCounter;
	}

	public void setStorageCounter(int storageCounter) {
		StorageCounter = storageCounter;
	}

	public int getPSUCounter() {
		return PSUCounter;
	}

	public void setPSUCounter(int pSUCounter) {
		PSUCounter = pSUCounter;
	}

	public int getCaseCounter() {
		return CaseCounter;
	}

	public void setCaseCounter(int caseCounter) {
		CaseCounter = caseCounter;
	}

}
