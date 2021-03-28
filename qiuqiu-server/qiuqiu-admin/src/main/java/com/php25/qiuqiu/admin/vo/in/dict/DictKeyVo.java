package com.php25.qiuqiu.admin.vo.in.dict;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author penghuiping
 * @date 2021/3/11 20:18
 */
@Setter
@Getter
public class DictKeyVo {

    /**
     * 键
     */
    @NotBlank
    private String key;
}
