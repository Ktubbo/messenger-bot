package com.fbbotprototype.image;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class Screenshooter {

    public String getScreenshot(String saveFilePath, Rectangle screenShotWindow) {
        String fullFilePath = "";
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
            fullFilePath = saveFilePath + LocalDateTime.now().format(formatter) + ".jpg";
            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(screenShotWindow);
            ImageIO.write(screenShot, "jpg", new File(fullFilePath));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return fullFilePath;
    }
}
