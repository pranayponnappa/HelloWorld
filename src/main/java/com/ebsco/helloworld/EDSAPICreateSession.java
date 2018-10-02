package com.ebsco.helloworld;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class EDSAPICreateSession {
	
	public String profileId;
	
	public EDSAPICreateSession(String profileId) {
		this.profileId = profileId;
	}

	public Dictionary<String, String> getSession() throws Exception {
		EDSAPIAuth auth = new EDSAPIAuth("logigearqa!", "password");
		Dictionary<String, String> authResponse = auth.postAuth();
		System.out.println("\nAuthToken: " +authResponse);
		String authToken = authResponse.get("AuthToken");
		String authTimeout = authResponse.get("AuthTimeout");
		System.out.println("\nAuthToken: " +authToken);
		URL url = new URL("http://edsapi.ads.ade.epnet.com/edsapi/rest/CreateSession?profile="+profileId+"&Guest=n");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try {
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("x-authenticationToken", authToken);
			con.setDoOutput(true);

			int status = con.getResponseCode();
			if(status == 200) {
				BufferedReader in = new BufferedReader(
						  new InputStreamReader(con.getInputStream()));
				String inputLine;
				StringBuffer content = new StringBuffer();
				while ((inputLine = in.readLine()) != null) {
				    content.append(inputLine);
				}
				in.close();
				Document doc = DocumentBuilderFactory.newInstance()
						.newDocumentBuilder().parse(new InputSource(new StringReader(content.toString())));
				String sessionToken = doc.getElementsByTagName("SessionToken").item(0).getTextContent();				
				Dictionary<String, String> sessionResponse = new Hashtable<String, String>();
				sessionResponse.put("AuthToken", authToken);
				sessionResponse.put("AuthTimeout", authTimeout);
				sessionResponse.put("SessionToken", sessionToken);
				
				return sessionResponse;
			}
			else {
				throw new Exception("HTTP STATUS NOT OK, STATUS WAS : " +status);
			}
			
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		finally {
			con.disconnect();
		}
		return null;
	}
	
}
