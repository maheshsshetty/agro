package agro.assignment;

import static com.jayway.restassured.RestAssured.given;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.hamcrest.Matchers;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeGroups;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import com.jayway.restassured.RestAssured;
import com.jayway.restassured.config.EncoderConfig;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.internal.path.json.mapping.JsonObjectDeserializer;
import com.jayway.restassured.matcher.ResponseAwareMatcher;
import com.jayway.restassured.module.jsv.JsonSchemaValidator;
import com.jayway.restassured.module.jsv.JsonSchemaValidatorSettings;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.response.ResponseBody;
import com.jayway.restassured.specification.RequestSpecification;



public class ApiTestSuite {
	
	private RequestSpecification requestSpec; 
	private String baseUrl="https://api.github.com";
	private String username, password,reponame,repoId="";
	@BeforeClass
	public void setup()
	{   Constants obj=new Constants();
		reponame=obj.repoName;
	    username=obj.username;
	    password=obj.password;
	    System.out.println("in CApuia : "+obj.repoName+" "+obj.username+" "+obj.password);
		requestSpec = given().auth().preemptive().basic(username, password);
		
	}
  @Test(dependsOnMethods={"agro.assignment.Setup.getInput"})
  public void createand_verify_repo() {
	   Response response;
	   String input="{\"name\": \""+reponame+"\","+
	   "\"description\": \"This 1is your first repository\","+
       "\"homepage\": \"https://github.com\","+
  "\"private\": false,"+
  "\"has_issues\": true,"+
  "\"has_projects\": true,"+
  "\"has_wiki\": true"+
  	 "\"}";
	   response=requestSpec.given().contentType(ContentType.JSON).body(input)
				.when().post(baseUrl+"/user/repos").then().extract().response();
	   JsonPath jp = new JsonPath( response.asString() );
	   repoId = jp.getString( "id" );
	   Response resposne1=requestSpec.given().get(baseUrl+"/users/"+username+"/repos");
	   Assert.assertEquals(resposne1.asString().contains(repoId) /*Expected value*/, true /*Actual Value*/, "Id Found Sucesssfully");
   
  }

  @Test(dependsOnMethods = {"createand_verify_repo"})
  public void starandverify() {
	  Response response;
	  response=requestSpec.given().put(baseUrl+"/user/starred/"+username+"/"+reponame);
	  Assert.assertEquals(204, response.getStatusCode());
	  response=requestSpec.given().get(baseUrl+"/users/"+username+"/starred");
	  Assert.assertEquals(response.asString().contains(reponame) /*Expected value*/, true /*Actual Value*/, "Name Found Sucesssfully");
	  Assert.assertEquals(response.asString().contains(reponame) /*Expected value*/, true /*Actual Value*/, "Id Found Sucesssfully");
  }

 
}
