package com.midea.cloud.srm.supauth.entry.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.entry.dto.EntryConfigTitleDTO;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryCategoryConfig;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfig;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfigRecord;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.supauth.entry.service.IEntryCategoryConfigService;
import com.midea.cloud.srm.supauth.entry.service.IEntryConfigRecordService;
import com.midea.cloud.srm.supauth.entry.service.IEntryConfigService;
import com.midea.cloud.srm.supauth.entry.service.IEntryFileConfigService;
import com.midea.cloud.srm.supauth.materialtrial.service.IMaterialTrialService;
import com.midea.cloud.srm.supauth.quasample.service.IQuaSampleService;
import com.midea.cloud.srm.supauth.review.service.IReviewFormService;
import com.midea.cloud.srm.supauth.review.service.ISiteFormService;

/**
*  <pre>
 *  供应商准入流程 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-05 13:54:48
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/entry/entryConfig")
public class EntryConfigController extends BaseController {

    @Autowired
    private IEntryConfigService iEntryConfigService;
    
    @Autowired
    private IEntryCategoryConfigService iEntryCategoryConfigService;

    @Autowired
    private BaseClient baseClient;
    
    @Autowired
    private IEntryConfigRecordService iEntryConfigRecordService;
    
    @Autowired
    private IReviewFormService iReviewFormService;
    @Autowired
    private ISiteFormService iSiteFormService;
    @Autowired
    private IQuaSampleService iQuaSampleService;
    @Autowired
    private IMaterialTrialService iMaterialTrialService;
    
    @Autowired
	private IEntryFileConfigService iEntryFileConfigService;
    /**
    * 获取
    * @param entryConfigId
    */
    @GetMapping("/get")
    public EntryConfig get(@RequestParam("entryConfigId") Long entryConfigId) {
        Assert.notNull(entryConfigId, "entryConfigId不能为空");
        return iEntryConfigService.getById(entryConfigId);
    }

    /**
    * 新增
    * @param entryConfig
    */
    @PostMapping("/add")
    public void add(@RequestBody EntryConfig entryConfig) {
        Long id = IdGenrator.generate();
        entryConfig.setEntryConfigId(id);
        String entryConfigNum = baseClient.seqGen(SequenceCodeConstant.SEQ_SUP_ENTRY_CONFIG_NUM);
        entryConfig.setEntryConfigNum(entryConfigNum);
        iEntryConfigService.save(entryConfig);
    }
    
    /**
    * 删除
    * @param entryConfigId
    */
    @GetMapping("/delete")
    public void delete(@RequestParam("entryConfigId") Long entryConfigId) {
        Assert.notNull(entryConfigId, "entryConfigId不能为空");
        iEntryConfigService.deleteEntryConfig(entryConfigId);
    }

    /**
    * 保存编辑后的配置
    * @param entryConfig
    */
    @PostMapping("/modify")
    public void modify(@RequestBody EntryConfig entryConfig) {
        iEntryConfigService.updateByParam(entryConfig);
    }

    /**
    * 分页条件查询
    * @param entryConfig
    * @return
    */
    @PostMapping("/listPageByParam")
    public PageInfo<EntryConfig> listPageByParam(@RequestBody EntryConfig entryConfig) {
        Assert.notNull(entryConfig, "entryConfig不能为空");
        return new PageInfo<>(iEntryConfigService.listPageByParam(entryConfig));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<EntryConfig> listAll() {
        return iEntryConfigService.list();
    }

    /**
     * 编辑或保存
     * @param entryConfig
     */
    @PostMapping("/saveOrUpdateEntryConfig")
    public void saveOrUpdateEntryConfig(@RequestBody EntryConfig entryConfig) {
        iEntryConfigService.saveOrUpdateEntryConfig(entryConfig);
    }

    /**
     * 根据供方准入类型获取引入流程配置对象数据
     * @param quaReviewType 供方准入类型,参考字典码QUA_REVIEW_TYPE
     * @return EntryConfig
     */
    @GetMapping("/getEntryConfigByQuaReviewType")
    public EntryConfig getEntryConfigByQuaReviewType(String quaReviewType) {
        Assert.notNull(quaReviewType, "资质审查类型为空");
        return iEntryConfigService.getEntryConfigByQuaReviewType(quaReviewType);
    }

    /**
     * 根据供方准入类型获取引入流程配置对象数据(ceea)
     * @param quaReviewType 供方准入类型,参考字典码QUA_REVIEW_TYPE
     * @return
     */
    @PostMapping("/listEntryConfigByQuaReviewType")
    public List<EntryConfig> listEntryConfigByQuaReviewType(String quaReviewType) {
        Assert.notNull(quaReviewType, "资质审查类型为空");
        return iEntryConfigService.listEntryConfigByQuaReviewType(quaReviewType);
    }

    /**
     * (编辑)维护品类
     * @param
     */
    @PostMapping("/saveOrUpdateEntryCategoryConfig")
    public void saveOrUpdateEntryCategoryConfig(@RequestBody EntryConfig entryConfig) {
        iEntryConfigService.saveOrUpdateEntryCategoryConfig(entryConfig);
    }

    /**
     * 查询当前头表的品类记录
     * @param
     */
    @PostMapping("/listEntryCategoryConfig")
    public List<EntryCategoryConfig> listEntryCategoryConfig(@RequestParam("entryConfigId") Long entryConfigId) {
        Assert.notNull(entryConfigId, "entryId为空！");
        return iEntryConfigService.listEntryCategoryConfig(entryConfigId);
    }

    /**
     * 根据传入的供方准入类型和品类Id查询当前的entryConfig实体
     */
    @GetMapping("/getEntryConfigByTypeAndCategoryId")
    public EntryConfig getEntryConfigByTypeAndCategoryId(@RequestParam("quaReviewType") String quaReviewType,
                                                         @RequestParam("categoryId") Long categoryId) {
        Assert.notNull(quaReviewType, "传入的供方准入类型quaReviewType为空！");
        Assert.notNull(categoryId, "传入的品类categoryId为空！");
        return iEntryConfigService.getEntryConfigByTypeAndCategoryId(quaReviewType, categoryId);
    }

    /**
     * 批量配置（导入用，请忽略）
     * @modified xiexh12@meicloud.com
     */
    @PostMapping("/importEntryConfigs")
    public void importEntryConfigs(){
        iEntryConfigService.importEntryConfigNewVendor();
    }
    
    /**
     * 获取引入向导
     * @param type
     * @param categoryId
     * @return
     */
    @GetMapping("/getEntryGuide")
    public List<EntryConfigTitleDTO> getTitle(@RequestParam String quaReviewType,@RequestParam Long categoryId) {
    	QueryWrapper qw = new QueryWrapper();
    	qw.eq("QUA_REVIEW_TYPE", quaReviewType);
    	List<EntryConfig> list =  this.iEntryConfigService.list(qw);
    	if (null != list && list.size() > 0) {
    		qw = new QueryWrapper();
        	qw.eq("CATEGORY_ID", categoryId);
        	List<Long> ids = new ArrayList<Long>();
        	for (EntryConfig entryConfig:list) {
        		ids.add(entryConfig.getEntryConfigId());
        	}
        	qw.in("ENTRY_CONFIG_ID", ids);
        	EntryCategoryConfig category = iEntryCategoryConfigService.getOne(qw);
        	
        	if (null != category) {
        		EntryConfig entryConfig = iEntryConfigService.getById(category.getEntryConfigId());
        		return this.getEntryGuide(entryConfig);
        	} else {
        		throw new BaseException("没有该品类对及准入类型对应的记录");
        	}
    	} else {
    		throw new BaseException("没有该准入类型的记录");
    	}
    }
    
    /**
     * 获取资质审查供应商准入导航
     * @param entryConfig
     * @return
     */
    private List<EntryConfigTitleDTO> getEntryGuide(EntryConfig entryConfig) {
    	List<EntryConfigTitleDTO> list = new ArrayList<EntryConfigTitleDTO>();
    	EntryConfigTitleDTO dto = new EntryConfigTitleDTO();
    	dto.setStatus("DRAFT");
    	dto.setType("REVIEW");
    	list.add(dto);
    	if ("Y".equals(entryConfig.getIfAuth())) {
    		dto = new EntryConfigTitleDTO();
        	dto.setStatus("");
        	dto.setType("AUTH");
        	list.add(dto);
    	}
    	if ("Y".equals(entryConfig.getIfAuthSample())) {
    		dto = new EntryConfigTitleDTO();
        	dto.setStatus("");
        	dto.setType("SAMPLE");
        	list.add(dto);
    	}
    	if ("Y".equals(entryConfig.getIfMaterial())){
    		dto = new EntryConfigTitleDTO();
        	dto.setStatus("");
        	dto.setType("MATERIAL");
        	list.add(dto);
    	}
    	return list;
    }
    
    
    /**
     * 获取引入向导
     * @param type
     * @param categoryId
     * @return
     */
    @GetMapping("/getEntryGuideHis")
    public List<EntryConfigTitleDTO> getEntryGuideHis(@RequestParam Long businessId,@RequestParam String type) {
    	return this.getEntryGuide(this.iEntryConfigRecordService.getRecord(businessId, type));
    }
    
    /**
     * 获取资质审查供应商准入导航
     * @param entryConfig
     * @return
     */
    private List<EntryConfigTitleDTO> getEntryGuide(EntryConfigRecord entryConfig) {
    	List<EntryConfigTitleDTO> list = new ArrayList<EntryConfigTitleDTO>();
    	EntryConfigTitleDTO dto = new EntryConfigTitleDTO();
    	
    	if (null != entryConfig.getReviewFormId()) {
    		ReviewForm review = iReviewFormService.getById(entryConfig.getReviewFormId());
    		dto.setStatus(review.getApproveStatus());
		} else {
			dto.setStatus("DRAFT");
		}
    	dto.setType("REVIEW");
    	list.add(dto);
    	return list;
    }
    
    /**
     * 根据供方准入类型获取引入流程配置对象数据
     * @param quaReviewType 供方准入类型,参考字典码QUA_REVIEW_TYPE
     * @return EntryConfig
     */
    @GetMapping("/getEntryConfigRecord")
    public EntryConfigRecord getEntryConfigRecord(Long reviewFormId) {
        Assert.notNull(reviewFormId, "资质审查ID为空");
        return iEntryConfigRecordService.getRecord(reviewFormId);
    }
}
