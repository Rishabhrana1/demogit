package oAuth;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import Pojo.addPlace;
import Pojo.Location;
public class SpecBuilder {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		//to create a json structure data using java
		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		
	Location l = new Location();
	l.setLat(-38.383494);
	l.setLng(33.427362);
	

		addPlace p = new addPlace();
		p.setAccuracy(50);
		p.setName("Frontline house");
		p.setPhn("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		p.setLang("French-IN");
		p.setAddress("29, side layout, cohen 09");
		p.setType(myList);
		p.setLocation(l);
		//RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		//using restassured to add place usinf requestSpecbuilder () class
		//RequestSpecification is used for giving request 
		//ResponseSpecification is used for getting response
		
		RequestSpecification req =new  RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();
	
		RequestSpecification Res = given().spec(req)
				.body(p);
		ResponseSpecification responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		
		Response response	= Res.when().post("/maps/api/place/add/json").then().spec(responseSpec).extract().response();
		
		System.out.println(response.asString());
		
		
	}

}
