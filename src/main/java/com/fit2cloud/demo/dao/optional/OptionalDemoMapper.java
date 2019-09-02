package com.fit2cloud.demo.dao.optional;

import com.fit2cloud.demo.model.Demo;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 请自行修改成xml模式
 */
@Repository
public interface OptionalDemoMapper {
    @Select("SELECT * FROM user")
    List<Demo> selectAll();
}
