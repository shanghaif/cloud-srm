package com.midea.cloud.gernator.autocode.service.impl;

import com.midea.cloud.gernator.autocode.entity.CodeVo;
import com.midea.cloud.gernator.autocode.entity.ConfigDTO;
import com.midea.cloud.gernator.autocode.entity.TableDefDTO;
import com.midea.cloud.gernator.autocode.service.GeneratorService;
import com.midea.cloud.gernator.util.AutoCodeUtil;
import com.midea.cloud.gernator.util.FileUtil;
import com.midea.cloud.gernator.util.FreeMarkertUtil;
import com.midea.cloud.gernator.util.StringUtil;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.midea.cloud.gernator.util.FileUtil.compress;

/**
 * <pre>
 *  代码生成器 ServiceImpl
 * </pre>
 *
 * @author yixinyx@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
@Service
public class GeneratorServiceImpl implements GeneratorService {

    private static final Log log = LogFactory.getLog(GeneratorServiceImpl.class);

    ExecutorService newCachedThreadPool = Executors.newCachedThreadPool();

    @Autowired
    FreeMarkerConfigurer freeMarkerConfigurer;

//    @Resource
//    GeneratorDao generatorDao;

    /**
     * 查询所有表
     * @param tableNameDTO
     * @return
     */
//    @Override
//    public PageInfo<TableNameDTO> listTable(TableNameDTO tableNameDTO) {
//        tableNameDTO.setKeyName(tableNameDTO.getKeyName().toUpperCase());
//
//        List<TableNameDTO> tableNameDTOList =null;
//        String driverClassName = PropertiesUtil.getPropertiesDataSourceBy("dyn.jdbc.driverClassName");
//        if (DriverClassName.ORACLE.getValue() .equals(driverClassName) ){
//           tableNameDTOList = generatorDao.listTableForOracle(tableNameDTO);
//        }else if(DriverClassName.MYSQL.getValue() .equals(driverClassName)){
//            tableNameDTO.setDatabaseName(PropertiesUtil.getPropertiesDataSourceBy("dyn.jdbc.url.db.DSCOM").split("#")[1]);
//            tableNameDTOList = generatorDao.listTableForMySQL(tableNameDTO);
//        }
//
//        PageInfo<TableNameDTO> pageInfo = PageInfo.build(tableNameDTOList);
//        return pageInfo;
//    }

    /**
     * 根据名字查看基本配置信息
     *
     * @param
     * @return
     * @throws Exception
     */
//    @Override
//    public ConfigDTO getConfig(String tableName) throws Exception {
//
//        TableNameDTO tableNameDTO = new TableNameDTO();
//        tableNameDTO.setTableName(tableName);
//
//        TableNameDTO tableNameDtoResult=null;
//        String driverClassName = PropertiesUtil.getPropertiesDataSourceBy("dyn.jdbc.driverClassName");
//        if (DriverClassName.ORACLE.getValue() .equals(driverClassName) ){
//            tableNameDtoResult = generatorDao.listTableForOracle(tableNameDTO).get(0);
//        }else if(DriverClassName.MYSQL.getValue() .equals(driverClassName)){
//            tableNameDTO.setDatabaseName(PropertiesUtil.getPropertiesDataSourceBy("dyn.jdbc.url.db.DSCOM").split("#")[1]);
//            tableNameDtoResult = generatorDao.listTableForMySQL(tableNameDTO).get(0);
//        }
//
//        ConfigDTO configDTO = new ConfigDTO();
//        BeanUtils.copyProperties(tableNameDtoResult, configDTO);
//
//        //表名转换为实体名
//        String entity = StringUtil.underlineToCamel(tableNameDtoResult.getTableName().toLowerCase());
//        entity = entity.substring(0, 1).toUpperCase() + entity.substring(1);
//        configDTO.setEntity(entity);
//
//        //DTO
//        configDTO.setDto(entity + "DTO");
//
//        //设置功能作者，从git从获取
//        try {
//            Runtime runtime = Runtime.getRuntime();
//            Process process = runtime.exec("git config user.email");
//            InputStream inputStream = process.getInputStream();
//            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
//            configDTO.setAuthor(bufferedReader.readLine());
//            bufferedReader.close();
//        } catch (Exception e) {
//            //异常后继续运行
//            configDTO.setAuthor("");
//            e.printStackTrace();
//        }
//
//        //设置主键
//        String columnName ="";
//        if (DriverClassName.ORACLE.getValue() .equals(driverClassName) ){
//             columnName = generatorDao.getPkColumnNameByTableNameForOracle(tableName);
//        }else if(DriverClassName.MYSQL.getValue() .equals(driverClassName)){
//            Map map = new HashMap();
//            map.put("databaseName",PropertiesUtil.getPropertiesDataSourceBy("dyn.jdbc.url.db.DSCOM").split("#")[1]);
//            map.put("tableName",tableName);
//             columnName = generatorDao.getPkColumnNameByTableNameForMySQL(map);
//        }
//
//        columnName = StringUtil.underlineToCamel(columnName.toLowerCase());
//        configDTO.setPk(columnName);
//
//        //设置是否替换文件
//        configDTO.setIsReplaceExsitedFile(YesOrNo.NO.getValue());
//
//        return configDTO;
//    }
//
//    @Override
//    public List<TableDefDTO> getTableDefInfoByName(String tableName) {
//        List<TableDefDTO> tableDefDTOList =null;
//
//
//        String driverClassName = PropertiesUtil.getPropertiesDataSourceBy("dyn.jdbc.driverClassName");
//        if (DriverClassName.ORACLE.getValue() .equals(driverClassName) ){
//            tableDefDTOList = generatorDao.getTableDefInfoByNameForOracle(tableName);
//        }else if(DriverClassName.MYSQL.getValue() .equals(driverClassName)){
//            Map map = new HashMap();
//            map.put("databaseName",PropertiesUtil.getPropertiesDataSourceBy("dyn.jdbc.url.db.DSCOM").split("#")[1]);
//            map.put("tableName",tableName);
//           tableDefDTOList = generatorDao.getTableDefInfoByNameForMySQL(map);
//        }
//
//
//
//        for (TableDefDTO tableDefDTO : tableDefDTOList) {
//            //是否主键
//            if ("P".equalsIgnoreCase(tableDefDTO.getIsPk())) {
//                tableDefDTO.setIsPk("true");
//                //主键 isShowOnGrid、isQueryCondition、isShowOnForm、isRequired 默认不选中
//                tableDefDTO.setIsShowOnGrid(YesOrNo.NO.getValue());
//                tableDefDTO.setIsQueryCondition(YesOrNo.NO.getValue());
//                tableDefDTO.setIsShowOnForm(YesOrNo.NO.getValue());
//                tableDefDTO.setIsRequired(YesOrNo.NO.getValue());
//                //不设置匹配方式
//                tableDefDTO.setMatchQueryCondition("");
//            } else {
//                tableDefDTO.setIsPk("false");
//                tableDefDTO.setIsShowOnGrid(YesOrNo.YES.getValue());
//                tableDefDTO.setIsQueryCondition(YesOrNo.YES.getValue());
//                tableDefDTO.setIsShowOnForm(YesOrNo.YES.getValue());
//                tableDefDTO.setIsRequired(//允许为空，则非必填；否则为必填
//                        YesOrNo.YES.getValue().equalsIgnoreCase(tableDefDTO.getIfnull())?
//                                YesOrNo.NO.getValue():YesOrNo.YES.getValue()
//                );
//                //默认匹配方式为=
//                tableDefDTO.setMatchQueryCondition("=");
//            }
//
//            String code = tableDefDTO.getCode();//字段
//
//            //VERSION 和 ATTRIBUTE
//            if ("VERSION".equalsIgnoreCase(code) || code.startsWith("ATTRIBUTE")) {
//                //默认不选中
//                tableDefDTO.setIsShowOnGrid(YesOrNo.NO.getValue());
//                tableDefDTO.setIsQueryCondition(YesOrNo.NO.getValue());
//                tableDefDTO.setIsShowOnForm(YesOrNo.NO.getValue());
//                tableDefDTO.setIsRequired(YesOrNo.NO.getValue());
//                //不设置匹配方式
//                tableDefDTO.setMatchQueryCondition("");
//            }
//
//
//            String type = tableDefDTO.getType();//字段类型
//            String fileType = "";//字段在java中类型
//            String wex5FileType = "";//字段wext5类型
//            if ("VARCHAR".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "String";
//                wex5FileType = "String";
//            } else if ("VARCHAR2".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "String";
//                wex5FileType = "String";
//            } else if ("CHAR".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "String";
//                wex5FileType = "String";
//            } else if ("INTEGER".equalsIgnoreCase(type.toUpperCase()) || "BIGINT".equals(type.toUpperCase())) {
//                fileType = "Long";
//                wex5FileType = "Long";
//            } else if ("INT".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "Long";
//                wex5FileType = "INT";
//            } else if ("DECIMAL".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "Long";
//                wex5FileType = "Long";
//            } else if ("NUMBER".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "Long";
//                wex5FileType = "Long";
//            } else if ("TINYINT".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "Boolean";
//                wex5FileType = "Boolean";
//            } else if ("BLOB".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "Blob";
//                wex5FileType = "Blob";
//                //数据库只需要定义Data类型，不要定义其他类型
//            } else if (("DATE".equals(type.toUpperCase())) || "DATETIME".equalsIgnoreCase(type.toUpperCase()) ) {
//                //创建时间和修改时间需要用Timestamp
//                if (StringUtil.underlineToCamel(code.toLowerCase()).equals("creationDate") || StringUtil.underlineToCamel(code.toLowerCase()).equals("lastUpdateDate")) {
//                    fileType = "Timestamp";//实体类型为Timestamp
//                    wex5FileType = "DateTime";//wex5类型为DateTime
//                } else {
//                    fileType = "Date";//实体类型为Date
//                    wex5FileType = "Date";//wex5类型为Date
//                }
//
//            } else if ("TEXT".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "String";
//                wex5FileType = "String";
//            } else if ("LONGTEXT".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "String";
//                wex5FileType = "String";
//            } else if ("Double".equalsIgnoreCase(type.toUpperCase())) {
//                fileType = "Double";
//                wex5FileType = "Double";
//            }
//            tableDefDTO.setName(tableDefDTO.getName());
//            tableDefDTO.setDbCode(code);
//            tableDefDTO.setJavaCode(StringUtil.underlineToCamel(code.toLowerCase()));
//            tableDefDTO.setFiled_type(fileType);
//            tableDefDTO.setWex5_filed_type(wex5FileType);
//        }
//
//        return tableDefDTOList;
//    }


    @Override
    public String createCode(CodeVo codeVo,String uuid,String path) throws Exception {
        log.info("============生成代码开始===========");
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setModelName(codeVo.getModuleName());

        List<TableDefDTO> tableDefDTOList = codeVo.getSelectHeadFileList();


        //设置各种路基
        //  setAllPath(configDTO.getModelName().trim(), configDTO.getTableName().toLowerCase());
        //设置创建人和修改人
        setCreatedByNameAndLastUpdatedByName(tableDefDTOList);

        //================生成前校验开始================
        //存放路径的容器
        List<String> allPathList = new ArrayList<>();

//        //Dao.xml
//        String daoXmlFilePath = DAO_XML_PATH + configDTO.getEntity() + "Dao.xml";
//        allPathList.add(daoXmlFilePath);
//
//        //service
//        String serviceFilePath = SERVICE_PATH + configDTO.getEntity() + "Service.java";
//        allPathList.add(serviceFilePath);
//
//        //ServiceImpl
//        String serviceImplFilePath = SERVICEIMPL_PATH + configDTO.getEntity() + "ServiceImpl.java";
//        allPathList.add(serviceImplFilePath);

        //entity
        String entityFilePath = ENTITY_PATH + configDTO.getEntity() + ".java";
        allPathList.add(entityFilePath);

//        //dto
//        String dtoFilePath = DTO_PATH + configDTO.getEntity() + "DTO" + ".java";
//        allPathList.add(dtoFilePath);
//
//        //Dao
//        String daoFilePath = DAO_PATH + configDTO.getEntity() + "Dao.java";
//        allPathList.add(daoFilePath);
//
//        //Controller
//        String controllerFilePath = CONTROLLER_PATH + configDTO.getEntity() + "Controller.java";
//        allPathList.add(controllerFilePath);
//
//        //前端文件名
//        String fileUrl = PROJECT_PATH_UI + REQUEST_MAPPING_URL;
//        String fileName = configDTO.getEntity().substring(0, 1).toLowerCase() + configDTO.getEntity().substring(1);
//        String listWFileName = fileName + "_list.w";
//        String listWJsFileName = fileName + "_list.js";
//        String detailWFileName = fileName + "_edit.w";
//        String detailWJsFileName = fileName + "_edit.js";
//        //前端路径
//        String resultFileUrl = fileUrl.replaceAll("/", "\\\\") + "\\";
//        //list.w
//        String listWFilePath = resultFileUrl + listWFileName;
//        allPathList.add(listWFilePath);
//        //list.js
//        String listWJsFilePath = resultFileUrl + listWJsFileName;
//        allPathList.add(listWJsFilePath);
//        //edit.w
//        String detailWFilePath = resultFileUrl + detailWFileName;
//        allPathList.add(detailWFilePath);
//        //edit.js
//        String detailWJsFilePath = resultFileUrl + detailWJsFileName;
//        allPathList.add(detailWJsFilePath);
//
//        List<String> resultList = new ArrayList<>();
////        if(YesOrNo.NO.getValue().equals(configDTO.getIsReplaceExsitedFile())){//如果选择了替换已有文件
////            //校验资源是否存在
////            for (String path : allPathList) {
////                if(new File(path).exists()){//如果资源存在
////                    resultList.add(path.substring(path.lastIndexOf("\\")+1));
////                }
////            }
////        }
//
////        if(resultList != null && resultList.size() != 0){
////            String msg = "";
////            for (String str: resultList) {
////                msg += str + "\r\n";
////            }
////            return BaseResult.build(ErrorCode.BUSINESS_EXCEPTIONS,msg +"<br/>"+ "文件已存在,无需重复生成");//错误代码：业务错误
////        }
//
//        //================生成前校验结束================
//
//        setTableDefCodeByXml(true, tableDefDTOList);
//        //生成Dao.xml
////        createDaoXml(configDTO, tableDefDTOList,configDTO.getEntity() + "Dao.xml");
////
////        setTableDefCodeByXml(false, tableDefDTOList);
////        //生成service
////        createService(configDTO, tableDefDTOList, configDTO.getEntity() + "Service.java");
////        //生成ServiceImpl文件
////        createServiceImpl(configDTO, tableDefDTOList, configDTO.getEntity() + "ServiceImpl.java");
////        //生成entity对象
        createEntity(codeVo,uuid,path);
////        //生成dto对象
////        createDto(configDTO, tableDefDTOList, configDTO.getEntity() + "DTO" + ".java");
////        //生成Dao
////        createDao(configDTO, tableDefDTOList, configDTO.getEntity() + "Dao.java");
////        //生成Controller
////        createController(configDTO, tableDefDTOList, configDTO.getEntity() + "Controller.java");




        return "--";
    }

    @Override
    public void downloadCode(HttpServletResponse response, CodeVo codeVo,String path) throws Exception {
        //随机生成一个uuid文件名的目录
        String uuid = StringUtil.getUuid();

        //生成对应的模板文件和目录
        gernatorCode(codeVo,uuid,path);

        //压缩生成文件，返回下载流，而且删除已经生成的文件目录
        downloadCode(response, uuid,path);
    }
    public void gernatorCode(CodeVo codeVo,String uuid,String path){

        // 设置生成文件目录、实体包名等
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setModelName(codeVo.getModuleName());

        List<TableDefDTO> tableDefDTOList = codeVo.getSelectHeadFileList();
        configDTO.setTableName(codeVo.getTableList().get(0).getName());
        log.info("============生成代码开始===========");

        try {
            //设置各种路基
            setAlldownloadPath(configDTO.getModelName().trim(), configDTO.getTableName().toLowerCase(),uuid,path);
            if(0 == codeVo.getIsMultipleTables()){//单行
                //生成entity
                createEntity(codeVo,uuid,path);
                //生成excelEntityDto
                createExcelEntityDto(codeVo,uuid,path);

                //生成service
                createService(codeVo,uuid,path);

                //生成Dao文件
                createDao(codeVo,uuid,path);
                //生成xml
                createDaoXml(codeVo,uuid,path);
                //生成ServiceImpl
                createServiceImpl(codeVo,uuid,path);

                //生成Controller
                createController(codeVo,uuid,path);

                // 生成generate.js
                createGenerateJs(codeVo,uuid,path);

                // 生成index.js
                createRouterVue(codeVo,uuid,path);

                if("append".equalsIgnoreCase(codeVo.getAddOrEditorMode())){ //追加模式
                    createIndexVueByAppend(codeVo,uuid,path);
                }else {
                    // 生成index.vue
                    createIndexVue(codeVo,uuid,path);
                    // 生成edit.vue
                    createEditVue(codeVo,uuid,path);
                    // 生成list.vue
                    createListVue(codeVo,uuid,path);
                }
            }else {//多行

                //生成entity
                createHeadEntity(codeVo,uuid,path);
                createLineEntity(codeVo,uuid,path);
                //生成linxeExcelEntityDto
                createLineExcelEntityDto(codeVo,uuid,path);

                //生成Controller
                createHeadController(codeVo,uuid,path);
//
                //生成service
                createHeadService(codeVo,uuid,path);

                //生成lineService
                createLineService(codeVo,uuid,path);
//
//                //生成Dao文件
                createHeadDao(codeVo,uuid,path);

                //                //生成Dao文件
                createLineDao(codeVo,uuid,path);
//                //生成xml
                createHeadDaoXml(codeVo,uuid,path);
                createLineDaoXml(codeVo,uuid,path);
//                //生成ServiceImpl
                createHeadServiceImpl(codeVo,uuid,path);
                createLineServiceImpl(codeVo,uuid,path);



//
//
//                // ---------------生成前端-------------


                // 生成generate.js
                createMultiGenerateJs(codeVo,uuid,path);

                // 生成index.js
                createRouterVue(codeVo,uuid,path);

                // 生成index.vue
                createIndexVue(codeVo,uuid,path);


                // 生成edit.vue
                createMultipleEditVue(codeVo,uuid,path);

                // 生成list.vue
                createListVue(codeVo,uuid,path);
                // ------------end ---------------
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        // 生成对应的目录文件
    }

    public void downloadCode(HttpServletResponse response,String uuid,String path){
        List<String> filePath = new ArrayList<>();
        //获取文件的绝对路径，这样才能保持原有路径的压缩
        File upload = new File(path,"download");
        OutputStream os = null;
        InputStream is= null;
        try {
            ResourceUtils.getURL("classpath:").getPath();
            if(!upload.exists()) upload.mkdirs();
            //开始压缩
            FileUtil.folderMethod(path+File.separator+"download"+File.separator+uuid, filePath);
            String zipFilePath = path+File.separator+"download"+File.separator+uuid+File.separator+"down.zip";
            FileUtil.compress(filePath, zipFilePath, true);

            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setContentType("application/zip;charset=GBK");
            response.setHeader("Content-Disposition", "attachment;filename="+ new String((uuid+"down.zip").getBytes("utf-8"), "iso-8859-1"));
            //读取流
            File f = new File(upload.getAbsolutePath()+File.separator+uuid+File.separator+"down.zip");
            is = new FileInputStream(f);
            if (is == null) {
                log.error("下载附件失败，请检查文件是否存在");
            }
            //复制
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                log.error(ExceptionUtils.getFullStackTrace(e));
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                log.error(ExceptionUtils.getFullStackTrace(e));
            }
            //生成完成后删除文件
            FileUtil.deleteDirectory(path+File.separator+"download"+File.separator+uuid);
        }
    }



    /**
     * 得到类的路径
     *
     * @return D:/work/private_cloud/cdc/cdc-code-generator/target/classes/com/midea/scc/code/service
     * @throws Exception
     */
    public String getClassPath() throws Exception {
        String strClassName = getClass().getName();
        String strPackageName = "";
        if (getClass().getPackage() != null) {
            strPackageName = getClass().getPackage().getName();
        }
        String strClassFileName = "";
        if (!"".equals(strPackageName)) {
            strClassFileName = strClassName.substring(strPackageName.length() + 1,
                    strClassName.length());
        } else {
            strClassFileName = strClassName;
        }
        URL url = null;
        url = getClass().getResource(strClassFileName + ".class");
        String strUrl = url.toString();
        strUrl = strUrl.substring(strUrl.indexOf('/') + 1, strUrl
                .lastIndexOf('/'));

        //返回当前类的路径，并且处理路径中的空格，因为在路径中出现的空格如果不处理的话，
        //在访问时就会从空格处断开，那么也就取不到完整的信息了，这个问题在web开发中尤其要注意
        strUrl = strUrl.replaceAll("/", "\\\\").replaceAll("%20", " ");
        strUrl = URLDecoder.decode(strUrl, "utf-8");
        return strUrl;
    }


    //工程路径 D:\work\private_cloud\cdc\
    private String PROJECT_PATH = "";
    //工程路径UI D:\work\private_cloud\cdcui\
    private String PROJECT_PATH_UI = "";
    //核心路径，平台标识\工程模块缩写\功能模块简称  scc\sup\bda
    private String CORE_PACKAGE_PATH = "";
    //核心包名  scc.sup.bda
    private String CORE_PACKAGE_NAME = "";
    //service服务接口的包命 com.midea.scc.sup.bda.service
    private String SERVICE_PACKAGE_PATH = "";
    //controller层requestMapping请求地址
    private String REQUEST_MAPPING_URL = "";

    //entity包名
    private String ENTITY_PACKAGE = "";

    //实体名称
    private String ENTITY_NAME = "";
    //entity类生成路径
    private String ENTITY_PATH = "";
    //dto类生成路径
    private String DTO_PATH = "";

    //dto类生成路径
    private String lINE_DTO_PATH = "";
    //服务接口路径
    private String SERVICE_PATH = "";

    //服务接口路径
    private String HEAD_SERVICE_PATH = "";

    //服务接口路径
    private String LINE_SERVICE_PATH = "";
    //服务实现路径
    private String SERVICEIMPL_PATH = "";
    //业务中转路径
    private String CONTROLLER_PATH = "";

    //业务中转路径
    private String HEAD_CONTROLLER_PATH = "";
    //DAO路径
    private String DAO_PATH = "";

    //DAO路径
    private String HEAD_DAO_PATH = "";
    //DAO路径
    private String LINE_DAO_XML_PATH = "";
    //DAO路径
    private String LINE_DAO_PATH = "";
    //DAO_XML 路径
    private String DAO_XML_PATH = "";
    //模板路径
    private String TEMPLETE_PATH = "";
    //JAVA后台模板路径
    private String TEMPLETE_JAVA_PATH = "";
    //w模板路径
    private String TEMPLETE_W_PATH = "";


    //vue
    private String INDEX_JS_PATH = "";
    private String GENERATE_JS_PATH = "";
    private String LIST_VUE_PATH = "";
    private String EDIT_VUE_PATH = "";
    private String ROUTER_VUE_PATH = "";

    //前端
    private String VUE_lIST_PATH = "";


    /**
     * 设置各种路基
     *
     * @param modelName
     * @param tableName
     * @throws Exception
     */
    public void setAlldownloadPath(String modelName, String tableName ,String uuid,String path) throws Exception {
        //设置工程路径

        PROJECT_PATH =path;

        System.out.println(PROJECT_PATH+"测试");

        //:/apps/svr/cloud_srm_application/code-gernator/code-gernator-extend-boot.jar!/BOOT-INF/classes!/测试


        //设置工程路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        String  vueDownloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"vue";

        //设置工程UI路径
//         PROJECT_PATH_UI = getClassPath().substring(0, getClassPath().lastIndexOf("\\cdc\\cdc-tech\\cdc-tech-code-generator\\target\\classes\\com\\midea\\scc\\code\\service")) + "\\cdcui";

        String tableArray[] = tableName.split("_");
        String className = "";//
        String functionName = "";//功能名称比如 scc_lgt_region 功能为region 如果是这样 scc_lgt_score_rule_config  则功能名称为 score_rule_config
        for(int i=2;i<tableArray.length ;i++){
            functionName = functionName + tableArray[i] + "_";
            if(i == tableArray.length ){
                functionName = functionName + tableArray[i];
            }
        }
        className = StringUtil.underlineToCamel(functionName);//类名或者java类名
        String  packageFileName = className.toLowerCase();//全部转换为小写，作为，包文件名称
        String  entity = "com.midea.cloud.srm.model."+modelName+"."+packageFileName+".entity"; //实体包名

        String  servicePackagePath = "com.midea.cloud.srm."+modelName+"."+packageFileName+".service"; //服务包名
        String  serviceImplPackagePath = "com.midea.cloud.srm."+modelName+"."+packageFileName+".impl"; //服务实现包名

        String  controlPackagePath = "com.midea.cloud.srm."+modelName+"."+packageFileName+".controller"; //请求资源包名
        String  mapperPackagePath = "com.midea.cloud.srm."+modelName+"."+packageFileName+".mapper"; //dao层包名






        //核心路径，平台标识\\工程模块缩写\\功能模块简称
        CORE_PACKAGE_NAME = CORE_PACKAGE_PATH.replaceAll("\\\\", ".");

        //service服务接口的包命 com.midea.scc.sup.bda.service
        SERVICE_PACKAGE_PATH = "com.midea." + CORE_PACKAGE_NAME + ".service";

        //根据表名设置 requestMapping的url
//        String requestMappingPrefix = "/" + tableArray[0] + "/" + tableArray[1] + "/" + tableArray[2] + "/" + tableArray[3];
//        String requestMappingSuffix = "";
//        for (int i = 4; i < tableArray.length; i++) {
//            requestMappingSuffix = requestMappingSuffix + tableArray[i].substring(0, 1).toUpperCase().concat(tableArray[i].substring(1).toLowerCase());
//        }

        //controller层requestMapping请求地址
//        REQUEST_MAPPING_URL = requestMappingPrefix + requestMappingSuffix;


        ENTITY_NAME = StringUtil.formatFirstUpper(tableArray[2]) ;

        SERVICEIMPL_PATH = downloadProjectPath + "\\src\\main\\java\\com\\midea\\cloud\\srm\\"+modelName+"\\"+packageFileName+ "\\service\\"+ "\\impl\\";
        //dto类生成路径
        DTO_PATH = downloadProjectPath + "\\src\\main\\java\\com\\midea\\cloud\\srm\\model\\"+modelName+"\\"+packageFileName+ "\\dto\\";
        //服务接口路径

        //服务实现路径

        //业务中转路径
        CONTROLLER_PATH = downloadProjectPath + "\\src\\main\\java\\com\\midea\\cloud\\srm\\"+modelName+"\\"+packageFileName+"\\controller\\";
        //DAO路径
        DAO_PATH = downloadProjectPath + "\\src\\main\\java\\com\\midea\\cloud\\srm\\"+modelName+"\\"+packageFileName+ "\\mapper\\";
        //DAO_XML 路径
        DAO_XML_PATH = downloadProjectPath + "\\src\\main\\java\\com\\midea\\cloud\\srm\\"+modelName+"\\"+packageFileName+ "\\mapper\\";
        //模板路径
        TEMPLETE_PATH = PROJECT_PATH + "cloud-nsrm-extend\\tech\\code-gernator-extend\\src\\main\\resources";
        //JAVA后台模板路径
        TEMPLETE_JAVA_PATH = PROJECT_PATH + "\\template\\java\\";
        //w模板路径
        TEMPLETE_W_PATH = PROJECT_PATH + "\\template\\vue\\";

        VUE_lIST_PATH = vueDownloadProjectPath;
        String VUE_EDIT_PATH = vueDownloadProjectPath;
        String VUE_GENERATE_PATH = vueDownloadProjectPath;
        String VUE_ROUTER_PATH = vueDownloadProjectPath;
        String VUE_INDEX_PATH = vueDownloadProjectPath;

    }
    public void setAllPath(String modelName, String tableName ,String uuid) throws Exception {

        //设置工程路径
        PROJECT_PATH = getClassPath().substring(0, getClassPath().lastIndexOf("cloud-nsrm-extend\\tech\\code-gernator-extend\\target\\classes\\com\\midea\\cloud\\gernator\\autocode\\service\\impl"));

        //设置工程UI路径
        //  PROJECT_PATH_UI = getClassPath().substring(0, getClassPath().lastIndexOf("\\cdc\\cdc-tech\\cdc-tech-code-generator\\target\\classes\\com\\midea\\scc\\code\\service")) + "\\cdcui";

        String tableArray[] = tableName.split("_");
        String className = "";//
        String functionName = "";//功能名称比如 scc_lgt_region 功能为region 如果是这样 scc_lgt_score_rule_config  则功能名称为 score_rule_config
        for(int i=2;i<tableArray.length ;i++){
            functionName = functionName + tableArray[i] + "_";
            if(i == tableArray.length ){
                functionName = functionName + tableArray[i];
            }
        }
        className = StringUtil.underlineToCamel(functionName);//类名或者java类名
        String  packageFileName = className.toLowerCase();//全部转换为小写，作为，包文件名称
        String  entity = "com.midea.cloud.srm.model."+modelName+"."+packageFileName+".entity"; //实体包名

        String  servicePackagePath = "com.midea.cloud.srm."+modelName+"."+packageFileName+".service"; //服务包名
        String  serviceImplPackagePath = "com.midea.cloud.srm."+modelName+"."+packageFileName+".impl"; //服务实现包名

        String  controlPackagePath = "com.midea.cloud.srm."+modelName+"."+packageFileName+".controller"; //请求资源包名
        String  mapperPackagePath = "com.midea.cloud.srm."+modelName+"."+packageFileName+".mapper"; //dao层包名






        //核心路径，平台标识\\工程模块缩写\\功能模块简称
        CORE_PACKAGE_NAME = CORE_PACKAGE_PATH.replaceAll("\\\\", ".");

        //service服务接口的包命 com.midea.scc.sup.bda.service
        SERVICE_PACKAGE_PATH = "com.midea." + CORE_PACKAGE_NAME + ".service";

        //根据表名设置 requestMapping的url
//        String requestMappingPrefix = "/" + tableArray[0] + "/" + tableArray[1] + "/" + tableArray[2] + "/" + tableArray[3];
//        String requestMappingSuffix = "";
//        for (int i = 4; i < tableArray.length; i++) {
//            requestMappingSuffix = requestMappingSuffix + tableArray[i].substring(0, 1).toUpperCase().concat(tableArray[i].substring(1).toLowerCase());
//        }

        //controller层requestMapping请求地址
//        REQUEST_MAPPING_URL = requestMappingPrefix + requestMappingSuffix;

        //entity类生成路径
        //D:\workspace20201202\nj_srm\cloud-nsrm-extend\biz\cloud-biz-api-extend\src\main\java\com\midea\cloud\srm\model\base\dept\entity\Dept.java
        ENTITY_PATH = PROJECT_PATH + "\\cloud-nsrm-extend\\biz\\cloud-biz-api-extend\\src\\main\\java\\com\\midea\\cloud\\srm\\model\\"+modelName+"\\"+tableArray[2]+"\\entity\\";

        ENTITY_NAME = StringUtil.formatFirstUpper(tableArray[2]) ;
        //dto类生成路径
        DTO_PATH = PROJECT_PATH + "\\cdc-biz\\cdc-biz-interface\\src\\main\\java\\com\\midea\\" + CORE_PACKAGE_PATH + "\\dto\\";
        //服务接口路径
        SERVICE_PATH = PROJECT_PATH + "\\cdc-biz\\cdc-biz-interface\\src\\main\\java\\com\\midea\\" + CORE_PACKAGE_PATH + "\\service\\";
        //服务实现路径
        SERVICEIMPL_PATH = PROJECT_PATH + "\\cdc-biz\\" + modelName + "\\src\\main\\java\\com\\midea\\" + CORE_PACKAGE_PATH + "\\service\\";
        //业务中转路径
        CONTROLLER_PATH = PROJECT_PATH + "\\cdc-biz\\cdc-app\\src\\maincode\\com\\midea\\" + CORE_PACKAGE_PATH + "\\controller\\";
        //DAO路径
        DAO_PATH = PROJECT_PATH + "\\cdc-biz\\" + modelName + "\\src\\main\\java\\com\\midea\\" + CORE_PACKAGE_PATH + "\\dao\\";
        //DAO_XML 路径
        DAO_XML_PATH = PROJECT_PATH + "\\cdc-biz\\" + modelName + "\\src\\main\\java\\com\\midea\\" + CORE_PACKAGE_PATH + "\\dao\\";
        //模板路径
        TEMPLETE_PATH = PROJECT_PATH + "cloud-nsrm-extend\\tech\\code-gernator-extend\\src\\main\\resources";
        //JAVA后台模板路径
        TEMPLETE_JAVA_PATH = PROJECT_PATH + "\\template\\java\\";
        //w模板路径
        TEMPLETE_W_PATH = TEMPLETE_PATH + "\\w";




    }

    //设置创建人和修改人
    public void setCreatedByNameAndLastUpdatedByName(List<TableDefDTO> tableDefDTOList) throws Exception {

//        TableDefDTO createdByTableDefDTO = null;
//        TableDefDTO lastUpdatedByTableDefDTO = null;
//
//        for (TableDefDTO tableDefDTO : tableDefDTOList) {
//            String code = tableDefDTO.getCode();//字段
//            //创建人
//            if ("CREATED_BY".equalsIgnoreCase(code)) {
//                createdByTableDefDTO = tableDefDTO;
//            }
//            //修改人
//            if ("LAST_UPDATED_BY".equalsIgnoreCase(code)) {
//                lastUpdatedByTableDefDTO = tableDefDTO;
//            }
//        }
//
//        //设置创建人
//        TableDefDTO createdByNameTableDef = new TableDefDTO();
//        BeanUtils.copyProperties(createdByTableDefDTO, createdByNameTableDef);
//        createdByNameTableDef.setCode("CREATED_BY_NAME");
//        createdByNameTableDef.setJavaCode("createdByName");
//        createdByNameTableDef.setDbCode("CREATED_BY_NAME");
//        createdByNameTableDef.setName("创建人");
//        createdByNameTableDef.setType("VARCHAR");
//        createdByNameTableDef.setFiled_type("String");
//        createdByNameTableDef.setWex5_filed_type("String");
//
//        tableDefDTOList.add(createdByNameTableDef);
//
//        //设置修改人
//        TableDefDTO lastUpdatedByNameTableDef = new TableDefDTO();
//        BeanUtils.copyProperties(lastUpdatedByTableDefDTO, lastUpdatedByNameTableDef);
//        lastUpdatedByNameTableDef.setCode("LAST_UPDATED_BY_NAME");
//        lastUpdatedByNameTableDef.setJavaCode("lastUpdatedByName");
//        lastUpdatedByNameTableDef.setDbCode("LAST_UPDATED_BY_NAME");
//        lastUpdatedByNameTableDef.setName("最后更新人");
//        lastUpdatedByNameTableDef.setType("VARCHAR");
//        lastUpdatedByNameTableDef.setFiled_type("String");
//        lastUpdatedByNameTableDef.setWex5_filed_type("String");
//        tableDefDTOList.add(lastUpdatedByNameTableDef);

    }

    public void setTableDefCodeByXml(boolean isXml, List<TableDefDTO> tables) throws Exception {

        for (TableDefDTO table : tables) {
            if (isXml) {
                table.setCode(table.getCode().toLowerCase());
            } else {
                table.setCode(StringUtil.underlineToCamel(table.getCode().toLowerCase()));
            }
        }
    }

    /**
     * 生成Service文件
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createService(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+"Service.java";//生产java名称
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        SERVICE_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"service"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("targetName",targetName);
        root.put("currentTimeMillis",currentTimeMillis);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        freemk.processToTemplate(freeMarkerConfigurer,root, "service.java.ftl", SERVICE_PATH,  fileName);

    }

    /**
     * 生成Service文件
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createHeadService(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        String lineDatabaseTable = codeVo.getTableList().get(1).getName();//全表名称
        String lineShortTableName = AutoCodeUtil.getTableName(lineDatabaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+"Service.java";//生产java名称
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        //entity类生成路径

        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("targetName",targetName);
        root.put("currentTimeMillis",currentTimeMillis);
        FreeMarkertUtil freemk = new FreeMarkertUtil();


        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
        root.put("lineClassFileName",lineClassFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("selectLineFileList",selectLineFileList);

        String templatePath = "java"+File.separator+"multiple"+File.separator+"head";

        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        HEAD_SERVICE_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"service"+File.separator;
        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "service.java.ftl", HEAD_SERVICE_PATH,  fileName);

    }
    /**
     * 生成createLineService文件
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createLineService(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust

        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        //entity类生成路径

        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("targetName",targetName);
        root.put("currentTimeMillis",currentTimeMillis);
        FreeMarkertUtil freemk = new FreeMarkertUtil();

        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名
        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
        root.put("lineClassFileName",lineClassFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("selectLineFileList",selectLineFileList);
        String fileName = lineClassFileName+"Service.java";//生产java名称
        String templatePath = "java"+File.separator+"multiple"+File.separator+"line";

        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        LINE_SERVICE_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"service"+File.separator;
        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "service.java.ftl", LINE_SERVICE_PATH,  fileName);

    }

    /**
     * 生成ServiceImpl文件
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createServiceImpl(CodeVo codeVo,String uuid,String path) throws Exception {

        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+"ServiceImpl.java";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        SERVICEIMPL_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"service"+File.separator+"impl"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        String pk = AutoCodeUtil.getPk(selectHeadFileList);
        root.put("pk", pk);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        freemk.processToTemplate(freeMarkerConfigurer,root, "serviceImpl.java.ftl", SERVICEIMPL_PATH,  fileName);
    }
    /**
     * 生成ServiceImpl文件
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createHeadServiceImpl(CodeVo codeVo,String uuid,String path) throws Exception {

        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust

        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        String pk = AutoCodeUtil.getPk(selectHeadFileList);
        root.put("pk", pk);
        String linePk = AutoCodeUtil.getPk(codeVo.getSelectLineFileList());
        root.put("linePk", linePk);
        FreeMarkertUtil freemk = new FreeMarkertUtil();

        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名
        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
        root.put("lineClassFileName",lineClassFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("selectLineFileList",selectLineFileList);

        String templatePath = "java"+File.separator+"multiple"+File.separator+"head";

        String fileName = classFileName+"ServiceImpl.java";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        SERVICEIMPL_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"service"+File.separator+"impl"+File.separator;
        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "serviceImpl.java.ftl", SERVICEIMPL_PATH,  fileName);
    }

    /**
     * 生成ServiceImpl文件
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createLineServiceImpl(CodeVo codeVo,String uuid,String path) throws Exception {

        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust

        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        String pk = AutoCodeUtil.getPk(selectHeadFileList);
        root.put("pk", pk);
        FreeMarkertUtil freemk = new FreeMarkertUtil();

        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名
        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
        String linePk = AutoCodeUtil.getPk(codeVo.getSelectLineFileList());
        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
        root.put("lineClassFileName",lineClassFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("selectLineFileList",selectLineFileList);
        root.put("linePk", linePk);
        String templatePath = "java"+File.separator+"multiple"+File.separator+"line";

        String fileName = lineClassFileName+"ServiceImpl.java";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        SERVICEIMPL_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"service"+File.separator+"impl"+File.separator;
        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "serviceImpl.java.ftl", SERVICEIMPL_PATH,  fileName);
    }
    /**
     * 生成entity对象
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createEntity(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        int currentTimeMillis =  (int)((Math.random()*9+1)*100000);//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+".java";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        ENTITY_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+File.separator+"model"+
                File.separator+modelName+File.separator+packageName+File.separator+"entity"+File.separator;

        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",String.valueOf(currentTimeMillis));
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        freemk.processToTemplate(freeMarkerConfigurer,root, "entity.java.ftl", ENTITY_PATH,  fileName);
        return ;
    }

    /**
     * 生成entity对象
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createHeadEntity(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名

        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        int currentTimeMillis =  (int)((Math.random()*9+1)*100000);//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名//行表表名
        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+".java";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        ENTITY_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+File.separator+"model"+
                File.separator+modelName+File.separator+packageName+File.separator+"entity"+File.separator;

        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("lineClassFileName",lineClassFileName);
        root.put("classFileName",classFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("currentTimeMillis",String.valueOf(currentTimeMillis));
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "java"+File.separator+"multiple"+File.separator+"head";
        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "entity.java.ftl", ENTITY_PATH,  fileName);
        return ;
    }
    /**
     * 生成entity对象
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createLineEntity(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名

        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        int currentTimeMillis =  (int)((Math.random()*9+1)*100000);//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust

        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        ENTITY_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+File.separator+"model"+
                File.separator+modelName+File.separator+packageName+File.separator+"entity"+File.separator;

        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("lineClassFileName",lineClassFileName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",String.valueOf(currentTimeMillis));

        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
        String lineDatabaseTable = codeVo.getTableList().get(1).getName();//全表名称
        root.put("lineClassFileName",lineClassFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("selectLineFileList",selectLineFileList);
        root.put("lineDatabaseTable",lineDatabaseTable);

        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String fileName = lineClassFileName+".java";//生产java名称
        String templatePath = "java"+File.separator+"multiple"+File.separator+"line";
        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "entity.java.ftl", ENTITY_PATH,  fileName);
        return ;
    }
    /**
     * 生成createExcelEntityDto对象
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createExcelEntityDto(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        int currentTimeMillis =  (int)((Math.random()*9+1)*100000);//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = "Excel"+classFileName+"Dto"+".java";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        ENTITY_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+File.separator+"model"+
                File.separator+modelName+File.separator+packageName+File.separator+"dto"+File.separator;

        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",String.valueOf(currentTimeMillis));
//        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名
//        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
//        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
//        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
//        root.put("lineClassFileName",lineClassFileName);
//        root.put("lineTargetName",lineTargetName);
//        root.put("selectLineFileList",selectLineFileList);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        freemk.processToTemplate(freeMarkerConfigurer,root, "excel_entity_dto.java.ftl", ENTITY_PATH,  fileName);
        return ;
    }

    /**
     * 生成createExcelEntityDto对象
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createLineExcelEntityDto(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        int currentTimeMillis =  (int)((Math.random()*9+1)*100000);//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust


        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",String.valueOf(currentTimeMillis));
        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名
        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
        root.put("lineClassFileName",lineClassFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("selectLineFileList",selectLineFileList);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "java"+File.separator+"multiple"+File.separator+"line";
        String fileName = "Excel"+lineClassFileName+"Dto"+".java";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        lINE_DTO_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+File.separator+"model"+
                File.separator+modelName+File.separator+packageName+File.separator+"dto"+File.separator;
        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "excel_entity_dto.java.ftl", lINE_DTO_PATH,  fileName);
        return ;
    }
    /**
     * 生成dto对象
     *
     * @param configDTO
     * @param tableDefList
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public String createDto(ConfigDTO configDTO, List<TableDefDTO> tableDefList, String fileName) throws Exception {
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, Object> root = new HashMap<>();
        String classsName = configDTO.getEntity() + "DTO";
        root.put("currentTimeMillis", String.valueOf(currentTimeMillis));
        root.put("package", SERVICE_PACKAGE_PATH);
        root.put("entity", configDTO.getEntity());
        root.put("tableComment", configDTO.getTableComment());
        root.put("tableName", configDTO.getTableName());
        root.put("classsName", classsName);
        root.put("pk", configDTO.getPk());
        root.put("author", configDTO.getAuthor());//作者
        root.put("corePackageName", CORE_PACKAGE_NAME);//核心基本包名称
        root.put("tabledef", tableDefList);
        Writer out = new OutputStreamWriter(System.out);
        FreeMarkertUtil freemk = new FreeMarkertUtil(TEMPLETE_JAVA_PATH, "dto.ftl");
        String dtoFilePath = freemk.processTemplate(root, out, DTO_PATH, fileName);
        out.flush();
        return dtoFilePath;
    }


    /**
     * 生成Dao
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createDao(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+"Mapper.java";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        DAO_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"mapper"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        freemk.processToTemplate(freeMarkerConfigurer,root, "mapper.java.ftl", DAO_PATH,  fileName);

    }

    /**
     * 生成Dao
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createHeadDao(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+"Mapper.java";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        HEAD_DAO_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"mapper"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "java"+File.separator+"multiple"+File.separator+"head";
        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "mapper.java.ftl", HEAD_DAO_PATH,  fileName);

    }

    /**
     * 生成Dao
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createLineDao(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust

        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        LINE_DAO_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"mapper"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);

        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名
        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
        root.put("lineClassFileName",lineClassFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("selectLineFileList",selectLineFileList);
        String fileName = lineClassFileName+"Mapper.java";//生产java名称
        root.put("currentTimeMillis",currentTimeMillis);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "java"+File.separator+"multiple"+File.separator+"line";
        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "mapper.java.ftl", LINE_DAO_PATH,  fileName);

    }
    /**
     * 生成Dao.xml
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createDaoXml(CodeVo codeVo,String uuid,String path) throws Exception {

        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+"Mapper.xml";//生产xml名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        DAO_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"mapper"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        freemk.processToTemplate(freeMarkerConfigurer,root, "mapper.xml.ftl", DAO_PATH,  fileName);
    }

    /**
     * 生成Dao.xml
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createHeadDaoXml(CodeVo codeVo,String uuid,String path) throws Exception {

        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+"Mapper.xml";//生产xml名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        HEAD_DAO_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"mapper"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        freemk.processToTemplate(freeMarkerConfigurer,root, "mapper.xml.ftl", HEAD_DAO_PATH,  fileName);
    }
    /**
     * 生成Dao.xml
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createLineDaoXml(CodeVo codeVo,String uuid,String path) throws Exception {

        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust

        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        LINE_DAO_XML_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"mapper"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);

        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名
        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
        root.put("lineClassFileName",lineClassFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("selectLineFileList",selectLineFileList);
        root.put("currentTimeMillis",currentTimeMillis);
        String fileName = lineClassFileName+"Mapper.xml";//生产xml名称
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "java"+File.separator+"multiple"+File.separator+"line";
        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "mapper.xml.ftl", LINE_DAO_XML_PATH,  fileName);
    }

    /**
     * 生成Controller
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createController(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+"Controller.java";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        CONTROLLER_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"controller"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        freemk.processToTemplate(freeMarkerConfigurer,root, "controller.java.ftl", CONTROLLER_PATH,  fileName);
    }
    /**
     * 生成头Controller
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createHeadController(CodeVo codeVo,String uuid,String path) throws Exception {
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = classFileName+"Controller.java";//生产java名称
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        FreeMarkertUtil freemk = new FreeMarkertUtil();


        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名
        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
        root.put("lineClassFileName",lineClassFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("selectLineFileList",selectLineFileList);

        String templatePath = "java"+File.separator+"multiple"+File.separator+"head";

        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"code";
        HEAD_CONTROLLER_PATH = downloadProjectPath + File.separator+"src"+File.separator+"main"+File.separator+"java"+File.separator+"com"+
                File.separator+"midea"+File.separator+"cloud"+File.separator+"srm"+
                File.separator+modelName+File.separator+packageName+File.separator+"controller"+File.separator;

        freemk.processMultipleTemplate(templatePath,freeMarkerConfigurer,root, "controller.java.ftl", HEAD_CONTROLLER_PATH,  fileName);
    }

    /**
     * 生成前端index.js
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createIndexVue(CodeVo codeVo,String uuid,String path) throws Exception {
        String pk ="";//主键
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        pk = AutoCodeUtil.getPk(selectHeadFileList);
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = "index.vue";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"vue";
        INDEX_JS_PATH = downloadProjectPath + File.separator+"src"+File.separator+"modules"+File.separator+"generate"+File.separator+"views"
                +File.separator+packageName+File.separator;

        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        root.put("pk",pk);
        root.put("modelName",modelName);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "vue";
        freemk.processVueToTemplate(templatePath,freeMarkerConfigurer,root, "index.ftl", INDEX_JS_PATH,  fileName);
    }

    /**
     * 生成前端index.js
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createIndexVueByAppend(CodeVo codeVo,String uuid,String path) throws Exception {
        String pk ="";//主键
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        pk = AutoCodeUtil.getPk(selectHeadFileList);
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = "index.vue";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"vue";
        INDEX_JS_PATH = downloadProjectPath + File.separator+"src"+File.separator+"modules"+File.separator+"generate"+File.separator+"views"
                +File.separator+packageName+File.separator;

        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        root.put("pk",pk);
        root.put("modelName",modelName);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "vue";
        freemk.processVueToTemplate(templatePath,freeMarkerConfigurer,root, "append.ftl", INDEX_JS_PATH,  fileName);
    }

    /**
     * 生成前端generate.js
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createGenerateJs(CodeVo codeVo,String uuid,String path) throws Exception {
        String pk ="";//主键
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        pk = AutoCodeUtil.getPk(selectHeadFileList);
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        if(partHeadMap.size() <= 0 ){
            partHeadMap = null;
        }
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = "generate.js";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"vue";
        GENERATE_JS_PATH = downloadProjectPath + File.separator+"src"+File.separator+"service"+File.separator+"modules"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        root.put("modelName",modelName);
        root.put("pk",pk);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "vue";
        freemk.processVueToTemplate(templatePath,freeMarkerConfigurer,root, "generate.ftl", GENERATE_JS_PATH,  fileName);
    }

    /**
     * 生成前端generate.js
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createMultiGenerateJs(CodeVo codeVo,String uuid,String path) throws Exception {
        String pk ="";//主键
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        pk = AutoCodeUtil.getPk(selectHeadFileList);
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        if(partHeadMap.size() <= 0 ){
            partHeadMap = null;
        }
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = "generate.js";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"vue";
        GENERATE_JS_PATH = downloadProjectPath + File.separator+"src"+File.separator+"service"+File.separator+"modules"+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        root.put("modelName",modelName);
        root.put("pk",pk);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "vue"+File.separator+"multiple";
        freemk.processVueToTemplate(templatePath,freeMarkerConfigurer,root, "generate.ftl", GENERATE_JS_PATH,  fileName);
    }

    /**
     * create edit.vue
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createEditVue(CodeVo codeVo,String uuid,String path) throws Exception {
        String pk ="";//主键
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        pk = AutoCodeUtil.getPk(selectHeadFileList);
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        if(partHeadMap.size() <= 0 ){
            partHeadMap = null;
        }
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = "edit.vue";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"vue";
        EDIT_VUE_PATH = downloadProjectPath + File.separator+"src"+File.separator+"modules"+File.separator+"generate"
                +File.separator+"views"+File.separator+packageName+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        root.put("modelName",modelName);
        root.put("pk",pk);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "vue";
//        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名
//        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
//        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
//        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
//        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
//        root.put("lineClassFileName",lineClassFileName);
//        root.put("lineTargetName",lineTargetName);
//        root.put("selectLineFileList",selectLineFileList);
        freemk.processVueToTemplate(templatePath,freeMarkerConfigurer,root, "edit.ftl", EDIT_VUE_PATH,  fileName);
    }

    /**
     * create edit.vue
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createMultipleEditVue(CodeVo codeVo,String uuid,String path) throws Exception {
        String pk ="";//主键
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        pk = AutoCodeUtil.getPk(selectHeadFileList);
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        if(partHeadMap.size() <= 0 ){
            partHeadMap = null;
        }
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = "edit.vue";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"vue";
        EDIT_VUE_PATH = downloadProjectPath + File.separator+"src"+File.separator+"modules"+File.separator+"generate"
                +File.separator+"views"+File.separator+packageName+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        root.put("modelName",modelName);
        root.put("pk",pk);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "vue"+File.separator+"multiple";
        String lineShortTableName = AutoCodeUtil.getTableName(codeVo.getTableList().get(1).getName());//行表表名
        String lineClassFileName = StringUtil.upperCase(StringUtil.underlineToCamel(lineShortTableName));//行表java类名
        String lineTargetName = StringUtil.underlineToCamel(lineShortTableName);//java对象
        List<TableDefDTO> selectLineFileList = codeVo.getSelectLineFileList();//查询所有字段
        AutoCodeUtil.setJavaType(selectLineFileList);//设置字段名称类型
        root.put("lineClassFileName",lineClassFileName);
        root.put("lineTargetName",lineTargetName);
        root.put("selectLineFileList",selectLineFileList);
        freemk.processVueToTemplate(templatePath,freeMarkerConfigurer,root, "edit.ftl", EDIT_VUE_PATH,  fileName);
    }

    /**
     * create List.vue
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createRouterVue(CodeVo codeVo,String uuid,String path) throws Exception {
        String pk ="";//主键
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        pk = AutoCodeUtil.getPk(selectHeadFileList);
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        if(partHeadMap.size() <= 0 ){
            partHeadMap = null;
        }
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = "index.js";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"vue";
        ROUTER_VUE_PATH = downloadProjectPath + File.separator+"src"+File.separator+"modules"+File.separator+"generate"+File.separator+"router"
                +File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        root.put("modelName",modelName);
        root.put("pk",pk);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "vue";
        freemk.processVueToTemplate(templatePath,freeMarkerConfigurer,root, "router.ftl", ROUTER_VUE_PATH,  fileName);
    }

    /**
     * create List.vue
     *
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createListVue(CodeVo codeVo,String uuid,String path) throws Exception {
        String pk ="";//主键
        String modelName = codeVo.getModuleName();
        List<TableDefDTO> selectHeadFileList =  new ArrayList<>();
        selectHeadFileList = codeVo.getSelectHeadFileList();//查询所有字段
        pk = AutoCodeUtil.getPk(selectHeadFileList);
        Map<String,List<TableDefDTO>>  partHeadMap = AutoCodeUtil.getHeadPartMap(selectHeadFileList);//分区组装
        if(partHeadMap.size() <= 0 ){
            partHeadMap = null;
        }
        String databaseTable = codeVo.getTableList().get(0).getName();//全表名称
        String shortTableName = AutoCodeUtil.getTableName(databaseTable);//平台_模块_表名(ceea_inquiry_quota_adjust) 取出表名 quota_adjust
        AutoCodeUtil.setJavaType(selectHeadFileList);//设置字段名称类型
        codeVo.setSelectHeadFileList(selectHeadFileList);
        long currentTimeMillis = System.currentTimeMillis();//生产系列号
        String classFileName = StringUtil.upperCase(StringUtil.underlineToCamel(shortTableName));//java类名
        String targetName = StringUtil.underlineToCamel(shortTableName);//java对象
        String packageName = StringUtil.underlineToCamel(shortTableName).toLowerCase();//基本包名 quota_adjust --->quotaadjust
        String fileName = "list.vue";//生产java名称
        //entity类生成路径
        String  downloadProjectPath = path+File.separator+"download"+File.separator+uuid+File.separator+"project"+File.separator+"vue";
        LIST_VUE_PATH = downloadProjectPath + File.separator+"src"+File.separator+"modules"+File.separator+"generate"
                +File.separator+"views"+File.separator+packageName+File.separator;
        Map<String, Object> root = new HashMap<>();
        root.put("codeVo",codeVo);
        root.put("selectHeadFileList",selectHeadFileList);
        root.put("partHeadMap",partHeadMap);
        root.put("databaseTable",databaseTable);
        root.put("packageName",packageName);
        root.put("classFileName",classFileName);
        root.put("currentTimeMillis",currentTimeMillis);
        root.put("targetName",targetName);
        root.put("modelName",modelName);
        root.put("pk",pk);
        FreeMarkertUtil freemk = new FreeMarkertUtil();
        String templatePath = "vue";
        freemk.processVueToTemplate(templatePath,freeMarkerConfigurer,root, "list.ftl", LIST_VUE_PATH,  fileName);
    }

    /**
     * 生成前段文件
     *
     * @param configDTO
     * @param tableDefList
     * @param fileUrl           前段文件路径
     * @param listWFileName     列表W文件名称
     * @param listWJsFileName   列表JS文件名称
     * @param detailWFileName   详情W文件名称
     * @param detailWJsFileName 详情JS文件名称
     * @throws Exception
     */
//    public void createForepart(ConfigDTO configDTO, List<TableDefDTO> tableDefList, String fileUrl, String listWFileName, String listWJsFileName, String detailWFileName, String detailWJsFileName) throws Exception {
//
//        Thread.sleep(1000 * 60);
//
//        //生成生成list.w
    //  createListW(configDTO, tableDefList, fileUrl, listWFileName);
//        //生成listw.js
//        createListWJs(configDTO, tableDefList, fileUrl, listWJsFileName);
//        //生成detail.w
//        createDetailW(configDTO, tableDefList, fileUrl, detailWFileName);
//        //生成detail.js
//        createDetailJs(configDTO, tableDefList, fileUrl, detailWJsFileName);
//
//        log.info("============代码生成结束===========");
//
//    }


    /**
     * 对tableDefTempList进行分组
     *
     * @param tableDefTempList
     * @param colNum           多少列为一行
     * @return
     * @throws Exception
     */
    public Map<String, List<TableDefDTO>> getMapListTableDefDTOByRowLine(List<TableDefDTO> tableDefTempList, int colNum) throws Exception {
        List<TableDefDTO> tableDefs = new ArrayList<>();
        Map<String, List<TableDefDTO>> tablesMap = new LinkedHashMap<>();
        String[] arrayForWFile = null;
        String[] arrayForWFileThree = {"0_2", "3_5", "6_8", "9_11",
                "12_14", "15_17", "18_20", "19_23",
                "24_26", "25_29", "30_32", "33_35",
                "34_38", "39_41", "42_44", "45_47",
                "48_50", "51_53", "54_56", "57_59"};
        String[] arrayForWFileFour = {"0_3", "4_7", "8_11", "12_15",
                "16_19", "20_23", "24_27", "28_31",
                "32_35", "36_39", "40_43", "44_47",
                "48_51", "52_55", "56_59"};
        if (colNum == 3) {
            arrayForWFile = arrayForWFileThree;
        } else if (colNum == 4) {
            arrayForWFile = arrayForWFileFour;
        }

        int i = 0;
        for (int j = 0; j < arrayForWFile.length && i < (tableDefTempList.size() - 1); j++) {
            String value = arrayForWFile[j];
            int min = Integer.valueOf(value.split("_")[0]);
            int max = Integer.valueOf(value.split("_")[1]);
            for (; i < tableDefTempList.size(); i++) {
                if (i >= min && i <= max) {
                    TableDefDTO tableDef = tableDefTempList.get(i);
                    tableDefs.add(tableDef);

                }
                if (i > max || i == (tableDefTempList.size() - 1)) {
                    tablesMap.put(value + "", tableDefs);
                    tableDefs = new ArrayList<>();
                    break;
                }
            }
        }

        return tablesMap;
    }

    /**
     * 获取数字字典
     *
     * @param tableDefList
     * @return
     * @throws Exception
     */
//    public List<DictItemDTO> getDictItemDTOList(List<TableDefDTO> tableDefList) throws Exception {
//        //设置所有字段List
//        List<DictItemDTO> dictItemDTOList = new ArrayList<>();
//
//        for (TableDefDTO tableDef : tableDefList) {
//            if (SccStringUtils.isNotEmpty(tableDef.getDictItemCode())) {
//                DictItemDTO dictItemDTO = new DictItemDTO();
//                dictItemDTO.setJavaCode(tableDef.getJavaCode());
//                dictItemDTO.setDictItemCode(tableDef.getDictItemCode());
//                dictItemDTO.setDictItemCodeCamel(StringUtil.underlineToCamel(tableDef.getDictItemCode().toLowerCase()));
//                dictItemDTO.setDictItemName(tableDef.getDictItemName());
//                dictItemDTOList.add(dictItemDTO);
//            }
//
//        }
//        return dictItemDTOList;
//
//    }

    /**
     * 生成list.w文件
     *
     * @param configDTO
     * @param tableDefList
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
    public void createListW(ConfigDTO configDTO, List<TableDefDTO> tableDefList, String fileName) throws Exception {

        long currentTimeMillis = System.currentTimeMillis();
        Map<String, Object> root = new HashMap<>();

        root.put("requestMappingUrl", REQUEST_MAPPING_URL);
        root.put("currentTimeMillis", String.valueOf(currentTimeMillis));
        root.put("package", SERVICE_PACKAGE_PATH);
        root.put("entity", configDTO.getEntity());
        root.put("tableComment", configDTO.getTableComment());
        root.put("tableName", configDTO.getTableName());
        root.put("pk", configDTO.getPk());
        root.put("author", configDTO.getAuthor());//作者
        root.put("corePackageName", CORE_PACKAGE_NAME);//核心基本包名称


        //设置列表显示的字段
        List<TableDefDTO> gridTableDefTempList = new ArrayList<>();

        //设置查询要显示的字段
        List<TableDefDTO> queryTableDefTempList = new ArrayList<>();

        for (TableDefDTO tableDef : tableDefList) {
            String code = tableDef.getCode();
            String isShowOnGrid = tableDef.getIsShowOnGrid();
            String isQueryCondition = tableDef.getIsQueryCondition();
//            if (!"createdBy".equalsIgnoreCase(code) && !"lastUpdatedBy".equalsIgnoreCase(code)) {
//                if (YesOrNo.YES.getValue().equals(isShowOnGrid)) {
//                    gridTableDefTempList.add(tableDef);
//                }
//                if (YesOrNo.YES.getValue().equals(isQueryCondition)) {
//                    queryTableDefTempList.add(tableDef);
//                }
//            }
//            //设置数字字典驼峰
//            if (SccStringUtils.isNotEmpty(tableDef.getDictItemCode())) {
//                tableDef.setDictItemCodeCamel(StringUtil.underlineToCamel(tableDef.getDictItemCode().toLowerCase()));
//            }
        }

        //获取查询条件的Map，3个条件为一列
        Map<String, List<TableDefDTO>> tablesQueryMap = getMapListTableDefDTOByRowLine(queryTableDefTempList, 3);
        //设置panel的top高度
        int panelTopHeight = 120 + (tablesQueryMap.size() * 40);

        root.put("dataTabledef", tableDefList);//data数据
        root.put("gridTabledef", gridTableDefTempList);//列表
        root.put("dateTablesQuery", queryTableDefTempList);//条件查询data
        root.put("tablesQueryMap", tablesQueryMap);//条件查询
        root.put("panelTopHeight", panelTopHeight);//hide-top与grid的高度
//        root.put("dictItemDTOList", getDictItemDTOList(tableDefList));//字典data
        root.put("isNeedExcelImport", configDTO.getIsNeedExcelImport());//是否需要导入功能
        root.put("isNeedExcelExport", configDTO.getIsNeedExcelExport());//是否需要导出功能
        Writer out = new OutputStreamWriter(System.out);
        FreeMarkertUtil freemk = new FreeMarkertUtil(TEMPLETE_W_PATH, "list.art");
        freemk.processTemplate(root, out, VUE_lIST_PATH, fileName);
        out.flush();

    }


    /**
     * 生成listw.js文件
     *
     * @param configDTO
     * @param tableDefList
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
//    public void createListWJs(ConfigDTO configDTO, List<TableDefDTO> tableDefList, String fileUrl, String fileName) throws Exception {
//
//        long currentTimeMillis = System.currentTimeMillis();
//        Map<String, Object> root = new HashMap<>();
//
//        root.put("requestMappingUrl", REQUEST_MAPPING_URL);
//        root.put("currentTimeMillis", String.valueOf(currentTimeMillis));
//        root.put("package", SERVICE_PACKAGE_PATH);
//        root.put("entity", configDTO.getEntity());
//        root.put("dto", configDTO.getDto());
//        root.put("tableComment", configDTO.getTableComment());
//        root.put("tableName", configDTO.getTableName());
//        root.put("pk", configDTO.getPk());
//        root.put("author", configDTO.getAuthor());//作者
//        root.put("corePackageName", CORE_PACKAGE_NAME);//核心基本包名称
//        root.put("tabledef", tableDefList);
//        String gridSetModelName = configDTO.getTableName().substring(configDTO.getTableName().indexOf("_", configDTO.getTableName().indexOf("_") + 1) + 1);
//        root.put("gridSetModelName", gridSetModelName.toLowerCase());//表格自定义设置
//
//        //导入 subPath 模板子路径
//        String importSubPath = configDTO.getTableName().substring(configDTO.getTableName().indexOf("_", configDTO.getTableName().indexOf("_", configDTO.getTableName().indexOf("_") + 1) + 1) + 1);
//        root.put("importSubPath", StringUtil.underlineToCamel(importSubPath.toLowerCase()));//统一附件模块名
//
//        root.put("dictItemDTOList", getDictItemDTOList(tableDefList));//字典data
//        root.put("isNeedExcelImport", configDTO.getIsNeedExcelImport());//是否需要导入功能
//        root.put("isNeedExcelExport", configDTO.getIsNeedExcelExport());//是否需要导出功能
//        root.put("isNeedCommonFile", configDTO.getIsNeedCommonFile());//是否需要统一公用附件
//        Writer out = new OutputStreamWriter(System.out);
//        FreeMarkertUtil freemk = new FreeMarkertUtil(TEMPLETE_W_PATH, "listWJs.ftl");
//        freemk.processTemplate(root, out, fileUrl, fileName);
//        out.flush();
//
//    }


    /**
     * 生成detail.w文件
     *
     * @param configDTO
     * @param tableDefList
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
//    public void createDetailW(ConfigDTO configDTO, List<TableDefDTO> tableDefList, String fileUrl, String fileName) throws Exception {
//
//        long currentTimeMillis = System.currentTimeMillis();
//        List<TableDefDTO> tableDefTempList = new ArrayList<>();
//        //设置显示的字段
//        for (TableDefDTO tableDef : tableDefList) {
//            String isShowOnForm = tableDef.getIsShowOnForm();
//            String code = tableDef.getCode();
//            if (YesOrNo.YES.getValue().equals(isShowOnForm) && !"createdBy".equalsIgnoreCase(code) && !"lastUpdatedBy".equalsIgnoreCase(code)) {
//                tableDefTempList.add(tableDef);
//            }
//            //设置数字字典驼峰
//            if (SccStringUtils.isNotEmpty(tableDef.getDictItemCode())) {
//                tableDef.setDictItemCodeCamel(StringUtil.underlineToCamel(tableDef.getDictItemCode().toLowerCase()));
//            }
//        }
//
//        //获取表单的Map，4个条件为一列
//        Map<String, List<TableDefDTO>> tablesMap = getMapListTableDefDTOByRowLine(tableDefTempList, 4);
//
//        Map<String, Object> root = new HashMap<>();
//        root.put("requestMappingUrl", REQUEST_MAPPING_URL);
//
//        root.put("currentTimeMillis", String.valueOf(currentTimeMillis));
//        root.put("package", SERVICE_PACKAGE_PATH);
//        root.put("entity", configDTO.getEntity());
//        root.put("tableComment", configDTO.getTableComment());
//        root.put("tableName", configDTO.getTableName());
//        root.put("pk", configDTO.getPk());
//        root.put("author", configDTO.getAuthor());//作者
//        root.put("isNeedExcelImport", configDTO.getIsNeedExcelImport());//是否需要导入功能
//        root.put("isNeedExcelExport", configDTO.getIsNeedExcelExport());//是否需要导出功能
//        root.put("isNeedCommonFile", configDTO.getIsNeedCommonFile());//是否需要统一公用附件
//        root.put("corePackageName", CORE_PACKAGE_NAME);//核心基本包名称
//        root.put("dataTabledef", tableDefList);
//        root.put("tablesMap", tablesMap);
//        root.put("dictItemDTOList", getDictItemDTOList(tableDefList));//字典data
//        Writer out = new OutputStreamWriter(System.out);
//        FreeMarkertUtil freemk = new FreeMarkertUtil(TEMPLETE_W_PATH, "detailW.ftl");
//        freemk.processTemplate(root, out, fileUrl, fileName);
//        out.flush();
//    }


    /**
     * 生成detail.js文件
     *
     * @param configDTO
     * @param tableDefList
     * @throws IOException
     * @throws TemplateException
     * @throws SQLException
     */
//    public void createDetailJs(ConfigDTO configDTO, List<TableDefDTO> tableDefList, String fileUrl, String fileName) throws Exception {
//
//        long currentTimeMillis = System.currentTimeMillis();
//        Map<String, Object> root = new HashMap<>();
//
//        root.put("requestMappingUrl", REQUEST_MAPPING_URL);
//
//        root.put("currentTimeMillis", String.valueOf(currentTimeMillis));
//        root.put("package", SERVICE_PACKAGE_PATH);
//        root.put("entity", configDTO.getEntity());
//        root.put("tableComment", configDTO.getTableComment());
//        root.put("tableName", configDTO.getTableName());
//        root.put("pk", configDTO.getPk());
//        root.put("pkDb", StringUtil.camelToUnderline(configDTO.getPk()).toUpperCase());
//
//        String commonFileModelName = configDTO.getTableName().substring(configDTO.getTableName().indexOf("_", configDTO.getTableName().indexOf("_") + 1) + 1);
//        root.put("commonFileModelName", commonFileModelName.toLowerCase());//统一附件模块名
//        root.put("author", configDTO.getAuthor());//作者
//        root.put("isNeedExcelImport", configDTO.getIsNeedExcelImport());//是否需要导入功能
//        root.put("isNeedExcelExport", configDTO.getIsNeedExcelExport());//是否需要导出功能
//        root.put("isNeedCommonFile", configDTO.getIsNeedCommonFile());//是否需要统一公用附件
//        root.put("corePackageName", CORE_PACKAGE_NAME);//核心基本包名称
//        root.put("tabledef", tableDefList);
//        root.put("dictItemDTOList", getDictItemDTOList(tableDefList));//字典data
//        Writer out = new OutputStreamWriter(System.out);
//        FreeMarkertUtil freemk = new FreeMarkertUtil(TEMPLETE_W_PATH, "detailJs.ftl");
//        freemk.processTemplate(root, out, fileUrl, fileName);
//        out.flush();
//
//    }
}


