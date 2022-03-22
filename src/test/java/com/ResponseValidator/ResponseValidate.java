package com.ResponseValidator;

import org.testng.Assert;

import io.restassured.response.Response;

public class ResponseValidate {
	
	
	public static int  validateStatusCode(Response Res , int ExpectedStatusCode) {
		
		int ActualStatusCode = Res.getStatusCode();
		
		int aa = ExpectedStatusCode ;
		
		Assert.assertEquals(ActualStatusCode, ExpectedStatusCode);
		Assert.assertEquals(ActualStatusCode, ExpectedStatusCode);
		
		return ActualStatusCode;
		
	}
	
	public static int  validateHeaderCount(Response Res , int ExpectedHeaderCode) {
		
		int ActualHeaderCode = Res.getHeaders().size();
	
		Assert.assertEquals(ActualHeaderCode, ExpectedHeaderCode);
		
		return ActualHeaderCode;
		
	}

}
