package application;

import java.io.IOException;
import java.awt.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Node;

//This class is in charge of PartInfo.fxml displays changes and changes scenes based on button press
public class PartInfoController implements Initializable {

	@FXML
	private Button bCPart;

	@FXML
	private Label lPMSRP;

	@FXML
	private Label lPName;

	@FXML
	private Label lPNotes;

	@FXML
	private Label lPType;

	// sets labels according to the list contents
	@Override
	public void initialize(URL url, ResourceBundle rb) {

		int partID = getPID();
		Part p2 = new Part(partID);

		double MSRP = p2.getPartMSRP();
		lPMSRP.setText(String.valueOf(MSRP));
		lPName.setText(p2.getPartName());
		lPNotes.setText(p2.getPartNotes());

		String ident = p2.getPartIdentifier();

		if (ident.equals("c")) {
			lPType.setText("CPU");
		} else if (ident.equals("g")) {
			lPType.setText("GPU");
		} else if (ident.equals("m")) {
			lPType.setText("Motherboard");
		} else if (ident.equals("r")) {
			lPType.setText("Ram");
		} else if (ident.equals("o")) {
			lPType.setText("Cooler");
		} else if (ident.equals("s")) {
			lPType.setText("Storage");
		} else if (ident.equals("p")) {
			lPType.setText("Power Supply");
		} else if (ident.equals("a")) {
			lPType.setText("Case");
		}

		try {
			fillURLS(partID);
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	// on click, changes to changePart.fxml, with the current part
	@FXML
	private void changePart(ActionEvent event) throws IOException {

		SceneHandler.setPreviousScene("PartInfo.fxml");
		Parent changePartParent = FXMLLoader.load(getClass().getResource("ChangePart.fxml"));
		Scene changePartScene = new Scene(changePartParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(changePartScene);
		window.show();
	}

	private static int PID;

	public static void setPID(int partID) {
		PID = partID;
	}

	private int getPID() {
		return PID;
	}

	@FXML
	private Hyperlink aLink;

	@FXML
	private Hyperlink nLink;

	// fills the urls with the respective ones from the url table
	private void fillURLS(int partID) throws Exception {
		Connection con = DBFunctions.getConnection();
		PreparedStatement getURLS = con.prepareStatement("SELECT * FROM urls WHERE parts_part_id = ?");
		getURLS.setInt(1, partID);

		ResultSet urls = getURLS.executeQuery();
		while (urls.next()) {
			if (urls.getString("site_id").equals("a")) {
				aLink.setText(urls.getString("url"));
			} else if (urls.getString("site_id").equals("n")) {
				nLink.setText(urls.getString("url"));
			}
		}
	}
	
	@FXML 
	private void openALink(ActionEvent event) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI(aLink.getText()));
	}
	
	@FXML 
	private void openNLink(ActionEvent event) throws IOException, URISyntaxException {
		Desktop.getDesktop().browse(new URI(nLink.getText()));
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
