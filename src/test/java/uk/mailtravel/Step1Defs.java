package uk.mailtravel;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class Step1Defs {


    private WebDriver webDriver;


    @Given("I am on the home page")
    public void i_am_on_the_home_page() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        Utils.setupDriverManager();
        // TODO: Customize utility method for multiple driver selection
        webDriver = new ChromeDriver();
        webDriver.get("https://www.mailtravel.co.uk/");
        webDriver.manage().window().maximize();
        Thread.sleep(1000);
    }
    @When("I click Accept All Cookies")
    public void i_click_accept_all_cookies() throws InterruptedException {

        // Click on the cookie popup
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(50));
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"onetrust-accept-btn-handler\"]"))).click();


        // Search for "India" and select the first item from search results
        WebElement searchBox = webDriver.findElement(By.xpath("//*[@id=\"searchtext_freetext_search_form\"]"));
        searchBox.sendKeys("india");

        Thread.sleep(3000);

        WebElement ul = webDriver.findElement(By.id("as_ul"));
        List<WebElement> liElements = ul.findElements(By.tagName("li"));

        JavascriptExecutor jse = (JavascriptExecutor) webDriver;

        Actions actions = new Actions(webDriver);
        actions.moveToElement(liElements.get((0)));
        actions.click(liElements.get(0));
        actions.perform();

        Thread.sleep(5000);

        WebElement bookFlow = webDriver.findElement(By.name("enterbookingflow"));
        bookFlow.click();


        WebElement element1 = webDriver.findElement(By.xpath("//*[@id='calendar_container']//*"));

        jse.executeScript("arguments[0].scrollIntoView(true);", element1);

        WebElement elements = webDriver.findElement(By.cssSelector("#calendar_container"));


        List<WebElement> rows = elements.findElements(By.cssSelector("div.nbf_tpl_pms_calendar_days"));

        int i = 0;
        double amt = 0.0;

        String lblId = null;

        // Select the first available dat from the first page,
        // TODO: Iterate the next calender pages if no date available with custom method
        for (WebElement e : rows) {
                List<WebElement> list = e.findElements(By.tagName("label"));

                if(list.size() > 0) {
                    lblId = list.get(0).getAttribute("for");
                        WebElement e2 = e.findElement(By.cssSelector(".ibecurr"));
                        System.out.println("Amount :" + e2.getAttribute("data-amt"));
                        amt = Double.parseDouble(e2.getAttribute("data-amt"));

                        WebElement e3 = e.findElement(By.cssSelector("input#" + lblId));
                        new Actions(webDriver).click(e3).perform();
                }
        }

        WebElement optionsElm = webDriver.findElement(By.cssSelector("div#options-cont"));
        WebElement selectElm = optionsElm.findElement(By.name("numAdults"));
        Select select = new Select(selectElm);
        select.selectByValue("2");

        WebElement priceElm = optionsElm.findElement(By.cssSelector("div#tour-price-wrap"));
        WebElement currElm = priceElm.findElement(By.cssSelector("span.ibecurr"));

        double total = Double.parseDouble(currElm.getAttribute("data-amt"));

        WebElement btnGroup = webDriver.findElement(By.cssSelector("div#book-button"));
        WebElement btn = btnGroup.findElement(By.tagName("button"));

        new Actions(webDriver).click(btn).perform();

        WebDriverWait wait2 = new WebDriverWait(webDriver, Duration.ofSeconds(50));
        wait2.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div#paxDepDateInfo"))).click();

        WebElement header1 = webDriver.findElement(By.cssSelector("div#paxDepDateInfo"));

        WebElement passengerDetails = header1.findElement(By.cssSelector("div.nbf_tpl_pms_bf_panel__body"));
        WebElement passengerCount = header1.findElement((By.cssSelector("div.nbf_tpl_pms_bf_passenger_number")));

        // Validate the count
        Assertions.assertEquals(2, Integer.parseInt(passengerCount.getText()));
        // TODO:Add other validations

        WebDriverWait wait3 = new WebDriverWait(webDriver, Duration.ofSeconds(50));
        wait3.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div#accomForm-container"))).click();


        // Identify the id for select option
        WebElement accomodationSection = webDriver.findElement(By.cssSelector("div#accomForm-rooms"));
        String selectId = accomodationSection.findElement(By.cssSelector("[id^=room]")).getAttribute("id");

        WebElement roomDiv = webDriver.findElement(By.cssSelector("#" + selectId));
        WebElement selectElm2 = roomDiv.findElement(By.name(selectId + "-numselect"));
        Select select2 = new Select(selectElm2);
        select2.selectByValue("1");

        WebElement accFormSelect = webDriver.findElement(By.cssSelector("div#accomForm-select"));
        WebElement accSubmit = accFormSelect.findElement(By.tagName("button"));

        new Actions(webDriver).click(accSubmit).perform();


        WebDriverWait wait4 = new WebDriverWait(webDriver, Duration.ofSeconds(50));
        wait4.until(ExpectedConditions.elementToBeClickable(By.cssSelector("[id^=extraForm-container]"))).click();

        WebElement extraFormContainer = webDriver.findElement(By.cssSelector("div#extrasSelection"));
        new Actions(webDriver).click(extraFormContainer.findElement(By.tagName("button"))).click().perform();

        Thread.sleep(5000);

        // Pax selection
        WebElement paxSection = webDriver.findElement(By.cssSelector("div#adult-1"));
        List<WebElement> tables = paxSection.findElements(By.cssSelector("table.nbf_tpl_pms_bf_paxinfo"));

        for (int k = 0 ; k < 2; k++) {

            int id = k + 1;
            WebElement subElm = tables.get(k);

            WebElement titleElm = subElm.findElement(By.name("pax-a-title-" + id));
            Select selectTitle = new Select(titleElm);
            String tle = k == 0 ? "Mr" : "Mrs";
            selectTitle.selectByValue(tle);

            WebElement fName = subElm.findElement(By.name("pax-a-first-" + id));
            fName.sendKeys("FName" + id );

            WebElement lName = subElm.findElement(By.name("pax-a-last-" + id));
            lName.sendKeys("LName" + id);

            WebElement dob1 = subElm.findElement(By.name("pax-a-dobd-" + id));
            Select dobd1 = new Select(dob1);
            dobd1.selectByValue("10");

            WebElement dob2 = subElm.findElement(By.name("pax-a-dobm-" + id));
            Select dobm = new Select(dob2);
            dobm.selectByValue("3");

            WebElement dob3 = subElm.findElement(By.name("pax-a-doby-" + id));
            Select doby = new Select(dob3);
            doby.selectByValue("1975");

        }

        WebElement leadContect = webDriver.findElement(By.cssSelector("div.nbf_tpl_pms_bf_leadpaxdetails"));
        WebElement tableContact = leadContect.findElement(By.cssSelector("table.nbf_tpl_pms_bf_leadpaxdetails"));

        WebElement cName = tableContact.findElement(By.cssSelector("input#contact-name"));
        cName.clear();
        cName.sendKeys("FirstName, LastName");

        WebElement cMob = tableContact.findElement(By.cssSelector("input#contact-mobile"));
        cMob.sendKeys("0741234567");

        WebElement cEmail = tableContact.findElement(By.cssSelector("input#contact-email"));
        cEmail.sendKeys("test@gmail.com");

        WebElement cAddr1 = tableContact.findElement(By.cssSelector("input#contact-address1"));
        cAddr1.sendKeys("Addr1");

        WebElement cAddr2 = tableContact.findElement(By.cssSelector("input#contact-address2"));
        cAddr2.sendKeys("Addr2");

        WebElement cCity = tableContact.findElement(By.cssSelector("input#contact-city"));
        cCity.sendKeys("City1");

        WebElement cPCode = tableContact.findElement(By.cssSelector("input#contact-postcode"));
        cPCode.sendKeys("0099");

        WebElement cHAbt = tableContact.findElement(By.name("wheredidyouhear"));
        Select hearAbt = new Select(cHAbt);
        hearAbt.selectByValue("2");

        //Submit details
        WebElement submitDiv = webDriver.findElement(By.cssSelector("div#paxform-select"));
        WebElement submitBtn = submitDiv.findElement(By.tagName("button"));
        new Actions(webDriver).click(submitBtn).perform();

    }
    @Then("The page title should have {string}")
    public void the_page_title_should_have(String string) {
        // Write code here that turns the phrase above into concrete actions
        //webDriver.quit();
    }



}
