package com.yhd.gps.schedule.common;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.yihaodian.architecture.hedwig.common.constants.PropKeyConstants;
import com.yihaodian.architecture.hedwig.common.util.HedwigContextUtil;

/**
 * ---- 请加注释 ------
 * @Author xuyong
 * @CreateTime 2016-4-1 下午06:24:28
 */
public class PoolUtils {
    private static final Log log = LogFactory.getLog(PoolUtils.class);
    private static String poolName;
    private static String poolIP;
    // 从hedwig中获取客户端IP和pool名称
    private static String clientVersion = (String) HedwigContextUtil.getAttribute(PropKeyConstants.CLIENT_VERSION,
            "unknowClient");

    static {
        poolName = computePoolName();
        poolIP = computePoolIp();
    }

    public static String getPoolName() {
        return poolName;
    }

    public static String getPoolIP() {
        return poolIP;
    }

    public static String getClientVersion() {
        return clientVersion;
    }

    private static String computePoolName() {
        String result = "";
        try {
            java.net.URL url = PoolUtils.class.getProtectionDomain().getCodeSource().getLocation();
            try {
                result = java.net.URLDecoder.decode(url.getPath(), "utf-8");
            } catch (Exception e) {
                log.error(" ", e);
            }
            if(result.endsWith(".jar")) {
                result = result.substring(0, result.lastIndexOf("/") + 1);
            }
            java.io.File file = new java.io.File(result);
            result = file.getAbsolutePath();
            int index = result.indexOf("webapps");
            boolean hasPoolName = false;
            if(index > 0) {
                int start = result.indexOf("/", index) + 1;
                int end = result.indexOf("/", start);
                if(start > 0 && end > 0) {
                    result = result.substring(start, end);
                    hasPoolName = true;
                }
            }
            if(hasPoolName == false) {
                Map<String, String> map = System.getenv();
                String userName = map.get("USERNAME");// 获取用户名
                if(userName != null) {
                    result = userName + "@" + result;
                }
            }
        } catch (Exception e) {
            result = "";
            log.error(" ", e);
        }
        return result;
    }

    /**
     * 获取本机的ip地址,多个IP地址(只返回最多2个)以;隔开
     * @return
     */
    public static String computePoolIp() {
        int ipCount = 0;
        String result = null;
        Enumeration<NetworkInterface> netInterfaces = null;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            while(netInterfaces.hasMoreElements()) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> ips = ni.getInetAddresses();
                while(ips.hasMoreElements()) {
                    InetAddress ip = ips.nextElement();
                    String ipStr = ip.getHostAddress();
                    if(ipCount > 1){
                        return result;
                    }
                    if(ip instanceof Inet4Address && !"127.0.0.1".equals(ipStr)) {
                        if(result == null) {
                            result = ipStr;
                        } else {
                            result += ";";
                            result += ipStr;
                        }
                        ipCount++;
                    }
                }
            }
        } catch (Exception e) {
            log.error(" ", e);
            result = "-1";
        }
        return result;
    }

}