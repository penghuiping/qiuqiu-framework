package com.php25.qiuqiu.admin.vo.in.dict;

import lombok.Getter;
import lombok.Setter;

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
    private Long id;

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

    /**
     * true:有效,false: 无效
     */
    private Boolean enable;
}
