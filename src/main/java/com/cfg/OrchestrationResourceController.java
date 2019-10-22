package com.cfg;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class OrchestrationResourceController {

	@Autowired
	RestTemplate restTemplate;

	@KafkaListener(topics = Constants.topic, groupId = Constants.kakfaGroupJSON, containerFactory = Constants.kakfaContainerFactory)
	public boolean processStep(PaymentProcess paymentProcess) throws Exception {

		// Orchestrator
		String respStr = "";
		String eventName = "RequestEvent";
		System.out.println("Invoking Orchestration Controller");

		do {
			CloseableHttpClient client = HttpClients.createDefault();

			URIBuilder builder2 = new URIBuilder(Constants.process_resource);
			builder2.setParameter("eventName", eventName);
			HttpPut httpPut = new HttpPut(builder2.build());
			CloseableHttpResponse response = client.execute(httpPut);
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));

			String output;
			respStr = "";
			while ((output = br.readLine()) != null) {
				respStr = respStr + output;
			}
			client.close();

			System.out.println("Next step is " + respStr);
			if (!respStr.equals(state.End.toString())) {
				String url = Constants.base_url + respStr;
				try {
					ResponseEntity<PaymentProcess> newPaymentProcess = restTemplate.postForEntity(url, paymentProcess,
							PaymentProcess.class);
					paymentProcess = newPaymentProcess.getBody();
					eventName = respStr + "Event";
				} catch (HttpServerErrorException e) {
					return false;
				}
			}
		} while (!respStr.equals(state.End.toString()));
		return true;
	}

}
