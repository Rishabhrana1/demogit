package JIRAAPI;




import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class jiratest {

	public static void main (String[] args)
	{
		RestAssured.baseURI = "http://localhost:8080";
		
		//login scenerio
		SessionFilter session = new SessionFilter();
		
		String response =given().header("content-Type", "application/json").body("{ \r\n"
				+ "    \"username\": \"aryanrana0008\", \r\n"
				+ "\"password\": \"@Jira123\" \r\n"
				+ "}").log().all().filter(session).when().post("/rest/auth/1/session").then().log().all().extract().response().asString();
		
		
		
		//add comment
		String comment = "hi new one?";
		String addcommentresponse=given().pathParam("id","10105").log().all().header("content-Type", "application/json").body("{\r\n"
				+ "    \"body\": \""+comment+"\",\r\n"
				+ "    \"visibility\": {\r\n"
				+ "        \"type\": \"role\",\r\n"
				+ "        \"value\": \"Administrators\"\r\n"
				+ "    }\r\n"
				+ "}").filter(session).when().post("/rest/api/2/issue/{id}/comment").then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		JsonPath js = new JsonPath(addcommentresponse);
		String commentID= js.getString("id");
		
		
		
		//add attachment
		given().header("X-Atlassian-Token","no-check").filter(session).pathParam("id","10105").header("content-Type", "multipart/form-data")
		.multiPart("file", new File("jira.txt")).when().post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);
		
		
		//get issue
		String issueDetails= given().filter(session).pathParam("key", "10105").queryParam("fields", "comment")
				.log().all().when().get("/rest/api/2/issue/{key}").then().log().all().extract().response().asString();
		System.out.println(issueDetails);
		
		JsonPath js1 = new JsonPath(issueDetails);
		int commentsCount = js1.get("fields.comment.comments.size()");
		
		for(int i=0; i<commentsCount; i++)
		{
			//System.out.println(js1.getInt("fields.comment.comments["+i+"].id"));
			String commentidissue=js1.get("fields.comment.comments["+i+"].id").toString();
			if(commentidissue.equals(commentID))
			{
				String issuecomment = js1.get("fields.comment.comments["+i+"].body").toString();
				System.out.println(js1.get("fields.comment.comments["+i+"].body").toString());
				
				Assert.assertEquals(issuecomment, comment);
			}
		}
	}
	
}
