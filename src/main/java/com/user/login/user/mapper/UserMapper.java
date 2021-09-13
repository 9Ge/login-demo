package com.user.login.user.mapper;

import com.user.login.user.model.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @Date: 2021/9/13 13:45<br/>
 * @Author: yangcheng
 * @Version: 1.0
 */
@Repository
public interface UserMapper extends Mapper<User> {
}
