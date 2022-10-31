package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

//backend for parts 
public class Part {

	private int part_id;
	private String partName;
	private String partIdentifier;
	private String partNotes;
	private double partMSRP;
	private ArrayList<String> URLS = new ArrayList<String>();
	private int counter;


	//creates a part for controller display using the part_id from the db
	public Part(int part_id) {
		try {
			Connection con = DBFunctions.getConnection();
			PreparedStatement partInfo = con.prepareStatement("SELECT * FROM parts WHERE part_id = ?");
			partInfo.setInt(1, part_id);

			ResultSet currentPart = partInfo.executeQuery();

			while (currentPart.next()) {
				this.setPart_id(currentPart.getInt("part_id"));
				this.setPartName(currentPart.getString("part_name"));
				this.setPartIdentifier(currentPart.getString("identifier"));
				this.setPartNotes(currentPart.getString("notes"));
				this.setPartMSRP(currentPart.getDouble("msrp"));
				this.setCounter(currentPart.getInt("part_counter"));

			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	//default constructor
	public Part() {
	}

	//creates a part with a part id, partname, msrp and counter - for use with admin part
	public Part(int pID, String partName2, double mSRP, int counter2) {
		this.part_id = pID;
		this.partName = partName2;
		this.partMSRP = mSRP;
		this.counter = counter2;
	}

	public int getPart_id() {
		return part_id;
	}

	public void setPart_id(int part_id) {
		this.part_id = part_id;
	}

	public String getPartName() {
		return partName;
	}

	public void setPartName(String partName) {
		this.partName = partName;
	}

	public String getPartIdentifier() {
		return partIdentifier;
	}

	public void setPartIdentifier(String partIdentifier) {
		this.partIdentifier = partIdentifier;
	}

	public String getPartNotes() {
		return partNotes;
	}

	public void setPartNotes(String partNotes) {
		this.partNotes = partNotes;
	}

	public double getPartMSRP() {
		return partMSRP;
	}

	public void setPartMSRP(double partMSRP) {
		this.partMSRP = partMSRP;
	}

	public int getCounter() {
		return counter;
	}

	public void setCounter(int counter) {
		this.counter = counter;
	}
}
