package com.php25.qiuqiu.monitor.dao.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/11 16:16
 */
@Setter
@Getter
@TableName("t_dict")
public class DictPo  {

    /**
     * 自增id
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 键
     */
    @TableField
    private String key0;

    /**
     * 值
     */
    @TableField
    private String value;

    /**
     * 描述
     */
    @TableField
    private String description;

    /**
     * true: 有效,false: 无效
     */
    @TableField
    private Boolean enable;

}
