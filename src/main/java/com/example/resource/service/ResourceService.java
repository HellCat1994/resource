package com.example.resource.service;
import java.util.HashMap;

public interface ResourceService {
    HashMap<String,String> deleteECSServer(int userid, int resourceid);
    HashMap<String,Object> userOrderECS(int userid);
    HashMap<String,Object> queryResourceByUserId(int userid);
}
