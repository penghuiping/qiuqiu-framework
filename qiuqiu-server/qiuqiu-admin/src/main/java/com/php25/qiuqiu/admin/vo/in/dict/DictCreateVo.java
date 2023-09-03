package com.php25.qiuqiu.admin.vo.in.dict;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;

/**
 * @author penghuiping
 * @date 2021/3/11 20:15
 */
@Setter
@Getter
public class DictCreateVo {

    /**
     * 键
     */
    @NotBlank
    private String key;

    /**
     * 值
     */
    @NotBlank
    private String value;

    /**
     * 描述
     */
    @NotBlank
    private String description;
}
