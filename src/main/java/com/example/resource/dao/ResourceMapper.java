package com.example.resource.dao;

import com.example.resource.pojo.Resource;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ResourceMapper {
    @Select("select count(1) from t_demo_resource_s00504329 where user_id=#{userid}")
    int countResourceUseId(int userid);

    @Delete("delete from t_demo_resource_s00504329 where user_id=#{userid} and resource_id = #{resourceid}")
    int deleteEcsServer(@Param("userid") int userid, @Param("resourceid") int resourceid);

    @Insert("insert into t_demo_resource_s00504329(resource_state,user_id) values ('运行',#{userId})")
    @Options(useGeneratedKeys = true,keyProperty ="resourceId",keyColumn = "resource_id")
    int insertECSServer(Resource resource);

    @Results({
            @Result(property = "resourceId",column = "resource_id"),
            @Result(property = "resourceState",column = "resource_state"),
            @Result(property = "userId",column = "user_id")}
    )
    @Select("select * from t_demo_resource_s00504329 where resource_id=#{resourceid}")
    Resource queryByResourceId(int resourceid);

    @Results({
            @Result(property = "resourceId",column = "resource_id"),
            @Result(property = "resourceState",column = "resource_state"),
            @Result(property = "userId",column = "user_id")}
    )
    @Select("select * from t_demo_resource_s00504329 where user_id=#{userid}")
    List<Resource> queryResourceByUserId(int userid);
}
