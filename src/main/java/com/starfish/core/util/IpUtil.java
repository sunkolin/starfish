package com.starfish.core.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Data;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.io.Serializable;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * IpUtil
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2015-01-11
 */
public class IpUtil {

    /**
     * 淘宝查询IP地址接口
     */
    private static final String TAO_BAO_INTERFACE_URL = "https://ip.taobao.com/outGetIpInfo?accessKey=alibaba-inc";

    /**
     * 获取本机局域网IP地址
     *
     * @return 结果
     * @throws NoSuchElementException 异常
     * @throws SocketException        异常
     */
    public static String getLocalAreaNetworkIp() throws NoSuchElementException, SocketException {
        String result = "";
        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress tmp = addresses.nextElement();
                // Inet4Address.isSiteLocalAddress()验证条件去掉
                // 方法验证是否以一下前缀开头，部分情况下不好用，在我的mac下局域网地址是20.18.138.3
                // 10/8 prefix
                // 172.16/12 prefix
                // 192.168/16 prefix
                if (tmp instanceof Inet4Address && !tmp.isLoopbackAddress() && !tmp.getHostAddress().contains(":")) {
                    result = tmp.getHostAddress();
                    break;
                }
            }
        }

        return result;
    }

    /**
     * 获取本机公网IP地址，<a href="https://ipv4.icanhazip.com/">接口地址</a>
     *
     * @return 结果
     */
    public static String getPublicNetworkIp() {
        String url = "https://ipv4.icanhazip.com";
        HttpMethod method = HttpMethod.GET;
        Map<String, String> headers = new HashMap<>(8);
        Map<String, String> params = new HashMap<>(8);
        Class<String> responseType = String.class;
        ResponseEntity<String> response = RestTemplates.exchange(url, method, headers, params, null, responseType);
        return response.getBody();
    }

    /**
     * 根据ip获取实际地址
     * get the location,use tao bao interface default
     *
     * @param ip ip
     * @return the location
     */
    public static String getAddress(String ip) {
        String result;
        try {
            //call remote interface
            Map<String, String> params = Map.of("ip", ip);
            ResponseEntity<String> responseEntity = RestTemplates.exchange(TAO_BAO_INTERFACE_URL, HttpMethod.GET, null, params, null, String.class);
            String json = responseEntity.getBody();
            GetIpAddressResult getIpAddressResult = JsonUtil.toObject(json, GetIpAddressResult.class);
            GetIpAddressData data = getIpAddressResult.getData();

            //return
            return data.getCountry() + " " + data.getRegion() + " " + data.getCity();
        } catch (Exception e) {
            result = "未知";
            return result;
        }
    }

    /**
     * 根据请求获取实际地址
     * get address by ip
     *
     * @param request request
     * @return the location
     */
    public static String getAddress(HttpServletRequest request) {
        return getAddress(WebUtil.getRequestIp(request));
    }

    @Data
    static class GetIpAddressResult implements Serializable {

        private Integer code;

        private String msg;

        private GetIpAddressData data;

    }

    @Data
    static class GetIpAddressData implements Serializable {

        private String area;

        private String country;

        @JsonProperty("isp_id")
        private String ispId;

        private String queryIp;

        private String city;

        private String ip;

        private String isp;

        private String county;

        @JsonProperty("region_id")
        private String regionId;

        @JsonProperty("area_id")
        private String areaId;

        @JsonProperty("county_id")
        private String countyId;

        private String region;

        @JsonProperty("country_id")
        private String countryId;

        @JsonProperty("city_id")
        private String cityId;

    }

}
