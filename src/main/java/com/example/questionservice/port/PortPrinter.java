package com.example.questionservice.port;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class PortPrinter {

    private static final Logger logger = LoggerFactory.getLogger(PortPrinter.class);

    @EventListener
    public void onApplicationEvent(WebServerInitializedEvent event) {
        int port = event.getWebServer().getPort();
        logger.info("✅ Application is running on port: {}", port);
    }
}
