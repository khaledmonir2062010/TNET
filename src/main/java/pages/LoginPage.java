package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends PageBase{

	public LoginPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	
	//Login Page Element Locators//
		@FindBy(id = "username")
		public WebElement username_TxtField;
		
		@FindBy(id="password")
		WebElement Password_TxtField;
		
		@FindBy(css =".block")
		WebElement Signin_btn;

		@FindBy(css=".info > p")
		public WebElement successLogoutMsg;
		
		//Login Function//
		public void SiginProcess(String Username, String Pass)
		{
			waitElementToBeVisibleAndClickable(username_TxtField);
			setTextElementText(username_TxtField, Username);
			waitElementToBeVisibleAndClickable(Password_TxtField);
			setTextElementText(Password_TxtField, Pass);
			waitElementToBeClickable(Signin_btn);
			clickButton(Signin_btn);
		}
}
