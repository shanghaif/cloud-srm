package com.midea.cloud.srm.inq.quote.controller;

import com.alibaba.fastjson.JSON;
import com.midea.cloud.common.utils.DateUtil;
import com.midea.cloud.common.utils.EasyExcelUtil;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.inq.quote.service.IQuoteLadderPriceService;
import com.midea.cloud.srm.inq.quote.service.IQuoteSelectionService;
import com.midea.cloud.srm.model.inq.quote.vo.QuoteSelectionExcelVO;
import com.midea.cloud.srm.model.base.dict.dto.DictItemDTO;
import com.midea.cloud.srm.model.inq.quote.dto.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *  功能名称
 * </pre>
 *
 * @author linxc6@meicloud.com
 * @version 1.00.00
 * <p>
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-3-24 15:12
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/quote/selection")
public class QuoteSelectionController {

    @Autowired
    private IQuoteSelectionService iQuoteSelectionService;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private IQuoteLadderPriceService iQuoteLadderPriceService;
    @Autowired
    private BaseClient baseClient;

    /**
     * 报价评选-价格计算
     */
    @RequestMapping
    public List<QuoteSelectionQueryResponseDTO> getAllEffectiveQuoteItems(Long inquiryId) {
        Assert.notNull(inquiryId, "询价单ID不能为空");

        String key = getKey(inquiryId);
        /*查询数据库并计算*/
        Map<Long, List<QuoteSelectionQueryResponseDTO>> map = iQuoteSelectionService.quoteSelect(inquiryId);
        /*把分组map转化为list前端输出*/
        List<QuoteSelectionQueryResponseDTO> response = converMapToList(map);
        /*把查询和计算结果存到redis中*/
        setReponseInRedis(response, key);

        return response;
    }

    /**
     * 报价评选-评分
     */
    @RequestMapping("/score")
    public List<QuoteSelectionQueryResponseDTO> calculateScore(Long inquiryId) {
        Assert.notNull(inquiryId, "询价单ID不能为空");

        List<QuoteSelectionQueryResponseDTO> stroeInRedis;
        String key = getKey(inquiryId);
        if (redisTemplate.hasKey(key)) {
            stroeInRedis = getReponseInRedis(key);
        }else {
            /*重新查询一次并缓存起来*/
            stroeInRedis = getAllEffectiveQuoteItems(inquiryId);
        }

        Map<Long, List<QuoteSelectionQueryResponseDTO>> resultMap = iQuoteSelectionService.calculateScore(inquiryId, stroeInRedis);
        /*把分组map转化为list前端输出*/
        List<QuoteSelectionQueryResponseDTO> response = converMapToList(resultMap);
        /*把查询和计算结果存到redis中*/
        setReponseInRedis(response, key);
        return response;
    }

    /**
     * 报价评选-阶梯价比价
     */
    @RequestMapping("/ladderPrice")
    public List<LadderPriceCompareResponseDTO> compareLadderPrice(Long inquiryId, Long quoteItemId) {
        Assert.notNull(inquiryId, "询价单ID不能为空");
        Assert.notNull(quoteItemId, "报价行ID不能为空");

        String key = getKey(inquiryId);

        List<QuoteSelectionQueryResponseDTO> stroeInRedis;
        if (redisTemplate.hasKey(key)) {
            /*从缓存里拿出报价行信息*/
            stroeInRedis = getReponseInRedis(key);
        }else {
            /*重新查询一次并缓存起来*/
            stroeInRedis = getAllEffectiveQuoteItems(inquiryId);
        }

        return iQuoteLadderPriceService.compareLadderPrice(quoteItemId, stroeInRedis);
    }

    /**
     * 保存报价评选
     */
    @RequestMapping("/save")
    public void saveQuoteSelection(Long inquiryId) {

        Assert.notNull(inquiryId, "询价单ID不能为空");
        iQuoteSelectionService.saveQuoteSelection(inquiryId);
    }

    /**
     * 继续议价查询
     */
    @PostMapping("/bargaining")
    public QuoteBargainingQueryResponseDTO getQuoteBargaining(@RequestBody QuoteBargainingQueryRequestDTO request) {

        Assert.notNull(request.getInquiryId(), "询价单ID不能为空");
        Assert.notNull(request.getQuoteItemIds(), "报价行信息不能为空");

        String key = getKey(request.getInquiryId());

        List<QuoteSelectionQueryResponseDTO> stroeInRedis;
        if (redisTemplate.hasKey(key)) {
            /*从缓存里拿出报价行信息*/
            stroeInRedis = getReponseInRedis(key);
        }else {
            /*重新查询一次并缓存起来*/
            stroeInRedis = getAllEffectiveQuoteItems(request.getInquiryId());
        }

        return iQuoteSelectionService.getQuoteBargaining(request, stroeInRedis);
    }

    /**
     * 继续议价保存
     */
    @PutMapping("/bargaining")
    public void getQuoteBargaining(@RequestBody QuoteBargainingSaveRequestDTO request) {

        Assert.notNull(request.getInquiryId(), "询价单ID不能为空");
        Assert.notNull(request.getQuoteItemIds(), "选择的报价行不能为空");
        Assert.notNull(request.getItems(), "物料信息不能为空");
        Assert.notNull(request.getVendors(), "供应商信息不能为空");
        iQuoteSelectionService.saveBargaining(request);
    }

    /**
     * 评选导出
     */
    @RequestMapping("/excel")
    public void exportExcel(Long inquiryId, HttpServletResponse response) throws Exception {
        Assert.notNull(inquiryId, "询价单ID不能为空");

        List<QuoteSelectionQueryResponseDTO> stroeInRedis;
        String key = getKey(inquiryId);
        if (redisTemplate.hasKey(key)) {
            stroeInRedis = getReponseInRedis(key);
        }else {
            /*重新查询一次并缓存起来*/
            stroeInRedis = getAllEffectiveQuoteItems(inquiryId);
        }

        List<QuoteSelectionExcelVO> excel = convertToExcelVO(stroeInRedis);
        byte[] buffer = EasyExcelUtil.writeExcelWithModel(excel, QuoteSelectionExcelVO.class);

        String fileName = "评选比价信息.xls";
        response.setContentType("application/vnd.ms-excel");
        response.setCharacterEncoding("utf-8");
        fileName = URLEncoder.encode(fileName, "UTF-8");
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.getOutputStream().write(buffer);
        response.getOutputStream().close();
    }

    private List<QuoteSelectionExcelVO> convertToExcelVO(List<QuoteSelectionQueryResponseDTO> stroeInRedis) {
        //询价结果
        List<DictItemDTO> rfqResult = baseClient.listAllByDictCode("RFQ_RESULT");

        List<QuoteSelectionExcelVO> excel = new ArrayList<>();
        stroeInRedis.forEach(selection -> {
            DictItemDTO item = rfqResult.stream().filter(f ->
                    f.getDictItemCode().equals(selection.getIsSelected())).findFirst().orElse(null);

            QuoteSelectionExcelVO excelVO = new QuoteSelectionExcelVO();
            BeanUtils.copyProperties(selection, excelVO);
            excelVO.setIsSelected(item == null ? selection.getIsSelected() : item.getDictItemName());
            excelVO.setFixedPriceBegin(DateUtil.parseDateToStr(selection.getFixedPriceBegin(), DateUtil.YYYY_MM_DD));
            excelVO.setFixedPriceEnd(DateUtil.parseDateToStr(selection.getFixedPriceEnd(), DateUtil.YYYY_MM_DD));
            excel.add(excelVO);
        });
        return excel;
    }

    /**
     *
     */
    private List<QuoteSelectionQueryResponseDTO> converMapToList(Map<Long, List<QuoteSelectionQueryResponseDTO>> response) {

        List<QuoteSelectionQueryResponseDTO> temp = new ArrayList<>();
        response.forEach((k, v) -> {
            temp.addAll(v);
        });
        return temp;
    }

    /**
     * 从redis中查询计算结果
     */
    private List<QuoteSelectionQueryResponseDTO> getReponseInRedis(String key) {

        String value = redisTemplate.opsForValue().get(key);
        List<QuoteSelectionQueryResponseDTO> response = JSON.parseArray(value, QuoteSelectionQueryResponseDTO.class);
        Assert.notNull(response, "获取计算结果失败");
        return response;
    }

    /**
     * 查询结果放到redis中
     */
    private void setReponseInRedis(List<QuoteSelectionQueryResponseDTO> reponseInRedis, String key) {
        String jsonString = JSON.toJSONString(reponseInRedis);
        redisTemplate.opsForValue().set(key, jsonString, 5, TimeUnit.MINUTES);
    }

    /**
     * 获取key
     */
    private String getKey(Long inquiryId) {
        return "scc_quote_selection:" + inquiryId;
    }
}