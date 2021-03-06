package com.php25.qiuqiu.user.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 21:38
 */
@Setter
@Getter
public class UserCreateDto {
    private String nickname;

    private String username;

    private String password;

    private List<Long> roleIds;

    private Long groupId;
}
