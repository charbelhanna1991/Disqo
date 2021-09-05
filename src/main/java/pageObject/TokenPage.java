package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class TokenPage {
	public WebDriver driver;
	private By token=By.cssSelector(".user-select-all.text-break");
	
	public TokenPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
				
	}
	
	public WebElement gettoken()
	{
		return driver.findElement(token);
	}
	

}
