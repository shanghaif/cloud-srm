package com.midea.cloud.srm.supcooperate.deliverynote.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.CellTypeSheetWriteHandler;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.base.categorydv.dto.DvRequestDTO;
import com.midea.cloud.srm.model.base.categorydv.entity.CategoryDv;
import com.midea.cloud.srm.model.common.ExportExcelParam;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;

import com.midea.cloud.srm.model.suppliercooperate.deliverynote.dto.WmsFileDTO;
import com.midea.cloud.srm.model.suppliercooperate.deliverynote.entry.DeliveryNoteWms;
import com.midea.cloud.srm.supcooperate.deliverynote.mapper.DeliveryNoteWmsMapper;
import com.midea.cloud.srm.supcooperate.deliverynote.service.IDeliveryNoteWmsService;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

/**
 * <pre>
 *  送货单WMS清单 服务实现类
 * </pre>
 *
 * @author chensl26@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-08-17 19:11:49
 *  修改内容:
 * </pre>
 */
@Service
public class DeliveryNoteWmsServiceImpl extends ServiceImpl<DeliveryNoteWmsMapper, DeliveryNoteWms> implements IDeliveryNoteWmsService {

    @Autowired
    private BaseClient baseClient;

    /**
     * 导入excel数据
     *
     * @param file
     * @throws Exception
     */
    @Override
    @Transactional
    public List<DeliveryNoteWms> importExcel(MultipartFile file, Long id) throws Exception {
        String originalFilename = file.getOriginalFilename();
        if (!EasyExcelUtil.isExcel(originalFilename)) {
            throw new RuntimeException("请导入正确的Excel文件");
        }

        //----------------------------------------------------------------------------------
        InputStream inputStream = file.getInputStream();
        ArrayList<DeliveryNoteWms> deliveryNoteWmsList = new ArrayList<>();

        //检查导入数据
        //读入文件，每一行对应一个 Model，获取 Model 列表
        List<Object> objects = EasyExcelUtil.readExcelWithModel(inputStream, WmsFileDTO.class);
        if (!CollectionUtils.isEmpty(objects)) {
            for (int i = 0; i < objects.size(); i++) {
                Object object = objects.get(i);
                if (object == null) continue;
                WmsFileDTO wmsFilelDTO = (WmsFileDTO) object;
                //wmsFilelDTO.setRow(i + 2);
                DeliveryNoteWms deliveryNoteWms = new DeliveryNoteWms();
                //设置行号
                wmsFilelDTO.setRow(i + 2);
                //检查参数
                checkParamBeforeImportExcel(wmsFilelDTO, deliveryNoteWms);
                deliveryNoteWms.setDeliveryNoteWmsId(IdGenrator.generate());
                deliveryNoteWmsList.add(deliveryNoteWms);
            }
            //保存数据
            this.saveBatch(deliveryNoteWmsList);
        } else {
            throw new BaseException(LocaleHandler.getLocaleMsg("导入excel没有数据"));
        }
        if (id != null) {
            List<DeliveryNoteWms> deliveryNoteWms = this.deliveryNoteWmsList(new DeliveryNoteWms().setDeliveryNoteId(id));
            deliveryNoteWmsList.addAll(deliveryNoteWms);
        }
        return deliveryNoteWmsList;
    }


    /**
     * 校验文件，及保存对应的数据
     *
     * @param wmsFileDTO
     * @param deliveryNoteWms
     */
    private void checkParamBeforeImportExcel(WmsFileDTO wmsFileDTO, DeliveryNoteWms deliveryNoteWms) throws Exception {
        ArrayList<String> datas = new ArrayList<>();
        datas.add(wmsFileDTO.getProduceDate());
        datas.add(wmsFileDTO.getEffectiveDate());
        datas.add(wmsFileDTO.getPlanReceiveDate());

        int row = wmsFileDTO.getRow();
        //校验日期列数据格式
        for (String fileDate : datas) {
            if (StringUtils.isNotBlank(fileDate)) {
                try {
                    LocalDate localDate = DateUtil.dateToLocalDate(DateUtil.parseDate(fileDate));
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new BaseException(LocaleHandler.getLocaleMsg("第" + row + "第行日期相关格式错误,请检查!(示例：2020-08-26 16:00:00)"));
                }
            }
        }
        //其他字段校验判空判空(必填非必填)
        //空报错，非空保存
        Field[] declaredFields = wmsFileDTO.getClass().getDeclaredFields();
        for (int i = 0; i < declaredFields.length - 1; i++) {
            String names = declaredFields[i].getName();
            String name = names.substring(0, 1).toUpperCase() + names.substring(1);
            Method m = wmsFileDTO.getClass().getMethod("get" + name);
            String value = (String) m.invoke(wmsFileDTO); // 调用getter方法获取属性值
            if (StringUtils.isBlank(value)) {
                if (i < 9) {
                    ExcelProperty annotation = declaredFields[i].getAnnotation(ExcelProperty.class);
                    String color = "";
                    if (StringUtils.isNotBlank(annotation.value()[0])) {
                        color = annotation.value()[0];
                    }
                    throw new BaseException(LocaleHandler.getLocaleMsg("第" + row + "行" + color + "为空"));
                }
            } else {
                //获取传入对象对应的实体的属性
                //保存对应的值
                deliveryNoteWms = this.mapConvertEntity(deliveryNoteWms, names, value);
            }
        }

    }

    /**
     * 导出Exce模板
     *
     * @return
     * @throws Exception
     */
    @Override
    public Map<String, Object> downloadTemplate() throws Exception {
        //生成随机名字
        String fileName = UUID.randomUUID().toString() + ".xls";
        CellTypeSheetWriteHandler handler2 = new CellTypeSheetWriteHandler(WmsFileDTO.class);
        EasyExcel.write(fileName, WmsFileDTO.class).registerWriteHandler(handler2).sheet("模板").doWrite(new ArrayList<WmsFileDTO>());
        File file = new File(fileName);
        byte[] buffer = FileUtils.readFileToByteArray(file);
        file.delete();
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("buffer", buffer);
        result.put("fileName", "模板.xls");
        return result;
    }

    @Override
    public List<DeliveryNoteWms> deliveryNoteWmsList(DeliveryNoteWms deliveryNoteWms) {
        QueryWrapper<DeliveryNoteWms> wrapper = new QueryWrapper<>();
        wrapper.eq(deliveryNoteWms.getDeliveryNoteId() != null, "DELIVERY_NOTE_ID", deliveryNoteWms.getDeliveryNoteId());
        List<DeliveryNoteWms> list = this.list(wrapper);
        return list;
    }

    @Override
    public void excelEportRequirementLine(Long deliveryNoteId, HttpServletResponse response) throws IOException {
        // 标题
        WmsFileDTO wmsFileDTO = new WmsFileDTO();
        Field[] declaredFields = wmsFileDTO.getClass().getDeclaredFields();
        ArrayList<String> head = new ArrayList<>();
        ArrayList<String> headName = new ArrayList<>();
        for (Field field : declaredFields) {
            head.add(field.getName());
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            if (null != annotation) {
                headName.add(annotation.value()[0]);
            }
        }
        // 获取导出的数据
        List<List<Object>> dataList = this.queryExportData(deliveryNoteId, head);
        // 文件名
        String fileName = "送货单导出";
        // 开始导出
        EasyExcelUtil.exportStart(response, dataList, headName, fileName);
    }

    // 获取导出的数据
    public List<List<Object>> queryExportData(Long deliveryNoteId, List<String> param) {
        //查新需要导出的数据
        QueryWrapper<DeliveryNoteWms> wrapper = new QueryWrapper<>();
        wrapper.select("LINE_NUM,ORDER_NUMBER,MATERIAL_CODE,MATERIAL_NAME,BATCH_NUM,CONSIGNMENT_NUM,BOX_NUM,UNIT,DELIVERY_QUANTITY,PRODUCE_DATE,EFFECTIVE_DATE,PLAN_RECEIVE_DATE,COMMENTS,JUDGE_STANDARD,USING_STATUS");
        wrapper.eq("DELIVERY_NOTE_ID", deliveryNoteId);
        List<DeliveryNoteWms> deliveryNoteWms = this.list(wrapper);

        List<List<Object>> dataList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(deliveryNoteWms)) {
            List<Map<String, Object>> mapList = BeanMapUtils.objectsToMaps(deliveryNoteWms);
            List<String> titleList = param;
            if (org.apache.commons.collections4.CollectionUtils.isNotEmpty(titleList)) {
                for (Map<String, Object> map : mapList) {
                    ArrayList<Object> objects = new ArrayList<>();
                    for (String key : titleList) {
                        objects.add(map.get(key));
                    }
                    dataList.add(objects);
                }
            }
        }
        return dataList;
    }

    public static void main(String[] args) throws Exception {
        DeliveryNoteWms deliveryNoteWms = new DeliveryNoteWms();
        Class<?> clazz = deliveryNoteWms.getClass();
        Field lineNum = getField(clazz, "lineNum");
        Class<?> type = lineNum.getType();
        Object o = convertValType("11", type);
        Method method = null;
        String setMethodName = convertStr("lineNum");
        method = clazz.getMethod(setMethodName, lineNum.getType());
        if (lineNum.isAccessible()) {
            lineNum.setAccessible(true);
        }
        method.invoke(deliveryNoteWms, o);
        lineNum.setAccessible(false);
    }

    /**
     * 反射转储对象信息
     *
     * @param obj    接受对象
     * @param key    接受对象对应的接受属性的名字
     * @param values 接受的值
     * @return
     */
    public <T> T mapConvertEntity(T obj, String key, String values) {
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
                log.error("操作失败",e);
            }
        }
        return (T) obj;
    }

    /**
     * @param clazz
     * @param fieldName
     * @return
     * @description 获取类的属性
     */
    private static Field getField(Class<?> clazz, String fieldName) {

        //判断传入的是Object类型或者是null，直接返回空
        if (clazz == null || Object.class.getName().equals(clazz.getName())) {
            return null;
        }

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }

        Class<?> classUpper = clazz.getSuperclass();
        if (classUpper != null) {
            return getField(classUpper, fieldName);
        }
        return null;
    }

    /**
     * @param obj
     * @param fieldType
     * @return
     * @description 将object中的值按照类型转换为entity中的值
     */
    private static Object convertValType(Object obj, Class<?> fieldType) {

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
            retVal = convertStrToDate(obj.toString());
        }
        return retVal;
    }

    /**
     * @param str
     * @return
     * @description 将String类型数据转换为Date类型
     */
    private static Date convertStrToDate(String str) {

        Date date = null;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            date = simpleDateFormat.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将string字符串的首字母大写
     *
     * @param str
     * @return
     */
    private static String nameFrefix = "set";

    private static String convertStr(String str) {
        String keyStr = nameFrefix.concat(str.substring(0, 1).toUpperCase() + str.substring(1));
        return keyStr;
    }
}
