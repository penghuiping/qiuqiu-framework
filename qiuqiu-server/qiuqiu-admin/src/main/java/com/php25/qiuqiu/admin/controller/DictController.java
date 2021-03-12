package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.DataGridPageDto;
import com.php25.common.flux.web.APIVersion;
import com.php25.common.flux.web.JSONController;
import com.php25.common.flux.web.JSONResponse;
import com.php25.qiuqiu.admin.vo.in.DictPageVo;
import com.php25.qiuqiu.admin.vo.in.dict.DictCreateVo;
import com.php25.qiuqiu.admin.vo.in.dict.DictKeyVo;
import com.php25.qiuqiu.admin.vo.in.dict.DictUpdateVo;
import com.php25.qiuqiu.admin.vo.out.DictVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.monitor.dto.DictDto;
import com.php25.qiuqiu.monitor.service.DictionaryService;
import io.github.yedaxia.apidocs.ApiDoc;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author penghuiping
 * @date 2021/3/11 16:50
 */
@RestController
@RequestMapping("/dict")
@RequiredArgsConstructor
public class DictController extends JSONController {

    private final DictionaryService dictionaryService;

    /**
     * 数据字典分页查询
     *
     * @param dictPageVo 分页信息
     */
    @ApiDoc(result = DictVo.class, url = "/qiuqiu_admin/v1/dict/page")
    @APIVersion("v1")
    @PostMapping("/page")
    public JSONResponse page(@Valid @RequestBody DictPageVo dictPageVo) {
        DataGridPageDto<DictDto> dataGrid = dictionaryService.page(dictPageVo.getKey(), dictPageVo.getPageNum(), dictPageVo.getPageSize());
        List<DictDto> list = dataGrid.getData();
        PageResultVo<DictVo> res = new PageResultVo<>();
        res.setData(list.stream().map(dictDto -> {
            DictVo dictVo = new DictVo();
            BeanUtils.copyProperties(dictDto, dictVo);
            return dictVo;
        }).collect(Collectors.toList()));
        res.setTotal(dataGrid.getRecordsTotal());
        res.setCurrentPage(dictPageVo.getPageNum());
        return succeed(res);
    }

    /**
     * 创建字典记录
     *
     * @param dictCreateVo 字典记录
     */
    @AuditLog
    @ApiDoc(stringResult = "true:创建成功", url = "/qiuqiu_admin/v1/dict/create")
    @APIVersion("v1")
    @PostMapping("/create")
    public JSONResponse create(@Valid @RequestBody DictCreateVo dictCreateVo) {
        return succeed(dictionaryService.create(
                dictCreateVo.getKey(),
                dictCreateVo.getValue(),
                dictCreateVo.getDescription()));
    }

    /**
     * 更新字典记录
     *
     * @param dictUpdateVo 字典记录
     */
    @AuditLog
    @ApiDoc(stringResult = "true:更新成功", url = "/qiuqiu_admin/v1/dict/update")
    @APIVersion("v1")
    @PostMapping("/update")
    public JSONResponse update(@Valid @RequestBody DictUpdateVo dictUpdateVo) {
        DictDto dictDto = new DictDto();
        BeanUtils.copyProperties(dictUpdateVo, dictDto);
        return succeed(dictionaryService.update(dictDto));

    }

    /**
     * 删除字典记录
     *
     * @param dictKeyVo 删除key
     */
    @AuditLog
    @ApiDoc(stringResult = "true:删除成功", url = "/qiuqiu_admin/v1/dict/delete")
    @APIVersion("v1")
    @PostMapping("/delete")
    public JSONResponse delete(@Valid @RequestBody DictKeyVo dictKeyVo) {
        return succeed(dictionaryService.delete(dictKeyVo.getKey()));
    }

    /**
     * 刷新缓存
     *
     * @param dictKeyVo 刷新key
     */
    @AuditLog
    @ApiDoc(stringResult = "true:操作成功", url = "/qiuqiu_admin/v1/dict/refresh")
    @APIVersion("v1")
    @PostMapping("/refresh")
    public JSONResponse refresh(@Valid @RequestBody DictKeyVo dictKeyVo) {
        return succeed(dictionaryService.removeCache(dictKeyVo.getKey()));
    }
}
