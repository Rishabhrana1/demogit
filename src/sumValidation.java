import org.testng.Assert;

import files.payload;
import io.restassured.path.json.JsonPath;

public class sumValidation {
	
	public static void sumOfCourses()
	{
		
		JsonPath js = new JsonPath(payload.CoursePrice());
		int sum =0;
		int price =0;
		int copies =0;
		int count = js.getInt("courses.size()");
		for(int j=0; j<count; j++)
		{
			System.out.println("inside for loop");
			 price = js.getInt("courses["+j+"].price");
			 copies = js.getInt("courses["+j+"].copies");
			System.out.println(price);
			sum=sum+(price*copies);
			
		}
		
		Assert.assertEquals(sum, js.getInt("dashboard.purchaseAmount"));
		if(sum==js.getInt("dashboard.purchaseAmount"))
		{
			System.out.println("total price = "+sum);
			System.out.println("matched");
		}
	}

}
