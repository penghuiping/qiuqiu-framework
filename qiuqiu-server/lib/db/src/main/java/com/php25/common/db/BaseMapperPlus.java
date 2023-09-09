package com.php25.common.db;



import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.Collection;

/**
 * @author penghuiping
 * @date 2023/1/19 20:53
 */
public interface BaseMapperPlus <T> extends BaseMapper<T> {

    /**
     * 高效率批量插入
     * entityList数量不能太大，否则存在丢包问题；
     * 单次entityList数量务必控制在一万内，或者在service中再次封装控制数量；
     * @param entityList 数据列表
     * @return 成功标示
     */
    Integer insertBatchSomeColumn(Collection<T> entityList);

}
