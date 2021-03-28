package com.php25.qiuqiu.admin.vo.in.role;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
    @Positive
    private Integer pageNum;

    /**
     * 每页几条数据
     */
    @Positive
    @Max(100)
    private Integer pageSize;

    /**
     * 角色名搜索
     */
    private String roleName;
}
