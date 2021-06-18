import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.File;
import java.nio.file.*;

import org.codehaus.groovy.control.BytecodeProcessor;
import org.testng.Assert;

import com.google.common.io.ByteProcessor;
import com.google.common.io.Files;

import files.payload;
import files.reusableMethods;
public class Basic {

	public static void main(String[] args)
	{
//validate if add place API is working as expected
//Add place--> update place with new address --> get place to validate if new address is present in response		
		//add place
		//given- all input details
		//when - submit the API - resource,http method
		//Then - validate the response
		//content of the file to String -> content of file can convert into Byte -> Byte data to String
		
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("Key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().assertThat().statusCode(200).body("scope",equalTo("APP")).header("server", "Apache/2.4.18 (Ubuntu)").extract().response().asString();
		
		
		System.out.println(response);
		
		JsonPath js = reusableMethods.rawTojson(response);//for parsing Json
		String placeID = js.getString("place_id");
		System.out.println(placeID);
		
		
		
		//update place
		String newAdd = "70  walk, USA";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n"
				+ "\"place_id\":\""+placeID+"\",\r\n"
				+ "\"address\":\""+newAdd+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		System.out.println("address updated");
		//Get place

		String getPlaceResponse=	given().log().all().queryParam("key", "qaclick123")
				.queryParam("place_id",placeID)
				.when().get("maps/api/place/get/json")
				.then().assertThat().log().all().statusCode(200).extract().response().asString();
		
		System.out.println(getPlaceResponse);
		JsonPath js1 = reusableMethods.rawTojson(getPlaceResponse);
		String actualadd = js1.getString("address");
		System.out.println(actualadd);
		Assert.assertEquals(actualadd, newAdd);
		
	}
}
