package com.myRetail.bean;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Description {
private String title;

public Description() {
	
}


public Description(String title) {
	this.title = title;
}


public String getTitle() {
	return title;
}


public void setTitle(String title) {
	this.title = title;
}




}
