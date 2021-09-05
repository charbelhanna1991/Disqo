package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LandingPage{


	public WebDriver driver;
	private By login=By.cssSelector(".nav-item a[href*='login']");
	
	public LandingPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
				
	}
	
	public WebElement getlogin()
	{
		return driver.findElement(login);
	}
}
