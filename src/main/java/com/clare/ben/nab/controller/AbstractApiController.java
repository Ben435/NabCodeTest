package com.clare.ben.nab.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api")
public abstract class AbstractApiController {

    protected Logger LOGGER = LogManager.getLogger(getClass());
}
