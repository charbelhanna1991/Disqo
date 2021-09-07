package Default;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import resources.Base;

public class APIUpdatetUsers extends Base {

	
	@Test 
	public void UpdateUserPass() throws SQLException, IOException
	{
		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod()).header("Content-Type","application/json");
		
		
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint=1 ORDER BY RAND() limit 1");
	  	int id = 0;
	 	String name="",email="",gender="",status="";
	 	while (rs.next())
	 	{
	 		id=rs.getInt("id");
	 		name=rs.getString("name");
	 		email=rs.getString("email");
	 		gender=rs.getString("gender");
	 	}
	 	JSONObject json = postBody(id,name,email,gender,"Inactive");
		
		request.body(json.toJSONString());
		Response resp= request.put(getWeb()+"public/v1/users/"+id);
		int code = resp.getStatusCode();
		s.executeUpdate("update users set Status= 'Inactive' where id="+id);
		Assert.assertEquals(code, 200);
	} 
	
	
	@Test
	public void UpdateUserFail1() throws SQLException, IOException
	{
		

		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod())
				.header("Content-Type","application/json");
		
		
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint in (0,2) ORDER BY RAND() limit 1");
	 	int id = 0;
	 	String name="",email="",gender="",status="";
	 	while (rs.next())
	 	{
	 		id=rs.getInt("id");
	 		name=rs.getString("name");
	 		email=rs.getString("email");
	 		gender=rs.getString("gender");
	 		status=rs.getString("Status");
	 	}
	 	JSONObject json = postBody(id,name,email,gender,status);
		request.body(json.toJSONString());
		Response resp= request.put(getWeb()+"public/v1/users/"+id);
		int code = resp.getStatusCode();
		Assert.assertEquals(code, 404);

	} 
	
	@Test
	public void UpdateUserFail2() throws SQLException, IOException
	{

		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod())
				.header("Content-Type","application/json");
		
		
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs1= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint=1  ORDER BY RAND() limit 1");
	 	int id = 0;
	 	String name="",email="",gender="",status="";
	 	while (rs1.next())
	 	{
	 		id=rs1.getInt("id");
	 		name=rs1.getString("name");
	 		gender=rs1.getString("gender");
	 		status=rs1.getString("Status");
	 	}
	 	
	 	ResultSet rs2=s.executeQuery("SELECT  * FROM testingdb.users where id!=" + id + " and PostedByEndpoint=1  ORDER BY RAND() limit 1");
	 	
	 	while (rs2.next())
	 	{
	 		email=rs2.getString("email");
	 	}
	 	JSONObject json = postBody(id,name,email,gender,status);
		
		request.body(json.toJSONString());
		Response resp= request.put(getWeb()+"public/v1/users/"+id);
		int code = resp.getStatusCode();
		Assert.assertEquals(code, 422);
		
	} 
	
}


