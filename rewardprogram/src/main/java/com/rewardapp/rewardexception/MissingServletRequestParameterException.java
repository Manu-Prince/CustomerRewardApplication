package com.rewardapp.rewardexception;

public class MissingServletRequestParameterException extends RuntimeException{

		public MissingServletRequestParameterException(String message) {
			super(message);
		}
}	
	