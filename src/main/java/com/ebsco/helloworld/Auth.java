package com.ebsco.helloworld;

public class Auth {
	
	public String authToken;
	
    public Auth(String authToken) {
        this.authToken = authToken;
    }

    public String getAuthToken() {
        return authToken;
    }
}
