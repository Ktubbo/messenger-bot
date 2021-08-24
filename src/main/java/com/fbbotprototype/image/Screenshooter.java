package com.fbbotprototype.image;

import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.image.BufferedImage;

@Component
public class Screenshooter {

    public void getScreenshot(Rectangle screenShotWindow) {
        try {
            Robot robot = new Robot();
            BufferedImage screenShot = robot.createScreenCapture(screenShotWindow);
            TransferableImage transferableImage = new TransferableImage(screenShot);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            ClipboardOwner clipboardOwner = (cl, con) -> { };
            clipboard.setContents(transferableImage, clipboardOwner);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
