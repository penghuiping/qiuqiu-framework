package com.php25.qiuqiu.monitor.dao.es.po;

import cn.easyes.annotation.IndexField;
import cn.easyes.annotation.IndexId;
import cn.easyes.annotation.IndexName;
import cn.easyes.annotation.rely.FieldType;
import cn.easyes.annotation.rely.IdType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author penghuiping
 * @date 2023/9/10 20:15
 */
@Setter
@Getter
@ToString
@IndexName("t_system_log")
public class SystemLogPo {
    @IndexId(type = IdType.UUID)
    private String id;

    @JsonProperty("@timestamp")
    @IndexField(fieldType = FieldType.KEYWORD)
    private String timestamp;

    @IndexField(fieldType = FieldType.TEXT)
    private String message;

    @JsonProperty("logger_name")
    @IndexField(fieldType = FieldType.TEXT)
    private String loggerName;

    @JsonProperty("thread_name")
    @IndexField(fieldType = FieldType.TEXT)
    private String threadName;

    @IndexField(fieldType = FieldType.KEYWORD)
    private String level;
}
