package com.php25.qiuqiu.admin.vo.out;

import lombok.Getter;
import lombok.Setter;

/**
 * @author penghuiping
 * @date 2023/9/10 22:49
 */
@Setter
@Getter
public class SystemLogPageOutVo {
    private String id;

    private String timestamp;

    private String message;

    private String threadName;

    private String loggerName;

    private String level;
}
