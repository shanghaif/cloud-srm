package com.midea.cloud.srm.supauth.entry.controller;

import java.util.ArrayList;
import java.util.List;

import com.midea.cloud.srm.model.supplierauth.entry.dto.EntryFileAndCategoryDTO;
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
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplierauth.entry.dto.EntryFileConfigDTO;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryCategoryConfig;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfig;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryConfigRecord;
import com.midea.cloud.srm.model.supplierauth.entry.entity.EntryFileConfig;
import com.midea.cloud.srm.model.supplierauth.quasample.entity.QuaSample;
import com.midea.cloud.srm.supauth.entry.service.IEntryCategoryConfigService;
import com.midea.cloud.srm.supauth.entry.service.IEntryConfigRecordService;
import com.midea.cloud.srm.supauth.entry.service.IEntryConfigService;
import com.midea.cloud.srm.supauth.entry.service.IEntryFileConfigService;
import com.midea.cloud.srm.supauth.quasample.service.IQuaSampleService;

/**
 * <pre>
 *  准入附件配置 前端控制器
 * </pre>
 *
 * @author kuangzm
 * @version 1.00.00
 *
 *          <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Jan 21, 2021 2:15:47 PM
 *  修改内容:
 *          </pre>
 */
@RestController
@RequestMapping("/entry/fileconfig")
public class EntryFileConfigController extends BaseController {

	@Autowired
	private IEntryFileConfigService iEntryFileConfigService;

	@Autowired
	private IEntryConfigRecordService iEntryConfigRecordService;
	
	@Autowired
    private IEntryCategoryConfigService iEntryCategoryConfigService;
	
	@Autowired
    private IEntryConfigService iEntryConfigService;

	@Autowired
    private IQuaSampleService iQuaSampleService;
	
	/**
	 * 获取
	 * 
	 * @param id
	 */
	@GetMapping("/get")
	public EntryFileConfig get(Long id) {
		Assert.notNull(id, "id不能为空");
		return iEntryFileConfigService.getById(id);
	}

	/**
	 * 新增
	 * 
	 * @param entryFileConfig
	 */
	@PostMapping("/add")
	public void add(@RequestBody EntryFileConfig entryFileConfig) {
		Long id = IdGenrator.generate();
		entryFileConfig.setFileConfigId(id);
		iEntryFileConfigService.save(entryFileConfig);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 */
	@GetMapping("/delete")
	public void delete(Long id) {
		Assert.notNull(id, "id不能为空");
		iEntryFileConfigService.removeById(id);
	}

	/**
	 * 修改
	 * 
	 * @param entryFileConfig
	 */
	@PostMapping("/modify")
	public void modify(@RequestBody EntryFileConfig entryFileConfig) {
		iEntryFileConfigService.updateById(entryFileConfig);
	}

	/**
	 * 分页查询
	 * 
	 * @param entryFileConfig
	 * @return
	 */
	@PostMapping("/listPage")
	public PageInfo<EntryFileConfig> listPage(@RequestBody EntryFileConfig entryFileConfig) {
		PageUtil.startPage(1, 10);
		QueryWrapper<EntryFileConfig> wrapper = new QueryWrapper<EntryFileConfig>(entryFileConfig);
		PageInfo pageInfo = new PageInfo<EntryFileConfig>(iEntryFileConfigService.list(wrapper));

		return pageInfo;
	}

	/**
	 * 查询所有
	 * 
	 * @return
	 */
	@PostMapping("/listAll")
	public List<EntryFileConfig> listAll() {
		return iEntryFileConfigService.list();
	}

	/**
	 * 新增
	 * 
	 * @param dto
	 */
	@PostMapping("/batchSave")
	public void batchSave(@RequestBody EntryFileConfigDTO dto) {
		Assert.notNull(dto.getEntryConfigId(), "准入配置ID不能为空");
		Assert.notNull(dto.getList(), "准入配置附件不能为空");
		iEntryFileConfigService.batchSave(dto.getEntryConfigId(), dto.getList());
	}

	/**
	 * 根据头表Id查询附件
	 * 
	 */
	@GetMapping("/getFileListByEntryId")
	public PageInfo<EntryFileConfig> getFileListByEntryId(@RequestParam("entryConfigId") Long entryConfigId) {
		Assert.notNull(entryConfigId, "entryId为空！");
		QueryWrapper<EntryFileConfig> wrapper = new QueryWrapper<EntryFileConfig>();
		wrapper.eq("ENTRY_CONFIG_ID", entryConfigId);
		return new PageInfo<>(iEntryFileConfigService.list(wrapper));
	}

	/**
	 * 根据头表Id查询附件
	 * 
	 */
	@GetMapping("/getTemplateFiles")
	public PageInfo<EntryFileConfig> getTemplateFiles(@RequestParam("entryConfigId") Long entryConfigId,
			@RequestParam("type") String type) {
		Assert.notNull(entryConfigId, "entryId为空！");
		QueryWrapper<EntryFileConfig> wrapper = new QueryWrapper<EntryFileConfig>();
		wrapper.eq("ENTRY_CONFIG_ID", entryConfigId);
		wrapper.eq("TYPE", type);
		return new PageInfo<>(iEntryFileConfigService.list(wrapper));
	}
	
	/**
     * 资质审查创建时根据 类型及品类获取附件模板
     * @param quaReviewType
     * @param categoryId
     * @return
     */
    @GetMapping("/getTemplateFilesByReviewCreate")
    public EntryFileAndCategoryDTO getTemplateFilesByReviewCreate(@RequestParam String quaReviewType, @RequestParam Long categoryId) {
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
			EntryFileAndCategoryDTO entryFileAndCategoryDTO = new EntryFileAndCategoryDTO();
			entryFileAndCategoryDTO.setEntryCategoryConfig(category);

        	if (null != category) {
        		EntryConfig entryConfig = iEntryConfigService.getById(category.getEntryConfigId());
        		qw = new QueryWrapper();
        		qw.eq("ENTRY_CONFIG_ID", entryConfig.getEntryConfigId());
        		qw.eq("TYPE", "REVIEW");
        		//获取附件模板
				List<EntryFileConfig> entryFileConfigList = iEntryFileConfigService.list(qw);
				entryFileAndCategoryDTO.setEntryFileConfigList(entryFileConfigList);
				return entryFileAndCategoryDTO;
        	} else {
        		return null;
        	}
    	} else {
    		return null;
    	}
    }
    
    /**
     * 其他类型创建时根据资质审查单号获取附件配置
     * @param type
     * @param reviewFormId
     * @return
     */
    @GetMapping("/getTemplateFilesByReviewFormId")
    public List<EntryFileConfig> getTemplateFilesByReviewFormId(@RequestParam Long reviewFormId,@RequestParam String type) {
    	Assert.notNull(reviewFormId, "reviewFormId为空！");
    	Assert.notNull(type, "type为空！");
    	//查询准入记录
    	QueryWrapper<EntryConfigRecord> qw = new QueryWrapper<EntryConfigRecord>();
    	qw.eq("REVIEW_FORM_ID", reviewFormId);
    	EntryConfigRecord record = iEntryConfigRecordService.getOne(qw);
    	Assert.notNull(record, "准入记录为空！");
    	//查询模板列表
    	QueryWrapper<EntryFileConfig> qf = new QueryWrapper<EntryFileConfig>();
    	qf.eq("ENTRY_CONFIG_ID", record.getEntryConfigId());
    	qf.eq("TYPE", type);
		return this.iEntryFileConfigService.list(qf);
    }
    
    /**
     * 其他类型创建时根据资质审查单号获取附件配置
     * @param sampleId
     * @return
     */
    @GetMapping("/getTemplateFilesBySampleId")
    public List<EntryFileConfig> getTemplateFilesBySampleId(@RequestParam Long sampleId) {
    	Assert.notNull(sampleId, "sampleId为空！");
    	QuaSample quaSample = iQuaSampleService.getById(sampleId);
    	if (null != quaSample && null != quaSample.getReviewFormId()) {
    		//查询准入记录
        	QueryWrapper<EntryConfigRecord> qw = new QueryWrapper<EntryConfigRecord>();
        	qw.eq("REVIEW_FORM_ID", quaSample.getReviewFormId());
        	EntryConfigRecord record = iEntryConfigRecordService.getOne(qw);
        	Assert.notNull(record, "准入记录为空！");
        	//查询模板列表
        	QueryWrapper<EntryFileConfig> qf = new QueryWrapper<EntryFileConfig>();
        	qf.eq("ENTRY_CONFIG_ID", record.getEntryConfigId());
        	qf.eq("TYPE", "MATERIAL");
    		return this.iEntryFileConfigService.list(qf);
    	}
		return null;
    }
}
