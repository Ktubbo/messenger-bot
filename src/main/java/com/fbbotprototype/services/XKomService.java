package com.fbbotprototype.services;

import com.fbbotprototype.image.Screenshooter;
import com.fbbotprototype.web.WebController;
import lombok.Getter;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.concurrent.TimeUnit;

import static org.openqa.selenium.By.xpath;

@Service
@Getter
public class XKomService {

    private final Screenshooter screenshooter;
    private final WebController webController;
    @Value("${xkom.xpath.component}")
    private String XPATH_COMPONENT;
    @Value("${xkom.web.path}")
    private String WEB_PATH;
    @Value("#{systemEnvironment['FBBOT_SAVE_PATH']}")
    private String FILE_SAVE_PATH;
    private String LAST_SCREENSHOT_PATH;
    @Value("${xkom.gpu.path}")
    private String GPU_PATH;
    @Value("${xkom.view.button}")
    private String VIEW_BUTTON;
    @Value("${xkom.xpath.gpu.component}")
    private String XPATH_GPU_COMPONENT;

    public XKomService(@Autowired Screenshooter screenshooter, @Autowired WebController webController) {
        this.screenshooter = screenshooter;
        this.webController = webController;
    }

    public String getHotShot(){
        WebDriver driver = webController.getDriver();
        driver.manage().window().setPosition(new org.openqa.selenium.Point(10,10));
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1000,800));
        driver.get(WEB_PATH);
        while (!driver.findElement(xpath(XPATH_COMPONENT)).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(d->d.findElement(xpath(XPATH_COMPONENT)));
        screenshooter.getScreenshot(new Rectangle(40,360,850,370));
        driver.close();
        return LAST_SCREENSHOT_PATH;
    }
    
    public void getGPUs(){
        WebDriver driver = webController.getDriver();
        driver.manage().window().setPosition(new org.openqa.selenium.Point(10,10));
        driver.manage().window().setSize(new org.openqa.selenium.Dimension(1300,1000));
        driver.get(GPU_PATH);
        while (!driver.findElement(xpath(XPATH_GPU_COMPONENT)).isDisplayed());
        WebDriverWait wait = new WebDriverWait(driver,30);
        wait.until(d->d.findElement(xpath(XPATH_GPU_COMPONENT)));
        WebElement button = driver.findElement(xpath(VIEW_BUTTON));
        if(button.isEnabled()) {
            button.click();
        }
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,200)", "");
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        screenshooter.getScreenshot(new Rectangle(400,290,850,510));
        driver.close();
    }
}
