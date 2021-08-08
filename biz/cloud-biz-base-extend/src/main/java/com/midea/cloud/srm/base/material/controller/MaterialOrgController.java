package com.midea.cloud.srm.base.material.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.enums.Enable;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.result.ResultCode;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.material.service.IMaterialOrgService;
import com.midea.cloud.srm.model.base.material.MaterialOrg;
import com.midea.cloud.srm.model.common.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
*  <pre>
 *  物料与组织关系表 前端控制器
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-07-25 11:14:25
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/base/materialOrg")
public class MaterialOrgController extends BaseController {

    @Resource
    private IMaterialOrgService iMaterialOrgService;

    /**
     *  根据ID获取物料-组织关系信息
     * @param id
     */
    @GetMapping("/getMaterialOrgById")
    public MaterialOrg getMaterialOrg(Long id) {
        Assert.notNull(id, "id不能为空");
        return iMaterialOrgService.getById(id);
    }

    /**
     * 新增物料与组织关系信息
     * @param materialOrg
     */
    @PostMapping("/addMaterialOrg")
    public void add(@RequestBody  MaterialOrg materialOrg) {
        Long id = IdGenrator.generate();
        materialOrg.setMaterialOrgId(id);
        //如果是否用于采购为空，则设置为N
        if(StringUtils.isBlank(materialOrg.getUserPurchase())){
            materialOrg.setUserPurchase(Enable.N.toString());
        }
        iMaterialOrgService.save(materialOrg);
    }

    /**
     * 删除物料与组织关系信息
     * @param materialOrgId
     */
    @GetMapping("/deleteMaterialOrg")
    public String deleteMaterialOrg(Long materialOrgId) {
        Assert.notNull(materialOrgId, "id不能为空");
        String result = ResultCode.OPERATION_FAILED.getMessage();
        boolean isDelete = iMaterialOrgService.removeById(materialOrgId);
        if(isDelete){
            result =  ResultCode.SUCCESS.getMessage();
        }
        return result;
    }

    /**
     * 修改物料与组织关系信息
     * @param materialOrg
     */
    @PostMapping("/updateMaterialOrg")
    public void updateMaterialOrg(@RequestBody MaterialOrg materialOrg) {
        Assert.notNull(materialOrg.getMaterialOrgId(), "id不能为空");
        iMaterialOrgService.updateById(materialOrg);
    }

    /**
     * 分页查询物料与组织关系信息
     * @param materialOrg
     * @return
     */
    @PostMapping("/listMaterialOrgPage")
    public PageInfo<MaterialOrg> listMaterialOrgPage(@RequestBody MaterialOrg materialOrg) {
        return iMaterialOrgService.findMateriaOrgPageList(materialOrg);
    }

    /*
    * @Description  获取全部物料和组织对应关系
    * @Date         2020/10/16 17:40
    * @Author       chenjj120@meicloud.com
    * @return
    **/
    @GetMapping("/listAll")
    public List<MaterialOrg> listMaterialOrgAll(){
        return iMaterialOrgService.list();
    }
    
    /**
     * 批量保存物料与组织关系信息
     * @param materialOrg
     */
    @PostMapping("/batchSaveOrUpdateMaterialOrg")
    public void batchSaveOrUpdateMaterialOrg(@RequestBody List<MaterialOrg> materialOrgs) {
        Assert.notNull(materialOrgs, "保存数据为空不能为空");
        try {
        	 if (null != materialOrgs && materialOrgs.size()>0) {
             	for (MaterialOrg materialOrg : materialOrgs) {
             		if (null != materialOrg.getMaterialOrgId()) {
             			iMaterialOrgService.updateById(materialOrg);
             		} else {
             			Long id = IdGenrator.generate();
         		        materialOrg.setMaterialOrgId(id);
         		        //如果是否用于采购为空，则设置为N
         		        if(StringUtils.isBlank(materialOrg.getUserPurchase())){
         		            materialOrg.setUserPurchase(Enable.N.toString());
         		        }
         		        iMaterialOrgService.save(materialOrg);
             		}
             	}
             }
		} catch (DuplicateKeyException e) {
			throw new BaseException("该物料已存在相同的业务实体及库存组织关系");
		} catch (Exception e) {
			throw new BaseException(e.getMessage());
		}
       
       
    }

}
