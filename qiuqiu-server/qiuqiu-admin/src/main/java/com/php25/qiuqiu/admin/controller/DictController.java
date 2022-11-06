package com.php25.qiuqiu.admin.controller;

import com.php25.common.core.dto.PageDto;
import com.php25.common.web.JsonController;
import com.php25.common.web.JsonResponse;
import com.php25.qiuqiu.admin.mapper.DictVoMapper;
import com.php25.qiuqiu.admin.vo.in.DictPageVo;
import com.php25.qiuqiu.admin.vo.in.dict.DictCreateVo;
import com.php25.qiuqiu.admin.vo.in.dict.DictKeyVo;
import com.php25.qiuqiu.admin.vo.in.dict.DictUpdateVo;
import com.php25.qiuqiu.admin.vo.out.DictVo;
import com.php25.qiuqiu.admin.vo.out.PageResultVo;
import com.php25.qiuqiu.monitor.aop.AuditLog;
import com.php25.qiuqiu.monitor.dto.DictDto;
import com.php25.qiuqiu.monitor.service.DictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "数据字段")
@RestController
@RequestMapping(value = "/api/dict",consumes = {"application/json"},produces = {"application/json"})
@RequiredArgsConstructor
public class DictController extends JsonController {

    private final DictionaryService dictionaryService;

    private final DictVoMapper dictVoMapper;


    @ApiOperation("数据字典分页查询")
    @PostMapping(value = "/page",headers = {"version=v1","jwt"})
    public JsonResponse<PageResultVo<DictVo>> page(@Valid @RequestBody DictPageVo dictPageVo) {
        PageDto<DictDto> dataGrid = dictionaryService.page(dictPageVo.getKey(), dictPageVo.getPageNum(), dictPageVo.getPageSize());
        List<DictDto> list = dataGrid.getData();
        PageResultVo<DictVo> res = new PageResultVo<>();
        res.setData(list.stream().map(dictVoMapper::toDictVo).collect(Collectors.toList()));
        res.setTotal(dataGrid.getTotal());
        res.setCurrentPage(dictPageVo.getPageNum());
        return succeed(res);
    }

    @AuditLog
    @ApiOperation("创建字典记录")
    @PostMapping(value = "/create",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> create(@Valid @RequestBody DictCreateVo dictCreateVo) {
        return succeed(dictionaryService.create(
                dictCreateVo.getKey(),
                dictCreateVo.getValue(),
                dictCreateVo.getDescription()));
    }

    @AuditLog
    @ApiOperation("更新字典记录")
    @PostMapping(value = "/update",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> update(@Valid @RequestBody DictUpdateVo dictUpdateVo) {
        DictDto dictDto = dictVoMapper.toDictDto(dictUpdateVo);
        return succeed(dictionaryService.update(dictDto));

    }

    @AuditLog
    @ApiOperation("删除字典记录")
    @PostMapping(value = "/delete",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> delete(@Valid @RequestBody DictKeyVo dictKeyVo) {
        return succeed(dictionaryService.delete(dictKeyVo.getKey()));
    }

    @AuditLog
    @ApiOperation("刷新缓存")
    @PostMapping(value = "/refresh",headers = {"version=v1","jwt"})
    public JsonResponse<Boolean> refresh(@Valid @RequestBody DictKeyVo dictKeyVo) {
        return succeed(dictionaryService.removeCache(dictKeyVo.getKey()));
    }
}
