package SDET_11_Advanced_Selenium_Assignm;

import SDET_10_SeleniumBasics.SharedDriver;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.HashMap;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class M11_assignm_Facebook {
    private static final String Home_Page_url = "https://www.facebook.com/";
    private static WebDriver driver;

    @BeforeAll
    public static void classSetup() {
        driver = SharedDriver.getWebDriver();
        driver.get(Home_Page_url);
    }
    @AfterAll
    public static void classTearDown() {
        SharedDriver.closeDriver();
    }
    @AfterEach
    public void testTearDown() {
        driver.get(Home_Page_url);
    }

// Verify all the error messages for empty fields
    @Test
    public void allErrorMessagesTest() throws InterruptedException {
        driver.findElement(By.xpath("//*[text()='Create new account']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Sign Up']")));
        Thread.sleep(3000);

        driver.findElement(By.xpath("//button[@name='websubmit']")).click();
        WebElement errorFirstname = driver.findElement(By.xpath("//*[contains(text(), 'your name')]"));
        assertNotNull(errorFirstname);

        driver.findElement(By.xpath("//input[@name='lastname']")).click();
        WebElement errorLastname = driver.findElement(By.xpath("//*[contains(text(),'name?')]"));
        assertNotNull(errorLastname);

        driver.findElement(By.xpath("//input[@name='reg_email__']")).click();
        WebElement errorMobOrEmail = driver.findElement(By.xpath("//*[contains(text(),'need to reset')]"));
        assertNotNull(errorMobOrEmail);

        driver.findElement(By.xpath("//input[@name='reg_passwd__']")).click();
        WebElement errorNewPssw = driver.findElement(By.xpath("//*[contains(text(),'Enter a combination')]"));
        assertNotNull(errorNewPssw);

        driver.findElement(By.xpath("//*[contains(@class,'_5dbc _5k_6 img sp_98fCI7IVTTz sx_54513f')]")).click();
        WebElement errorBirthdayWrapper = driver.findElement(By.xpath("//*[contains(text(),'your real birthday')]"));
        assertNotNull(errorBirthdayWrapper);

       driver.findElement(By.xpath("//*[contains(@class,'_5dbc _8esb img sp_98fCI7IVTTz sx_54513f')]")).click();
        WebElement errorGenderWrapper = driver.findElement(By.xpath("//*[contains(text(),'choose a gender')]"));
        assertNotNull(errorGenderWrapper);
    }
// Test Months drop list
    @ParameterizedTest
    @ValueSource(strings = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"})
    public void monthDroplistTest(String monthInput) throws InterruptedException {
        driver.findElement(By.xpath("//*[text()='Create new account']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Sign Up']")));
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@title='Month']")).click();
        driver.findElement(By.xpath("//*[text()='" + monthInput + "']")).click();

        HashMap<String, String> months = new HashMap<>();
        months.put("Jan", "1");
        months.put("Feb", "2");
        months.put("Mar", "3");
        months.put("Apr", "4");
        months.put("May", "5");
        months.put("Jun", "6");
        months.put("Jul", "7");
        months.put("Aug", "8");
        months.put("Sep", "9");
        months.put("Oct", "10");
        months.put("Nov", "11");
        months.put("Dec", "12");

        String monthValueExpected = months.get(monthInput);
        String monthValueActual = driver.findElement(By.xpath("//*[@title='Month']")).getAttribute("value");
        assertEquals(monthValueExpected, monthValueActual);
    }

// Test Years DropList (no need to test all, years 1905, 1950 and 2020 are enough)
    @ParameterizedTest
    @ValueSource(strings = {"1905", "1950", "2020"})
    public void yearParameterizedTest(String yearInput) throws InterruptedException {
        driver.findElement(By.xpath("//*[text()='Create new account']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Sign Up']")));
        Thread.sleep(2000);

        driver.findElement(By.xpath("//*[@title='Year']")).click();
        driver.findElement(By.xpath("//*[text()='" + yearInput + "']")).click();
        String yearValue = driver.findElement(By.xpath("//*[@title='Year']")).getAttribute("value");
        assertEquals(yearInput, yearValue);
    }
// Find and test Radio buttons using siblings in XPath
    @Test
    public void radioButtonsTest() throws InterruptedException {
        String radioFemaleXpath = "//*[text()='Female']//following-sibling::*[@type='radio']";
        String radioMaleXpath = "//*[text()='Male']//following-sibling::*[@type='radio']";
        String radioCustomXpath = "//*[text()='Custom']//following-sibling::*[@type='radio']";

        driver.findElement(By.xpath("//*[text()='Create new account']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Sign Up']")));
        Thread.sleep(1000);

        WebElement femaleRadio = driver.findElement(By.xpath(radioFemaleXpath));
        femaleRadio.click();
        String isFemaleChecked = driver.findElement(By.xpath(radioFemaleXpath)).getAttribute("checked");
        assertNotNull(isFemaleChecked);
        assertEquals("true", isFemaleChecked);

        WebElement maleRadio = driver.findElement(By.xpath(radioMaleXpath));
        maleRadio.click();
        String isMaleChecked = driver.findElement(By.xpath(radioMaleXpath)).getAttribute("checked");
        assertNotNull(isMaleChecked);
        assertEquals("true", isMaleChecked);

        WebElement customRadio = driver.findElement(By.xpath(radioCustomXpath));
        customRadio.click();
        String isCustomChecked = driver.findElement(By.xpath(radioCustomXpath)).getAttribute("checked");
        assertNotNull(isCustomChecked);
        assertEquals("true", isCustomChecked);
    }
// Test Terms and DataPolicy links, verify that the new pages are opened after pressing it
    @Test
    public void linkTermsTest() throws InterruptedException {
        String terms = "//a[@id='terms-link']";

        driver.findElement(By.xpath("//*[text()='Create new account']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Sign Up']")));
        Thread.sleep(5000);

        driver.findElement(By.xpath(terms)).click();

        String mainPageWindowHandle = driver.getWindowHandle();
        for(String termsOfService : driver.getWindowHandles()){
            if(!termsOfService.equals(mainPageWindowHandle)){
                driver.switchTo().window(termsOfService);
            }
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@type='submit']")));
        assertNotNull(driver.findElement(By.xpath("//input[@type='submit']")));

        driver.close();
        driver.switchTo().window(mainPageWindowHandle);
    }
    @Test
    public void linkPrivacyTest() throws InterruptedException {
       String privacy = "//a[@id='privacy-link']";

        driver.findElement(By.xpath("//*[text()='Create new account']")).click();
        assertNotNull(driver.findElement(By.xpath("//*[text()='Sign Up']")));
        Thread.sleep(5000);

        driver.findElement(By.xpath(privacy)).click();

        String mainPageWindowHandle = driver.getWindowHandle();
        for(String privacyPolicy : driver.getWindowHandles()){
            if(!privacyPolicy.equals(mainPageWindowHandle)){
                driver.switchTo().window(privacyPolicy);
            }
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Privacy Center']")));
        assertNotNull(driver.findElement(By.xpath("//*[text()='Privacy Center']")));

        driver.close();
        driver.switchTo().window(mainPageWindowHandle);
    }
}
