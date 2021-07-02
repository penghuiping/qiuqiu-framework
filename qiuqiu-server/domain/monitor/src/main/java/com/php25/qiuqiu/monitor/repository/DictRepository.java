package com.php25.qiuqiu.monitor.repository;

import com.php25.common.db.repository.BaseDbRepository;
import com.php25.qiuqiu.monitor.entity.Dict;

import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/11 16:20
 */
public interface DictRepository extends BaseDbRepository<Dict, Long> {

    Optional<Dict> findByKey(String key);

    Boolean deleteByKey(String key);
}
