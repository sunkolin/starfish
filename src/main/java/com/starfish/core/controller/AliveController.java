package com.starfish.core.controller;

import com.starfish.core.model.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AliveController
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2021-06-02
 */
@RestController
public class AliveController {

    /**
     * 健康检查
     */
    @GetMapping({"/api/alive", "/starfish/api/alive", "/${spring.application.name}/api/alive"})
    public Result<String> alive() {
        return Result.success("alive");
    }

}
