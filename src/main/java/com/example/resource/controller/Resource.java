package com.example.resource.controller;

import com.example.resource.service.ResourceService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;

@RestSchema(schemaId = "resource_t")
@RequestMapping(path = "/hwclouds/v1")
public class Resource {
    @Autowired
    private ResourceService resourceService;

    /**
     * 租户购买ECS服务器
     * @param userid
     * @return
     */
    @PostMapping(path = "/resouce/order")
    public HashMap<String,Object> userOrderECS(@RequestParam("userid") int userid){
        return resourceService.userOrderECS(userid);
    }

    /**
     * 租户删除ECS服务器
     * @param userid
     * @param resourceid
     * @return
     */
    @DeleteMapping(path = "/resouce")
    public HashMap<String,String> deleteECSServer(@RequestParam("userid") int userid,@RequestParam("resourceid") int resourceid) {
        return resourceService.deleteECSServer(userid,resourceid);
    }

    /**
     * 查询用户资源
     * @param userid
     * @return
     */
    @GetMapping(path = "/resouce")
    public HashMap<String,Object> queryUserResources(int userid) {
        return resourceService.queryResourceByUserId(userid);
    }
}
