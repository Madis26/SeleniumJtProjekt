package com.nortal.course.selenium;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.concurrent.TimeUnit;


public class TrivialSearchTest {

    private static WebDriver driver;
    private static String baseURL;
    private static String username;
    private static String password;


    @BeforeClass
    public static void setUp() {
        driver = new FirefoxDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        baseURL = "http://sertnix:8080/sk/login.do";
        username = "TEST";
        password = "TEST";

    }

    @Before
    public void testSetUp(){
        driver.navigate().to(baseURL);
        driver.findElement(By.name("j_username")).sendKeys("TEST");
        driver.findElement(By.name("j_password")).sendKeys("TEST");
        driver.findElement(By.name("login")).click();
    }

    @Test
    public void searchDocBySsn(){
        driver.findElement(By.cssSelector("a[href*='/sk/idc/find.do']")).click();
        driver.findElement(By.name("idcode")).sendKeys("47101010033");
        driver.findElement(By.name("action")).click();
    }

    public void registerPinEnvelop(){
        driver.findElement(By.cssSelector("a[href*='/sk/pinpuk/index.do']")).click();
        driver.findElement(By.cssSelector("a[href*='/sk/pinpuk/register.do']")).click();
        driver.findElement(By.id("start")).sendKeys("1");
        driver.findElement(By.id("amount")).sendKeys("5");
        driver.findElement(By.name("next")).click();

    }


    public void loginPageExists(){
        initWebDriver();
        driver.navigate().to("http://sertnix:8080/sk/login.do");
        Assert.assertTrue(driver.findElement(By.name("j_username")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.name("j_password")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.name("login")).isDisplayed());
        driver.quit();
    }


    public void searchDocumentByFirstname(){
        shouldOpenMainPage();
        driver.findElement(By.name("personForename")).sendKeys("MARI LIIS");
        driver.findElement(By.name("action")).click();
        String errormessage = driver.findElement(By.xpath("/html/body/table/tbody/tr[1]/td/table/tbody/tr[1]/td")).getText();
        System.out.println(errormessage);
        Assert.assertEquals("Täidetud peavad olema vähemalt ees- ja perenimi, isikukood või dokumendi number.", "Täidetud peavad olema vähemalt ees- ja perenimi, isikukood või dokumendi number.", errormessage);
    }


    public void reNewIdCardPinPuk(){
        shouldOpenMainPage();
        registerPinEnvelop();
        driver.findElement(By.cssSelector("a[href*='/sk/idc/find.do']")).click();
        searchDocBySsn();
        driver.findElement(By.cssSelector("a[href*='/sk/idc/details?docNr=X0010487&documentType=ID_CARD']")).click();
        driver.findElement(By.name("pinRenewal")).click();
        driver.findElement(By.id("envelopeNumber")).sendKeys("1");
        driver.findElement(By.xpath("/html /body/table/tbody/tr[4]/td/table/tbody/tr/td[3]/table/tbody/tr/td/form/center/table/tbody/tr[3]/td[1]/input")).click();
        driver.findElement(By.name("confirmKMA")).click();
        driver.findElement(By.name("confirmRenewal")).click();
        driver.quit();

    }


     public void suspendIdCard(){
        shouldOpenMainPage();
        searchDocBySsn();
        driver.findElement(By.cssSelector("a[href*='/sk/idc/details?docNr=X0010487&documentType=ID_CARD']")).click();
        WebElement checkBox1 = driver.findElement(By.xpath("//input[@value='vNEdoPAD1u7rhrbTGpHITjnFPME=']"));
        WebElement checkBox2 = driver.findElement(By.xpath("//input[@value='/kxAIwh14ZLZYTOddN2dBxClud8=']"));
        checkBox1.click();
        checkBox2.click();
        Select dropDown = new Select(driver.findElement(By.name("changeCertificatesStatus.certificateAction")));
        dropDown.selectByValue("SUSPEND");
        driver.findElement(By.name("certActionChangeStatus")).click();
        driver.findElement(By.id("confirmButton")).click();
        driver.quit();
    }


    public void stopSuspendIdCard(){
        driver.findElement(By.cssSelector("a[href*='/sk/idc/details?docNr=X0010487&documentType=ID_CARD']")).click();
        WebElement checkBox1 = driver.findElement(By.xpath("//input[@value='vNEdoPAD1u7rhrbTGpHITjnFPME=']"));
        WebElement checkBox2 = driver.findElement(By.xpath("//input[@value='/kxAIwh14ZLZYTOddN2dBxClud8=']"));
        checkBox1.click();
        checkBox2.click();
        Select dropDown = new Select(driver.findElement(By.name("changeCertificatesStatus.certificateAction")));
        dropDown.selectByValue("STOP_SUSPEND");
        driver.findElement(By.name("certActionChangeStatus")).click();
        driver.findElement(By.id("confirmButton")).click();
        driver.quit();
    }


}
