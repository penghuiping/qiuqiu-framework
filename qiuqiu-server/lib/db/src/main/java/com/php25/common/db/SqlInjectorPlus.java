package com.php25.common.db;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.extension.injector.methods.InsertBatchSomeColumn;

import java.util.List;

/**
 * @author penghuiping
 * @date 2023/9/9 22:49
 */
public class SqlInjectorPlus extends DefaultSqlInjector {

    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass, TableInfo tableInfo) {
        //继承原有方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass,tableInfo);
        //注入新方法
        methodList.add(new InsertBatchSomeColumn());
        return methodList;
    }
}
