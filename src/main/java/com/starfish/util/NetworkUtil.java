package com.starfish.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * NetworkUtil
 *
 * @author sunny
 * @version 1.0.0
 * @since 2018-12-20
 */
public class NetworkUtil {

    /**
     * 获取本机网络地址
     *
     * @return 结果
     * @throws Exception 异常
     */
    public static String getHostAddress() throws Exception {
        String result = "";

        Enumeration<NetworkInterface> allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = allNetInterfaces.nextElement();
            Enumeration<InetAddress> addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                InetAddress tmp = addresses.nextElement();
                if (tmp instanceof Inet4Address && tmp.isSiteLocalAddress()
                        && !tmp.isLoopbackAddress() && !tmp.getHostAddress().contains(":")) {
                    result = tmp.getHostAddress();
                    break;
                }
            }
        }

        return result;
    }

}
