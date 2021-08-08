package com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.bargaining.projectmanagement.signupmanagement.VendorFileType;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.mapper.BidVendorFileMapper;
import com.midea.cloud.srm.bargaining.suppliercooperate.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.entity.BidVendorFile;
import com.midea.cloud.srm.model.bargaining.suppliercooperate.vo.BidVendorFileVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <pre>
 *  投标报名附件表(供应商端) 服务实现类
 * </pre>
 *
 * @author zhuomb1@midea.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-04-06 15:06:23
 *  修改内容:
 * </pre>
 */
@Service
@Slf4j
public class BidVendorFileServiceImpl extends ServiceImpl<BidVendorFileMapper, BidVendorFile> implements IBidVendorFileService {

    @Override
    public List<BidVendorFileVO> getVendorFileList(BidVendorFileVO vendorFileVO) {
        if(VendorFileType.ENROLL.getValue().equals(vendorFileVO.getFileType())){
            return this.getBaseMapper().getVendorFileListForSignUp(vendorFileVO);
        }
        return this.getBaseMapper().getVendorFileList(vendorFileVO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveBatchVendorFilesByBusinessIdAndFileType(List<BidVendorFileVO> vendorFileVOs) {
        if (CollectionUtils.isEmpty(vendorFileVOs)) {
            return true;
        }
        List addList = new ArrayList();
        List updateList = new ArrayList();
        //找出数据库已有的，但是这里没有的，把他删除
       BidVendorFileVO bidVendorFileVO = vendorFileVOs.get(0);
        Long bidingId = bidVendorFileVO.getBidingId();
        Long vendorId = bidVendorFileVO.getVendorId();
        List<Long> collectIds = list(Wrappers.lambdaQuery(BidVendorFile.class)
                .eq(BidVendorFile::getBidingId, bidingId)
                .eq(BidVendorFile::getVendorId, vendorId)
        ).stream().map(BidVendorFile::getVendorFileId).collect(Collectors.toList());
        for (int i = collectIds.size() - 1; i >= 0; i--) {
            boolean delete = true;
            for (BidVendorFileVO vendorFileVO : vendorFileVOs) {
                if (Objects.equals(vendorFileVO.getVendorFileId(), collectIds.get(i))) {
                    delete = false;
                    break;
                }
            }
            if (delete) {
                collectIds.remove(i);
            }
        }
        for (BidVendorFileVO vendorFileVO : vendorFileVOs){
            Assert.notNull(vendorFileVO.getBusinessId(),"业务单ID不能为空");
            Assert.notNull(vendorFileVO.getFileType(),"文件类型不能为空");
            boolean addVendorFile = (null == vendorFileVO.getVendorFileId() || vendorFileVO.getVendorFileId()==0);
            BidVendorFile vendorFile = new BidVendorFile();
            BeanCopyUtil.copyProperties(vendorFile,vendorFileVO);
            if(addVendorFile){
                Long id = IdGenrator.generate();
                vendorFile.setVendorFileId(id);
                addList.add(vendorFile);
            }else{
                //更新数据
                updateList.add(vendorFile);
            }
        }
        if(addList.size()>0){
           this.saveBatch(addList);
        }
        if(updateList.size()>0){
            this.updateBatchById(updateList);
        }
        if(!CollectionUtils.isEmpty(collectIds)){
            this.removeByIds(collectIds);
        }
        return true;
    }
}
