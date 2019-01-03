package com.ebsco.helloworld;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Dictionary;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class EDSAPISearch {
	
	public String searchTerm;
	
	public EDSAPISearch(String searchTerm) {
		this.searchTerm = searchTerm;
	}

	public Document getSearch() throws Exception {
		EDSAPICreateSession session = new EDSAPICreateSession("edsapi");
		Dictionary<String, String> sessionResponse = session.getSession();
		String authToken = sessionResponse.get("AuthToken");
		String sessionToken = sessionResponse.get("SessionToken");
		
		URL url = new URL("http://edsapi.ads.ade.epnet.com/edsapi/rest/Search?query=cats&searchmode=all&resultsperpage=20&pagenumber=1&sort=relevance&highlight=y&includefacets=y&view=brief&autosuggest=n&autocorrect=n&includeimagequickview=n");
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		try {
			con.setRequestMethod("GET");
			con.setRequestProperty("Content-Type", "application/xml");
			con.setRequestProperty("x-authenticationToken", authToken);
			con.setRequestProperty("x-sessionToken", sessionToken);
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
				
				return doc;
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
