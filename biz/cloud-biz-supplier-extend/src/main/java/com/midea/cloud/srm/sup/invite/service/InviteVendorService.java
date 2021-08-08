package com.midea.cloud.srm.sup.invite.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.supplier.invite.entity.InviteVendor;

import java.util.List;
import java.io.IOException;

/**
 * <pre>
 *  邀请供应商 服务类
 * </pre>
 *
 * @author dengbin1@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: Apr 30, 2021 9:52:55 AM
 *  修改内容:
 * </pre>
 */
public interface InviteVendorService extends IService<InviteVendor> {
    /*
    批量更新或者批量新增
    */
    void batchSaveOrUpdate(List<InviteVendor> inviteVendorList) throws IOException;

    /*
     批量删除
     */
    void batchDeleted(List<Long> ids) throws IOException;

    /*
   分页查询
    */
    PageInfo<InviteVendor> listPage(InviteVendor inviteVendor);

    /****
     * 发布邀请
     * @param inviteVendor
     */
    void publishInviteVendor(InviteVendor inviteVendor);

    /****
     * 更新邀请供应商注册状态
     */
    void updateInviteStatus();

    /****
     * 新增数据
     * @param inviteVendor
     */
    void addNewData(InviteVendor inviteVendor);

    /****
     * 发送邮件
     * @param inviteVendor
     * @param inviteVendorNo
     */
    String sendEmail(InviteVendor inviteVendor, String inviteVendorNo);
}
