package SDET_11_Advanced_Selenium_Assignm;

import SDET_10_SeleniumBasics.SharedDriver;
import com.github.dockerjava.api.model.CpuStatsConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class M11_assignm_Davik {
    private static final String Davik_Home = "https://www.daviktapes.com/";
    private static WebDriver driver;

// navigate to ( you can also use driver.navigate.to("") method) https://www.daviktapes.com/
    @BeforeAll
    public static void classSetup() {
        driver = SharedDriver.getWebDriver();
        driver.navigate().to(Davik_Home);
    }
    @AfterAll
    public static void classTearDown() {
        SharedDriver.closeDriver();
    }
    @AfterEach
    public void testTearDown() {
        driver.navigate().to(Davik_Home);
    }
// Using actions class, move the mouse to every top menu option and verify it`s opened in each case.
//Use Explicit wait to check each menu had enough time to be expanded ( you may use the presenceOfElementLocated
// method with one of the options element XPath).
    @Test
    public void everyTopMenuOptionTest() {
        WebElement element = driver.findElement(By.xpath("//*[text()='Company']"));
        Actions actions = new Actions(driver);
        actions.moveToElement(element).build().perform();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

        driver.findElement(By.xpath("//*[text()='About us']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='About Davik']")));
        assertNotNull(driver.findElement(By.xpath("//*[text()='About Davik']")));

        actions.moveToElement(driver.findElement(By.xpath("//*[text()='Products']"))).build().perform();
        driver.findElement(By.xpath("//*[text()='Pest Control']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Pest Control']")));
        assertNotNull(driver.findElement(By.xpath("//*[text()='Pest Control']")));

        actions.moveToElement(driver.findElement(By.xpath("//*[text()='Industries']"))).build().perform();
        driver.findElement(By.xpath("//*[text()='Agriculture']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='Agriculture']")));
        assertNotNull(driver.findElement(By.xpath("//*[text()='Agriculture']")));

        actions.moveToElement(driver.findElement(By.xpath("//*[text()='Knowledge Center']"))).build().perform();
        driver.findElement(By.xpath("//*[text()='Events']")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[text()='News & events']")));
        assertNotNull(driver.findElement(By.xpath("//*[text()='News & events']")));
    }
}

