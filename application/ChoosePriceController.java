package application;

//This class is in charge of ChoosePrice.fxml - the user can either pick a tier or their desired pricepoint
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ChoosePriceController {

	@FXML
	private Button bHigh;

	@FXML
	private Button bLow;

	@FXML
	private Button bMid;

	@FXML
	private Button bSubmit;

	@FXML
	private TextField tfDesiredPrice;

	// If the user picks a tier, saves said selection and moves on to ChooseGames
	// scene
	@FXML
	public void changeToGames(ActionEvent event) throws IOException {

		if (event.getSource().equals(bLow)) {
			ChooseGameController.setTier(0);
		} else if (event.getSource().equals(bMid)) {
			ChooseGameController.setTier(9);
		} else if (event.getSource().equals(bHigh)) {
			ChooseGameController.setTier(18);
		}

		SceneHandler.setPreviousScene("ChoosePrice.fxml");
		Parent chooseGamesParent = FXMLLoader.load(getClass().getResource("ChooseGames.fxml"));
		Scene chooseGamesScene = new Scene(chooseGamesParent);

		Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
		window.setScene(chooseGamesScene);
		window.show();
	}

	// If the user enters a price, instead moves on to CalculateBuild and finds it
	// directly
	@FXML
	public void changeToLists(ActionEvent event) throws IOException {

		try {
			if (Double.parseDouble(tfDesiredPrice.getText()) != 0) {

				double desiredPrice = Double.parseDouble(tfDesiredPrice.getText());
				CalculateBuild c1 = new CalculateBuild();
				int recListID = c1.getRecListID(desiredPrice);

				//List.lastLID = recListID;
				ChooseBuildController.initialLID = recListID;

				SceneHandler.setPreviousScene("ChoosePrice.fxml");
				Parent chooseBuildParent = FXMLLoader.load(getClass().getResource("ChooseBuild.fxml"));
				Scene chooseBuildScene = new Scene(chooseBuildParent);

				Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
				window.setScene(chooseBuildScene);
				window.show();

			}
		} catch (Exception e1) {
			e1.printStackTrace();
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
