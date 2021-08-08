package com.midea.cloud.srm.pr.requirement.controller;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.exception.BaseException;
import com.midea.cloud.common.handler.TitleColorSheetWriteHandler;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.common.utils.*;
import com.midea.cloud.component.context.i18n.LocaleHandler;
import com.midea.cloud.srm.feign.inq.InqClient;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementLineUpdateDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementLineVO;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.ps.http.BgtCheckReqParamDto;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementLineImportDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirermentLineQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.param.FollowNameParam;
import com.midea.cloud.srm.model.pm.pr.requirement.param.OrderQuantityParam;
import com.midea.cloud.srm.model.pm.pr.requirement.param.SourceBusinessGenParam;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RecommendVendorVO;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.VendorAndEffectivePriceVO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import com.midea.cloud.srm.pr.requirement.service.IRequirementLineService;
import com.midea.cloud.srm.ps.http.fssc.service.IFSSCReqService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <pre>
 *  采购需求行表 前端控制器
 * </pre>
 *
 * @author fengdc3@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-12 18:46:40
 *  修改内容:
 * </pre>
 */
@RestController
@Slf4j
@RequestMapping("/pr/requirementLine")
public class RequirementLineController extends BaseController {
    @Autowired
    private IRequirementLineService iRequirementLineService;
    @Autowired
    private IFSSCReqService ifsscReqService;

    /**
     * 获取
     *
     * @param id
     */
    @GetMapping("/get")
    public RequirementLine get(@RequestParam("id") Long id) {
        Assert.notNull(id, "id不能为空");
        return iRequirementLineService.getById(id);
    }

    /**
     * 新增
     *
     * @param requirementLine
     */
    @PostMapping("/add")
    public void add(@RequestBody RequirementLine requirementLine) {
        Long id = IdGenrator.generate();
        requirementLine.setRequirementLineId(id);
        iRequirementLineService.save(requirementLine);
    }

    /**
     * 批量删除
     *
     * @param requirementLineIds
     */
    @PostMapping("/deleteBatch")
    public void delete(@RequestBody List<Long> requirementLineIds) {
        if (!CollectionUtils.isEmpty(requirementLineIds)) {
            iRequirementLineService.removeByIds(requirementLineIds);
        }
    }

    /**
     * 批量删除
     *
     * @param id
     */
    @GetMapping("/delete")
    public void delete(Long id) {
        Assert.notNull(id, "id不能为空");
        iRequirementLineService.removeById(id);
    }

    /**
     * 修改
     *
     * @param requirementLine
     */
    @PostMapping("/modify")
    public void modify(@RequestBody RequirementLine requirementLine) {
        iRequirementLineService.updateById(requirementLine);
    }

    /**
     * 发布招标单修改采购需求行外部单据名称
     *
     * @param followNameParam
     */
    @PostMapping("/updateIfExistRequirementLine")
    public void updateIfExistRequirementLine(@RequestBody FollowNameParam followNameParam) {
        iRequirementLineService.updateIfExistRequirementLine(followNameParam);
    }

    /**
     * 获取是否有:货源供应商,有效价格
     *
     * @param requirementLine
     */
    @PostMapping("/getVendorAndEffectivePrice")
    public VendorAndEffectivePriceVO getVendorAndEffectivePrice(@RequestBody RequirementLine requirementLine) {
        return iRequirementLineService.getVendorAndEffectivePrice(requirementLine);
    }

    /**
     * 分页查询
     *
     * @param requirementLine
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<RequirementLine> listPage(@RequestBody RequirementLine requirementLine) {
        PageUtil.startPage(requirementLine.getPageNum(), requirementLine.getPageSize());
        QueryWrapper<RequirementLine> wrapper = new QueryWrapper<RequirementLine>(requirementLine);
        return new PageInfo<RequirementLine>(iRequirementLineService.list(wrapper));
    }

    /**
     * 新建采购订单-查询物料明细
     * @param params
     * @return
     */
    @PostMapping("/listPageForOrder")
    public PageInfo<RequirementLineVO> listPage(@RequestBody RequirermentLineQueryDTO params){
        return iRequirementLineService.listPageForOrder(params);
    }

    /** "
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<RequirementLine> listAll() {
        return iRequirementLineService.list();
    }
//
//    隆基不需要
//    /**
//     * excel导出
//     */
//    @GetMapping("/excelExport")
//    public void excelEportRequirementLine(Long requirementHeadId, HttpServletResponse response) throws IOException {
//        List<RequirementLineImportDTO> dataList = new ArrayList<>();
//        if (requirementHeadId != null) {
//            //获取数据，转为字节数组
//            List<RequirementLine> requirementLineList =
//                    iRequirementLineService.list(new QueryWrapper<>(new RequirementLine().setRequirementHeadId(requirementHeadId)));
//            dataList = BeanCopyUtil.copyListProperties(requirementLineList, RequirementLineImportDTO.class);
//            Map<Integer, Date> requirementDateMap = requirementLineList.stream().collect(Collectors.toMap(RequirementLine::getRowNum,
//                    line -> DateChangeUtil.asDate(line.getRequirementDate())));
//            dataList.forEach(data -> {
//                Date requirementDate = requirementDateMap.get(data.getRowNum());
//                data.setRequirementDate(requirementDate);
//            });
//        }
//
//        String fileName = "采购需求信息.xls";
//        if (requirementHeadId != null) {
//            File file = new File(fileName);
//            //设置表头必填字体颜色
//            List<Integer> rowIndexs = Arrays.asList(0);
//            List<Integer> columnIndexs = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
//            Short colorIndex = IndexedColors.RED.getIndex();
//            TitleColorSheetWriteHandler handler = new TitleColorSheetWriteHandler(rowIndexs, columnIndexs, colorIndex);
//            EasyExcel.write(file, RequirementLineImportDTO.class).
//                    registerWriteHandler(handler).
//                    sheet().doWrite(dataList);
//
//            try {
//                byte[] buffer = FileUtils.readFileToByteArray(file);
//                file.delete();
//                response.setContentType("application/vnd.ms-excel");
//                response.setCharacterEncoding("utf-8");
//                fileName = URLEncoder.encode(fileName, "UTF-8");
//                response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
//                response.getOutputStream().write((byte[]) buffer);
//                response.getOutputStream().close();
//            } catch (IOException e) {
//                log.error("输出流报错:" + e.getMessage());
//                throw new BaseException(LocaleHandler.getLocaleMsg("系统异常,请联系系统管理员"));
//            }
//        }else {
//            // 模板下载
//            String fileModelName = "采购需求信息模板";
//            // 标题列表
//            List<String> titleList = Arrays.asList("申请行号", "*采购组织", "*需求部门", "*预算", "*物料编码",
//                    "*需求数量", "*需求日期", "*申请原因", "*库存地点", "*成本类型", "*成本编号", "指定品牌");
//            // 输出流
//            ServletOutputStream outputStream = EasyExcelUtil.getServletOutputStream(response, fileModelName);
//            // 表头需标红行号
//            List<Integer> rowIndexs = Arrays.asList(0);
//            // 表头需标红列号
//            List<Integer> columnIndexs = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
//            // 红色
//            short red = IndexedColors.RED.index;
//            TitleColorSheetWriteHandler titleColorSheetWriteHandler = new TitleColorSheetWriteHandler(rowIndexs,columnIndexs,red);
//            EasyExcelUtil.writeExcelWithModel(outputStream, dataList, titleList, fileModelName, titleColorSheetWriteHandler);
//        }
//    }

    /**
     * excel导入采购需求行
     * 隆基不需要
     */
//    @PostMapping("/excelImport")
//    public List<RequirementLine> excelImport(@RequestParam("file") MultipartFile file) throws IOException {
//        Assert.notNull(file, "文件上传失败");
//        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
//        Assert.isTrue((org.apache.commons.lang3.StringUtils.equals("xls", suffix.toLowerCase()) || org.apache.commons.lang3.StringUtils.equals("xlsx", suffix.toLowerCase())),
//                "请上传excel文件");
//        List<Object> voList = EasyExcelUtil.readExcelWithModel(file.getInputStream(),
//                RequirementLineImportDTO.class);
//        return iRequirementLineService.importExcelInfo(voList);
//    }




    /**
     * 管理/采购页-分页查询
     * @param requirementLine
     * @return
     */
    @PostMapping("/listApprovedApplyByPage")
    public PageInfo<RequirementLine> listApprovedApplyByPage(@RequestBody RequirementLine requirementLine) {
        return iRequirementLineService.listApprovedApplyByPage(requirementLine);
    }

    /**
     * 创建采购订单-邀请供应商列表
     * @param requirementLineList
     * @return
     */
    @PostMapping("/listRecommendVendor")
    public List<RecommendVendorVO> listRecommendVendor(@RequestBody List<RequirementLine> requirementLineList) {
        return iRequirementLineService.listRecommendVendor(requirementLineList);
    }

    /**
     * 生成采购订单
     * @param recommendVendorVOList
     * @return
     */
    @PostMapping("/genOrder")
    public void  genOrder(@RequestBody List<RecommendVendorVO> recommendVendorVOList) {
        iRequirementLineService.genOrder(recommendVendorVOList);
    }

    /**
     * 生成寻源单据
     * @param param
     * @return
     */
    @PostMapping("/genSourceBusiness")
    public void genSourceBusiness(@RequestBody SourceBusinessGenParam param) {
        iRequirementLineService.genSourceBusiness(param);
    }

    /**
     * 批量调整可下单数量
     * @param orderQuantityParamList
     * @return
     */
    @PostMapping("/updateOrderQuantityBatch")
    public void updateOrderQuantityBatch(@RequestBody List<OrderQuantityParam> orderQuantityParamList) {
        iRequirementLineService.updateOrderQuantityBatch(orderQuantityParamList);
    }

    /**
     * 供应商
     * @param detailList
     */
    @PostMapping("/orderReturn")
    public void orderReturn(@RequestBody List<OrderDetail> detailList){
        iRequirementLineService.orderReturn(detailList);
    }

    /**
     * 重新提交(ceea)
     * @param requirementLineIds
     */
    @PostMapping("/resubmit")
    public void resubmit(@RequestBody List<Long> requirementLineIds) {
        iRequirementLineService.resubmit(requirementLineIds);
    }

    /**
     * 取消
     */
    @PostMapping("/cancel")
    public BaseResult cancel(@RequestBody RequirermentLineQueryDTO requirermentLineQueryDTO) {
        FSSCResult fsscResult = iRequirementLineService.cancel(requirermentLineQueryDTO);
        BaseResult baseResult = BaseResult.buildSuccess();
        if (StringUtils.isNotEmpty(fsscResult.getMsg()) && !"success".equals(fsscResult.getMsg())) {
            baseResult.setCode(fsscResult.getCode());
            baseResult.setMessage(fsscResult.getMsg());
        }
        return baseResult;
    }

    /**
     * 需求变更
     * @param requirementLineUpdateDTO
     * @return
     */
    @PostMapping("/updateNum")
    public void ceeaUpdateNum(@RequestBody RequirementLineUpdateDTO requirementLineUpdateDTO){
        iRequirementLineService.ceeaUpdateNum(requirementLineUpdateDTO);
    }

    @GetMapping("/testToken")
    public String getToken() {
        String token = ifsscReqService.getToken();
        return token;
    }

    @PostMapping("/testApplyRelease")
    public BaseResult testApplyRelease(@RequestBody BgtCheckReqParamDto bgtCheckReqParamDto) {
        FSSCResult fsscResult = ifsscReqService.applyRelease(bgtCheckReqParamDto);
        return BaseResult.build(fsscResult.getCode(), fsscResult.getMsg());
    }

    @PostMapping("/testApplyFreeze")
    public BaseResult testApplyFreeze(@RequestBody BgtCheckReqParamDto bgtCheckReqParamDto) {
        FSSCResult fsscResult = ifsscReqService.applyFreeze(bgtCheckReqParamDto);
        return BaseResult.build(fsscResult.getCode(), fsscResult.getMsg());
    }

    /**
     * 采购需求-物料明细-导入文件模板下载
     * @param response
     * @throws IOException
     */
    @RequestMapping("/importMaterialItemModelDownload")
    public void importModelDownload(HttpServletResponse response) throws Exception {
        iRequirementLineService.importMaterialItemModelDownload(response);
    }


    /**
     * excel导入采购需求行
     */
    @PostMapping("/excelImport")
    public Map<String,Object> excelImport( String requirementHeadId , @RequestParam("file") MultipartFile file , Fileupload fileupload) throws Exception {
        Assert.notNull(file, "文件上传失败");
        return iRequirementLineService.importExcel(requirementHeadId, file , fileupload );
    }

}
