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
		String url = "jdbc:postgresql://localhost:5432/postgres";
		String user = "postgres";
		String password = "postgres";

		Class.forName("org.postgresql.Driver").newInstance();
		con = DriverManager.getConnection(url, user, password);

		con.setSchema("dojot");
	}

	public void closeConnection() throws SQLException {
		con.close();
	}
	
	public Boolean getPermission(String action, String resource, String accessSubject) {

		Boolean result = null;
		try {

			PreparedStatement preparedStatement = con
					.prepareStatement("select * from authorization where action = ? and resource = ? and accessSubject = ?");
			preparedStatement.setString(1, action);
			preparedStatement.setString(2, resource);
			preparedStatement.setString(3, accessSubject);

			ResultSet rs = preparedStatement.executeQuery();

			if (rs.next()) {
				result = true;
			} else {
				result = false;
			}				
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(Connect.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
			result = false;
		}
		return result;
	}
}