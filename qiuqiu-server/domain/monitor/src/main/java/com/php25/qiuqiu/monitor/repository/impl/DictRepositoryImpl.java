package com.php25.qiuqiu.monitor.repository.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.php25.common.core.util.StringUtil;
import com.php25.qiuqiu.monitor.dao.DictDao;
import com.php25.qiuqiu.monitor.dao.po.DictPo;
import com.php25.qiuqiu.monitor.entity.Dict;
import com.php25.qiuqiu.monitor.repository.DictRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/11 15:09
 */
@RequiredArgsConstructor
@Repository
public class DictRepositoryImpl implements DictRepository {
    private final DictDao dictDao;

    @Override
    public Optional<Dict> findByKey(String key) {
        DictPo dictPo = dictDao.selectOne(Wrappers.<DictPo>lambdaQuery().eq(DictPo::getKey0, key));
        if (null == dictPo) {
            return Optional.empty();
        }
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictPo, dict);
        dict.setKey(dictPo.getKey0());
        return Optional.of(dict);
    }

    @Override
    public Boolean deleteByKey(String key) {
        return dictDao.delete(Wrappers.<DictPo>lambdaQuery().eq(DictPo::getKey0, key)) > 0;
    }

    @Override
    public Boolean save(Dict dict) {
        if (dict.getId() == null) {
            //新增
            DictPo dictPo = new DictPo();
            BeanUtils.copyProperties(dict, dictPo);
            dictPo.setKey0(dict.getKey());
            return dictDao.insert(dictPo) > 0;
        } else {
            //更新
            DictPo dictPo = new DictPo();
            BeanUtils.copyProperties(dict, dictPo);
            dictPo.setKey0(dict.getKey());
            return dictDao.updateById(dictPo) > 0;
        }
    }

    @Override
    public IPage<Dict> page(String key, Integer pageNum, Integer pageSize) {
        IPage<DictPo> iPage = dictDao.selectPage(new Page<>(pageNum, pageSize)
                , Wrappers.<DictPo>lambdaQuery().eq(StringUtil.isNotBlank(key),DictPo::getKey0, key));
        IPage<Dict> result = new Page<Dict>();
        List<Dict> dictList = iPage.getRecords().stream().map(dictPo -> {
            Dict dict = new Dict();
            BeanUtils.copyProperties(dictPo, dict);
            dict.setKey(dictPo.getKey0());
            return dict;
        }).collect(Collectors.toList());
        result.setRecords(dictList);
        result.setTotal(iPage.getTotal());
        result.setCurrent(pageNum);
        result.setSize(pageSize);
        return result;
    }
}
