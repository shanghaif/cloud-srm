package com.midea.cloud.srm.supauth.review.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.ApproveStatusType;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.common.FormResultDTO;
import com.midea.cloud.srm.model.supplierauth.review.dto.EffectFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.EffectForm;
import com.midea.cloud.srm.supauth.review.service.IEffectFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  供方生效单据 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-19 21:09:09
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/review/effectForm")
public class EffectFormController extends BaseController {

    @Autowired
    private IEffectFormService iEffectFormService;

    /**
    * 根据effectFormId获取EffectFormDTO
    * @param effectFormId
    */
    @GetMapping("/getEffectFormDTOById")
    public EffectFormDTO getEffectFormDTOById(Long effectFormId) {
        return iEffectFormService.getEffectFormDTOById(effectFormId);
    }

    /**
    * 提交
    * @param effectFormDTO
    */
    @PostMapping("/submitted")
    public FormResultDTO add(@RequestBody EffectFormDTO effectFormDTO) {
        return iEffectFormService.submitted(effectFormDTO, ApproveStatusType.SUBMITTED.getValue());
    }
    
    /**
    * 删除
    * @param id
    */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iEffectFormService.removeById(id);
    }

    /**
    * 分页查询
    * @param effectForm
    * @return
    */
    @PostMapping("/listPageByParm")
    public PageInfo<EffectForm> listPage(@RequestBody EffectForm effectForm) {
        return iEffectFormService.listPageByParm(effectForm);
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<EffectForm> listAll() { 
        return iEffectFormService.list();
    }

    /**
     * 暂存
     * @param effectFormDTO
     */
    @PostMapping("/saveTemporary")
    public FormResultDTO saveTemporary(@RequestBody EffectFormDTO effectFormDTO) {
        return iEffectFormService.saveTemporary(effectFormDTO, ApproveStatusType.DRAFT.getValue());
    }

    /**
     * 批量删除
     */
    @PostMapping("/bachDeleteByList")
    public void bathDeleteByList(@RequestBody List<Long> effectFormIds) {
        Assert.notEmpty(effectFormIds, "effectFormIds不能为空");
        iEffectFormService.bachDeleteByList(effectFormIds);
    }
}
