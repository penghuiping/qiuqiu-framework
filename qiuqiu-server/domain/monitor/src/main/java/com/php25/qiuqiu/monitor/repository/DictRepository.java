package com.php25.qiuqiu.monitor.repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.php25.qiuqiu.monitor.entity.Dict;

import java.util.List;
import java.util.Optional;

/**
 * @author penghuiping
 * @date 2021/3/11 16:20
 */
public interface DictRepository {

    /**
     * 根据键获取字典类
     *
     * @param key 键
     * @return 字典类
     */
    Optional<Dict> findByKey(String key);

    /**
     * 根据键删除字典
     *
     * @param key 键
     * @return true:成功
     */
    Boolean deleteByKey(String key);

    /**
     * 保存字典
     *
     * @param dict 字典
     * @return true:成功
     */
    Boolean save(Dict dict);

    /**
     * 分页查询字典数据
     *
     * @param key      搜索关键字
     * @param pageNum  页码
     * @param pageSize 每页多少记录
     * @return 分页数据
     */
    IPage<Dict> page(String key, Integer pageNum, Integer pageSize);

    /**
     * key前缀模糊匹配查询
     * @param key 键前缀
     * @return  字典列表
     */
    List<Dict> findAllLikeRightKey(String key);
}
