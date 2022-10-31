package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

//This class finds the recommended build based on the tier and games chosen by the user, or their inputed desired pricepoint
public class CalculateBuild {

	private double[] allPrices = new double[27];
	private double total = 0;
	int i = 1;

	// default constructor
	public CalculateBuild() {
	}

	// Finds recommended listID based on user choices of games and tier - returns
	// the recommended ListID
	public int getRecListID(double desiredPrice) throws Exception {

		for (int j = 0; j < 27; j++) {
			Connection con = DBFunctions.getConnection();
			PreparedStatement findParts = con.prepareStatement("SELECT * FROM list_parts WHERE list_id = ?");
			findParts.setInt(1, i);

			ResultSet currentList = findParts.executeQuery();

			while (currentList.next()) {

				PreparedStatement partInfo = con.prepareStatement("SELECT * FROM parts WHERE part_id = ?");
				partInfo.setInt(1, currentList.getInt("part_id"));

				ResultSet currentPart = partInfo.executeQuery();
				while (currentPart.next()) {
					total += currentPart.getDouble("msrp");
				}
			}
			allPrices[j] = total;
			i++;
			total = 0;
		}
		int recID = closest(desiredPrice, allPrices);
		return recID;
	}

	// Finds recommended listID based on the closest list price to the desired price
	// that is entered by the user
	private int closest(double desiredPrice, double[] allPrices) {

		double curr = allPrices[0];
		int toReturn = 0;
		double diff = Math.abs(desiredPrice - curr);
		for (int val = 0; val < allPrices.length; val++) {
			double newdiff = Math.abs(desiredPrice - allPrices[val]);
			if (newdiff < diff) {
				diff = newdiff;
				curr = allPrices[val];
				toReturn = val;
			}
		}
		return toReturn + 1;

	}
}
