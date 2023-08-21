package org.example;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import jdk.jfr.Description;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Test1 {

    private RemoteWebDriver driver;
    private String Status = "failed";

    @BeforeMethod
    public void setup(Method m, ITestContext ctx) throws MalformedURLException {
        String username = "mohammadk";
        String authkey = "rakcBoBYHiy8BW7osVi4N1LGYjgJhRfAwvL1pPUvwCA1wfNChd";
        ;
        String hub = "@hub.lambdatest.com/wd/hub";

        DesiredCapabilities caps = new DesiredCapabilities();
        caps.setCapability("platform", "MacOS Catalina");
        caps.setCapability("browserName", "Safari");
        caps.setCapability("version", "latest");
        caps.setCapability("build", "allure-integration");
        caps.setCapability("name", m.getName() + " - " + this.getClass().getName());
        caps.setCapability("plugin", "git-testng");
        caps.setCapability("terminal", true);

        String[] Tags = new String[] { "Feature", "Falcon", "Severe" };
        caps.setCapability("tags", Tags);

        driver = new RemoteWebDriver(new URL("https://" + username + ":" + authkey + hub), caps);

    }

    @Test
    @Description("Verify fail test")
    public void basicTest() throws InterruptedException {
        String spanText;
        System.out.println("Loading Url");
        openUrl(driver);

        System.out.println("Session ID is :: "+driver.getSessionId());

        System.out.println("Checking Box");
        clickOnCheckBox(driver);

        // mocking failure
        boolean assertion  = false;
        if (!assertion){
            takeScreenshot();
        }
        Assert.assertTrue(false, "sample assetion message");
        Status = "passed";
        Thread.sleep(150);

        System.out.println("TestFinished");

    }

    @Step("Clicking on checkbox")
    private void clickOnCheckBox(RemoteWebDriver driver) {
        driver.findElement(By.name("li1")).click();
    }

    @Step("Open URL")
    private void openUrl(RemoteWebDriver driver) {
        driver.get("https://lambdatest.github.io/sample-todo-app/");
    }

    @AfterMethod
    public void tearDown() throws IOException {
        driver.executeScript("lambda-status=" + "failed");

        Allure.addAttachment("testSessionVideo", Utils.getVideoUrlFromLT(String.valueOf(driver.getSessionId())));
        driver.quit();
    }

    @Attachment
    private byte[] takeScreenshot(){
        return ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
    }


}