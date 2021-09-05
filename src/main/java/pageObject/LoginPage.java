package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class LoginPage {
	public WebDriver driver;
	private By loginGoogle=By.cssSelector("a[href*='google']");
	private By loginFacebook=By.cssSelector("a[href*='facebook']");
	private By loginGithub=By.cssSelector("a[href*='github']");
	
	public LoginPage(WebDriver driver) {
		// TODO Auto-generated constructor stub
		this.driver=driver;
				
	}
	
	public WebElement getloginGoogle()
	{
		return driver.findElement(loginGoogle);
	}
	
	public WebElement getloginFacebook()
	{
		return driver.findElement(loginFacebook);
	}
	
	public WebElement getloginGithub()
	{
		return driver.findElement(loginGithub);
	}
}
