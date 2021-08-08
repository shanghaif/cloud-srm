package com.midea.cloud.srm.sup.info.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.entity.ContactInfo;
import com.midea.cloud.srm.sup.info.service.IContactInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
*  <pre>
 *  联系人信息 前端控制器
 * </pre>
*
* @author zhuwl7@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-13 09:13:33
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/info/contactInfo")
public class ContactInfoController extends BaseController {

    @Autowired
    private IContactInfoService iContactInfoService;

    /**
    * 获取
    * @param id
    */
    @GetMapping("/get")
    public ContactInfo get(Long id) {
        Assert.notNull(id, "id不能为空");
        return iContactInfoService.getById(id);
    }

    /**
    * 新增
    * @param contactInfo
    */
    @PostMapping("/add")
    public void add(@RequestBody ContactInfo contactInfo) {
        Long id = IdGenrator.generate();
        contactInfo.setContactInfoId(id);
        iContactInfoService.save(contactInfo);
    }

    /**
    * 删除
    * @param contactInfoId
    */
    @GetMapping("/delete")
    public void delete(Long contactInfoId) {
        Assert.notNull(contactInfoId, "contactInfoId不能为空");
        iContactInfoService.removeById(contactInfoId);
    }

    /**
    * 修改
    * @param contactInfo
    */
    @PostMapping("/modify")
    public void modify(@RequestBody ContactInfo contactInfo) {
        iContactInfoService.updateById(contactInfo);
    }

    /**
    * 分页查询
    * @param contactInfo
    * @return
    */
    @PostMapping("/listPage")
    public PageInfo<ContactInfo> listPage(@RequestBody ContactInfo contactInfo) {
        PageUtil.startPage(contactInfo.getPageNum(), contactInfo.getPageSize());
        QueryWrapper<ContactInfo> wrapper = new QueryWrapper<ContactInfo>(contactInfo);
        return new PageInfo<ContactInfo>(iContactInfoService.list(wrapper));
    }

    /**
    * 查询所有
    * @return
    */
    @PostMapping("/listAll")
    public List<ContactInfo> listAll() {
        return iContactInfoService.list();
    }

    /**
     * 根据条件获取联系人信息
     *
     * 隆基版本：供应商与联系人由以前的1对1关系更改为1对多关系
     *
     * @param contactInfo
     */
    @PostMapping("/getContactInfoByParam")
    public ContactInfo getContactInfoByParam(@RequestBody ContactInfo contactInfo) {
        Assert.notNull(contactInfo, "查询对象不能为空");
//        return iContactInfoService.getOne(new QueryWrapper<>(contactInfo));
        List<ContactInfo> contactInfos = iContactInfoService.list(new QueryWrapper<>(contactInfo));
        if (contactInfos.isEmpty())
            return null;
        return contactInfos.stream()
                .filter(info -> "Y".equals(info.getCeeaDefaultContact()))   // 优先取默认联系人
                .findAny()
                .orElseGet(() -> contactInfos.get(0));  // 若没有默认联系人，则取第一个联系人
    }

    /**
     * 根据companyId查询供应商联系人信息 只返回一个联系人结果
     * @param companyId
     */
    @PostMapping("/getContactInfoByCompanyId")
    public ContactInfo getContactInfoByCompanyId(@RequestParam("companyId") Long companyId) {
        Assert.notNull(companyId, "根据供应商Id查询供应商信息时供应商Id为空！");
        return iContactInfoService.getContactInfoByCompanyId(companyId);
    }

    /**
     * 根据供应商id列表获取联系人信息列表
     * @param vendorIdList
     */
    @PostMapping("/listContactInfoByParam")
    List<ContactInfo> listContactInfoByParam(@RequestBody List<Long> vendorIdList) {
        return iContactInfoService.listContactInfoByParam(vendorIdList);
    }

    /**
     * @Description
     * @Param [companyId]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.25 14:08
     **/
    @GetMapping("/listContactInfoByCompanyId")
    List<ContactInfo> listContactInfoByCompanyId(@RequestParam("companyId") Long companyId) {
        return iContactInfoService.getByCompanyId(companyId);
    }
}
