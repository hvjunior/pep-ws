package br.com.cpqd.auth;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Connect {
	Connection con = null;

	public Connect() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		String url = "jdbc:postgresql://postgres:5432/dojot";
		String user = "postgres";
		String password = "postgres";

		Class.forName("org.postgresql.Driver").newInstance();
		con = DriverManager.getConnection(url, user, password);

		con.setSchema("dojot_authorization");
	}

	public void closeConnection() throws SQLException {
		con.close();
	}
	
	public Boolean getPermission(String action, String resource, String accessSubject) {

		Boolean result = null;
		try {

			PreparedStatement preparedStatement = con
					.prepareStatement("select resource from dojot_authorization.authorization where action = ? and accessSubject = ?");
			preparedStatement.setString(1, action);
			preparedStatement.setString(2, accessSubject);

			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				String resourceAuthorized = rs.getString("resource");
				
				if (resource.contains(resourceAuthorized)) {
					return true;
				}
			}
								
			return false;		
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Connect.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			return false;
		}
		return result;
	}
}