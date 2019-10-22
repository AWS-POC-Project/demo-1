package com.cfg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/orchestration")
public class Producer {
	
	@Autowired
	private KafkaTemplate<String, PaymentProcess> kafkaTemplate;
	
    @PostMapping("/publish")
    public String publish(@RequestBody PaymentProcess paymentProcess) {

        kafkaTemplate.send(Constants.topic,paymentProcess);
        return Constants.response;
    }  
}
