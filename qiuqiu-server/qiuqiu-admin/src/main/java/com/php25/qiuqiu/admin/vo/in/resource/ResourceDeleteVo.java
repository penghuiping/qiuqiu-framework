package com.php25.qiuqiu.admin.vo.in.resource;

import lombok.Getter;
import lombok.Setter;

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
    List<String> resources;
}
