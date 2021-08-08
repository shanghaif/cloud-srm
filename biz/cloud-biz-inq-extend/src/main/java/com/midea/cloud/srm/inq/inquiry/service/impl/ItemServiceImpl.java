package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.srm.inq.inquiry.mapper.ItemMapper;
import com.midea.cloud.srm.inq.inquiry.service.IHeaderService;
import com.midea.cloud.srm.inq.inquiry.service.IItemService;
import com.midea.cloud.srm.inq.inquiry.service.ILadderPriceService;
import com.midea.cloud.srm.inq.inquiry.service.impl.convertor.ItemTargetPriceConvertor;
import com.midea.cloud.srm.model.common.assist.util.AESUtil;
import com.midea.cloud.srm.model.inq.inquiry.dto.ItemDto;
import com.midea.cloud.srm.model.inq.inquiry.dto.ItemTargetPriceDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.ItemTargetPriceResponseDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.ItemTargetPriceSaveDTO;
import com.midea.cloud.srm.model.inq.inquiry.entity.Header;
import com.midea.cloud.srm.model.inq.inquiry.entity.Item;
import com.midea.cloud.srm.model.inq.inquiry.entity.LadderPrice;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
*  <pre>
 *  询价-询价信息行表 服务实现类
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
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements IItemService {

    @Autowired
    private IHeaderService iHeaderService;
    @Autowired
    private ILadderPriceService iLadderPriceService;

    @Override
    public List<ItemDto> getByHeadId(Long inquiryId) {
        List<ItemDto>  itemDto = new ArrayList<>();
        QueryWrapper warapper = new QueryWrapper();
        warapper.eq("INQUIRY_ID", inquiryId);
        List<Item> list = list(warapper);
        for(Item vo:list){
            ItemDto dto = new ItemDto();
            BeanUtils.copyProperties(vo,dto);
            QueryWrapper ladder = new QueryWrapper();
            ladder.eq("INQUIRY_ITEM_ID", vo.getInquiryItemId());
            List<LadderPrice> listLadder = iLadderPriceService.list(ladder);
            dto.setLadderPrices(listLadder);
            itemDto.add(dto);
        }
        return itemDto;
    }

    @Override
    public void saveItems(List<ItemDto> itemDto) {
        for (ItemDto item:itemDto){
            Item newItem = new Item();
            BeanUtils.copyProperties(item,newItem);
            save(newItem);
            List<LadderPrice> ladderPrices = item.getLadderPrices();
        }
    }

    @Override
    public List<ItemTargetPriceResponseDTO> queryTargetPrice(Long inquiryId) {

        QueryWrapper<Item> warapper = new QueryWrapper<>();
        warapper.eq("INQUIRY_ID", inquiryId);
        List<Item> list = list(warapper);
        return ItemTargetPriceConvertor.convert(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTargetPrice(ItemTargetPriceSaveDTO request) {

        Header header = iHeaderService.getById(request.getInquiryId());
        /*新增前数据校验*/
        checkBeforeAdd(header, request.getItemTargetPrices());

        List<Item> updateList = new ArrayList<>();
        request.getItemTargetPrices().forEach(itemTargetPriceSaveDTO -> {
            Item entity = new Item();
            entity.setInquiryItemId(itemTargetPriceSaveDTO.getInquiryItemId());
            /*目标价加密*/
            entity.setNotaxTargrtPrice(AESUtil.encrypt(itemTargetPriceSaveDTO.getNotaxTargrtPrice()));
            updateList.add(entity);
        });

        updateBatchById(updateList);
        header.setIsTargetPriceEncry(YesOrNo.YES.getValue());
        iHeaderService.updateById(header);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void decryptTargetPrice(Long inquiryId) {

        Header header = iHeaderService.getById(inquiryId);
        if (!YesOrNo.YES.getValue().equals(header.getIsTargetPriceEncry())) {
            throw new BaseException("目标价未设置");
        }

        QueryWrapper<Item> wrapper = new QueryWrapper<>();
        wrapper.eq("INQUIRY_ID", inquiryId);
        List<Item> items = list(wrapper);

        items.forEach(item -> item.setNotaxTargrtPrice(AESUtil.decrypt(item.getNotaxTargrtPrice())));

        header.setIsTargetPriceEncry(YesOrNo.NO.getValue());
        updateBatchById(items);
        iHeaderService.updateById(header);
    }

    @Override
    public List<Item> getItemsByIds(List<Long> itemIds) {
        QueryWrapper<Item> warapper = new QueryWrapper<>();
        warapper.in("INQUIRY_ITEM_ID", itemIds);
        return list(warapper);
    }

    /**
     * 新增前数据校验
     */
    private void checkBeforeAdd(Header header, List<ItemTargetPriceDTO> request) {

        if (YesOrNo.NO.getValue().equals(header.getIsTargetPriceEncry())) {
            /*设置过目标价并且已经解密*/
            throw new BaseException("目标价已经设置过并已解密");
        }

        request.forEach(itemTargetPriceSaveDTO -> {
            if (itemTargetPriceSaveDTO.getInquiryItemId() == null) {
                throw new BaseException("寻价行id不能为空");
            }
            if (itemTargetPriceSaveDTO.getNotaxTargrtPrice() == null ||
                    Double.valueOf(itemTargetPriceSaveDTO.getNotaxTargrtPrice()) < 0) {
                throw new BaseException("未税目标价参数有误");
            }
        });
    }
}
