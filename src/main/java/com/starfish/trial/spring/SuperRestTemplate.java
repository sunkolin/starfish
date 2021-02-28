package com.starfish.trial.spring;

import com.starfish.util.CommonUtil;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * SuperRestTemplate
 * 使用方式有两种
 * 1 RestTemplate restTemplate = SuperRestTemplate.buildRestTemplate();
 * 2 SuperRestTemplate superRestTemplate = SuperRestTemplate.buildSuperRestTemplate();
 *
 * @author sunny
 * @version 1.0.0
 * @since 2020-10-16
 */
public class SuperRestTemplate extends RestTemplate {

    private SuperRestTemplate() {
        super();
        List<HttpMessageConverter<?>> messageConverters = super.getMessageConverters();
        supportUtf8(messageConverters);
        supportJavascript(messageConverters);
    }

    public static SuperRestTemplate buildSuperRestTemplate() {
        return new SuperRestTemplate();
    }

    public static RestTemplate buildRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        supportUtf8(messageConverters);
        supportJavascript(messageConverters);
        return restTemplate;
    }

    /**
     * RestTemplate 解决乱码问题
     *
     * @param messageConverters messageConverters
     */
    public static void supportUtf8(List<HttpMessageConverter<?>> messageConverters) {
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : messageConverters) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }
        if (converterTarget != null) {
            messageConverters.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        messageConverters.add(converter);
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

//        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
//        stringHttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("application", "x-javascript")));
//        template.getMessageConverters().add(stringHttpMessageConverter);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, Map<String, Object> params, Map<String, String> headers, String body, Class<T> responseType) {
        url = CommonUtil.contact(url, params);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headers)) {
            httpHeaders.setAll(headers);
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        return super.exchange(url, method, httpEntity, responseType);
    }

    public <T> ResponseEntity<T> exchange(String url, HttpMethod method, Map<String, Object> params, HttpHeaders headers, String body, Class<T> responseType) {
        url = CommonUtil.contact(url, params);
        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        return super.exchange(url, method, httpEntity, responseType);
    }

}
