package com.php25.qiuqiu.admin.vo.in.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/6 19:56
 */
@Setter
@Getter
public class UserDetailVo {

    /***
     * 用户id
     */
    @NotNull
    @Positive
    @ApiModelProperty("用户id")
    private Long userId;
}
