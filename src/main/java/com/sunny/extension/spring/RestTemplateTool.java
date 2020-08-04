package com.sunny.extension.spring;

import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * RestTemplateTool
 *
 * @author sunny
 * @version 1.0.0
 * @since 2015-05-05
 */
public class RestTemplateTool {

    /**
     * 构建对象支持UTF-8编码
     *
     * @return 结果
     */
    public static RestTemplate buildRestTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        useUtf8(restTemplate);
        return restTemplate;
    }

    /**
     * RestTemplate 解决乱码问题
     *
     * @param template RestTemplate
     */
    public static void useUtf8(RestTemplate template) {
        List<HttpMessageConverter<?>> converterList = template.getMessageConverters();
        HttpMessageConverter<?> converterTarget = null;
        for (HttpMessageConverter<?> item : converterList) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                converterTarget = item;
                break;
            }
        }

        if (converterTarget != null) {
            converterList.remove(converterTarget);
        }
        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
        converterList.add(converter);
    }

}
