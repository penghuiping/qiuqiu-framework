package com.php25.qiuqiu.admin.vo.in.group;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;

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
    @Positive
    private Long groupId;
}
