package oAuth;



import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;



import Pojo.Api;
import Pojo.GetCourse;
import Pojo.WebAutomation;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
public class oAuthTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//System.setProperty("webdriver.chrome.driver", "F:\\eclipse\\Rishabh_Rana\\APIDemoProject\\driver\\chromedriver.exe");
		System.setProperty("webdriver.gecko.driver","F:\\eclipse\\Rishabh_Rana\\APIDemoProject\\driver\\geckodriver.exe");
		WebDriver driver =  new FirefoxDriver();
		
		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
		//enter email and pass
		
		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("aryanrana0008@gmail.com");
		
		driver.findElement(By.xpath("//span[contains(text(),'Next')]")).click();
		
		driver.findElement(By.xpath("//input[@type='Passward']")).sendKeys("@Coolone420");
		
		String url =driver.getCurrentUrl();
		
		String partialcode =url.split("code=")[1];
		String code = partialcode.split("&scope")[0];
		
		System.out.println(code);
		
		
		
		
		
		String accessTokenresponse =given().urlEncodingEnabled(false)
		.queryParam("code", code)
		.queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
		.queryParam("grant_type", "authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenresponse);
		String accesstoken=js.getString("access_token");
		
		
		
		GetCourse gc = given().queryParam("access token", accesstoken).expect().defaultParser(Parser.JSON)
				.when()
		.get("https://rahulshettyacademy.com/getCourse.php").as(GetCourse.class);
		
		//System.out.println(response);
		
		System.out.println(gc.getLinkedIn());
		
		
		//get price for Api course named as "SOAPUI"
	List<Api> apicourseTitle =  gc.getCourses().getApi();
		
		
		for(int i =0; i<apicourseTitle.size(); i++)
		{
			if(apicourseTitle.get(i).getCourseTitle().equalsIgnoreCase("SOAPUI"))
				
			{
				System.out.println(apicourseTitle.get(1).getPrice());
			}
		}
		
		
		//Get title for WebAutomation courses
		
		ArrayList<String> title = new ArrayList<String>();
		
		List<WebAutomation> webAutocoursetitle = gc.getCourses().getWebAutomation();
		
		for(int j=0; j<=webAutocoursetitle.size(); j++)
		{
			System.out.println(webAutocoursetitle.get(j).getCourseTitle());
			title.add(webAutocoursetitle.get(j).getCourseTitle());	
			
			System.out.println(title);
			
		}
		
	}

}
