package com.php25.qiuqiu.admin.vo.in.dict;

import lombok.Getter;
import lombok.Setter;

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
    private String key;

    /**
     * 值
     */
    private String value;

    /**
     * 描述
     */
    private String description;
}
