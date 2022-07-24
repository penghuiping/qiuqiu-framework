package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.PageDto;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.DictPageVo;
import com.php25.qiuqiu.admin.vo.in.dict.DictCreateVo;
import com.php25.qiuqiu.admin.vo.in.dict.DictKeyVo;
import com.php25.qiuqiu.admin.vo.in.dict.DictUpdateVo;
import com.php25.qiuqiu.admin.mapper.DictVoMapper;
import com.php25.qiuqiu.admin.vo.out.DictVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.monitor.dto.DictDto;
import com.php25.qiuqiu.monitor.service.DictionaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 数据字段
 *
 * @author penghuiping
 * @date 2021/3/11 16:50
 */
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictController extends JSONController {

    private final DictionaryService dictionaryService;

    private final DictVoMapper dictVoMapper;

    /**
     * 数据字典分页查询
     *
     * @since v1
     */
    @PostMapping(value = "/page",headers = {"version=v1"})
    public JSONResponse<PageResultVo<DictVo>> page(@Valid @RequestBody DictPageVo dictPageVo) {
        PageDto<DictDto> dataGrid = dictionaryService.page(dictPageVo.getKey(), dictPageVo.getPageNum(), dictPageVo.getPageSize());
        List<DictDto> list = dataGrid.getData();
        PageResultVo<DictVo> res = new PageResultVo<>();
        res.setData(list.stream().map(dictVoMapper::toDictVo).collect(Collectors.toList()));
        res.setTotal(dataGrid.getTotal());
        res.setCurrentPage(dictPageVo.getPageNum());
        return succeed(res);
    }

    /**
     * 创建字典记录
     *
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/create",headers = {"version=v1"})
    public JSONResponse<Boolean> create(@Valid @RequestBody DictCreateVo dictCreateVo) {
        return succeed(dictionaryService.create(
                dictCreateVo.getKey(),
                dictCreateVo.getValue(),
                dictCreateVo.getDescription()));
    }

    /**
     * 更新字典记录
     *
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/update",headers = {"version=v1"})
    public JSONResponse<Boolean> update(@Valid @RequestBody DictUpdateVo dictUpdateVo) {
        DictDto dictDto = dictVoMapper.toDictDto(dictUpdateVo);
        return succeed(dictionaryService.update(dictDto));

    }

    /**
     * 删除字典记录
     *
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/delete",headers = {"version=v1"})
    public JSONResponse<Boolean> delete(@Valid @RequestBody DictKeyVo dictKeyVo) {
        return succeed(dictionaryService.delete(dictKeyVo.getKey()));
    }

    /**
     * 刷新缓存
     *
     * @since v1
     */
    @AuditLog
    @PostMapping(value = "/refresh",headers = {"version=v1"})
    public JSONResponse<Boolean> refresh(@Valid @RequestBody DictKeyVo dictKeyVo) {
        return succeed(dictionaryService.removeCache(dictKeyVo.getKey()));
    }
}
