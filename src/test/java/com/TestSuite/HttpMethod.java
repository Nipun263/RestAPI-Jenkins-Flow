package com.TestSuite;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.util.List;
import java.util.Properties;
import org.hamcrest.Matchers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.XML;

import PojoClass.Datum;
import PojoClass.PojoExtractor;

import static io.restassured.module.jsv.JsonSchemaValidator.*;
import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapper;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;
import junit.framework.Test;


public class HttpMethod {
	
	Properties PRP ;
	
	public HttpMethod(Properties PRP) {
		this.PRP = PRP;
	}
	
	public Response Post_Generate_Token(String URI) {
		
		String URI_Key = PRP.getProperty(URI);
		
		Response Res = given().formParam("client_id", "Nipun_APP")
				.formParam("client_secret",  "71edede65b76eba8f6836676a30b60f6")
				.formParam("grant_type", "client_credentials")
				.when()
				.post(URI_Key  + "token");
		
		
		String Token = Res.jsonPath().getString("access_token");
		
		System.out.println(Token);
		
		return Res;
	}

	
	
	
	public void PostRequest_UnlockBarn(String URI) {
		
		
		String BearerToken = Post_Generate_Token(URI).jsonPath().get("access_token");
		
		String URI_Key = PRP.getProperty(URI) + "api/2060/barn-unlock";
		
		
		Response Res = given()
				.contentType(ContentType.JSON)
				.auth()
				.oauth2(BearerToken)
				.when()
				.post(URI_Key);
		
		//System.out.println(Res.asPrettyString());
		
		//System.out.println(Res.statusCode());
		
		String message = Res.jsonPath().getString("message");
		
		System.out.println(message);
		
		
	}
	
	
	public Response PostRequest(String BodyData , String URI , String PathParm ) {
		String URI_Key = PRP.getProperty(URI) + PathParm;
		
		Response Res = given().contentType(ContentType.JSON).body(BodyData).when().post(URI_Key);
		
		System.out.println("***************Post Response********************");
		
		System.out.println(Res.asPrettyString());
		
		System.out.println("***************Post Status Code*****************");
		
		System.out.println(Res.statusCode());
		
		
		
		return Res;
	}
	
	
	public Response GetRequest(String URI , String PathParm , String ExpectedJSONSchema) {
		String URI_Key = PRP.getProperty(URI) + PathParm;
		
		Response Res = given().contentType(ContentType.JSON).when().get(URI_Key);
		
		//Scehma Validations 
		
		Res.then().assertThat().body(matchesJsonSchema(ExpectedJSONSchema));
		
		//Response Extractor using Pojo Class
		
		PojoExtractor PE = Res.as(PojoExtractor.class,ObjectMapperType.GSON);
		
		int page = PE.getPage();
		int total = PE.getTotal();
		
		//System.out.println("Page" + "  " + page);
		//System.out.println("total" + "  " + total);
		
		List<Datum> Datum_Data = PE.getData();
		
		for(Datum data : Datum_Data) {
			
			String EmailID = data.getEmail();
		//System.out.println("Email Extracted" + "   " + EmailID);
		}
		
		
		// Response Extractor using JSON_Path 
		
		String FirstName = Res.jsonPath().getString("data[0].first_name").toString();
		
		System.out.println("FirstName" + "   " + FirstName);
		
		// Response Extractor using org.json
		
		JSONTokener JT = new JSONTokener(Res.asPrettyString());
		
		JSONObject OuterObj = new JSONObject(JT);
		
		JSONArray array = OuterObj.getJSONArray("data");
		
		for(int i =0;i<array.length();i++) {
		
		JSONObject Objt = array.getJSONObject(i);
		
		String First_Name = Objt.getString("first_name").toString();
		
		System.out.println("FirstName ::" +  "   " + First_Name);
		
		}
		
		
		
		return Res;
	}
	
	
	public Response PutRequest(String BodyData , String URI , String PathParm) {
		String URI_Key = PRP.getProperty(URI) + PathParm;
		
		Response Res = given().contentType(ContentType.JSON).body(BodyData).when().post(URI_Key);
		
		System.out.println("***************Put Response********************");
		
		System.out.println(Res.asPrettyString());
		
		System.out.println("***************Put Status Code*****************");
		
		System.out.println(Res.statusCode());
		
				
		return Res;
	}
	
	

	public Response DeleteRequest(String URI , String PathParm) {
		String URI_Key = PRP.getProperty(URI) + PathParm;
		
		Response Res = given().contentType(ContentType.JSON).when().get(URI_Key);
		
		System.out.println("***************Delete Response********************");
		
		System.out.println(Res.asPrettyString());
		
		System.out.println("***************Delete Status Code*****************");
		
		System.out.println(Res.statusCode());
				 
		return Res;
	}



	public void FileUpload(String URI ) {
		
		String URI_Key = PRP.getProperty(URI);
		
		String FileToUpload = System.getProperty("user.dir") + "\\ABCD.txt";
		
		File file = new File(FileToUpload);
		
		Response Res = given()
				.multiPart("File" ,file,"")
				.when()
				.post("https://the-internet.herokuapp.com/upload");
		
		System.out.println(Res.asPrettyString());
		System.out.println(Res.statusCode());
		
				
	}



}
