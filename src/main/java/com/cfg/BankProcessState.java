package com.cfg;

public class BankProcessState {

	
	String name="";
	String postURL="";
	
	public BankProcessState(String name) {
		this.name=name;
		this.postURL="http://localhost:8080/"+ name;
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostURL() {
		return postURL;
	}

	public void setPostURL(String postURL) {
		this.postURL = postURL;
	}
	
	
	
}
