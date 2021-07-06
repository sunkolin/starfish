package com.starfish.web;

import com.starfish.model.ApiResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AliveController
 *
 * @author sunny
 * @version 1.0.0
 * @since 2021-06-02
 */
@RestController
public class AliveController {

    /**
     * 健康检查
     */
    @GetMapping("/api/alive")
    public ApiResult<Object> alive() {
        return new ApiResult<>();
    }

}
