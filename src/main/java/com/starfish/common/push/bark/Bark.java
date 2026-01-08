package com.starfish.common.push.bark;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.starfish.common.push.Push;
import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.model.Result;
import com.starfish.core.util.JsonUtil;
import com.starfish.core.util.RestTemplates;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.SerializationFeature;
import tools.jackson.databind.json.JsonMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * Bark
 * 参考文档1：<a href="https://bark.day.app/#/tutorial?id=%e5%8f%91%e9%80%81%e6%8e%a8%e9%80%81">...</a>
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2026-01-06
 */
public class Bark implements Push {

    public static final int PUSH_DEER_SUCCESS_CODE = 0;

    private BarkProperties barkProperties;

    public Bark() {

    }

    public Bark(BarkProperties barkProperties) {
        this.barkProperties = barkProperties;
    }

    public Result<Object> push(BarkParam param) {
        String url = barkProperties.getBaseUrl() + barkProperties.getMessagePushUrl();

        // 对象转json，如果对象中字段是null，不输出。原因：传null到bark接口中会有各种奇怪的错误。
        String body = JsonUtil.toJson(param);
        ResponseEntity<String> responseEntity = RestTemplates.exchange(url, HttpMethod.POST, null, null, body, String.class);
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return Result.fail(ResultEnum.BARK_PUSH_RESPONSE_STATUS_CODE_ERROR);
        }
        String jsonBody = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonBody);
        int code = rootNode.path("code").asInt();
        if (code != PUSH_DEER_SUCCESS_CODE) {
            return Result.fail(ResultEnum.BARK_DEER_PUSH_RETURN_CODE_ERROR);
        }

        return Result.success();
    }

}
