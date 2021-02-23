package com.php25.qiuqiu.admin.vo.out;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author penghuiping
 * @date 2021/2/4 09:43
 */
@Setter
@Getter
public class UserVo {

    /**
     * 用户id
     */
    private Long id;

    /**
     * 用户昵称
     */
    private String nickname;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户信息创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * 用户信息最后修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime lastModifiedTime;

    /**
     * 用户账号是否可用 1:可用 2:不可用
     */
    private Integer enable;
}
