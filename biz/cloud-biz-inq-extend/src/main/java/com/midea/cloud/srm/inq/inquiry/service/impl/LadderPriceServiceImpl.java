package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.srm.inq.inquiry.mapper.LadderPriceMapper;
import com.midea.cloud.srm.inq.inquiry.service.ILadderPriceService;
import com.midea.cloud.srm.model.inq.inquiry.entity.LadderPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
*  <pre>
 *  询价--物料阶梯价表 服务实现类
 * </pre>
*
* @author zhongbh
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
*/
@Service
public class LadderPriceServiceImpl extends ServiceImpl<LadderPriceMapper, LadderPrice> implements ILadderPriceService {

    @Autowired
    private  ILadderPriceService iLadderPriceService;
    @Override
    public void savePrices(List<LadderPrice> ladderPrices) {
        if(ladderPrices.size() > 0){
            Long inquiryItemId = ladderPrices.get(0).getInquiryItemId();
            Long inquiryId = ladderPrices.get(0).getInquiryId();
            QueryWrapper query = new QueryWrapper();
            query.eq("INQUIRY_ITEM_ID",inquiryItemId);
            query.eq("INQUIRY_ID",inquiryId);
            iLadderPriceService.remove(query);
            iLadderPriceService.saveBatch(ladderPrices);
        }
    }

    @Override
    public List<LadderPrice> getLadderPrice(List<Long> inquiryItemIds) {
        QueryWrapper<LadderPrice> itemWrapper = new QueryWrapper<>();
        itemWrapper.in("INQUIRY_ITEM_ID", inquiryItemIds)
                .orderByAsc("BEGIN_QUANTITY").orderByAsc("END_QUANTITY");
        return list(itemWrapper);
    }
}
