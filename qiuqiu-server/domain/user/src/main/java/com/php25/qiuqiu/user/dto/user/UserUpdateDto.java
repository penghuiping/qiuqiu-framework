package com.php25.qiuqiu.user.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/5 21:42
 */
@Setter
@Getter
public class UserUpdateDto {
    private Long id;

    private String nickname;

    private String password;

    private List<Long> roleIds;

    private Long groupId;

    private Integer enable;
}
