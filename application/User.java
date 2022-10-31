package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

//backend for user 
public class User {

	private int list_id;
	private String listName;

	public User(int list_id, String listName) {
		this.list_id = list_id;
		this.listName = listName;
	}

	public User() {

	}

	public int getListID() {
		return this.list_id;
	}

	public String getListName() {
		return this.listName;
	}
	
	static boolean lIn = false;
	
	public void setLoggedIn(boolean iLN) {
		lIn = iLN;
	}
	public boolean isLoggedIn() {
		return lIn;
	}
	
	public static int login_id = 0;
	static boolean isAdmin = false;

	//Gets the login_id of the signed in username
	public int getLoginId(String enteredUsername, String enteredPassword) {
		String username = enteredUsername;
		String password = enteredPassword;
		String currentUsername = "";
		String currentPassword = "";
		boolean usernameExists = false;
	

		try {
			Connection con = DBFunctions.getConnection();
			PreparedStatement loginCheck = con.prepareStatement("SELECT * FROM login");

			ResultSet current = loginCheck.executeQuery();

			usernameExists = false;
			isAdmin = false;
			
			while (current.next()) {
				currentUsername = current.getString("username");
				//System.out.println(currentUsername);
				if (username.equals(currentUsername)) {
					usernameExists = true;
					currentPassword = current.getString("password");
					if (currentPassword.equals(password)) {
						login_id = current.getInt("login_id");
						setLoggedIn(true);
						if(current.getString("identifier").equals("a")) {
							isAdmin = true;
						}
					
					} else {
						login_id = 0;
					}
				}
			}
			if (usernameExists == false) {
				login_id = -1;
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return login_id;
	}

	//In RegistrationController.fxml - first checks the username exists if not saves into db
	public boolean getUsers(String enteredUsername, String enteredPassword, String ReenteredPassword) {

		String username = enteredUsername;
		String password = enteredPassword;
		String currentUsername = "";

		try {
			Connection con = DBFunctions.getConnection();
			PreparedStatement registering = con.prepareStatement("SELECT username FROM login WHERE username = ?");
			registering.setString(1, username);

			ResultSet current = registering.executeQuery();

			boolean usernameExists = false;

			while (current.next()) {
				currentUsername = current.getString("username");

				if (currentUsername.equalsIgnoreCase(username)) {
					usernameExists = true;

				}
			}

			if (usernameExists == true) {
				return false;
			} else {
				String userType = "u";
				PreparedStatement newUser = con.prepareStatement(
						"INSERT INTO login (username, password, identifier) VALUES ('" + username + "', '" + password + "', '" + userType + "')");
				newUser.executeUpdate();
				return true;

			}
		} catch (Exception e1) {
			System.out.println(e1);
		}
		return true;
	}

	
	//Returns an arrayList with the lists that the user owns (based on their login_id)
	public ArrayList<User> getUserLists(int login_id) {

		ArrayList<User> userLists = new ArrayList<User>();

		try {

			Connection con = DBFunctions.getConnection();
			PreparedStatement findLists = con.prepareStatement("SELECT * FROM list WHERE login_id = ?");
			findLists.setInt(1, login_id);

			ResultSet currentList = findLists.executeQuery();

			int i = 0;

			while (currentList.next()) {

				list_id = currentList.getInt("list_id");
				listName = currentList.getString("list_name");

				User u1 = new User(list_id, listName);
				userLists.add(i, u1);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		return userLists;
	}

}
