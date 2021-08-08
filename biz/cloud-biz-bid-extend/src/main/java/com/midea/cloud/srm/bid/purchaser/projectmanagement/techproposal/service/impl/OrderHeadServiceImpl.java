package com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.constants.SequenceCodeConstant;
import com.midea.cloud.common.enums.FileUploadType;
import com.midea.cloud.common.enums.YesOrNo;
import com.midea.cloud.common.enums.base.FormulaPriceExportTypeEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.bidinitiating.BidType;
import com.midea.cloud.common.enums.bid.projectmanagement.evaluation.OrderStatusEnum;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.SignUpStatus;
import com.midea.cloud.common.enums.bid.projectmanagement.signupmanagement.VendorFileType;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.utils.BeanCopyUtil;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.redis.RedisUtil;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.component.entity.EntityManager;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidRequirementMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidVendorMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.mapper.BidingMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.bidinitiating.service.IBidFileConfigService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderHeadFileMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderHeadMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderLineMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.mapper.OrderlinePaymentTermMapper;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IBidOrderLineFormulaPriceDetailService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IBidOrderLineTemplatePriceDetailService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderHeadService;
import com.midea.cloud.srm.bid.purchaser.projectmanagement.techproposal.service.IOrderLineService;
import com.midea.cloud.srm.bid.suppliercooperate.projectlist.service.IBidVendorFileService;
import com.midea.cloud.srm.feign.base.BaseClient;
import com.midea.cloud.srm.feign.base.IMaterialItemAttributeClient;
import com.midea.cloud.srm.feign.file.FileCenterClient;
import com.midea.cloud.srm.model.base.formula.dto.FormulaPriceDTO;
import com.midea.cloud.srm.model.base.material.vo.MaterialItemAttributeRelateVO;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.bidinitiating.entity.*;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.businessproposal.entity.Round;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.enums.BidingFileTypeEnum;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.enums.RequirementPricingType;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.entity.*;
import com.midea.cloud.srm.model.bid.purchaser.projectmanagement.techproposal.vo.BidOrderLineTemplatePriceDetailVO;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidOrderHeadVO;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidOrderLineVO;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.BidVendorFileVO;
import com.midea.cloud.srm.model.bid.suppliercooperate.vo.SupplierBidingVO;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 * 供应商投标头表
 * </pre>
 *
 * @author zhizhao1.fan@meicloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:tanjl11@meicloud.com
 *  修改日期: 2020-09-08
 *  修改内容:
 *          </pre>
 */
@Service
@Slf4j
public class OrderHeadServiceImpl extends ServiceImpl<OrderHeadMapper, OrderHead> implements IOrderHeadService {
    public static final Integer firstRound = 1;
    @Autowired
    private IOrderLineService iOrderLineService;
    @Autowired
    private IBidVendorFileService iBidVendorFileService;
    @Autowired
    private BaseClient baseClient;
    @Autowired
    private IBidOrderLineTemplatePriceDetailService templatePriceDetailService;
    @Autowired
    private IBidOrderLineFormulaPriceDetailService formulaPriceDetailService;
    @Autowired
    private BidOrderLineFormulaPriceDetailService formulaPriceService;
    @Autowired
    private OrderHeadFileMapper orderHeadFileMapper;
    @Autowired
    private OrderlinePaymentTermMapper paymentTermMapper;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private FileCenterClient fileCenterClient;


    private final EntityManager<Biding> biddingDao
            = EntityManager.use(BidingMapper.class);
    private final EntityManager<BidRequirement> bidRequirementDao
            = EntityManager.use(BidRequirementMapper.class);
    private final EntityManager<BidRequirementLine> bidRequirementLineDao
            = EntityManager.use(BidRequirementLineMapper.class);
    private final EntityManager<BidVendor> bidVendorDao
            = EntityManager.use(BidVendorMapper.class);
    private final EntityManager<OrderLine> orderLineDao
            = EntityManager.use(OrderLineMapper.class);

    @Resource
    private IMaterialItemAttributeClient materialItemAttributeClient;
    @Autowired
    private IBidFileConfigService fileConfigService;


    @Override
    public BidOrderHeadVO getOrderHeadByBidingIdAndBidVerdorId(Long bidingId, Long bidVendorId) {
        return this.getBaseMapper().getOrderHeadByBidingId(bidingId, bidVendorId);
    }

    @Override
    @Transactional
    public Long saveOrderInfo(BidOrderHeadVO bidOrderHeadVO, boolean isSubmit) {
        String key = new StringBuilder("bid-saveOrderInfo").append("-")
                .append(bidOrderHeadVO.getBidingId()).append("-").append(bidOrderHeadVO.getBidVendorId())
                .append("-").append(bidOrderHeadVO.getRoundId()).toString();
        Boolean lock = redisUtil.tryLock(key);
        if (!lock) {
            throw new BaseException("报价信息生成中，请稍后再试.");
        }
        //1 保存投标头表
        try {
            OrderHead orderHead = alreadyOrderHeadByBidingAndBidVendorIdAndRoundId(bidOrderHeadVO.getBidingId(), bidOrderHeadVO.getBidVendorId(), bidOrderHeadVO.getRoundId());
            boolean addOrderHead = (null == orderHead);
            if (addOrderHead) {
                orderHead = new OrderHead();
                String bidOrderNum = baseClient.seqGen(SequenceCodeConstant.SEQ_BID_BIDING_CODE);
                BeanCopyUtil.copyProperties(orderHead, bidOrderHeadVO);
                Long id = IdGenrator.generate();
                orderHead.setOrderHeadId(id);
                orderHead.setBidRoundId(bidOrderHeadVO.getRoundId());
                orderHead.setBidOrderNum(bidOrderNum);

                // 设置 价格类型
                BidRequirement bidRequirement = bidRequirementDao.findOne(
                        Wrappers.lambdaQuery(BidRequirement.class).eq(BidRequirement::getBidingId, bidOrderHeadVO.getBidingId())
                );

                if (bidRequirement == null)
                    throw new BaseException("招标需求头获取失败。 | biddingId: [" + bidOrderHeadVO.getBidingId() + "]");
                orderHead.setPricingType(bidRequirement.getPricingType());
                BidRequirementLine line = bidRequirementLineDao.findOne(
                        Wrappers.lambdaQuery(BidRequirementLine.class)
                                .select(BidRequirementLine::getRequirementLineId, BidRequirementLine::getIsSeaFoodFormula)
                                .eq(BidRequirementLine::getBidingId, bidOrderHeadVO.getBidingId())
                                .last("limit 1")
                );
                bidOrderHeadVO.setIsSeaFoodFormula(line.getIsSeaFoodFormula());
                this.save(orderHead);
                bidOrderHeadVO.setOrderHeadId(id);
                bidOrderHeadVO.setBidOrderNum(bidOrderNum);
                orderHead.setIsProxyBidding(bidOrderHeadVO.getIsProxyBidding());
            } else {
                orderHead.setOrderStatus(bidOrderHeadVO.getOrderStatus());
                if (OrderStatusEnum.SUBMISSION.getValue().equals(bidOrderHeadVO.getOrderStatus())) {
                    orderHead.setSubmitTime(new Date());
                }
                orderHead.setIsProxyBidding(bidOrderHeadVO.getIsProxyBidding());
                this.updateById(orderHead);
            }

            //2 保存投标行表
            List<BidOrderLineVO> orderLineVOS = bidOrderHeadVO.getOrderLines();
            Set<Long> materialIds = new HashSet<>();
            orderLineVOS.forEach(orderLinevo -> {
                if (addOrderHead) {
                    orderLinevo.setWin(null);
                    orderLinevo.setOrderLineId(null);
                }
                materialIds.add(orderLinevo.getTargetId());
                orderLinevo.setOrderHeadId(bidOrderHeadVO.getOrderHeadId());
                orderLinevo.setRound(bidOrderHeadVO.getRound());
                orderLinevo.setOrderStatus(bidOrderHeadVO.getOrderStatus());
                orderLinevo.setBidVendorId(bidOrderHeadVO.getBidVendorId());
            });
            iOrderLineService.saveBatchOrderLines(orderLineVOS, isSubmit, materialIds);
            Long orderHeadId = orderHead.getOrderHeadId();
            List<OrderHeadFile> orderHeadFiles = bidOrderHeadVO.getOrderHeadFiles();
            List<OrderHeadFile> files = orderHeadFileMapper.selectList(Wrappers.lambdaQuery(OrderHeadFile.class)
                    .eq(OrderHeadFile::getOrderHeadId, orderHeadId));
            List<Long> shouldDel = new LinkedList<>();
            for (OrderHeadFile file : files) {
                boolean find = false;
                for (OrderHeadFile orderHeadFile : orderHeadFiles) {
                    if (Objects.equals(file.getOrderHeadFileId(), orderHeadFile.getOrderHeadFileId())) {
                        find = true;
                        break;
                    }
                }
                if (!find) {
                    shouldDel.add(file.getOrderHeadFileId());
                }
            }

            List<BidFileConfig> configFiles = bidOrderHeadVO.getConfigFiles();
            for (OrderHeadFile orderHeadFile : orderHeadFiles) {
                if (orderHeadFile.getVendorReferenceFileType() == null) {
                    for (BidFileConfig configFile : configFiles) {
                        if (Objects.equals(configFile.getRequireId(), orderHeadFile.getConfigFileId())) {
                            orderHeadFile.setVendorReferenceFileType(configFile.getReferenceFileType());
                            break;
                        }
                    }
                }
                if (orderHeadFile.getOrderHeadFileId() == null) {
                    orderHeadFile.setOrderHeadFileId(IdGenrator.generate());
                    orderHeadFile.setOrderHeadId(orderHeadId);
                    orderHeadFileMapper.insert(orderHeadFile);
                } else {
                    orderHeadFileMapper.updateById(orderHeadFile);
                }
            }
            if (!CollectionUtils.isEmpty(shouldDel)) {
                orderHeadFileMapper.deleteBatchIds(shouldDel);
            }
            return orderHead.getOrderHeadId();
        } finally {
            redisUtil.unLock(key);
        }


    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean withdrawOrder(BidOrderHeadVO bidOrderHeadVO) {
        Long orderHeadId = bidOrderHeadVO.getOrderHeadId();
        String orderStatus = bidOrderHeadVO.getOrderStatus();
        OrderHead orderHead = this.getById(orderHeadId).setOrderStatus(orderStatus).setWithDrawReason(bidOrderHeadVO.getWithDrawReason()).setWithDrawTime(new Date());
        //更新投标投标信息
        boolean flag = this.updateById(orderHead);
        return updateOrderLineStatus(orderHeadId, orderStatus);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean submitOrder(BidOrderHeadVO bidOrderHeadVO) throws IOException {

        // 获取 寻源单
        Biding biding = Optional.ofNullable(bidOrderHeadVO.getBidingId())
                .map(biddingDao::findById)
                .orElseThrow(() -> new BaseException("获取寻源单失败。 | biddingId: [" + bidOrderHeadVO.getBidingId() + "]"));
        boolean isFirstRound = biding.getCurrentRound() == null || biding.getCurrentRound() == 1;

        //第一轮而且是技术+商务招标时，供应商需提交至少一个技术标附件
        if (isFirstRound) {
            boolean isTechBusiness = BidType.TECHNOLOGY_BUSINESS.getValue().equals(biding.getBidingType());
            if (isTechBusiness) {
                if (CollectionUtils.isEmpty(bidOrderHeadVO.getOrderHeadFiles())) {
                    throw new BaseException("技术+商务招标时，供应商必须上传至少一个技术附件才可以提交");
                }
                bidOrderHeadVO.getOrderHeadFiles().stream()
                        .filter(e -> Objects.equals(e.getVendorReferenceFileType(), BidingFileTypeEnum.TECHNICAL_BID.getCode()))
                        .findAny().orElseThrow(() -> new BaseException("技术+商务招标时，供应商必须上传至少一个技术附件才可以提交"));
            }
        }
        if (!CollectionUtils.isEmpty(bidOrderHeadVO.getOrderHeadFiles())) {
            boolean match = bidOrderHeadVO.getOrderHeadFiles().stream().anyMatch(e -> Objects.isNull(e.getVendorReferenceFileType()));
            if (match) {
                throw new BaseException("上传附件请维护附件类型");
            }
        }

        // 判断是否满足投标条件，如果不满足，会抛出异常
        this.judgeBidingConditions(bidOrderHeadVO);

        // 保存的时候，判断是否满足条件
        this.judgeSaveOrderInfoPerssion(bidOrderHeadVO);

        if (OrderStatusEnum.SUBMISSION.getValue().equals(bidOrderHeadVO.getOrderStatus()))
            throw new BaseException("投标已经提交，不能修改。");

        // 设置 状态 - [已提交]
        bidOrderHeadVO.setOrderStatus(OrderStatusEnum.SUBMISSION.getValue());
        bidOrderHeadVO.setSubmitTime(new Date());

        // 存储报价行
        Long orderHeaderId = this.saveOrderInfo(bidOrderHeadVO, true);

        /*// 更新 行状态
        return this.updateOrderLineStatus(orderHeaderId, bidOrderHeadVO.getOrderStatus());*/

        //生成公式报价excel并上传文件中心
        generateFormulaPricesAndUpload(orderHeaderId,bidOrderHeadVO.getFormulaPriceDTOList());


        return true;
    }

    /**
     * 生成公式报价excel并上传文件中心
     * @param orderHeaderId
     * @param formulaPriceDTOList
     */
    private void generateFormulaPricesAndUpload(Long orderHeaderId, List<FormulaPriceDTO> formulaPriceDTOList) throws IOException {
        if(CollectionUtils.isEmpty(formulaPriceDTOList)){
            return;
        }
        //构建表格
        Workbook workbook = createWorkbookModel(formulaPriceDTOList);
        MultipartFile file = tranferFile(orderHeaderId,workbook);
        String sourceType = "公式报价导出";
        String uploadType = FileUploadType.FASTDFS.name();
        String fileModular = "bid";
        String fileFunction = "公式报价导出";
        String fileType = "xlsx";
        Fileupload fileUpload = fileCenterClient.feignClientUpload(file,sourceType,uploadType,fileModular,fileFunction,fileType);
        /*将fileUpload关联业务单据id*/
        fileCenterClient.binding(new LinkedList<Long>(){{
            add(fileUpload.getFileuploadId());
        }},orderHeaderId);

    }

    private MultipartFile tranferFile(Long orderHeaderId,Workbook workbook) throws IOException {
        String name = new StringBuffer()
                .append(new SimpleDateFormat("yyyy-MM-dd").format(new Date()))
                .append("-公式报价导出")
                .append("-")
                .append(orderHeaderId)
                .append(".xlsx")
                .toString();
        String originalFilename = name;
        String contentType = "";

        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        workbook.write(bos);
        byte[] barray=bos.toByteArray();
        InputStream is=new ByteArrayInputStream(barray);
        MultipartFile multipartFile = new MockMultipartFile("file",originalFilename,contentType,is);
        return multipartFile;
    }

    private Workbook createWorkbookModel(List<FormulaPriceDTO> formulaPriceDTOList){
        // 创建工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();
        // 创建工作表:自定义导入
        XSSFSheet sheet = workbook.createSheet("sheet");
        // 创建单元格样式
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        // 设置水平对齐方式:中间对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        // 设置垂直对齐方式:中间对齐
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        // 设置字体样式
        Font font = workbook.createFont();
        font.setBold(true);
        cellStyle.setFont(font);

        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框


        List<FormulaPriceDTO> type1 = new ArrayList<>();
        List<FormulaPriceDTO> type2 = new ArrayList<>();
        List<FormulaPriceDTO> type3 = new ArrayList<>();
        for(FormulaPriceDTO formulaPriceDTO : formulaPriceDTOList){
            if(FormulaPriceExportTypeEnum.TYPE1.getValue().equals(formulaPriceDTO.getType())){
                type1.add(formulaPriceDTO);
            }
            if(FormulaPriceExportTypeEnum.TYPE2.getValue().equals(formulaPriceDTO.getType())){
                type2.add(formulaPriceDTO);
            }
            if(FormulaPriceExportTypeEnum.TYPE3.getValue().equals(formulaPriceDTO.getType())){
                type3.add(formulaPriceDTO);
            }
        }

        //行index
        int rowIndex = 0;

        //开始设置头表数据
        for(int i=0;i<type1.size();i++){
            //当前单元格下标
            int cellIndex = 0;
            FormulaPriceDTO formulaPriceDTO = type1.get(i);
            XSSFRow row = sheet.createRow(rowIndex);

            XSSFCell cell1 = row.createCell(cellIndex);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue(formulaPriceDTO.getContent());

            rowIndex ++;
        }

        //开始设置基材数据
        for(int i=0;i<type3.size();i++){
            //当前单元格下标
            int cellIndex = 0;
            FormulaPriceDTO formulaPriceDTO = type3.get(i);
            XSSFRow row = sheet.createRow(rowIndex);

            XSSFCell cell1 = row.createCell(cellIndex);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue(formulaPriceDTO.getBaseMaterialName());
            cellIndex ++;

            XSSFCell cell2 = row.createCell(cellIndex);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue(formulaPriceDTO.getBaseMaterialPrice());
            cellIndex ++;

            rowIndex ++;
        }

        //开始设置数据
        for(int i=0;i<type2.size();i++){
            //当前单元格下标
            int cellIndex = 0;
            FormulaPriceDTO formulaPriceDTO = formulaPriceDTOList.get(i);
            XSSFRow row = sheet.createRow(rowIndex);

            XSSFCell cell1 = row.createCell(cellIndex);
            cell1.setCellStyle(cellStyle);
            cell1.setCellValue(formulaPriceDTO.getOrgName());
            cellIndex ++;

            XSSFCell cell2 = row.createCell(cellIndex);
            cell2.setCellStyle(cellStyle);
            cell2.setCellValue(formulaPriceDTO.getMaterialItemDesc());
            cellIndex ++;

            JSONArray jsonArray = JSON.parseArray(formulaPriceDTO.getContent());
            for(int j=0;j<jsonArray.size();j++){
                //获取值
                JSONObject jsonObject = (JSONObject) jsonArray.get(j);
                String value = jsonObject.getString("value");
                XSSFCell cell3 = row.createCell(cellIndex);
                cell3.setCellStyle(cellStyle);
                cell3.setCellValue(value);
                cellIndex ++;
            }
            rowIndex ++;

        }
        return workbook;
    }



    private boolean updateOrderLineStatus(Long orderHeadId, String orderStatus) {
        boolean flag = false;
        List<OrderLine> orderLines = iOrderLineService.list(new QueryWrapper<>(new OrderLine().setOrderHeadId(orderHeadId)));
        List<OrderLine> updateLines = new ArrayList<>();
        for (OrderLine line : orderLines) {
            line.setOrderStatus(orderStatus);
            line.setWin(null);
            updateLines.add(line);
        }
        //更新行表投标状态
        if (updateLines.size() > 0) {
            flag = iOrderLineService.updateBatchById(updateLines);
        }
        return flag;
    }


    /**
     * 根据投标单ID，邀标供应商ID，轮次ID确定唯一条数据。
     * 即同一个投标记录，轮次不能重复
     *
     * @param bidingId
     * @param bidVendorId
     * @param roundId
     * @return
     */
    private OrderHead alreadyOrderHeadByBidingAndBidVendorIdAndRoundId(long bidingId, long bidVendorId, long roundId) {
        QueryWrapper queryWrapper = new QueryWrapper<>(new OrderHead().setBidingId(bidingId).setBidVendorId(bidVendorId).setBidRoundId(roundId));
        queryWrapper.in("ORDER_STATUS", OrderStatusEnum.DRAFT.getValue(), OrderStatusEnum.SUBMISSION.getValue());
        return this.getOne(queryWrapper);
    }


    /**
     * 判断是否符合投标的条件
     *
     * @param supplierBidingVO
     * @return
     */
    @Override
    public void judgeBidingConditions(SupplierBidingVO supplierBidingVO) {
        /**
         * 根据bidingId查询供应商投标轮次表
         * 1 伦次表没有数据，不允许投标
         * 2 查找最新的轮次数据，根据轮次round判断最新的数据
         *      是否已商务开标 = Y 当前投标已经结束，不允许投标
         *      当前时间 > 投标/报价结束时间，当前投标已经结束，不允许投标
         */

        boolean notRoundData = (null == supplierBidingVO.getRoundId());
        if (notRoundData) {
            throw new BaseException(LocaleHandler.getLocaleMsg("投标未发起，请等待采购商发起投标"));
        }
        //轮次投标结束时间
        Date endTime = supplierBidingVO.getEndTime();
        Date currentTime = new Date();
        //date1小于date2返回-1，date1大于date2返回1，相等返回0
        // 当当前时间 大于 投标结束时间，投标已经结束
        int compareTo = currentTime.compareTo(endTime);
        if (compareTo != -1) {
            throw new BaseException(LocaleHandler.getLocaleMsg("投标已结束，有问题请联系采购商"));
        }
        //商务开标
        String businessOpenBid = supplierBidingVO.getBusinessOpenBid();
        if (YesOrNo.YES.getValue().equals(businessOpenBid)) {
            throw new BaseException(LocaleHandler.getLocaleMsg("投标已结束，有问题请联系采购商"));
        }

        //2 查看报名表，有没有报名成功，报名成功才能发起投标，如果报名成功，但是被驳回，也不能投标
        boolean signUpData = (null != supplierBidingVO.getSignUpId() && !SignUpStatus.REJECTED.equals(supplierBidingVO.getSignUpStatus()));
        if (!signUpData) {
            throw new BaseException(LocaleHandler.getLocaleMsg("请先前往报名"));
        }
    }

    @Override
    public BidOrderHeadVO getOrderHeadInfo(BidOrderHeadVO bidOrderHeadVO) {
        Long bidingId = bidOrderHeadVO.getBidingId();
        Long vendorId = bidOrderHeadVO.getVendorId();
        Long bidVendorId = bidOrderHeadVO.getBidVendorId();

        BidOrderHeadVO orderHeadVO = new BidOrderHeadVO();
        BeanCopyUtil.copyProperties(orderHeadVO, bidOrderHeadVO);
        //1 获取表头数据
        Long orderHeadId = bidOrderHeadVO.getOrderHeadId();
        if (null != orderHeadId) {
            OrderHead orderHead = this.getById(orderHeadId);
            BeanCopyUtil.copyProperties(orderHeadVO, orderHead);
        }

        /*
         * 更改 - 追加设置 供应商信息
         *
         * @author zixuan.yan@meicloud.com
         */
        Optional.ofNullable(bidVendorDao.findOne(Wrappers.lambdaQuery(BidVendor.class).eq(BidVendor::getBidVendorId, bidVendorId)))
                .ifPresent(bidVendor -> {
                    if (!"Y".equals(bidOrderHeadVO.getIsProxyBidding()) && !vendorId.equals(bidVendor.getVendorId()))   // 非代理时，需校验供应商一致性权限
                        throw new BaseException("没有获取bidVendorId为[" + bidVendorId + "]的权限");
                    orderHeadVO.setVendorId(bidVendor.getVendorId());
                    orderHeadVO.setVendorCode(bidVendor.getVendorCode());
                });

        //2 获取行表数据
        List<BidOrderLineVO> bidOrderLineVOs = getOrderLineVOS(bidingId, orderHeadId, bidVendorId);
        orderHeadVO.setOrderLines(bidOrderLineVOs);

        //3 获取附件信息
        BidVendorFileVO vendorFileVO = new BidVendorFileVO();
        vendorFileVO.setBusinessId(orderHeadId);
        vendorFileVO.setFileType(VendorFileType.BIDING.getValue());
        vendorFileVO.setBidingId(bidingId);
        vendorFileVO.setVendorId(orderHeadVO.getVendorId());
        List<BidVendorFileVO> vendorFileVOs = iBidVendorFileService.getVendorFileList(vendorFileVO);
        orderHeadVO.setFiles(vendorFileVOs);
        List<BidFileConfig> list = fileConfigService.list(Wrappers.lambdaQuery(BidFileConfig.class)
                .eq(BidFileConfig::getBidingId, bidingId));
        orderHeadVO.setConfigFiles(list);
        List<OrderHeadFile> files = orderHeadFileMapper.selectList(Wrappers.lambdaQuery(OrderHeadFile.class)
                .eq(OrderHeadFile::getOrderHeadId, orderHeadId));
        orderHeadVO.setOrderHeadFiles(files);
        // 获取 招标单需求[头]
        BidRequirement demandHeader = Optional.ofNullable(bidRequirementDao.findOne(
                Wrappers.lambdaQuery(BidRequirement.class)
                        .select(BidRequirement::getRequirementId, BidRequirement::getPricingType)
                        .eq(BidRequirement::getBidingId, bidingId)
        )).orElseThrow(() -> new BaseException("获取招标单需求[头]失败。 | biddingId: [" + bidingId + "]"));
        orderHeadVO.setPricingType(demandHeader.getPricingType());
        return orderHeadVO;
    }

    @Override
    public List<BidOrderLineVO> getOrderLineVOS(Long biddingId, Long orderHeadId, Long bidVendorId) {

        // 获取 招标单需求[头]
        BidRequirement demandHeader = Optional.ofNullable(bidRequirementDao.findOne(
                Wrappers.lambdaQuery(BidRequirement.class)
                        .select(BidRequirement::getRequirementId, BidRequirement::getPricingType)
                        .eq(BidRequirement::getBidingId, biddingId)
        )).orElseThrow(() -> new BaseException("获取招标单需求[头]失败。 | biddingId: [" + biddingId + "]"));

        // 获取 招标单需求[行]集 & 按[需求行ID]获取
        Map<Long, BidRequirementLine> demandLines = bidRequirementLineDao
                .findAll(Wrappers.lambdaQuery(BidRequirementLine.class)
                        .eq(BidRequirementLine::getBidingId, biddingId)
                )
                .stream()
                .collect(Collectors.toMap(BidRequirementLine::getRequirementLineId, x -> x));

        // 获取/生成 供应商报价行
        List<BidOrderLineVO> orderLines = this.findOrderLines(biddingId, orderHeadId, bidVendorId, demandLines);

        // 补充信息
        orderLines.forEach(orderLine -> {

            // 获取 招标单需求[行]
            BidRequirementLine demandLine = Optional.ofNullable(demandLines.get(orderLine.getRequirementLineId()))
                    .orElseThrow(() -> new BaseException("获取寻源需求行失败。 | demandLineId: [" + orderLine.getRequirementLineId() + "]"));

            // 设置 物料组
            orderLine.setItemGroup(demandLine.getItemGroup());
            // 设置 物料配比
            orderLine.setMaterialMatching(demandLine.getMaterialMatching());
            if (!Objects.equals(demandLine.getShowRequireNum(), "Y")) {
                orderLine.setQuantity(null);
            }
            if (StringUtils.isEmpty(orderLine.getUomDesc())) {
                orderLine.setUomDesc(orderLine.getUomCode());
            }
        });

        // 如果是公式报价，则设置相关信息
        if (RequirementPricingType.FORMULA_PURCHASER.getCode().equals(demandHeader.getPricingType())) {

            // 获取 物料 关键属性
            Map<Long, List<MaterialItemAttributeRelateVO>> materialAttributes = materialItemAttributeClient.getKeyFeatureMaterialAttributes(
                    orderLines.stream()
                            .filter(orderLine -> orderLine.getTargetId() != null)
                            .map(BidOrderLineVO::getTargetId)
                            .distinct()
                            .collect(Collectors.toList())
            );

            orderLines.forEach(orderLine -> Optional.ofNullable(orderLine.getOrderLineId()).ifPresent(orderLineId -> {

                // 无物料号时，跳过
                if (orderLine.getTargetId() == null)
                    return;

                // 设置 公式报价信息
                BidOrderLineFormulaPriceDetail formulaPriceDetail = formulaPriceDetailService.findDetailsByLineId(orderLineId).stream()
                        .findAny()
                        .orElseGet(() -> {
                            Map<Long, String> materialAttributesMap = new HashMap<>();
                            materialAttributes.getOrDefault(orderLine.getTargetId(), Collections.emptyList())
                                    .forEach(materialAttribute ->
                                            materialAttributesMap.put(materialAttribute.getMaterialAttributeId(), materialAttribute.getAttributeValue())
                                    );
                            return BidOrderLineFormulaPriceDetail.builder()
                                    .materialExtValues(JSON.toJSONString(materialAttributesMap, SerializerFeature.WriteNonStringKeyAsString))
                                    .build();
                        });
                orderLine.setFormulaPriceDetailId(formulaPriceDetail.getId());
                orderLine.setEssentialFactorValues(formulaPriceDetail.getEssentialFactorValues());
                BidRequirementLine bidRequirementLine = demandLines.get(orderLine.getRequirementLineId());
                if (Objects.nonNull(formulaPriceDetail.getMaterialExtValues()) && Objects.nonNull(bidRequirementLine.getOrgId())) {
                    Map<String, Object> map = JSON.parseObject(formulaPriceDetail.getMaterialExtValues(), Map.class);
                    map.put("-1", bidRequirementLine.getOrgName());
                    if (Objects.equals(bidRequirementLine.getIsSeaFoodFormula(), "Y")) {
                        map.put("-2", bidRequirementLine.getTargetDesc());
                        map.put("-3", orderLine.getOrderLineId());
                    }
                    String value = JSON.toJSONString(map);
                    orderLine.setMaterialExtValues(value);
                }


            }));
        }

        return orderLines;
    }

    protected List<BidOrderLineVO> findOrderLines(Long biddingId, Long orderHeaderId, Long bidVendorId, Map<Long, BidRequirementLine> demandLines) {

        // 获取 投标单信息
        Biding biding = Optional
                .ofNullable(biddingDao.findOne(Wrappers.lambdaQuery(Biding.class)
                        .select(Biding::getBidingId, Biding::getCurrentRound)
                        .eq(Biding::getBidingId, biddingId)))
                .orElseThrow(() -> new BaseException("获取投标单失败。 | biddingId: [" + biddingId + "]"));


        /*
         * 若供应商还没进行过报价
         * 1. 若当前轮次为第一轮，直接获取需求行的数据
         * 2. 若当前轮次非第一轮，需要过滤已淘汰的行数据
         */
        if (orderHeaderId == null) {

            // 当第一轮报价的时候，直接获取需求行的数据
            if (firstRound.equals(biding.getCurrentRound()))
                return iOrderLineService.getRequirementLineByBidingIdAndBidVendorId(biddingId, bidVendorId);

            // 当不是第一次报价的时候，如果第二轮报价，淘汰的行数据不要查询出来
            List<BidOrderLineVO> result = iOrderLineService.getWinOrderLineByBidingIdAndVendorId(biddingId, bidVendorId);
            for (BidOrderLineVO bidOrderLineVO : result) {
                BidRequirementLine bidRequirementLine = demandLines.get(bidOrderLineVO.getRequirementLineId());
                BeanUtils.copyProperties(bidOrderLineVO, bidRequirementLine, BeanCopyUtil.getNullPropertyNames(bidOrderLineVO));
            }
            return result;
        }
        /*
         * 若供应商已进行过报价
         * 1. 直接查询报价单信息
         */
        return iOrderLineService.getOrderLineByOrderHeadId(orderHeaderId);
    }

    @Override
    public List<BidOrderLineVO> matchOrderLine(Long bidingId, Long orderHeadId, Long bidVendorId, List<Object> list) {
        List<BidOrderLineVO> orderLineVOS = null;
        orderLineVOS = getOrderLineVOS(bidingId, orderHeadId, bidVendorId);
        orderLineVOS.forEach(basic -> {
            for (Object obj : list) {
                BidOrderLineVO excle = (BidOrderLineVO) obj;
                if (basic.getRequirementLineId().equals(excle.getRequirementLineId())) {
                    basic.setPrice(excle.getPrice());
                    basic.setTaxRate(excle.getTaxRate());
                }
            }
        });
        return orderLineVOS;
    }

    @Override
    public void judgeSaveOrderInfoPerssion(BidOrderHeadVO bidOrderHeadVO) {
        /**
         * 如果组合投标，不能部分报价
         * 只有未投标状态，才可以修改数据
         *
         */
        List<BidOrderLineVO> orderLineVOS = bidOrderHeadVO.getOrderLines();
        Set<String> itemGroupSet = new HashSet();
        for (BidOrderLineVO orderLineVO : orderLineVOS) {
            itemGroupSet.add(orderLineVO.getItemGroup());
        }
        log.info("itemGroupSet : {}", itemGroupSet);
        for (String itemGroup : itemGroupSet) {
            if (null == itemGroup || "".equals(itemGroup)) {
                continue;
            }
            Set<BigDecimal> priceSet = new HashSet();
            for (BidOrderLineVO orderLineVO : orderLineVOS) {
                if (itemGroup.equals(orderLineVO.getItemGroup())) {
                    priceSet.add(orderLineVO.getPrice());
                }
            }
            int len = priceSet.size();
            if (len > 1) {
                if (priceSet.contains(null)) {
                    throw new BaseException(LocaleHandler.getLocaleMsg("组合投标不允许部分报价"));
                }
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void submitOrderForSupplier(Long bidSupplierId, OrderHead lastHead, Long bidingId, Round round) {
        //获取上一轮的报价信息
        List<BidOrderLineVO> bidOrderLineVOs = getOrderLineVOS(bidingId, lastHead.getOrderHeadId(), bidSupplierId);
        for (int i = bidOrderLineVOs.size() - 1; i >= 0; i--) {
            BidOrderLineVO bidOrderLineVO = bidOrderLineVOs.get(i);
            if (!Objects.equals(bidOrderLineVO.getWin(), "Y") || Objects.equals(bidOrderLineVO.getWin(), "Q")) {
                bidOrderLineVOs.remove(i);
            }
        }
        if (CollectionUtils.isEmpty(bidOrderLineVOs)) {
            return;
        }
        OrderHead orderHead = BeanCopyUtil.copyProperties(lastHead, OrderHead::new);
        String bidOrderNum = baseClient.seqGen(SequenceCodeConstant.SEQ_BID_BIDING_CODE);
        Long newOrderHeaderId = IdGenrator.generate();
        orderHead.setOrderHeadId(newOrderHeaderId);
        orderHead.setBidRoundId(round.getRoundId());
        orderHead.setBidOrderNum(bidOrderNum);
        orderHead.setBidingId(bidingId);
        orderHead.setRound(round.getRound());
        orderHead.setSubmitTime(new Date());
        orderHead.setOrderStatus(OrderStatusEnum.SUBMISSION.getValue());
        save(orderHead);
        String pricingType = lastHead.getPricingType();
        List<Long> orderLineIds = bidOrderLineVOs.stream().map(BidOrderLineVO::getOrderLineId).collect(Collectors.toList());
        Map<Long, List<OrderlinePaymentTerm>> collect = paymentTermMapper.selectList(Wrappers.lambdaQuery(OrderlinePaymentTerm.class)
                .in(OrderlinePaymentTerm::getOrderLineId, orderLineIds)).stream().collect(Collectors.groupingBy(OrderlinePaymentTerm::getOrderLineId));
        Set<Long> materialIds = new HashSet<>();
        bidOrderLineVOs.forEach(orderLineVO -> {
            List<OrderlinePaymentTerm> orderLinePaymentTerms = collect.get(orderLineVO.getOrderLineId());
            for (OrderlinePaymentTerm orderlinePaymentTerm : orderLinePaymentTerms) {
                orderlinePaymentTerm.setPaymentTermId(null);
                orderlinePaymentTerm.setOrderLineId(null);
            }
            orderLineVO.setPaymentTermList(orderLinePaymentTerms);
            orderLineVO.setOrderLineId(null);
            orderLineVO.setBidVendorId(orderHead.getBidVendorId());
            orderLineVO.setOrderHeadId(orderHead.getOrderHeadId());
            orderLineVO.setBidingId(bidingId);
            orderLineVO.setRound(round.getRound());
            orderLineVO.setWin(null);
            materialIds.add(orderLineVO.getTargetId());
        });
        iOrderLineService.saveBatchOrderLines(bidOrderLineVOs, true, materialIds);
        if (RequirementPricingType.SIMPLE_PURCHASER.getCode().equals(pricingType)) {
            return;
        }
        List<BidOrderLineFormulaPriceDetail> list = new LinkedList<>();
        List<BidOrderLineFormulaPriceDetail> formulaPriceDetails = null;
        if (RequirementPricingType.FORMULA_PURCHASER.getCode().equals(pricingType)) {
            formulaPriceDetails = formulaPriceDetailService.findDetailsByLineId(bidOrderLineVOs.get(0).getOrderLineId());
        }
        for (BidOrderLineVO bidOrderLineVO : bidOrderLineVOs) {
            //公式报价保存
            if (RequirementPricingType.FORMULA_PURCHASER.getCode().equals(pricingType)) {
                for (BidOrderLineFormulaPriceDetail formulaPriceDetail : formulaPriceDetails) {
                    formulaPriceDetail.setId(null);
                    formulaPriceDetail.setHeaderId(bidOrderLineVO.getOrderHeadId());
                    formulaPriceDetail.setLineId(bidOrderLineVO.getOrderLineId());
                    list.add(formulaPriceDetail);
                }
            }
            //价格模型报价保存
            if (RequirementPricingType.MODEL_PURCHASER.getCode().equals(pricingType)) {
                List<BidOrderLineTemplatePriceDetailVO> templatePriceDetailVOS = templatePriceDetailService.findDetailsByLineId(bidOrderLineVO.getOrderLineId());
                for (BidOrderLineTemplatePriceDetailVO detailVO : templatePriceDetailVOS) {
                    BidOrderLineTemplatePriceDetail templatePriceDetail = detailVO.getTemplatePriceDetail();
                    templatePriceDetail.setId(null);
                    templatePriceDetail.setLineId(bidOrderLineVO.getOrderLineId());
                    templatePriceDetail.setHeaderId(bidOrderLineVO.getOrderHeadId());
                    List<BidOrderLineTemplatePriceDetailLine> templatePriceDetailLines = detailVO.getTemplatePriceDetailLines();
                    for (BidOrderLineTemplatePriceDetailLine line : templatePriceDetailLines) {
                        line.setId(null);
                        line.setHeaderId(null);
                    }
                }
                templatePriceDetailService.saveDetails(templatePriceDetailVOS);
            }

        }
        if (!CollectionUtils.isEmpty(list)) {
            formulaPriceService.saveBatch(formulaPriceDetails);
        }

    }


    @Transactional(rollbackFor = Exception.class)
    @Override
    public BidOrderHeadVO getOrCreateOrderHeadInfo(BidOrderHeadVO queryParam) {

        if (queryParam.getOrderHeadId() == null) {

            // 尝试获取投标单
            BidOrderHeadVO orderHeader = getOrderHeadInfo(queryParam);

            // 暂存
            Long orderHeaderId = saveOrderInfo(orderHeader, false);

            // 设置投标单ID
            queryParam.setOrderHeadId(orderHeaderId);
            orderHeader.setOrderHeadId(orderHeaderId);
            List<BidOrderLineVO> orderLineVOS = getOrderLineVOS(queryParam.getBidingId(), orderHeaderId, queryParam.getBidVendorId());
            orderHeader.setOrderLines(orderLineVOS);
            return orderHeader;
        }

        BidOrderHeadVO orderHeadInfo = getOrderHeadInfo(queryParam);

        /**
         * 计算投标剩余时间
         */
        Date endTime = orderHeadInfo.getEndTime();
        Long remainingTime = null == endTime ? 0L : endTime.getTime() - new Date().getTime();
        orderHeadInfo.setRemainingTime(remainingTime >= 0 ? remainingTime : 0L);
        return orderHeadInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateOrderLinePrice(Long lineId, BigDecimal price) {

        // 获取 投标行
        OrderLine orderLine = iOrderLineService.getById(lineId);
        Assert.notNull(orderLine, "投标行获取失败。 | lineId: [" + lineId + "]");

        // 设置价格
        orderLine.setPrice(price);

        // persist.
        iOrderLineService.saveOrUpdate(orderLine);
    }

    @Override
    public List<BidVendor> findNotPricingVendors(Long biddingId) {

        // 获取 招标单
        Biding bidding = Optional.ofNullable(biddingDao.findById(biddingId))
                .orElseThrow(() -> new BaseException("获取招标单失败。 | biddingId: [" + biddingId + "]"));

        if (bidding.getCurrentRound() == null || bidding.getCurrentRound() == 1) {
            List<BidVendor> firstRoundNotPricingVendors = getBaseMapper().findFirstRoundNotPricingVendors(biddingId);
            firstRoundNotPricingVendors.forEach(e->e.setRound(1));
            return firstRoundNotPricingVendors;
        }


        // 获取 上一轮投标中，能进入下一轮的投标情情况
        Map<Long, OrderLine> allowNextRoundOrderLines = orderLineDao
                .findAll(Wrappers.lambdaQuery(OrderLine.class)
                        .eq(OrderLine::getBidingId, bidding.getBidingId())
                        .eq(OrderLine::getRound, bidding.getCurrentRound() - 1)
                        .eq(OrderLine::getOrderStatus, OrderStatusEnum.SUBMISSION.getValue())
                )
                .stream()
                .collect(Collectors.toMap(OrderLine::getOrderLineId, x -> x));

        // 获取 需求进行投标的供应商ID
        Set<Long> allowNextRoundBidVendorIds = allowNextRoundOrderLines.values().stream()
                .map(OrderLine::getBidVendorId)
                .collect(Collectors.toSet());


        // 获取 当前轮次已提交的投标情况
        Map<Long, OrderLine> currentRoundOrderLines = orderLineDao
                .findAll(Wrappers.lambdaQuery(OrderLine.class)
                        .eq(OrderLine::getBidingId, bidding.getBidingId())
                        .eq(OrderLine::getRound, bidding.getCurrentRound())
                        .eq(OrderLine::getOrderStatus, OrderStatusEnum.SUBMISSION.getValue())
                )
                .stream()
                .collect(Collectors.toMap(OrderLine::getOrderLineId, x -> x));

        // 获取 当前轮次已提交投标的供应商ID
        Set<Long> currentRoundSubmittedBidVendorIds = currentRoundOrderLines.values().stream()
                .map(OrderLine::getBidVendorId)
                .collect(Collectors.toSet());


        // 获取 当前轮次未提交投标的供应商ID
        Set<Long> currentRoundNotSubmitBidVendorIds = allowNextRoundBidVendorIds.stream()
                .filter(id -> !currentRoundSubmittedBidVendorIds.contains(id))
                .collect(Collectors.toSet());

        // 获取 未提交投标的供应商信息
        List<BidVendor> all = bidVendorDao.findAll(Wrappers.lambdaQuery(BidVendor.class).in(BidVendor::getBidVendorId, currentRoundNotSubmitBidVendorIds));
        all.forEach(e->e.setRound(bidding.getCurrentRound()));
        return currentRoundNotSubmitBidVendorIds.isEmpty()
                ? Collections.emptyList()
                : all;
    }




}
