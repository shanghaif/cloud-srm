package com.midea.cloud.gernator.autocode.controller;

import com.midea.cloud.gernator.autocode.entity.CodeVo;
import com.midea.cloud.gernator.autocode.service.GeneratorService;
import com.midea.cloud.gernator.util.AutoCodeUtil;
import org.apache.commons.collections4.list.TransformedList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import com.midea.cloud.gernator.autocode.entity.*;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  业务类型配置表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-10-21 14:57:34
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/acode")
public class AutoCodeController {
    @Autowired
    private GeneratorService generatorService;
    /**
    * 下载模板
    * @param
    */
    @PostMapping("/getCodeDowload")
    public void getCodeDowload(HttpServletResponse response,@RequestBody CodeVo codeVo) {
        try {
            System.out.println(System.getProperty("user.dir"));
            String path = System.getProperty("user.dir");
            String mName = AutoCodeUtil.getSwitchModule(codeVo.getModuleName());//模块名称做一个转换
            String applicationName = AutoCodeUtil.getAplicationNameByModule(codeVo.getModuleName());//模块名称做一个转换
            codeVo.setModuleName(mName);
            codeVo.setApplicationName(applicationName);
            generatorService.downloadCode(response,codeVo,path);
        } catch (Exception e) {
           e.printStackTrace();
        }
        return ;
    }
    @GetMapping("/codeVo")
    public CodeVo codeVo() {
        CodeVo codeVo1 =new CodeVo();
        List<Table> tableList1 = new ArrayList<>();
        Table  table = new Table();
        tableList1.add(table);

        List<TableDefDTO> selectHeadFileList = new ArrayList<>();
        TableDefDTO tableDefDTO = new TableDefDTO();
        selectHeadFileList.add(tableDefDTO);
        codeVo1.setSelectHeadFileList(selectHeadFileList);
        codeVo1.setTableList(tableList1);
        return codeVo1;
    }
    /**
     * 生成对应磁盘代码
     * @param
     */
    @PostMapping ("/gernatorCode")
    public void gernatorCode(@RequestBody CodeVo codeVo) {
        try {
         //   generatorService.createCode(codeVo);
        } catch (Exception e) {
              e.printStackTrace();
        }
        return ;
    }

}
