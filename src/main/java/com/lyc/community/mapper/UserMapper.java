package com.lyc.community.mapper;

import com.lyc.community.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * @program: community
 * @description:
 * @author: liyuecheng
 * @create: 2019-10-02 20:38
 **/
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user (name,account_id,token,gmt_create,gmt_modified) values(#{name},#{accountId},#{token},#{gmtCreate},#{gmtModified})")
    public void insert(User user);


}
