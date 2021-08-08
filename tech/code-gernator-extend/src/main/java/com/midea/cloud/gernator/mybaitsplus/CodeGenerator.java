package com.midea.cloud.gernator.mybaitsplus;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <pre>
 *  代码生成器
 *  演示例子，执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 * </pre>
 *
 * @author huanghb14@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2019-11-6 19:29
 *  修改内容:
 * </pre>
 */
public class CodeGenerator {

    /**ud_
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotEmpty(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    private final static String PARENT_PATH = "com.midea.cloud.srm.sup";

    private final static String DATASOURCE_URL = "jdbc:mysql://10.17.149.182:3306/database?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai&autoReconnect=true";

    private final static String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";

    private final static String DATABASE_ACCOUNT = "cdc_base_new";

    private final static String DATABASE_PASSWORD = "123456";

    private final static String MIP_NAME = "wangpr@meiCloud.com";

    private final static String PARENT_URL = PARENT_PATH.replaceAll("\\.", "/");

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/tech/code-gernator/src/main/generator/target");
        gc.setAuthor(MIP_NAME);
        gc.setOpen(false);
        // gc.setSwagger2(true); 实体属性 Swagger2 注解
        mpg.setGlobalConfig(gc);

        // 数据源配置
        String databaseUrl = DATASOURCE_URL.replaceAll("database", scanner("数据库名"));
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setDbType(DbType.MYSQL);
        dsc.setUrl(databaseUrl);
        // dsc.setSchemaName("public");
        dsc.setDriverName(DATABASE_DRIVER);
        dsc.setUsername(DATABASE_ACCOUNT);
        dsc.setPassword(DATABASE_PASSWORD);
        dsc.setTypeConvert(new MySqlTypeConvert(){
            /**
             * 数据库字段与java类型转换
             * @param globalConfig
             * @param fieldType
             * @return
             */
            @Override
            public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
                // mysql数据库的datetime类型转换成java的util date
                if (fieldType.equals("datetime")) {
                    return DbColumnType.DATE;
                }
                return super.processTypeConvert(globalConfig, fieldType);
            }
        });
        mpg.setDataSource(dsc);

        // 包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName(scanner("模块名"));
        pc.setParent(PARENT_PATH);
        mpg.setPackageInfo(pc);

        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
            public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
                objectMap.put("datetime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())); // 用于模板注释
                return objectMap;
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        // 如果模板引擎是 velocity
        // String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/tech/code-gernator/src/main/generator/target/" + PARENT_URL + "/" + pc.getModuleName() + "/mapper/"
                         + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });
        /*
        cfg.setFileCreate(new IFileCreate() {
            @Override
            public boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath) {
                // 判断自定义文件夹是否需要创建
                checkDir("调用默认方法创建的目录");
                return false;
            }
        });
        */
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        //指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        templateConfig.setEntity("template/entity.java");
        templateConfig.setService("template/service.java");
        templateConfig.setServiceImpl("template/serviceImpl.java");
        templateConfig.setController("template/controller.java");

        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("com.midea.cloud.srm.model.common.BaseEntity");
        strategy.setEntitySerialVersionUID(true);
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setTablePrefix(scanner("需要删除表名前缀（如果不需要删除，输入： xx ）"));
        // 公共父类
//        strategy.setSuperControllerClass("com.baomidou.ant.common.BaseController");
        // 写于父类中的公共字段
//        strategy.setSuperEntityColumns("id");
        strategy.setInclude(scanner("表名，多个英文逗号分割").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        // 自定义需要填充的字段
        List<TableFill> tableFills = new ArrayList<TableFill>();
        tableFills.add(new TableFill("CREATED_ID", FieldFill.INSERT));
        tableFills.add(new TableFill("CREATED_BY", FieldFill.INSERT));
        tableFills.add(new TableFill("CREATION_DATE", FieldFill.INSERT));
        tableFills.add(new TableFill("CREATED_BY_IP", FieldFill.INSERT));
        tableFills.add(new TableFill("CREATED_FULL_NAME", FieldFill.INSERT));
        tableFills.add(new TableFill("LAST_UPDATED_ID", FieldFill.INSERT_UPDATE));
        tableFills.add(new TableFill("LAST_UPDATED_BY", FieldFill.INSERT_UPDATE));
        tableFills.add(new TableFill("LAST_UPDATE_DATE", FieldFill.INSERT_UPDATE));
        tableFills.add(new TableFill("LAST_UPDATED_BY_IP", FieldFill.INSERT_UPDATE));
        tableFills.add(new TableFill("LAST_UPDATED_FULL_NAME", FieldFill.INSERT_UPDATE));
        strategy.setTableFillList(tableFills);
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }


}
