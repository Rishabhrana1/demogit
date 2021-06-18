package oAuth;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import Pojo.addPlace;
import Pojo.Location;
public class serializeTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		List<String> myList = new ArrayList<String>();
		myList.add("shoe park");
		myList.add("shop");
		
	Location l = new Location();
	l.setLat(-38.383494);
	l.setLng(33.427362);
	
		addPlace p = new addPlace();
		p.setAccuracy(50);
		p.setName("Frontline house");
		//p.setPhn("(+91) 983 893 3937");
		p.setWebsite("http://google.com");
		//p.setLang("French-IN");
		p.setAddress("29, side layout, cohen 09");
		//p.setType(myList);
		p.setLocation(l);
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		String Response = given().log().all().queryParam("key", "qaclick123").body(p)
		.when().post("/maps/api/place/add/json").then().assertThat().statusCode(200).extract().response().asString();
		
		System.out.println(Response);
		
		
	}

}
