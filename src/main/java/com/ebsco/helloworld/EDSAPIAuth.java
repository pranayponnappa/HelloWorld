package com.ebsco.helloworld;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.*;

import org.w3c.dom.Document;

public class EDSAPIAuth {
		
	public String response;
	
	public String getResponse() {
		try {
			return postAuth();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    public EDSAPIAuth(String response) {
        
        this.response = response;
    }
	
	public String postAuth() throws Exception {
			
			URL url = new URL("http://edsapi.ads.ade.epnet.com/authservice/rest/uidauth");
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			String doc = "<UIDAuthRequestMessage xmlns=\"http://www.ebscohost.com/services/public/AuthService/Response/2012/06/01\" xmlns:i=\"http://www.w3.org/2001/XMLSchema-instance\">\r\n" + 
					"  <UserId>logigearqa!</UserId>\r\n" + 
					"  <Password>password</Password>\r\n" + 
					"  <InterfaceId>edsapi_console</InterfaceId>\r\n" + 
					"</UIDAuthRequestMessage>";
			try {
				con.setRequestMethod("POST");
				con.setRequestProperty("Content-Type", "application/xml");
				con.setRequestProperty("Content-Length", Integer.toString(doc.length()));
				con.setDoOutput(true);
				con.getOutputStream().write(doc.getBytes("UTF8"));

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
					return content.toString();
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
