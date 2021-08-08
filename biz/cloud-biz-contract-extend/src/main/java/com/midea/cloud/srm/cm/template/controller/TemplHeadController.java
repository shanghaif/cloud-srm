package com.midea.cloud.srm.cm.template.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.cm.template.service.ITemplHeadService;
import com.midea.cloud.srm.model.cm.template.dto.TemplHeadQueryDTO;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.cm.template.dto.ContractTemplDTO;
import com.midea.cloud.srm.model.cm.template.entity.TemplHead;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

/**
*  <pre>
 *  合同模板头表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-19 08:58:08
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/template/templHead")
public class TemplHeadController extends BaseController {

    @Autowired
    private ITemplHeadService iTemplHeadService;

    /**
    * 获取合同模板
    * @param templHeadId
    */
    @GetMapping("/getContractTemplDTO")
    public ContractTemplDTO getContractTemplDTO(Long templHeadId) {
        Assert.notNull(templHeadId, "templHeadId不能为空");
        return iTemplHeadService.getContractTemplDTO(templHeadId);
    }

    /**
    * 保存(无需校验)
    * @param contractTemplDTO
    */
    @PostMapping("/add")
    public void add(@RequestBody ContractTemplDTO contractTemplDTO) {
        iTemplHeadService.saveContractTemplDTO(contractTemplDTO);
    }
    
    /**
    * 删除
    * @param templHeadId
    */
    @GetMapping("/deleteContractTemplDTO")
    public void deleteContractTemplDTO(Long templHeadId) {
        iTemplHeadService.deleteContractTemplDTO(templHeadId);
    }

    /**
    * 修改(无需校验)
    * @param contractTemplDTO
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ContractTemplDTO contractTemplDTO) {
        iTemplHeadService.updateContractTemplDTO(contractTemplDTO);
    }

    /**
    * 分页查询
    * @param templHeadQueryDTO
    * @return
    */
    @PostMapping("/listPageByParm")
    public PageInfo<TemplHead> listPageByParm(@RequestBody TemplHeadQueryDTO templHeadQueryDTO) {
        return iTemplHeadService.listPageByParm(templHeadQueryDTO);
    }

    /**
     * 生效
     * @param templHeadId
     */
    @GetMapping("/effective")
    public void effective(Long templHeadId) {
        iTemplHeadService.effective(templHeadId);
    }


    /**
     * 失效
     * @param templHead
     */
    @PostMapping("/invalid")
    public void invalid(@RequestBody TemplHead templHead) {
        iTemplHeadService.invalid(templHead);
    }

    /**
     * 复制
     * @param templHeadId
     */
    @GetMapping("/copy")
    public void copy(@RequestParam Long templHeadId) {
        iTemplHeadService.copy(templHeadId);
    }
}
