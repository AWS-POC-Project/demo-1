package com.cfg;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.HttpException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@RestController
public class OrchestrationResourceController {

	@Autowired
	RestTemplate restTemplate;
	
	@Autowired
	private org.springframework.core.env.Environment environment;
	
	@RequestMapping(path = "/orchestration-execute", method = RequestMethod.POST)
	public String processStep(@RequestParam String eventName,@RequestBody PaymentProcess paymentProcess) throws Exception {
		
		String respStr="";
		System.out.println("Invoking Orchestration Controller");
		
		do {
		//Orchestrator
		CloseableHttpClient client = HttpClients.createDefault();

		URIBuilder builder2 = new URIBuilder("http://localhost:8080/process-resource/");
		builder2.setParameter("eventName", eventName);
		HttpPut httpPut = new HttpPut(builder2.build());
		CloseableHttpResponse response = client.execute(httpPut);
		BufferedReader br = new BufferedReader(
                new InputStreamReader((response.getEntity().getContent())));

		String output;
		respStr = "";
		while ((output = br.readLine()) != null) {
			respStr = respStr + output;
		}
		client.close();
		
		System.out.println("Next step is " + respStr);
		if(!respStr.equals(state.End.toString())) {
		String url="http://localhost:8080/"+respStr;
		try {
		ResponseEntity<PaymentProcess> newPaymentProcess=restTemplate.postForEntity(url, paymentProcess, PaymentProcess.class);
		paymentProcess=newPaymentProcess.getBody();
		eventName=respStr+"Event";
		}
		catch(HttpServerErrorException e) {
			return "FAILURE";
		}
		}
		}while(!respStr.equals(state.End.toString()));
		return "SUCCESS";
	}

}
