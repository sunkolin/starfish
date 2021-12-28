//package com.starfish.extension.spring;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.cloud.client.ServiceInstance;
//import org.springframework.cloud.client.discovery.DiscoveryClient;
//import org.springframework.stereotype.Service;
//import org.springframework.util.CollectionUtils;
//
//import java.util.List;
//
///**
// * ApplicationService
// *
// * @author neacle
// * @version 1.0.0
// * @since 2019-03-28
// */
//@Slf4j
//@Service
//public class DiscoveryPlus {
//
//    @Autowired
//    private DiscoveryClient discoveryClient;
//
//    @Value("${spring.application.name}")
//    private String serviceId;
//
//    /**
//     * 获取集群服务数量
//     */
//    public Integer getServerCount() {
//        List<ServiceInstance> serviceInstanceList = discoveryClient.getInstances(serviceId);
//
//        if (CollectionUtils.isEmpty(serviceInstanceList)) {
//            return 0;
//        }
//
//        return serviceInstanceList.size();
//    }
//
//    public List<ServiceInstance> getEurekaRegisterServerList(String serviceId) {
//        return discoveryClient.getInstances(serviceId);
//    }
//
//    public List<ServiceInstance> getEurekaServerList() {
//        return discoveryClient.getInstances(serviceId);
//    }
//
//}
