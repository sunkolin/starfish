package com.starfish.common.push.pushdeer;

import com.starfish.common.push.Push;
import com.starfish.core.enumeration.ResultEnum;
import com.starfish.core.model.Result;
import com.starfish.core.util.RestTemplates;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

/**
 * PushDeer
 * PushDeer已停止更新，建议使用Bark
 * <a href="https://www.pushdeer.com/official.html">参考文档1</a>
 * <a href="https://ilovintit.github.io/pushdeer-api-doc/">参考文档2</a>
 *
 * @author sunkolin
 * @version 1.0.0
 *
 *
 * @since 2026-01-06
 */
@Deprecated
public class PushDeer implements Push {

    public static final int PUSH_DEER_SUCCESS_CODE = 0;

    private PushDeerProperties pushDeerProperties;

    public PushDeer() {

    }

    public PushDeer(PushDeerProperties pushDeerProperties) {
        this.pushDeerProperties = pushDeerProperties;
    }

    public Result<Object> push(PushDeerParam param) {
        Map<String, String> params = new HashMap<>();
        params.put("pushkey", param.getPushKey());
        params.put("text", param.getText());
        params.put("desp", param.getDesp());
        params.put("type", param.getType());
        String url = pushDeerProperties.getBaseUrl() + pushDeerProperties.getMessagePushUrl();
        ResponseEntity<String> responseEntity = RestTemplates.exchange(url, HttpMethod.POST, null, params, null, String.class);
        if (!HttpStatus.OK.equals(responseEntity.getStatusCode())) {
            return Result.fail(ResultEnum.PUSH_DEER_PUSH_RESPONSE_STATUS_CODE_ERROR);
        }
        String jsonBody = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(jsonBody);
        int code = rootNode.path("code").asInt();
        if (code != PUSH_DEER_SUCCESS_CODE) {
            return Result.fail(ResultEnum.PUSH_DEER_PUSH_RETURN_CODE_ERROR);
        }

        return Result.success();
    }

}
