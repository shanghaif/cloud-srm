package com.midea.cloud.srm.base.notice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.base.notice.mapper.NoticeMapper;
import com.midea.cloud.srm.base.notice.service.INoticeService;
import com.midea.cloud.srm.base.notice.service.INoticeVendorService;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.feign.supplier.SupplierClient;
import com.midea.cloud.srm.model.base.notice.dto.NoticeDetailDTO;
import com.midea.cloud.srm.model.base.notice.dto.NoticeRequestDTO;
import com.midea.cloud.srm.model.base.notice.dto.NoticeSaveRequestDTO;
import com.midea.cloud.srm.model.base.notice.entry.Notice;
import com.midea.cloud.srm.model.base.notice.entry.NoticeVendor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 *  公告表 服务实现类
 * </pre>
 *
 * @author huangbf3
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020/4/8 16:45
 *  修改内容:
 * </pre>
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, Notice> implements INoticeService {
    @Autowired
    private INoticeVendorService iNoticeVendorService;
    @Autowired
    private FileCenterClient fileCenterClient;
    @Autowired
    private SupplierClient supplierClient;
    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    @Transactional
    public void delete(Long noticeId) {
        Notice notice = this.getById(noticeId);
        this.removeById(noticeId);

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("NOTICE_ID",noticeId);
        iNoticeVendorService.remove(queryWrapper);

        //删除文件
        if(notice.getFileRelationId()!=null){
            try{
                fileCenterClient.delete(notice.getFileRelationId());
            }catch(Exception e){
                Assert.isTrue(false,"文件刪除失败");
            }
        }
    }

    @Override
    public List listPage(NoticeRequestDTO noticeRequestDTO) {
        return noticeMapper.findList(noticeRequestDTO);
    }

    @Override
    public NoticeDetailDTO getDetail(Long noticeId) {
    	NoticeDetailDTO dto = noticeMapper.getDetail(noticeId);
    	List<NoticeVendor> vendors = new ArrayList<NoticeVendor>();
    	if (null != dto && dto.getNoticeVendors().size() > 0) {
    		for (NoticeVendor vendor : dto.getNoticeVendors()) {
    			if (null != vendor.getNoticeVendorId()) {
    				vendors.add(vendor);
    			}
    		}
    		dto.setNoticeVendors(vendors);
    	}
        return dto;
    }

    @Override
    @Transactional
    public void saveOrUpdate(NoticeSaveRequestDTO noticeSaveRequestDTO) {
        if(noticeSaveRequestDTO.getNotice().getNoticeId()==null){
            noticeSaveRequestDTO.getNotice().setNoticeId(IdGenrator.generate());
            this.save(noticeSaveRequestDTO.getNotice());
        }else{
            this.updateById(noticeSaveRequestDTO.getNotice());
            if(noticeSaveRequestDTO.getDeleteNoticeVendorIds()!=null&& noticeSaveRequestDTO.getDeleteNoticeVendorIds().size()>0){
                iNoticeVendorService.removeByIds(noticeSaveRequestDTO.getDeleteNoticeVendorIds());
            }
        }

        if(noticeSaveRequestDTO.getNoticeVendors()!=null&& noticeSaveRequestDTO.getNoticeVendors().size()>0){
            noticeSaveRequestDTO.getNoticeVendors().forEach(item->{
                if(item.getNoticeVendorId()==null){
                    item.setNoticeVendorId(IdGenrator.generate());
                    item.setNoticeId(noticeSaveRequestDTO.getNotice().getNoticeId());
                    item.setReadStatus("N");
                }
            });
            iNoticeVendorService.saveOrUpdateBatch(noticeSaveRequestDTO.getNoticeVendors());
        }
    }

}
