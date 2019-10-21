package com.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class WorkhorseController {
	
	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private Environment environment;
	

    @RequestMapping( path =  "/workitem-resource", method = RequestMethod.POST)
    public String processWorkitem() {
        return "Doing some work on request";
    }
    
    @RequestMapping( path =  "/Request", method = RequestMethod.POST)
    public String processRequest() {
    	System.out.println("Doing some request work on request");
        return "Doing some request work on request";
    }
    @RequestMapping( path =  "/Validate", method = RequestMethod.POST)
    public ResponseEntity<PaymentProcess> processValidate(@RequestBody PaymentProcess paymentProcess) throws Exception {
    	System.out.println("Invoking Validation Service");
    	String url="http://localhost:8082/send/fireRules";
    	try {
		ResponseEntity<PaymentProcess> newPaymentProcess=restTemplate.postForEntity(url, paymentProcess, PaymentProcess.class);
		System.out.println("Rules Executed");
        return newPaymentProcess;
    	}
    	catch(HttpClientErrorException e) {
    		throw new Exception( environment.getProperty("VALIDATION.InvalidData").toString());
    	}
			
		//System.out.println("Channel set to: "+newPaymentProcess.getBody().getChannel());
    }
    @RequestMapping( path =  "/Dispatch", method = RequestMethod.POST)
    public ResponseEntity<PaymentProcess> processDispatch(@RequestBody PaymentProcess paymentProcess) {
    	System.out.println("Triggering Email Notifications");
    	String url="http://localhost:8082/send/notify";
    	ResponseEntity<PaymentProcess> newPaymentProcess=restTemplate.postForEntity(url, paymentProcess, PaymentProcess.class);
        return newPaymentProcess;
    }

}