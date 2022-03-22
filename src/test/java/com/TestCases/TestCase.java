package com.TestCases;

import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.Test;

import com.ResponseValidator.ResponseValidate;
import com.TestSuite.HttpMethod;
import com.Utility.JSONReader;
import com.Utility.PropertyFileReader;

import io.restassured.response.Response;

public class TestCase {

	
	
	@Test(priority=-1)
	public void TestCase_Generate_Token() throws IOException {

		Properties PRP = PropertyFileReader.ReadPropertyFile();

		HttpMethod HTM = new HttpMethod(PRP);
				
		Response Res = HTM.Post_Generate_Token("Coops");
		
		
	}

	@Test(priority =-99)
	public void TestCase_Unlock() throws IOException {
		
		Properties PRP = PropertyFileReader.ReadPropertyFile();
		
		HttpMethod HTM = new HttpMethod(PRP);
		
		//HTM.PostRequest_UnlockBarn("Coops");
		
		HTM.FileUpload("Herouapp");
	}
	
	

	@Test(priority = 1)
	public void TestCase_PostRequest() throws IOException {

		Properties PRP = PropertyFileReader.ReadPropertyFile();

		String JSONBodyData = JSONReader.ReadJSONFile("Post.json", "Resources\\");
		
		HttpMethod HTM = new HttpMethod(PRP);

		Response Res = HTM.PostRequest(JSONBodyData, "ReqRes", "users");
	
		ResponseValidate.validateStatusCode(Res,201);
		
		ResponseValidate.validateHeaderCount(Res, 15);

	}

	@Test(priority = 2)
	public void TestCase_GETRequest() throws IOException {

		Properties PRP = PropertyFileReader.ReadPropertyFile();

		HttpMethod HTM = new HttpMethod(PRP);
		
		String ExpectedJSONSchema = JSONReader.ReadJSONFile("Post_ReqRes_Schema.JSON" , "Schema\\");
				
		Response Res = HTM.GetRequest("ReqRes", "users",ExpectedJSONSchema);
		
		ResponseValidate.validateStatusCode(Res,200);
		
		
	}

	@Test(priority = 3)
	public void TestCase_PutRequest() throws IOException {

		Properties PRP = PropertyFileReader.ReadPropertyFile();

		String JSONBodyData = JSONReader.ReadJSONFile("Put.json" , "Resources\\" );

		HttpMethod HTM = new HttpMethod(PRP);

		Response Res = HTM.PutRequest(JSONBodyData, "ReqRes", "users/2");
		
		ResponseValidate.validateStatusCode(Res,201);
		
		

	}

	@Test(priority = 4)
	public void TestCase_DeleteRequest() throws IOException {

		Properties PRP = PropertyFileReader.ReadPropertyFile();
		HttpMethod HTM = new HttpMethod(PRP);

		Response Res = HTM.DeleteRequest("ReqRes", "users/2");
		
		ResponseValidate.validateStatusCode(Res,200);

	}

}
