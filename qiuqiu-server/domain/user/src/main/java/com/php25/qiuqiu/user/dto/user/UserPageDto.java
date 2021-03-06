package com.php25.qiuqiu.user.dto.user;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author penghuiping
 * @date 2021/3/5 21:32
 */
@Setter
@Getter
public class UserPageDto {

    private Long id;

    private String nickname;

    private String username;

    private LocalDateTime createTime;

    private LocalDateTime lastModifiedTime;

    private Integer enable;
}
