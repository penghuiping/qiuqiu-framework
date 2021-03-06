package com.php25.common.db.core.manager;

import com.google.common.collect.Lists;
import com.php25.common.core.util.AssertUtil;
import com.php25.common.core.util.ReflectUtil;
import com.php25.common.core.util.StringUtil;
import com.php25.common.db.core.annotation.Column;
import com.php25.common.db.core.annotation.DbSchema;
import com.php25.common.db.core.annotation.GeneratedValue;
import com.php25.common.db.core.annotation.SequenceGenerator;
import com.php25.common.db.core.annotation.Table;
import com.php25.common.db.exception.DbException;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.annotation.Version;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author: penghuiping
 * @date: 2019/7/25 14:37
 * @description:
 */
class JdbcModelManagerHelper {

    private static final Logger log = LoggerFactory.getLogger(JdbcModelManagerHelper.class);


    protected static ModelMeta getModelMeta(Class<?> cls) {
        ModelMeta modelMeta = new ModelMeta();
        String tableName = getTableName(cls);
        modelMeta.setLogicalTableName(tableName);
        Field[] fields = cls.getDeclaredFields();
        List<String> dbColumns = new ArrayList<>();
        List<String> classColumns = new ArrayList<>();
        List<Class> columnsType = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            Transient tmp = field.getAnnotation(Transient.class);
            if (null == tmp && !Collection.class.isAssignableFrom(field.getType())) {
                classColumns.add(field.getName());
                Column column = field.getAnnotation(Column.class);
                String columnName = null;
                if (null == column) {
                    columnName = field.getName();
                } else {
                    columnName = StringUtil.isBlank(column.value()) ? field.getName() : column.value();
                }
                dbColumns.add(columnName);
                columnsType.add(field.getType());
            }
        }
        modelMeta.setClassColumns(classColumns);
        modelMeta.setDbColumns(dbColumns);
        modelMeta.setColumnTypes(columnsType);
        modelMeta.setPkField(getPrimaryKeyField(cls));
        modelMeta.setDbPkName(getPrimaryKeyColName(cls));
        modelMeta.setClassPkName(getPrimaryKeyFieldName(cls));
        modelMeta.setExistCollectionAttribute(existCollectionAttribute(cls));
        Optional<Field> versionOptional = getVersionField(cls);
        versionOptional.ifPresent(modelMeta::setVersionField);
        getAnnotationGeneratedValue(cls).ifPresent(modelMeta::setGeneratedValue);
        getAnnotationSequenceGenerator(cls).ifPresent(modelMeta::setSequenceGenerator);
        return modelMeta;
    }

    /**
     * ??????model???,@version?????????
     *
     * @param cls model?????????class
     * @return model?????????????????? version field
     */
    protected static Optional<Field> getVersionField(Class<?> cls) {
        AssertUtil.notNull(cls, "class?????????null");
        Field[] fields = cls.getDeclaredFields();
        Field versionField = null;
        for (Field field : fields) {
            Version version = field.getAnnotation(Version.class);
            if (version != null) {
                versionField = field;
                break;
            }
        }
        return Optional.ofNullable(versionField);
    }

    /****
     * ????????????class????????????
     * @param cls
     * @return
     */
    protected static String getTableName(Class<?> cls) {
        Assert.notNull(cls, "class?????????null");

        Table table = cls.getAnnotation(Table.class);
        if (null == table) {
            throw new DbException(cls.getName() + ":??????Table??????");
        }

        DbSchema schema = cls.getAnnotation(DbSchema.class);

        //????????????
        String tableName = table.value();

        if (schema != null && !StringUtil.isBlank(schema.value())) {
            String schemaString = schema.value();
            tableName = schemaString + "." + tableName;
        }


        if (StringUtil.isBlank(tableName)) {
            return cls.getSimpleName();
        } else {
            return tableName;
        }
    }

    /**
     * ??????model????????????field
     *
     * @param cls model?????????class
     * @return model????????????????????????field
     */
    protected static Field getPrimaryKeyField(Class<?> cls) {
        AssertUtil.notNull(cls, "class?????????null");
        Field[] fields = cls.getDeclaredFields();
        Field primaryKeyField = null;
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                primaryKeyField = field;
                break;
            }
        }
        //throw new DbException("???????????????@Id??????");
        return primaryKeyField;
    }

    /**
     * ??????model?????????????????????
     *
     * @param cls
     * @return
     */
    protected static String getPrimaryKeyColName(Class<?> cls) {
        AssertUtil.notNull(cls, "class?????????null");
        Field[] fields = cls.getDeclaredFields();
        Field primaryKeyField = null;
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                primaryKeyField = field;
                break;
            }
        }
        if (null == primaryKeyField) {
            //throw new DbException("???????????????@Id??????");
            return null;
        }

        String pkName = primaryKeyField.getName();
        Column column = primaryKeyField.getAnnotation(Column.class);
        if (null != column && !StringUtil.isBlank(column.value())) {
            pkName = column.value();
        }

        return pkName;
    }

    /**
     * ??????model???@GeneratedValue??????
     *
     * @param cls
     * @return
     */
    protected static Optional<GeneratedValue> getAnnotationGeneratedValue(Class<?> cls) {
        AssertUtil.notNull(cls, "class?????????null");
        Field[] fields = cls.getDeclaredFields();
        GeneratedValue generatedValue = null;
        for (Field field : fields) {
            generatedValue = field.getAnnotation(GeneratedValue.class);
            if (generatedValue != null) {
                break;
            }
        }
        return Optional.ofNullable(generatedValue);
    }

    /**
     * ??????model???@SequenceGenerator??????
     *
     * @param cls
     * @return
     */
    protected static Optional<SequenceGenerator> getAnnotationSequenceGenerator(Class<?> cls) {

        AssertUtil.notNull(cls, "class?????????null");
        Field[] fields = cls.getDeclaredFields();
        SequenceGenerator generatedValue = null;
        for (Field field : fields) {
            generatedValue = field.getAnnotation(SequenceGenerator.class);
            if (generatedValue != null) {
                break;
            }
        }
        return Optional.ofNullable(generatedValue);
    }

    /**
     * ??????model?????????????????????
     *
     * @param cls
     * @return
     */
    protected static String getPrimaryKeyFieldName(Class<?> cls) {
        AssertUtil.notNull(cls, "class?????????null");
        Field[] fields = cls.getDeclaredFields();
        Field primaryKeyField = null;
        for (Field field : fields) {
            Id id = field.getAnnotation(Id.class);
            if (id != null) {
                primaryKeyField = field;
                break;
            }
        }
        if (null == primaryKeyField) {
            return null;
            //throw new DbException("???????????????@Id??????");
        }
        return primaryKeyField.getName();
    }

    /**
     * ?????????????????????db??????
     *
     * @param cls
     * @param name
     * @return
     */
    protected static String getDbColumnByClassColumn(Class<?> cls, String name) {
        AssertUtil.notNull(cls, "class?????????null");
        AssertUtil.hasText(name, "name????????????");
        Field[] fields = cls.getDeclaredFields();
        Optional<Field> fieldOptional = Lists.newArrayList(fields).stream().filter(field -> field.getName().equals(name)).findFirst();
        if (!fieldOptional.isPresent()) {
            throw new RuntimeException(String.format("%s??????%s???????????????", cls.getSimpleName(), name));
        }
        Column column = fieldOptional.get().getAnnotation(Column.class);
        String columnName = null;
        if (null == column) {
            columnName = name;
        } else {
            columnName = StringUtil.isBlank(column.value()) ? name : column.value();
        }
        return columnName;
    }

    /**
     * ??????db?????????????????????
     *
     * @param cls
     * @param name
     * @return
     */
    protected static String getClassColumnByDbColumn(Class<?> cls, String name) {
        AssertUtil.notNull(cls, "class?????????null");
        AssertUtil.hasText(name, "name????????????");
        Field[] fields = cls.getDeclaredFields();
        Optional<Field> fieldOptional = Lists.newArrayList(fields).stream().filter(field -> {
            Column column = field.getAnnotation(Column.class);
            String columnName = StringUtil.isBlank(column.value()) ? field.getName() : column.value();
            return columnName.equals(name);
        }).findFirst();
        if (!fieldOptional.isPresent()) {
            throw new RuntimeException(String.format("%s??????%s??????????????????", cls.getSimpleName(), name));
        }
        return fieldOptional.get().getName();
    }

    /**
     * ???????????????????????????
     *
     * @param t
     * @param ignoreNull
     * @param <T>
     * @return
     */
    protected static <T> List<ImmutablePair<String, Object>> getTableColumnNameAndValue(T t, boolean ignoreNull) {
        AssertUtil.notNull(t, "t?????????null");
        Field[] fields = t.getClass().getDeclaredFields();
        Stream<ImmutablePair<String, Object>> stream = Lists.newArrayList(fields).stream()
                .filter(field -> (null == field.getAnnotation(Transient.class)) && (!Collection.class.isAssignableFrom(field.getType())))
                .map(field1 -> {
                    Column column = field1.getAnnotation(Column.class);
                    String fieldName = field1.getName();
                    String columnName = null;
                    if (null == column) {
                        columnName = fieldName;
                    } else {
                        columnName = StringUtil.isBlank(column.value()) ? fieldName : column.value();
                    }
                    Object value = null;
                    try {
                        value = ReflectUtil.getMethod(t.getClass(), "get" + StringUtil.capitalizeFirstLetter(fieldName)).invoke(t);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new DbException(e.getMessage(), e);
                    }
                    return new ImmutablePair<>(columnName, value);
                });
        List<ImmutablePair<String, Object>> pairList = null;
        if (ignoreNull) {
            String id = null;
            try {
                id = getPrimaryKeyColName(t.getClass());
            } catch (Exception e) {

            }
            if (!StringUtil.isBlank(id)) {
                String id1 = id;
                pairList = stream.filter(pair -> (pair.getLeft().equals(id1) || pair.right != null)).collect(Collectors.toList());
            } else {
                pairList = stream.filter(pair -> pair.right != null).collect(Collectors.toList());
            }
        } else {
            pairList = stream.collect(Collectors.toList());
        }
        return pairList;
    }

    protected static <T> List<ImmutablePair<String, Object>> getTableColumnNameAndCollectionValue(T t) {
        AssertUtil.notNull(t, "t?????????null");
        Field[] fields = t.getClass().getDeclaredFields();
        Stream<ImmutablePair<String, Object>> stream = Lists.newArrayList(fields).stream()
                .filter(field -> (null == field.getAnnotation(Transient.class)) && (Collection.class.isAssignableFrom(field.getType())))
                .map(field1 -> {
                    Column column = field1.getAnnotation(Column.class);
                    String fieldName = field1.getName();
                    String columnName = null;
                    if (null == column || StringUtil.isBlank(column.value())) {
                        throw new DbException("collection??????????????????@Column?????????value???????????????????????????????????????");
                    } else {
                        columnName = column.value();
                    }
                    Object value = null;
                    try {
                        value = ReflectUtil.getMethod(t.getClass(), "get" + StringUtil.capitalizeFirstLetter(fieldName)).invoke(t);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new DbException(e.getMessage(), e);
                    }
                    return new ImmutablePair<>(columnName, value);
                });
        List<ImmutablePair<String, Object>> pairList = null;
        pairList = stream.filter(pair -> pair.right != null).collect(Collectors.toList());
        return pairList;
    }

    protected static Boolean existCollectionAttribute(Class<?> cls) {
        AssertUtil.notNull(cls, "cls?????????null");
        Field[] fields = cls.getDeclaredFields();
        Optional<Field> fieldOptional = Lists.newArrayList(fields).stream().filter(field -> (null == field.getAnnotation(Transient.class)) && (Collection.class.isAssignableFrom(field.getType()))).findAny();
        return fieldOptional.isPresent();
    }
}
