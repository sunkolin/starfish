package com.starfish.trial.spring;

import com.starfish.util.CommonUtil;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * SuperRestTemplate
 * 使用方式有两种
 * 1 RestTemplate restTemplate = RestTemplatePlus.buildRestTemplate();
 * 2 RestTemplatePlus superRestTemplate = RestTemplatePlus.buildRestTemplatePlus();
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-16
 */
public final class RestTemplatePlus {

    /**
     * RestTemplate
     */
    private static final RestTemplate REST_TEMPLATE;

    static {
        REST_TEMPLATE = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = REST_TEMPLATE.getMessageConverters();
        supportJavascript(messageConverters);
    }

    private RestTemplatePlus() {

    }

    public static RestTemplate buildRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        supportJavascript(messageConverters);
        return restTemplate;
    }

    /**
     * 支持application/x-javascript类型
     *
     * @param messageConverters messageConverters
     */
    public static void supportJavascript(List<HttpMessageConverter<?>> messageConverters) {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("application", "x-javascript")));
        messageConverters.add(mappingJackson2HttpMessageConverter);
    }

    public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, Map<String, Object> params, Map<String, String> headers, String body, Class<T> responseType) {
        url = CommonUtil.contact(url, params);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headers)) {
            httpHeaders.setAll(headers);
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        return REST_TEMPLATE.exchange(url, method, httpEntity, responseType);
    }

    public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, Map<String, Object> params, HttpHeaders headers, String body, Class<T> responseType) {
        url = CommonUtil.contact(url, params);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        return REST_TEMPLATE.exchange(url, method, httpEntity, responseType);
    }

}
