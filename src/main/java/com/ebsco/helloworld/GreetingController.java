package com.ebsco.helloworld;

import java.util.Dictionary;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
    
    @RequestMapping("/vtest")
    public EDSAPIVtest vtest() {
    	return new EDSAPIVtest("");
    }
    
    @RequestMapping("/auth")
    public Dictionary<String, String> auth(@RequestParam(value="UserId", defaultValue="logigearqa!") String userId,
    		@RequestParam(value="password", defaultValue="password") String password) throws Exception {
    	EDSAPIAuth auth = new EDSAPIAuth(userId, password);
    	return auth.postAuth();
    }
    
    @RequestMapping("/session")
    public Dictionary<String, String> session(@RequestParam(value="profileId", defaultValue="edsapi") String profileId) throws Exception {
    	EDSAPICreateSession createSession = new EDSAPICreateSession(profileId);
    	return createSession.getSession();
    }
}