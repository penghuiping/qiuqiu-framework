package com.php25.qiuqiu.admin.vo.in.user;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/6 19:56
 */
@Tag(name = "用户")
@Setter
@Getter
public class UserDetailVo {

    /***
     * 用户id
     */
    @NotNull
    @Positive
    @Schema(description = "用户id")
    private Long userId;
}
