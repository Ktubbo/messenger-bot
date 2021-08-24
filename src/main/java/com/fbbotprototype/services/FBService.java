package com.fbbotprototype.services;

import com.fbbotprototype.web.WebController;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static org.openqa.selenium.By.xpath;

@Service
public class FBService {

    @Value("${fb.xpath.cookies}")
    private String XPATH_COOKIES;
    @Value("${fb.web.path}")
    private String WEB_PATH;
    @Value("${fb.xpath.email}")
    private String XPATH_EMAIL;
    @Value("${fb.xpath.pass}")
    private String XPATH_PASS;
    @Value("${fb.xpath.login}")
    private String XPATH_LOGIN;
    @Value("${fb.xpath.logged}")
    private String XPATH_LOGGED;
    @Value("#{systemEnvironment['BOT_USERNAME']}")
    private String botUsername;
    @Value("#{systemEnvironment['BOT_PASSWORD']}")
    private String botPass;
    @Value("#{systemEnvironment['OS']}")
    private String operatingSystem;

    private final WebDriver driver;

    public FBService(@Autowired WebController webController) {
        this.driver = webController.getDriver();
    }

    public boolean botLogIn() {

        driver.manage().window().setPosition(new org.openqa.selenium.Point(10,10));
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1000,700));
        driver.get(WEB_PATH);
        while (!driver.findElement(xpath(XPATH_COOKIES)).isDisplayed());
        driver.findElement(By.xpath(XPATH_COOKIES)).click();
        WebDriverWait waitForCookies = new WebDriverWait(driver, 10);
        waitForCookies.until(d -> d.findElement(xpath(XPATH_EMAIL)));
        driver.findElement(xpath(XPATH_EMAIL)).sendKeys(botUsername);
        driver.findElement(xpath(XPATH_PASS)).sendKeys(botPass);
        driver.findElement(By.xpath(XPATH_LOGIN)).click();
        WebDriverWait waitForLogIn = new WebDriverWait(driver,20);
        waitForLogIn.until(d -> d.findElement(By.xpath((XPATH_LOGGED))));
        return driver.findElement(By.xpath(XPATH_LOGGED)).isDisplayed();
        }

    public void sendImgTo(String imgPath, String receiver) {
        driver.findElement(By.xpath("//*[text()='"+receiver+"']")).click();

        try {
            Robot robot = new Robot();

            robot.delay(500);
            if (operatingSystem.equals("Raspbian")) {
                robot.mouseMove(424,700);
            } else {
                robot.mouseMove(424,676);
            }
            robot.delay(500);
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(500);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            robot.delay(2000);

            StringSelection stringSelection = new StringSelection(imgPath);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, stringSelection);

            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.delay(3000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(3000);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.delay(3000);

        } catch (AWTException e) {
            e.printStackTrace();
            }
        }
}
