package com.example.resource.service;

import com.example.resource.dao.ResourceMapper;
import com.example.resource.pojo.Resource;
import org.apache.servicecomb.provider.springmvc.reference.RestTemplateBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;

@Service
public class ResourceServiceImpl implements ResourceService {
    private final RestTemplate restTemplate = RestTemplateBuilder.create();
    @Autowired
    private ResourceMapper resourceMapper;
    @Override
    public HashMap<String,String> deleteECSServer(int userid, int resourceid) {
        int statu = resourceMapper.deleteEcsServer(userid,resourceid);
        HashMap<String,String> hashMap = new HashMap<>(2);
        if(statu>0){
            hashMap.put("retcode","000");
            hashMap.put("retdesc","删除成功");
        }else{
            hashMap.put("retcode","111");
            hashMap.put("retdesc","删除失败");
        }
        return hashMap;
    }

    @Override
    public HashMap<String, Object> userOrderECS(int userid) {
        //首先判断该用户的配额，从customer服务中获取
        HashMap<String,Object> hashMap = new HashMap<>();
        int cnt=0;
        String serviceName = "customer";
        String level = restTemplate.getForObject("cse://" + serviceName + "/hwclouds/v1/customerid/" + String.valueOf(userid), String.class);
        if(level.equals("V0")||level.equals("V1")||level.equals("V2")||level.equals("V3")){
            cnt = 2;
        }
        if(level.equals("V4")){
            cnt = 5;
        }
        if(level.equals("V5")){
            cnt = 10;
        }
        //获取用户已经创建的个数
        int hasOrderNumber = resourceMapper.countResourceUseId(userid);
        if(hasOrderNumber<cnt){
            Resource resource = new Resource();
            resource.setUserId(userid);
            int state = resourceMapper.insertECSServer(resource);
            int currentSourceId = resource.getResourceId();
            if(state > 0){
                hashMap.put("retcode","000");
                hashMap.put("retdesc","分配成功");
                hashMap.put("resource",resourceMapper.queryByResourceId(currentSourceId));
            }
        }else{
            hashMap.put("retcode","111");
            hashMap.put("retdesc","配额不足");
            hashMap.put("resource",null);
        }
        return hashMap;
    }

    @Override
    public HashMap<String,Object> queryResourceByUserId(int userid) {
        HashMap<String,Object> hashMap = new HashMap<>();
        List<Resource> resources = resourceMapper.queryResourceByUserId(userid);
        if(resources.size()==0){
            hashMap.put("retcode","111");
            hashMap.put("retdesc","配额不足");
            hashMap.put("resource",null);
        }else{
            hashMap.put("retcode","000");
            hashMap.put("retdesc","查询成功");
            hashMap.put("resource",resources);
        }
        return hashMap;
    }
}
