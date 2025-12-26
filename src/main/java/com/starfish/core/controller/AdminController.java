package com.starfish.core.controller;

import com.starfish.core.model.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2023-11-29
 */
@Slf4j
@RestController
public class AdminController {

    @GetMapping({"/api/admin/getVersion"})
    public Result<String> getVersion() {
        String version = "2.1.2";
        log.info("current starfish version is {}", version);
        return Result.success(version);
    }

}
