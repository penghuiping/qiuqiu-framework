package com.php25.qiuqiu.admin.vo.in.role;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @NotNull(message = "页数不能为空")
    @Min(0)
    private Integer pageNum;

    /**
     * 每页几条数据
     */
    @NotNull(message = "每页条数不能为空")
    @Min(1)
    private Integer pageSize;

    /**
     * 角色名搜索
     */
    private String roleName;
}
