package com.starfish.core.controller;

import com.starfish.core.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AdminController
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2023-11-29
 */
@RestController
public class AdminController {

    @GetMapping({"/api/admin/getVersion"})
    public Result<String> getVersion() {
        return Result.success("1.0.0");
    }

}
