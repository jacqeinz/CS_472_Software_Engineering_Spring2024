package org.eclipse.jakarta.model.entity;

import java.util.random.RandomGenerator;

public class Confirmation {
	
	private Cart conCart;
	private String conID;
	
	public Confirmation(Cart cart) {
		this.conCart=cart;
		conID=randomGenerator();
		
	}
	
	public String randomGenerator() {
		String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
		         + "0123456789"
		         + "abcdefghijklmnopqrstuvxyz";
		StringBuilder sb = new StringBuilder(8);
		for (int i = 0; i < 9; i++) {  
			   int index = (int)(AlphaNumericString.length()* Math.random());
			   sb.append(AlphaNumericString.charAt(index)); 
			  } 
			 
			  return sb.toString(); 
		
		
	}

}
