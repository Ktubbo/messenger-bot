package com.fbbotprototype.web;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class WebController {

    @Value("#{systemEnvironment['FIREFOX']}")
    private String fireFoxDriverPath;

    public WebDriver getDriver() {
        System.setProperty("webdriver.gecko.driver", fireFoxDriverPath);
            return new FirefoxDriver();
    }
}
