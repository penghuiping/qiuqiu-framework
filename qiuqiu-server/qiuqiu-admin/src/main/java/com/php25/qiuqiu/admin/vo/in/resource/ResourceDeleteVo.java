package com.php25.qiuqiu.admin.vo.in.resource;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

/**
 * @author penghuiping
 * @date 2021/3/26 14:18
 */
@Setter
@Getter
public class ResourceDeleteVo {

    /**
     * 需要删除的资源名列表
     */
    @NotNull
    @Size(min = 1)
    List<String> resources;
}
