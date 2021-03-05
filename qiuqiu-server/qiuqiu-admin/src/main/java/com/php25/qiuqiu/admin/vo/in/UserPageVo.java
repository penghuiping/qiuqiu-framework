package com.php25.qiuqiu.admin.vo.in;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

/**
 * @author penghuiping
 * @date 2021/3/5 21:18
 */
@Setter
@Getter
public class UserPageVo {

    /**
     * 当前第几页
     */
    @NotBlank(message = "页数不能为空")
    @Min(1)
    private Integer pageNum;

    /**
     * 每页几条数据
     */
    @NotBlank(message = "每页条数不能为空")
    @Min(1)
    private Integer pageSize;

    /**
     * 用户名搜索
     */
    private String username;

}
