package com.php25.qiuqiu.admin.copyer;

import com.php25.qiuqiu.admin.vo.out.user.UserVo;
import com.php25.qiuqiu.user.dto.user.UserDto;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author penghuiping
 * @date 2021/7/8 09:40
 */
public abstract class UserVoCopyerDecorator implements UserVoCopyer {

    @Autowired
    private UserVoCopyer delegate;


    @Override
    public UserVo toVo(UserDto userDto) {
        return delegate.toVo(userDto);
    }

    @Override
    public UserDto toDto(UserVo userVo) {
        return delegate.toDto(userVo);
    }
}
