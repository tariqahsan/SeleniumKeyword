package org.isb.training.selenium;

public class Result {
	
	private boolean result;
	private String message;
	
	public Result() {}

	public Result(boolean result, String message) {
		super();
		this.result = result;
		this.message = message;
	}

	public boolean isResult() {
		return result;
	}

	public void setResult(boolean result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "Result [result=" + result + ", message=" + message + "]";
	}

}
