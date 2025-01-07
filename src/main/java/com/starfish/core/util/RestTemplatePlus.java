package com.starfish.core.util;

import com.google.common.base.Strings;
import org.springframework.http.*;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateExpiredException;
import java.security.cert.CertificateNotYetValidException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * SuperRestTemplate
 * 使用方式有两种
 * 1 RestTemplate restTemplate = RestTemplatePlus.buildRestTemplate();
 * 2 RestTemplatePlus superRestTemplate = RestTemplatePlus.buildRestTemplatePlus();
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2020-10-16
 */
@SuppressWarnings("unused")
public final class RestTemplatePlus {

    /**
     * RestTemplate
     */
    private static final RestTemplate REST_TEMPLATE;

    private static final RestTemplate SSL_REST_TEMPLATE;

    private static final int CONNECT_TIMEOUT = 3000;

    private static final int READ_TIMEOUT = 3000;

    static {
        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setConnectTimeout(CONNECT_TIMEOUT);
        simpleClientHttpRequestFactory.setReadTimeout(READ_TIMEOUT);
        REST_TEMPLATE = new RestTemplate(simpleClientHttpRequestFactory);
        init(REST_TEMPLATE);

        SslHttpRequestFactory factory = new SslHttpRequestFactory(true);
        factory.setReadTimeout(CONNECT_TIMEOUT);
        factory.setConnectTimeout(READ_TIMEOUT);
        SSL_REST_TEMPLATE = new RestTemplate(factory);
        init(SSL_REST_TEMPLATE);
    }

    private RestTemplatePlus() {
        // constructor
    }

    private static void init(RestTemplate restTemplate) {
        supportUtf8(restTemplate);
        supportJavascript(restTemplate);
    }

    /**
     * 支持UTF-8
     *
     * @param restTemplate restTemplate
     */
    private static void supportUtf8(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        for (HttpMessageConverter<?> item : messageConverters) {
            if (item.getClass() == StringHttpMessageConverter.class) {
                StringHttpMessageConverter messageConverter = (StringHttpMessageConverter) item;
                messageConverter.setDefaultCharset(StandardCharsets.UTF_8);
                break;
            }
        }
    }

    /**
     * 支持application/x-javascript类型
     *
     * @param restTemplate restTemplate
     */
    private static void supportJavascript(RestTemplate restTemplate) {
        List<HttpMessageConverter<?>> messageConverters = restTemplate.getMessageConverters();
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Collections.singletonList(new MediaType("application", "x-javascript")));
        messageConverters.add(mappingJackson2HttpMessageConverter);
    }

    /**
     * 发起http请求
     *
     * @param url          请求地址
     * @param method       方法
     * @param params       参数
     * @param headers      headers
     * @param form         请求体
     * @param responseType 返回类型
     * @param <T>          T
     * @return 结果
     */
    public static <T> ResponseEntity<T> form(String url, HttpMethod method, Map<String, String> headers, Map<String, String> params, Map<String, String> form, Class<T> responseType) {
        url = CommonUtil.contact(url, params);

        HttpHeaders httpHeaders = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headers)) {
            httpHeaders.setAll(headers);
        }
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> innerForm = new LinkedMultiValueMap<>();
        if (!CollectionUtils.isEmpty(form)) {
            for (Map.Entry<String, String> item : form.entrySet()) {
                innerForm.add(item.getKey(), item.getValue());
            }
        }
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(innerForm, httpHeaders);
        return REST_TEMPLATE.exchange(url, method, requestEntity, responseType);
    }

    /**
     * 发起http请求
     *
     * @param url          请求地址
     * @param method       方法
     * @param params       参数
     * @param headers      headers
     * @param body         请求体
     * @param responseType 返回类型
     * @param <T>          T
     * @return 结果
     */
    public static <T> ResponseEntity<T> exchange(String url, HttpMethod method, Map<String, String> headers, Map<String, String> params, String body, Class<T> responseType) {
        url = CommonUtil.contact(url, params);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headers)) {
            httpHeaders.setAll(headers);
        }
        if (!Strings.isNullOrEmpty(body)) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        return REST_TEMPLATE.exchange(url, method, httpEntity, responseType);
    }

    /**
     * 发起http请求，支持跳过SSL证书，<a href="https://blog.csdn.net/weixin_39982274/article/details/88818213">参考文档</a>
     *
     * @param url          请求地址
     * @param method       方法
     * @param params       参数
     * @param headers      headers
     * @param body         请求体
     * @param responseType 返回类型
     * @param <T>          T
     * @return 结果
     */
    public static <T> ResponseEntity<T> exchangeSkipSsl(String url, HttpMethod method, Map<String, String> headers, Map<String, String> params, String body, Class<T> responseType) {
        url = CommonUtil.contact(url, params);
        HttpHeaders httpHeaders = new HttpHeaders();
        if (!CollectionUtils.isEmpty(headers)) {
            httpHeaders.setAll(headers);
        }
        if (!Strings.isNullOrEmpty(body)) {
            httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        }
        HttpEntity<String> httpEntity = new HttpEntity<>(body, httpHeaders);
        return SSL_REST_TEMPLATE.exchange(url, method, httpEntity, responseType);
    }

    static class SslHttpRequestFactory extends SimpleClientHttpRequestFactory {

        private final boolean skipSsl;

        public SslHttpRequestFactory(boolean skipSsl) {
            // fix javax.net.ssl.SSLProtocolException: handshake alert:unrecognized_name
            // reference:https://blog.csdn.net/leisurelen/article/details/74407817
            System.setProperty("jsse.enableSNIExtension", "false");

            this.skipSsl = skipSsl;
        }

        @Override
        protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
            if (connection instanceof HttpsURLConnection httpsUrlConnection) {
                prepareHttpsConnection(httpsUrlConnection);
            }
            super.prepareConnection(connection, httpMethod);
        }

        private void prepareHttpsConnection(HttpsURLConnection connection) {
            connection.setHostnameVerifier(new SkipHostnameVerifier(skipSsl));
            try {
                connection.setSSLSocketFactory(createSslSocketFactory());
            } catch (Exception ex) {
                // Ignore
            }
        }

        private SSLSocketFactory createSslSocketFactory() throws NoSuchAlgorithmException, KeyManagementException {
            SSLContext context = SSLContext.getInstance("TLSv1.2");
            context.init(null, new TrustManager[]{new SkipX509TrustManager(skipSsl)}, new SecureRandom());
            return context.getSocketFactory();
        }

        private static class SkipHostnameVerifier implements HostnameVerifier {

            private final boolean skipSsl;

            public SkipHostnameVerifier(boolean skipSsl) {
                this.skipSsl = skipSsl;
            }

            @Override
            public boolean verify(String hostname, SSLSession sslSession) {
                // skipSsl
                return skipSsl;
            }

        }

        private static class SkipX509TrustManager implements X509TrustManager {

            private final boolean skipSsl;

            public SkipX509TrustManager(boolean skipSsl) {
                this.skipSsl = skipSsl;
            }

            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateNotYetValidException, CertificateExpiredException {
                // skipSsl
                if (!skipSsl && chain != null) {
                    for (X509Certificate certificate : chain) {
                        certificate.checkValidity();
                    }
                }
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateNotYetValidException, CertificateExpiredException {
                // skipSsl
                if (!skipSsl && chain != null) {
                    for (X509Certificate certificate : chain) {
                        certificate.checkValidity();
                    }
                }
            }

        }

    }

}