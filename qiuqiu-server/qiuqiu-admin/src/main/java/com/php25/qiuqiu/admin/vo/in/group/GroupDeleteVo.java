package com.php25.qiuqiu.admin.vo.in.group;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/10 16:02
 */
@Setter
@Getter
public class GroupDeleteVo {

    /**
     * 组id
     */
    @NotNull
    @Positive
    private Long groupId;
}
