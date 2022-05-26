package com.TestSuite;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.ARRAY_MISMATCH_TEMPLATE;

import java.io.File;
import java.util.List;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import com.POJOClass.Datum;
import com.POJOClass.Example;

import io.restassured.http.ContentType;
import io.restassured.mapper.ObjectMapperType;
import io.restassured.response.Response;



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
		
		System.out.println(Res.asPrettyString());
		
		//JSON Parsing using JSONPath 
		
		String Total_Pages = Res.jsonPath().getString("total_pages").toString();
		
		String per_page = Res.jsonPath().getString("per_page").toString();
		
		String first_name = Res.jsonPath().getString("data[0].first_name").toString();
		
		//JSON Parsing using Org.JSON
		
		JSONTokener JT = new JSONTokener(Res.asPrettyString());
		
		JSONObject Obj = new JSONObject(JT);
		
		JSONArray array = Obj.getJSONArray("data");
		
		for(int i =0;i<array.length();i++) {
			
			String email = array.getJSONObject(i).getString("email").toString();
			
			//System.out.println(email);
		}
		
		//JSON Parsing using Pojo Class 
		
		Example EE = Res.as(Example.class, ObjectMapperType.GSON);
		
		List<Datum> DD =  EE.getData();
		
		for(Datum DF : DD) {
			String AA = DF.getEmail();
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
