package com.php25.common.core.dto;

/**
 * @author penghuiping
 * @date 2021/4/9 15:10
 */
public enum DataAccessLevel {
    /**
     * 可以访问企业信息系统内全部的该业务对象信息。
     */
    GLOBAL_DATA,
    /**
     * 可以访问所在组织节点范围内的该业务对象信息。
     */
    GROUP_DATA,
    /**
     * 可以访问指定其他组织节点(可以包括下级组织节点)内的该业务对象信息。
     */
    GROUP_AND_CHILDREN_DATA,
    /**
     * 仅可以访问与自己相关的(具体如何与自己相关由业务逻辑所决定)该业务对象信息。
     */
    ONLY_SELF;
}
