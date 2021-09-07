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

public class APIDeleteUsers extends Base {

	@Test
	public void DeleteUserPass() throws SQLException, IOException
	{

		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod())
				.header("Content-Type","application/json");
		
		
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint=1 ORDER BY RAND() limit 1");
	 	
	 	int id = 0;
	 	
	 	while (rs.next())
	 	{
	 		id=rs.getInt("id");
	 	}

		Response resp= request.delete(getWeb()+"public/v1/users/"+id);
		int code = resp.getStatusCode();
		Assert.assertEquals(code, 204);
		int rs1 =s.executeUpdate("update users set PostedByEndpoint=2 where id="+id);
		

	}
	
	@Test
	public void DeleteUserFail1() throws SQLException, IOException
	{

		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod())
				.header("Content-Type","application/json");
				
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint=0");
	 	
	 	int id = 0;
	 	
	 	while (rs.next())
	 	{
	 		id=rs.getInt("id");
	 	}

		Response resp= request.delete(getWeb()+"public/v1/users/"+id);
		int code = resp.getStatusCode();
		Assert.assertEquals(code, 404);
		
	}
	
	@Test
	public void DeleteUserFail2() throws SQLException, IOException
	{

		RequestSpecification request =RestAssured.given().auth().oauth2(getTokenMethod())
				.header("Content-Type","application/json");
				
		Connection con=getConnection();
		Statement s = con.createStatement();
	 	ResultSet rs= s.executeQuery("SELECT  * FROM testingdb.users where PostedByEndpoint=2");
	 	
	 	int id = 0;
	 	
	 	while (rs.next())
	 	{
	 		id=rs.getInt("id");
	 	}

		Response resp= request.delete(getWeb()+"public/v1/users/"+id);
		int code = resp.getStatusCode();
		Assert.assertEquals(code, 404);
		
	}
	
	
}


