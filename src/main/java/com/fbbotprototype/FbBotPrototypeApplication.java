package com.fbbotprototype;

import com.fbbotprototype.services.FBService;
import com.fbbotprototype.services.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class FbBotPrototypeApplication {

    public static void main(String[] args) {

        System.setProperty("java.awt.headless", "false");
        ApplicationContext applicationContext = SpringApplication.run(FbBotPrototypeApplication.class, args);
        FBService fbService = applicationContext.getBean(FBService.class);
        fbService.botLogIn();
        XKomService xkomService = applicationContext.getBean(XKomService.class);
        xkomService.getGPUs();
        fbService.sendClipboardImageTo("Hubert Hubo");
    }
}
