import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		JsonPath js = new JsonPath(payload.CoursePrice());
		
		int courseSize=js.getInt("courses.size()");
		System.out.println(courseSize);
		
		//print purchase amount
		int amount = js.getInt("dashboard.purchaseAmount");
		System.out.println(amount);
		
		//print title of the first course
		String firstcourseName = js.getString("courses[0].title");
		System.out.println(firstcourseName);
		
		//print all courses titles and their respective prices
		//Print no of copies sold by RPA Course 
		
		int i=0;
		while(i<courseSize)
		{
			System.out.println(i+"th course = "+js.getString("courses["+i+"].title") );
			System.out.println(i+"th course price = "+js.getInt("courses["+i+"].price"));
			
			if(js.get("courses["+i+"].title").equals("RPA"))
			{
				System.out.println((js.get("courses["+i+"].copies")).toString());
				break;
			}
			i++;
			
		}
		
		//Verify if Sum of all Course prices matches with Purchase Amount
		sumValidation.sumOfCourses();
	}

}
