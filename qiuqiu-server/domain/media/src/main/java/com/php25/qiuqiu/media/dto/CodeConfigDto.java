package com.php25.qiuqiu.media.dto;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

/**
 * @author penghuiping
 * @date 2021/7/14 17:49
 */
@Setter
@Getter
public class CodeConfigDto {

    /**
     * 图片宽(px)
     */
    private Integer width;

    /**
     * 图片高(px)
     */
    private Integer height;

    /**
     * 图形验证码字体
     */
    private Font font;

    /**
     * 干扰性数量
     */
    private Integer interfereCount;
}
