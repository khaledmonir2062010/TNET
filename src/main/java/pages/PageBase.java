package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PageBase {

    public JavascriptExecutor jse;
    public Select select;
    public Actions action;
    public WebDriverWait wait;

    // create constructor
    public PageBase(WebDriver driver) {
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    protected static void clickButton(WebElement button) {
        button.click();
    }

    protected static void setTextElementText(WebElement textElement, String value) {
        textElement.sendKeys(value);
    }

    public void scrollToBottom() {
        jse.executeScript("scrollBy(0,2500)");
    }

    public void clearText(WebElement element) {
        element.clear();
    }

    public void waitElementToBeVisible(WebElement Element) {
        wait.until(ExpectedConditions.visibilityOf(Element));
    }

    public void waitElementToBeClickable(WebElement Element) {
        wait.until(ExpectedConditions.elementToBeClickable(Element));
    }

    public void waitElementToBeVisibleAndClickable(WebElement Element) {
        wait.until(ExpectedConditions.and(ExpectedConditions.visibilityOf(Element), ExpectedConditions.elementToBeClickable(Element)));
    }

}
