package com.php25.qiuqiu.user.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author penghuiping
 * @date 2021/2/3 13:24
 */
@Setter
@Getter
public class UserDto {

    private Long id;

    private String nickname;

    private String username;

    private String password;

    private LocalDateTime createTime;

    private LocalDateTime lastModifiedTime;

    private Integer enable;
}
