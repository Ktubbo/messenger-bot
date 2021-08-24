package com.fbbotprototype.services;

import com.fbbotprototype.image.Screenshooter;
import com.fbbotprototype.web.WebController;
import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.*;

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
        LAST_SCREENSHOT_PATH = screenshooter.getScreenshot(FILE_SAVE_PATH,
                new Rectangle(40,360,850,370));
        driver.close();
        return LAST_SCREENSHOT_PATH;
    }
}
