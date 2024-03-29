package com.php25.qiuqiu.admin.vo.in.dict;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * @author penghuiping
 * @date 2021/3/11 20:16
 */
@Setter
@Getter
public class DictUpdateVo {

    /**
     * id值
     */
    @NotNull
    @Positive
    private Long id;

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

    /**
     * true:有效,false: 无效
     */
    @NotNull
    private Boolean enable;
}
