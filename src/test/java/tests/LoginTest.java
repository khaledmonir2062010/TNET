package tests;

import java.time.Duration;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

import pages.LoginPage;



public class LoginTest extends TestBase{
	
	LoginPage Loginobj;

	
	String Expected_HomePageURL=
			"https://oyn-adminportal-qc-demo.salmonsky-1edff179.westeurope.azurecontainerapps.io/admin/dashboard";

	@Test(priority = 1)
	public void Successfull_Login() throws InterruptedException {
		
		//Step_1: Enter the email and password to login
		Loginobj=new LoginPage(driver);
		WebDriverWait wait= new WebDriverWait(driver, Duration.ofSeconds(50));
		wait.until(ExpectedConditions.visibilityOf(Loginobj.username_TxtField));
		Loginobj.SiginProcess("KHALED123", "Pass@2020");
	}

}
