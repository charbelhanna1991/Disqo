package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class RedirectionPage {
	public WebDriver driver;
	private By username=By.cssSelector("#login_field");
	private By password=By.cssSelector("#password");
	private By login=By.cssSelector("input[name='commit']");
	

	
	public RedirectionPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
				
	}
	
	public WebElement getusername()
	{
		return driver.findElement(username);
	}
	
	public WebElement getpassword()
	{
		return driver.findElement(password);
	}
	
	public WebElement getlogin()
	{
		return driver.findElement(login);
	}
}
