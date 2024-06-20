package com.example.carclinic.Controller;


import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.slf4j.Logger;

@RestController
public class LogController {

    Logger logger = LoggerFactory.getLogger(LogController.class);
    @RequestMapping("/log")
    public String log() {
        logger.trace("Log level: TRACE");
        logger.info("Log level: INFO");
        logger.debug("Log level: DEBUG");
        logger.error("Log level: ERROR");
        logger.warn("Log level: WARN");

        return "Hello World";
    }
}
