package files;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
public class DynamicJson {
	
	@Test(dataProvider = "BooksData")
	public void addBook(String isbn, String aisle)
	{
		RestAssured.baseURI = "http://216.10.245.166";
		String response= given().header("Content-Type", "application/json")
		.body(payload.addbook(isbn,aisle)).when().post("Library/Addbook.php").then().assertThat().statusCode(200).extract().response().asString();
		
		JsonPath js = reusableMethods.rawTojson(response);
		
		String ID=js.get("ID");
		System.out.println(ID);
		
		//deleteBook
	}
	
	
	
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		//array = collection of elements
		//multidimensional array = collection of arrays
		 return new Object[][]
				{
			      {
			    	  "errgg","78798"
			      },
			      { 
			    	  "gfgfg","5765"
			      },
			      {
			    	  "tgfgfg","576433"
			      }
				};
	}

}
