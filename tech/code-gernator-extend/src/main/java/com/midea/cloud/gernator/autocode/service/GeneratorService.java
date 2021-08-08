package com.midea.cloud.gernator.autocode.service;


import com.midea.cloud.gernator.autocode.entity.CodeVo;
import com.midea.cloud.gernator.autocode.entity.ConfigDTO;
import com.midea.cloud.gernator.autocode.entity.TableDefDTO;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * <pre>
 *  代码生成器 Service
 * </pre>
 *
 * @author yixinyx@meicloud.com
 * @version 1.00.00
 *
 *          <pre>
 * 修改记录
 *    修改后版本:     修改人：  修改日期:     修改内容:
 * </pre>
 */
public interface GeneratorService {

//    /**
//     * 查看所有表
//     */
//    PageInfo<TableNameDTO> listTable(TableNameDTO tableNameDTO);

    /**
     * 根据名字查看基本配置信息
     *
     * @param tableName
     * @return
     * @throws Exception
     */
//    ConfigDTO getConfig(String tableName) throws Exception;

    /**
     * 根据名字查看表基结构信息
     *
     * @param
     * @return
     * @throws Exception
     */
//    List<TableDefDTO> getTableDefInfoByName(String tableName);

    /**
     * 生成代码
     *
     * @param
     * @return
     * @throws Exception
     */
    String createCode(CodeVo codeVo,String uuid,String path) throws Exception;
    /**
     * 生成代码而且删除代码
     *
     * @param
     * @return
     * @throws Exception
     */
    void downloadCode(HttpServletResponse response,CodeVo codeVo,String path) throws Exception;


}
