package com.php25.qiuqiu.admin.vo.in.role;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/9 11:14
 */
@Setter
@Getter
public class RolePageVo {

    /**
     * 当前第几页
     */
    @NotNull
    @Positive
    private Integer pageNum;

    /**
     * 每页几条数据
     */
    @NotNull
    @Positive
    @Max(100)
    private Integer pageSize;

    /**
     * 角色名搜索
     */
    private String roleName;
}
