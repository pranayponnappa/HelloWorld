package com.ebsco.helloworld;

import java.io.*;
import java.net.*;

public class EDSAPIVtest {
	
	public String GetVTest() throws Exception {
		
		URL url = new URL("http://edsapi.ads.ade.epnet.com/edsapi/private/vtest?level=2&version=2");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try {
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/json");
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
