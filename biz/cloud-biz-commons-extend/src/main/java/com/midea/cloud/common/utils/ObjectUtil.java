package com.midea.cloud.common.utils;

import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.srm.model.annonations.JudgeEquals;
import com.midea.cloud.srm.model.bargaining.purchaser.projectmanagement.bidinitiating.entity.BidRequirementLine;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ReflectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.groups.Default;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.SoftReference;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static org.apache.ibatis.ognl.OgnlRuntime.getField;

/**
 * <pre>
 *  对象操作工具类
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-6-13 14:27
 *  修改内容:
 * </pre>
 */
@Slf4j
public class ObjectUtil {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    private static final ConcurrentHashMap<String, Collection<Field>> CACHE_FIELD_MAP = new ConcurrentHashMap<>();
    /**
     * 将string字符串的首字母大写
     *
     * @param str
     * @return
     */
    private static final String nameFrefix = "set";

    /**
     * Description 对象反序列化
     *
     * @return
     * @throws BaseException
     * @Param
     * @Author huanghb14@meicloud.com
     * @Date 2020.06.13
     **/
    public static Object readObj(byte[] bytes) {
        try {
            ObjectInputStream inputStream = new ObjectInputStream(new ByteArrayInputStream(bytes));
            return inputStream.readObject();
        } catch (Exception e) {
            throw new BaseException(ResultCode.OPERATION_FAILED, "对象反序列化失败", e);
        }
    }

    /**
     * Description 对象序列化
     *
     * @return
     * @throws BaseException
     * @Param
     * @Author wuwl18@meicloud.com
     * @Date 2020.06.13
     **/
    public static byte[] writeObj(Object obj) {
        try {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
            outputStream.writeObject(obj);
            return byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            throw new BaseException(ResultCode.OPERATION_FAILED, "对象序列化失败", e);
        }
    }

    /**
     * Description 对象深层复制
     *
     * @return
     * @throws BaseException
     * @Param
     * @Author huanghb14@meicloud.com
     * @Date 2020.06.13
     **/
    public static Object deepCopy(Object obj) {
        try {
            return readObj(writeObj(obj));
        } catch (Exception e) {
            throw new BaseException(ResultCode.OPERATION_FAILED, "对象深层复制失败", e);
        }
    }

    public static Object setStrFieldNullIfEmpty(Object o) {
        Class<?> targetClass = o.getClass();
        for (Field declaredField : targetClass.getDeclaredFields()) {
            declaredField.setAccessible(true);
            try {
                Object s = declaredField.get(o);
                if (s instanceof String) {
                    String s1 = (String) s;
                    if (Strings.isEmpty(s1) || Strings.isBlank(s1)) {
                        declaredField.set(o, null);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

    /**
     * 验证方法
     *
     * @param t
     * @param <T>
     * @author tanjl11
     */
    public static <T> void validate(String prefixInfo, T t) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t);
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            throw new BaseException(prefixInfo + constraintViolation.getMessage());
        }
    }

    public static <T> void validate(T t) {
        validate(t, null);
    }

    /**
     * @param t
     * @param splitSymbol
     * @param <T>         返回值
     * @see YesOrNo
     */
    public static <T> String validateForImport(T t, String splitSymbol) {
        return validateForImport(t, splitSymbol, null);
    }


    public static <T> void validateAllFieldMessage(T t) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = validator.validate(t, new Class[0]);
        Iterator var3 = constraintViolations.iterator();
        StringBuilder sb = new StringBuilder();
        while (var3.hasNext()) {
            ConstraintViolation<T> constraintViolation = (ConstraintViolation) var3.next();
            sb.append(constraintViolation.getMessage());
        }
        if (sb.length() > 0) {
            throw new BaseException(sb.toString());
        }
    }

    /**
     * @param t
     * @param splitSymbol
     * @param validClass  valid的group
     * @return
     */
    public static <T> String validateForImport(T t, String splitSymbol, Class<? extends Default>... validClass) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = Objects.isNull(validClass) ? validator.validate(t) : validator.validate(t, validClass);
        StringBuilder builder = new StringBuilder();
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return YesOrNo.YES.getValue();
        }
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            builder.append(constraintViolation.getMessage())
                    .append(splitSymbol);
        }
        builder = builder.delete(builder.length() - splitSymbol.length(), builder.length());
        return builder.toString();
    }

    /**
     * @param t
     * @param validClass 校验的group
     */
    public static <T> void validate(T t, Class<? extends Default>... validClass) {
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> constraintViolations = Objects.isNull(validClass) ? validator.validate(t) : validator.validate(t, validClass);
        for (ConstraintViolation<T> constraintViolation : constraintViolations) {
            throw new BaseException(constraintViolation.getMessage());
        }
    }

    public static <T> boolean isFieldAllEquals(T obj1, T obj2) {
        if (Objects.equals(obj1.getClass(), obj2.getClass())) {
            Class<?> judgeClass = obj1.getClass();
            String canonicalName = judgeClass.getCanonicalName();
            Collection<Field> fields = null;
            Collection<Field> reference = CACHE_FIELD_MAP.get(canonicalName);
            if (Objects.isNull(reference)) {
                fields = loadCache(canonicalName, judgeClass);
            }
            boolean isEqual = true;
            if (!CollectionUtils.isEmpty(fields)) {
                for (Field field : fields) {
                    try {
                        Object o1 = field.get(obj1);
                        Object o2 = field.get(obj2);
                        if (!Objects.equals(o1, o2)) {
                            if (field.getType().equals(String.class)) {
                                //如果都为空，跳出这次比对
                                if (StringUtil.isEmpty(o1) && StringUtil.isEmpty(o2)) {
                                    continue;
                                }
                            }
                            isEqual = false;
                            break;
                        }
                    } catch (IllegalAccessException e) {
                        throw new BaseException("调用对比接口时报错" + e.getMessage());
                    }
                }
            } else {
                return false;
            }
            return isEqual;
        }
        return false;
    }

    private static Collection<Field> loadCache(String simpleName, Class<?> judgeClass) {
        List<Field> judgeFields = new LinkedList<>();
        synchronized (CACHE_FIELD_MAP) {
            if (Objects.isNull(CACHE_FIELD_MAP.get(simpleName))) {
                for (Field field : judgeClass.getDeclaredFields()) {
                    field.setAccessible(true);
                    if (field.isAnnotationPresent(JudgeEquals.class)) {
                        judgeFields.add(field);
                    }
                }
                CACHE_FIELD_MAP.put(simpleName, judgeFields);
            } else {
                return CACHE_FIELD_MAP.get(simpleName);
            }
        }
        return judgeFields;
    }

    /**
     * 反射转储对象信息
     *
     * @param obj    接受对象
     * @param key    接受对象对应的接受属性的名字
     * @param values 接受的值
     * @return
     */
    public static  <T> T mapConvertEntity(T obj, String key, String values)throws Exception {
        //获取obj对象
        Class<?> clazz = obj.getClass();
        Field field = getField(clazz, key);
        if (field != null) {
            try {
                Class<?> fieldType = field.getType();
                Object value = convertValType(values, fieldType);
                Method method = null;
                //获取set方法名
                String setMethodName = convertStr(key);
                method = clazz.getMethod(setMethodName, field.getType());
                if (field.isAccessible()) {
                    field.setAccessible(true);
                }
                method.invoke(obj, value);
                field.setAccessible(false);
            } catch (Exception e) {
                log.error("操作失败", e);
            }
        }
        return (T) obj;
    }
    /**
     * @param obj
     * @param fieldType
     * @return
     * @description 将object中的值按照类型转换为entity中的值
     */
    private static Object convertValType(Object obj, Class<?> fieldType) throws Exception{
        Object retVal = null;
        if (Long.class.getName().equals(fieldType.getName()) || long.class.getName().equals(fieldType.getName())) {
            if (StringUtils.isNumeric(obj.toString())) {
                retVal = Long.parseLong(obj.toString());
            }
        } else if (Double.class.getName().equals(fieldType.getName()) || double.class.getName().equals(fieldType.getName())) {
            retVal = Double.parseDouble(obj.toString());
        } else if (Integer.class.getName().equals(fieldType.getName()) || int.class.getName().equals(fieldType.getName())) {
            if (StringUtils.isNumeric(obj.toString())) {
                retVal = Integer.parseInt(obj.toString());
            }
        } else if (Float.class.getName().equals(fieldType.getName()) || float.class.getName().equals(fieldType.getName())) {
            retVal = Float.parseFloat(obj.toString());
        } else if (String.class.getName().equals(fieldType.getName())) {
            retVal = obj;
        } else if (Character.class.getName().equals(fieldType.getName()) || char.class.getName().equals(fieldType.getName())) {
            retVal = obj;
        } else if (Date.class.getName().equals(fieldType.getName())) {
            retVal = DateUtil.parseDate(obj.toString());
        } else if (BigDecimal.class.getName().equals(fieldType.getName())) {
            retVal = new BigDecimal(obj.toString());
        }
        return retVal;
    }

    private static String convertStr(String str) {
        String keyStr = nameFrefix.concat(str.substring(0, 1).toUpperCase() + str.substring(1));
        return keyStr;
    }
}
