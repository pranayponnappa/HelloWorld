package com.ebsco.helloworld;

import java.util.Dictionary;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

@RestController
public class EDSAPIController {

    @RequestMapping("/vtest")
    public String vtest() throws Exception {
    	EDSAPIVtest vtest = new EDSAPIVtest();
    	return vtest.GetVTest();
    }

    @RequestMapping("/edsapiauth")
    public Dictionary<String, String> edsapiauth(@RequestParam(value="UserId", defaultValue="logigearqa!") String userId,
    		@RequestParam(value="password", defaultValue="password") String password) throws Exception {
    	EDSAPIAuth auth = new EDSAPIAuth(userId, password);
    	return auth.postAuth();
    }
    
    @RequestMapping("/session")
    public Dictionary<String, String> session(@RequestParam(value="profileId", defaultValue="edsapi") String profileId) throws Exception {
    	EDSAPICreateSession createSession = new EDSAPICreateSession(profileId);
    	return createSession.getSession();
    }
    
    @RequestMapping("/search")
    public Document search(@RequestParam(value="searchTerm", defaultValue="search") String searchTerm) throws Exception {
    	EDSAPISearch search = new EDSAPISearch(searchTerm);
    	return search.getSearch();
    }
}