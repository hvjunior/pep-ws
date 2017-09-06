package br.com.cpqd.auth;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.JSONObject;
import org.w3c.dom.Document;

@Path("/entitlement")
public class AuthorizationAPIS {
	@POST
	@Path("/decision")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_XML)
	public Response verifyRESTService(InputStream incomingData) {
		StringBuilder resultBuilder = new StringBuilder();
		String resultString = null;

		try {
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				resultBuilder.append(line);
			}

			// Convert the incomingData to JSON
			JSONObject incomingDataJSON = new JSONObject(resultBuilder.toString());

			// Extract the values of action, resource and accessSubject
			String action = incomingDataJSON.getJSONObject("Request").getJSONObject("Action").getJSONArray("Attribute").getJSONObject(0).getString("Value");
			String resource = incomingDataJSON.getJSONObject("Request").getJSONObject("Resource").getJSONArray("Attribute").getJSONObject(0).getString("Value");
			String accessSubject = incomingDataJSON.getJSONObject("Request").getJSONObject("AccessSubject").getJSONArray("Attribute").getJSONObject(0).getString("Value");

			// Get the permission in Database
			Connect connect = new Connect();
	        Boolean result = connect.getPermission(action, resource, accessSubject);
	        connect.closeConnection();

	        if (result) {
	        	resultString = "<Response>"
						+ "<Result>"
						+ "<Decision>Permit</Decision>"
						+ "</Result>"
						+ "</Response>";
	        } else {
	        	resultString = "<Response>"
						+ "<Result>"
						+ "<Decision>Deny</Decision>"
						+ "</Result>"
						+ "</Response>";
	        }
			
			Document resultXML = Utils.convertStringToXML(resultString);
			
			if (resultXML != null) {
				return Response.status(200).entity(resultXML).build();
			} else {
				return Response.status(500).entity("Parsing XML error!").build();
			}			
		} catch (Exception e) {
			return Response.status(500).entity(e.toString()).build();
		}
	}
}
