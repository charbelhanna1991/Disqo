package resources;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONObject;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

public class Base {
	
	private Properties prop;
	private Connection con;
	private WebDriver driver;
	private String username;
	private String password;
	private String web;
	
	public JSONObject postBody(int id, String name, String email, String gender,String status) throws SQLException, IOException
	{
		con= getConnection();
		Statement s = con.createStatement();
		JSONObject json =new JSONObject();
		json.put("id", id);
		json.put("name", name);
		json.put("email", email);
		json.put("gender", gender);
		json.put("status", status);
		return json;
	}
	
	
	public void initializeDriver() throws IOException, SQLException
	{

		
		prop = new Properties();
		FileInputStream fis=new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\java\\resources\\data.properties");
		
		prop.load(fis);
		username = prop.getProperty("username");
		password = prop.getProperty("password");

		web=prop.getProperty("url");

		//mvn test -Dbrowser=chrome
		//String BrowserName =System.getProperty("browser"); // System.getProperty will get the value from the maven command
		String BrowserName =prop.getProperty("browser"); // prop.getProperty will get the value from data.properties file
		
		if (BrowserName.contains("chrome"))
		{
			//execute in chrome
			System.setProperty("webdriver.chrome.driver", "C:\\Users\\ch_59\\Desktop\\AutomationLearning\\chromedriver.exe");
			 if (BrowserName.contains("headless"))
			 {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("headless");
			driver=new ChromeDriver(options);
			 }
			 else 
				 driver=new ChromeDriver();
			 
		}
		else if (BrowserName.equalsIgnoreCase("firefox"))
		{
			// execute in firefox
			System.setProperty("webdriver.gecko.driver", "C:\\Users\\ch_59\\Desktop\\AutomationLearning\\geckodriver.exe");
			driver=new FirefoxDriver();
		}
		else if (BrowserName.equalsIgnoreCase("IE"))
		{	
			//execute in IE
			System.setProperty("webdriver.ie.driver", "C:\\Users\\ch_59\\Desktop\\AutomationLearning\\IEDriverServer.exe");
			driver=new InternetExplorerDriver();
		}
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	public String getUsername()
	{
		return username;
	}
	
	public WebDriver getDriver()
	{
		return driver;
	}
	
	public String getPassword()
	{
		return password;
	}
	public String getWeb()
	{
		return web;
	}
	public Connection getConnection() throws SQLException
	{
		String port = prop.getProperty("port");
		String host = prop.getProperty("host");
		Connection con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/testingdb", "root", "P@ssw0rd");
		return con;
	}
}
