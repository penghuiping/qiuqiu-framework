package com.php25.qiuqiu.user.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2021/3/1 21:05
 */
@Setter
@Getter
public class GroupDto {

    private Long id;

    private String name;

    private String description;

    private Long parentId;

}
