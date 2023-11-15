package uk.mailtravel;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Util methods to be used by for test cases/steps
 */
public class Utils {


    public static void setupDriverManager() {
        WebDriverManager.chromedriver().setup();
    }


    public static void clickItemFromSelect(WebDriver driver, WebElement parentElm, String elmName, String value) {

        WebElement selectElm = driver.findElement(By.name(elmName));
        Select select2 = new Select(selectElm);
        select2.selectByValue(value);
    }

    public static void performButtonClick(WebDriver driver, WebElement parentElm, String... buttonName) {

        if(buttonName != null && !buttonName.equals("")) {
            // Click the button with the given name
        } else {
            new Actions(driver).click(parentElm.findElement(By.tagName("button"))).click().perform();
        }
    }

    public static void waitForElement(WebDriver driver, String cssSelector, int duration) {
        if(duration > 100) {
            duration = 50;
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(duration));
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div#paxSelection"))).click();
    }

}
