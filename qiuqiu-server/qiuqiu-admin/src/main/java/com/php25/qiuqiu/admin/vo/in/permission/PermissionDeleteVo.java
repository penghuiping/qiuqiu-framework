package com.php25.qiuqiu.admin.vo.in.permission;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author penghuiping
 * @date 2021/3/9 17:34
 */
@Setter
@Getter
public class PermissionDeleteVo {

    /**
     * 需要删除的权限id列表
     */
    @NotBlank
    private String permission;
}
