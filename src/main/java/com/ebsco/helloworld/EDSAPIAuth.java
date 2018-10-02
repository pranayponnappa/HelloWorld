package com.ebsco.helloworld;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Dictionary;
import java.util.Hashtable;

import javax.xml.parsers.*;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class EDSAPIAuth {
		
	public String userId;
	public String password;
	

	
    public EDSAPIAuth(String userId, String password) {
        
        this.userId = userId;
        this.password = password;
    }
	
	public Dictionary<String, String> postAuth() throws Exception {
			
			URL url = new URL("http://edsapi.ads.ade.epnet.com/authservice/rest/uidauth");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			String authRequest = "<UIDAuthRequestMessage xmlns=\"http://www.ebscohost.com/services/public/AuthService/Response/2012/06/01\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" + 
					"  <UserId>"+userId+"</UserId>\r\n" + 
					"  <Password>"+password+"</Password>\r\n" + 
					"  <InterfaceId>edsapi_console</InterfaceId>\r\n" + 
					"</UIDAuthRequestMessage>";
			try {
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/xml");
				con.setRequestProperty("Content-Length", Integer.toString(authRequest.length()));
				con.setDoOutput(true);
				con.getOutputStream().write(authRequest.getBytes("UTF8"));

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
					System.out.println(content);
					Document doc = DocumentBuilderFactory.newInstance()
							.newDocumentBuilder().parse(new InputSource(new StringReader(content.toString())));
					String authToken = doc.getElementsByTagName("AuthToken").item(0).getTextContent();
					String authTimeout = doc.getElementsByTagName("AuthTimeout").item(0).getTextContent();
					Dictionary<String, String> authResponse = new Hashtable<String, String>();
					authResponse.put("AuthToken", authToken);
					authResponse.put("AuthTimeout", authTimeout);
					return authResponse;
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
