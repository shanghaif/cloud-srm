package com.midea.cloud.gernator.util;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.util.Map;

/**
 * 模板工具类
 */
public class FreeMarkertUtil {

    private static final Log log = LogFactory.getLog(FreeMarkertUtil.class);

    private String templatePath = "";

    private String templateName = "";

    private String templateEncoding = "";

    public FreeMarkertUtil() {
    }


    /**
     * @param templatePath 模版路径
     * @param templateName 模版名称
     */
    public FreeMarkertUtil(String templatePath, String templateName) {
        this.templatePath = templatePath;
        this.templateName = templateName;
        this.templateEncoding = "UTF-8";
    }

    /**
     * @param templatePath     模版路径
     * @param templateName     模版名称
     * @param templateEncoding 字符集
     */
    public FreeMarkertUtil(String templatePath, String templateName, String templateEncoding) {
        this.templatePath = templatePath;
        this.templateName = templateName;
        this.templateEncoding = templateEncoding;
    }

    /**
     * 生成指定文件
     *
     * @param root     数据源
     * @param out      流
     * @param fileUrl  文件路径
     * @param fileName 文件名称
     */
    public String processTemplate(Map<String, Object> root, Writer out, String fileUrl, String fileName) {
        File fileCode = null;
        try {
            Configuration config = new Configuration();
            File file = new File(templatePath);
            //设置要解析的模板所在的目录，并加载模板文件
            config.setDirectoryForTemplateLoading(file);
            //设置包装器，并将对象包装为数据模型
            config.setObjectWrapper(new DefaultObjectWrapper());
            //获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致
            Template template = config.getTemplate(templateName, templateEncoding);
            //指定文件存放路径是否存在，不存在，自动创建
            File furl = new File(fileUrl);
            if (!furl.exists()) {
                furl.mkdirs();
            }
            //要生成文件存放位置
            fileCode = new File(fileUrl + "/" + fileName);
            //合并数据模型与模板
            log.info("生成文件的路径====>" + fileCode);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileCode), "UTF-8"));
            template.process(root, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return fileCode.toString();

    }



    /**
     *
     * @param root  生成模板的对象参数 map
     * @param tpName  具体模板名称
     * @param fileUrl
     * @param fileName
     */
    public  void processToTemplate(FreeMarkerConfigurer freeMarkerConfigurer, Map<String, Object> root,String tpName,String fileUrl, String fileName){
        // 加载模板
        Template template = null;
        try {
            // 创建配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //configuration.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "template");
            // 设置模板路径
           // String classpath = this.getClass().getResource("/").getPath();
            //configuration.setDirectoryForTemplateLoading(new File(classpath + directory));
            //System.out.println(classpath + directory+tpName);

            //FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
            template = freeMarkerConfigurer.getConfiguration().getTemplate("java"+File.separator+tpName);

            //template = configuration.getTemplate("entity.java.ftl");
            // 静态化页面内容
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
            InputStream inputStream = IOUtils.toInputStream(content,"UTF-8");
            //文件不存在则创建
            File htmlFile = new File(fileUrl);
            if (!htmlFile.exists()) {
                htmlFile.mkdirs();
            }
            // 输出文件
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileUrl + File.separator + fileName));
            IOUtils.copy(inputStream, fileOutputStream);
            // 关闭流
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     *
     * @param root  生成模板的对象参数 map
     * @param tpName  具体模板名称
     * @param fileUrl
     * @param fileName
     */
    public  void processVueToTemplate(String templatePath,FreeMarkerConfigurer freeMarkerConfigurer, Map<String, Object> root,String tpName,String fileUrl, String fileName){
        // 加载模板
        Template template = null;
        try {
            // 创建配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //configuration.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "template");
            // 设置模板路径
            // String classpath = this.getClass().getResource("/").getPath();
            //configuration.setDirectoryForTemplateLoading(new File(classpath + directory));
            //System.out.println(classpath + directory+tpName);

            //FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
            template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath+File.separator+tpName);

            //template = configuration.getTemplate("entity.java.ftl");
            // 静态化页面内容
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
            InputStream inputStream = IOUtils.toInputStream(content,"UTF-8");
            //文件不存在则创建
            File htmlFile = new File(fileUrl);
            if (!htmlFile.exists()) {
                htmlFile.mkdirs();
            }
            // 输出文件
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileUrl + File.separator + fileName));
            IOUtils.copy(inputStream, fileOutputStream);
            // 关闭流
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     *
     * @param root  生成模板的对象参数 map
     * @param tpName  具体模板名称
     * @param fileUrl
     * @param fileName
     */
    public  void processMultipleTemplate(String templatePath,FreeMarkerConfigurer freeMarkerConfigurer, Map<String, Object> root,String tpName,String fileUrl, String fileName){
        // 加载模板
        Template template = null;
        try {
            // 创建配置类
            Configuration configuration = new Configuration(Configuration.getVersion());
            //configuration.setClassLoaderForTemplateLoading(getClass().getClassLoader(), "template");
            // 设置模板路径
            // String classpath = this.getClass().getResource("/").getPath();
            //configuration.setDirectoryForTemplateLoading(new File(classpath + directory));
            //System.out.println(classpath + directory+tpName);

            //FreeMarkerConfigurer freeMarkerConfigurer = new FreeMarkerConfigurer();
            template = freeMarkerConfigurer.getConfiguration().getTemplate(templatePath+File.separator+tpName);

            //template = configuration.getTemplate("entity.java.ftl");
            // 静态化页面内容
            String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, root);
            InputStream inputStream = IOUtils.toInputStream(content,"UTF-8");
            //文件不存在则创建
            File htmlFile = new File(fileUrl);
            if (!htmlFile.exists()) {
                htmlFile.mkdirs();
            }
            // 输出文件
            FileOutputStream fileOutputStream = new FileOutputStream(new File(fileUrl + File.separator + fileName));
            IOUtils.copy(inputStream, fileOutputStream);
            // 关闭流
            inputStream.close();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    /**
     * @param templatePath     模板文件存放目录
     * @param templateName     模板文件名称
     * @param root             数据模型根对象
     * @param templateEncoding 模板文件的编码方式
     * @param out              输出流
     */
    public static void processTemplate(String templatePath, String templateName, String templateEncoding, Map<?, ?> root, Writer out) {
        try {
            Configuration config = new Configuration();
            File file = new File(templatePath);
            //设置要解析的模板所在的目录，并加载模板文件  
            config.setDirectoryForTemplateLoading(file);
            //设置包装器，并将对象包装为数据模型  
            config.setObjectWrapper(new DefaultObjectWrapper());

            //获取模板,并设置编码方式，这个编码必须要与页面中的编码格式一致  
            Template template = config.getTemplate(templateName, templateEncoding);
            //合并数据模型与模板  
            String htmlPath = "d:/123.html";
            File htmlFile = new File(htmlPath);
            out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(htmlFile), "UTF-8"));
            template.process(root, out);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }


    public static boolean creatDirs(String aParentDir, String aSubDir) {
        File aFile = new File(aParentDir);
        if (aFile.exists()) {
            File aSubFile = new File(aParentDir + aSubDir);
            if (!aSubFile.exists()) {
                return aSubFile.mkdirs();
            } else {
                return true;
            }
        } else {
            return false;
        }
    }


} 