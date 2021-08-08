package com.midea.cloud.srm.inq.inquiry.controller;

import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.common.utils.PageUtil;
import com.midea.cloud.srm.inq.inquiry.service.IHeaderService;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.inq.inquiry.dto.InquiryDeadlineRequestDTO;
import com.midea.cloud.srm.model.inq.inquiry.dto.InquiryHeaderDto;
import com.midea.cloud.srm.model.inq.inquiry.entity.Header;
import com.midea.cloud.srm.model.inq.inquiry.entity.Item;
import com.midea.cloud.srm.model.inq.inquiry.entity.Vendor;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import jxl.Cell;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *  询价-询价信息头表 前端控制器
 * </pre>
 *
 * @author zhongbh
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-12 18:46:55
 *  修改内容:
 * </pre>
 */
@RestController
@RequestMapping("/inquiry/header")
public class HeaderController extends BaseController {

    @Autowired
    private IHeaderService iHeaderService;

    /**
     * 获取
     *
     * @param header
     */
    @PostMapping("/get")
    public Header get(@RequestBody Header header) {
        Assert.notNull(header.getInquiryId(), "id不能为空");
        return iHeaderService.getById(header.getInquiryId());
    }

    /**
     * 新增
     *
     * @param header
     */
    @PostMapping("/add")
    public void add(@RequestBody Header header) {
        Long id = IdGenrator.generate();
        header.setInquiryId(id);
        iHeaderService.save(header);
    }

    /**
     * 删除
     *
     * @param header
     */
    @PostMapping("/delete")
    public void delete(@RequestBody Header header) {
        Assert.notNull(header.getInquiryId(), "id不能为空");
        iHeaderService.removeById(header.getInquiryId());
    }

    /**
     * 修改
     *
     * @param header
     */
    @PostMapping("/modify")
    public void modify(@RequestBody Header header) {
        iHeaderService.updateById(header);
    }

    /**
     * 分页查询
     *
     * @param header
     * @return
     */
    @PostMapping("/listPage")
    public PageInfo<Header> listPage(@RequestBody Header header) {
        PageUtil.startPage(header.getPageNum(), header.getPageSize());
        String title = header.getInquiryTitle();
        if (null != title) {
            header.setInquiryTitle(null);
        }
        QueryWrapper<Header> wrapper = new QueryWrapper<Header>(header);
        if (null != title) {
            wrapper.like("INQUIRY_TITLE", title);
        }
        wrapper.orderByDesc("INQUIRY_NO", "CREATION_DATE");
        return new PageInfo<Header>(iHeaderService.list(wrapper));
    }

    /**
     * 查询所有
     *
     * @return
     */
    @PostMapping("/listAll")
    public List<Header> listAll() {
        return iHeaderService.list();
    }

    /**
     * 提前截止报价
     */
    @PostMapping("/deadline")
    public void changDeadline(@RequestBody InquiryDeadlineRequestDTO request) {
        Assert.notNull(request.getInquiryId(), "询价单id不能为空");
        iHeaderService.changDeadline(request);
    }

    /**
     * 保存
     *
     * @param header
     */
    @PostMapping("/saveHead")
    public Long saveAndUpdate(@RequestBody InquiryHeaderDto header) {
        Assert.notNull(header.getHeader(), "询价单信息不能为空");
        return iHeaderService.saveAndUpdate(header, "SAVE");
    }

    /**
     * 提交审批
     *
     * @param header
     */
    @PostMapping("/commitHead")
    public Long commitAudit(@RequestBody InquiryHeaderDto header) {
        Assert.notNull(header.getHeader(), "询价单信息不能为空");
        return iHeaderService.saveAndUpdate(header, "SUBMIT");
    }

    /**
     * 初始化流程信息
     */
    @GetMapping("/initFlow")
    public Map<String, Object> commitWorkFlow(Long inquiryId, Long menuId) {
        Assert.notNull(inquiryId, "询价单id不能为空");
        Assert.notNull(menuId, "菜单id不能为空");
        return iHeaderService.initWorkFlow(inquiryId, menuId);
    }

    /**
     * 通过
     *
     * @param header
     */
    @PostMapping("/passAudit")
    public Long passAudit(@RequestBody InquiryHeaderDto header) {
        Assert.notNull(header.getHeader(), "询价单信息不能为空");
        return iHeaderService.commit(header, "PASS");
    }

    /**
     * 发布
     *
     * @param header
     */
    @PostMapping("/publish")
    public Long publish(@RequestBody Header header) {
        Assert.notNull(header, "询价单信息不能为空");
        return iHeaderService.publish(header, "PUBLISH");
    }

    /**
     * 取消
     *
     * @param header
     */
    @PostMapping("/cancel")
    public Long cancel(@RequestBody Header header) {
        Assert.notNull(header, "询价单信息不能为空");
        return iHeaderService.publish(header, "CANCEL");
    }

    /**
     * 拒绝
     *
     * @param header
     */
    @PostMapping("/rejectAudit")
    public Long rejectAudit(@RequestBody InquiryHeaderDto header) {
        Assert.notNull(header.getHeader(), "询价单信息不能为空");
        return iHeaderService.commit(header, "REJECTED");
    }

    /**
     * 获取头信息
     *
     */
    @GetMapping("/getHeadById")
    public InquiryHeaderDto getHeadById(Long inquiryId) {
        Assert.notNull(inquiryId, "id不能为空");
        return iHeaderService.getHeadById(inquiryId);
    }

    /**
     * excel导入询价单-需求信息
     *
     * @param file
     * @param file
     * @throws Exception
     */
    @PostMapping("/saveItemsByExcel")
    public List<Item> saveItemsByExcel(@RequestParam("file") MultipartFile file, @RequestParam("inquiryId") Long inquiryId) throws Exception {
        Assert.notNull(inquiryId, "询价单id不能为空");
        Assert.notNull(file, "文件上传失败");

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Assert.isTrue(
                (StringUtils.equals("xls", suffix.toLowerCase()) || StringUtils.equals("xlsx", suffix.toLowerCase())),
                "请上传excel文件");
        Assert.isTrue(new Double(147294) / (1024 * 1024) < 1, "文件大小不能超过1M");

        Workbook rwb = Workbook.getWorkbook(file.getInputStream());

        List<Item> list = checkItemsExcelData(rwb);
        return iHeaderService.saveItemsByExcel(inquiryId, list);
    }

    /**
     * excel导入询价单-邀请供应商
     *
     * @param file
     * @param file
     * @throws Exception
     */
    @PostMapping("/saveVendorsByExcel")
    public List<Vendor> saveVendorsByExcel(@RequestParam("file") MultipartFile file, @RequestParam("inquiryId") Long inquiryId) throws Exception {
        Assert.notNull(inquiryId, "询价单id不能为空");
        Assert.notNull(file, "文件上传失败");

        String suffix = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        Assert.isTrue(
                (StringUtils.equals("xls", suffix.toLowerCase()) || StringUtils.equals("xlsx", suffix.toLowerCase())),
                "请上传excel文件");
        Assert.isTrue(new Double(147294) / (1024 * 1024) < 1, "文件大小不能超过1M");

        Workbook rwb = Workbook.getWorkbook(file.getInputStream());

        List<Vendor> list = checkVendorsExcelData(rwb);
        return iHeaderService.saveVendorsByExcel(inquiryId, list);
    }

    /**
     * 检验excel文件数据
     *
     * @param workbook
     * @return execl询价单-需求信息导入接收对象列表
     */
    private List checkItemsExcelData(Workbook workbook) {
        List list = new ArrayList();
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheet(sheetIndex);
            //行数(表头的目录不需要，从1开始)
            for (int i = 1; i < sheet.getRows(); i++) {
                boolean isBlack = true;
                for (int c = 0; c < sheet.getColumns(); c++) {
                    if (StringUtils.isNotBlank(sheet.getCell(c, i).getContents())) {
                        isBlack = false;
                        break;
                    }
                }
                if (isBlack) {
                    break;
                }
                Cell cell = null;
                //列数
                for (int j = 0; j < sheet.getColumns(); j++) {
                    //获取第i行，第j列的值
                    cell = sheet.getCell(j, i);
                    checkItemsCell(sheetIndex, cell, list);
                }
            }
        }
        return list;
    }


    /**
     * 检验excel文件数据
     *
     * @param workbook
     * @return execl询价单-供应商邀请导入接收对象列表
     */
    private List checkVendorsExcelData(Workbook workbook) {
        List list = new ArrayList();
        for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
            Sheet sheet = workbook.getSheet(sheetIndex);
            //行数(表头的目录不需要，从1开始)
            for (int i = 1; i < sheet.getRows(); i++) {
                boolean isBlack = true;
                for (int c = 0; c < sheet.getColumns(); c++) {
                    if (StringUtils.isNotBlank(sheet.getCell(c, i).getContents())) {
                        isBlack = false;
                        break;
                    }
                }
                if (isBlack) {
                    break;
                }
                Cell cell = null;
                //列数
                for (int j = 0; j < sheet.getColumns(); j++) {
                    //获取第i行，第j列的值
                    cell = sheet.getCell(j, i);
                    checkVendorsCell(sheetIndex, cell, list);
                }
            }
        }
        return list;
    }

    /**
     * 校验单元格数据及格式
     *
     * @param sheetIndex sheet下标
     * @param cell       单元格
     * @param list       询价单-需求信息接收对象列表
     */
    private void checkItemsCell(int sheetIndex, Cell cell, List<Item> list) {
        Item eord = null;
        if (list.size() != 0) {
            eord = list.get(list.size() - 1);
        }
        switch (cell.getColumn()) {
            case 0://物料编码
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "物料编码不能为空");
                Item newEord = new Item();
                newEord.setItemCode(cell.getContents());
                list.add(newEord);
                break;
            case 1://物料名称
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "物料名称不能为空");
                eord.setItemDesc(cell.getContents());
                break;
            case 2://物料分类
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "物料分类不能为空");
                eord.setCategoryName(cell.getContents());
                break;
            case 3://行类型
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "行类型不能为空");
                eord.setItemType(cell.getContents());
                break;
            case 4://预计采购量
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "预计采购量不能为空");
                Assert.isTrue(this.checkPositiveInt(cell.getContents()), getErrorPosition(sheetIndex, cell) + "预计采购量格式错误");
                eord.setDemandQuantity(new BigDecimal(cell.getContents()));
                break;
            case 5://是否阶梯报价
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "是否阶梯报价不能为空");
                switch (cell.getContents()) {
                    case "是":
                        eord.setIsLadder("Y");
                        break;
                    case "否":
                        eord.setIsLadder("N");
                        break;
                    default:
                        Assert.isTrue(false, getErrorPosition(sheetIndex, cell) + "是否阶梯报价请填:是/否");
                        break;
                }
                break;
            case 6://税率编码
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "税率编码不能为空");
                eord.setTaxKey(cell.getContents());
                break;
            case 7://币种
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "币种不能为空");
                eord.setCurrency(cell.getContents());
                break;
            case 8://定价开始日期
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "定价开始日期不能为空");
                Date date = this.getDateByStr(cell);
                Assert.notNull(date, getErrorPosition(sheetIndex, cell) + "定价开始日期格式错误");
                eord.setFixedPriceBegin(date);
                break;
            case 9://定价结束日期
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "定价结束日期不能为空");
                Date date2 = this.getDateByStr(cell);
                Assert.notNull(date2, getErrorPosition(sheetIndex, cell) + "定价结束日期格式错误");

                Assert.isTrue(!(date2.getTime() < eord.getFixedPriceBegin().getTime()),
                        getErrorPosition(sheetIndex, cell) + "定价结束日期不能小于定价开始日期");
                eord.setFixedPriceEnd(date2);
                break;
        }
    }

    /**
     * 校验单元格数据及格式
     *
     * @param sheetIndex sheet下标
     * @param cell       单元格
     * @param list       询价单-邀请供应商接收对象列表
     */
    private void checkVendorsCell(int sheetIndex, Cell cell, List<Vendor> list) {
        Vendor eord = null;
        if (list.size() != 0) {
            eord = list.get(list.size() - 1);
        }
        switch (cell.getColumn()) {
            case 0://供应商编号
                Assert.hasLength(cell.getContents(), getErrorPosition(sheetIndex, cell) + "供应商编号不能为空");
                Vendor newEord = new Vendor();
                newEord.setVendorCode(cell.getContents());
                list.add(newEord);
                break;
            case 1://供应商名称
                eord.setVendorName(cell.getContents());
                break;
            case 2://联系人
                eord.setLinkMan(cell.getContents());
                break;
            case 3://电话
                eord.setPhone(cell.getContents());
                break;
            case 4://邮箱
                if (StringUtils.isNotBlank(cell.getContents())) {
                    Assert.isTrue(checkEmail(cell.getContents()), "邮箱格式错误");
                    eord.setEmail(cell.getContents());
                }
                break;
        }
    }

    /**
     * 检验邮件格式
     *
     * @param email
     * @return
     */
    private Boolean checkEmail(String email) {
        if (email.contains("@") && email.contains(".")) {
            if (email.lastIndexOf(".") > email.lastIndexOf("@")) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取excel报错错误单元格位置
     *
     * @param sheetIndex sheet的下标
     * @param cell       单元格
     * @return
     */
    private String getErrorPosition(int sheetIndex, Cell cell) {
        return new StringBuffer("第").append(sheetIndex + 1).append("个sheet，第").append(cell.getRow() + 1)
                .append("行,第").append(cell.getColumn() + 1).append("列，").toString();
    }

    /**
     * 检查文本是否是正整型字符串
     *
     * @param text
     * @return
     */
    private static boolean checkPositiveInt(String text) {
        try {
            Integer number = new Integer(text);
            return number > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 检查文本是否是正数
     *
     * @param text
     * @return
     */
    private static boolean checkPositiveNumber(String text) {
        try {
            Double number = new Double(text);
            return number > 0;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 將excel中的日期转换为Date对象
     * @param cell
     * @return
     */
    private static Date getDateByStr(Cell cell){
        try{
            if(StringUtils.equals("Date",cell.getType().toString())){
                DateCell dateCell = (DateCell) cell;
                return dateCell.getDate();
            }else{
                return getDateByStr(cell.getContents());
            }
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 將excel中的日期转换为Date对象
     * @param text
     * @return
     */
    private static Date getDateByStr(String text){
        if(StringUtils.isBlank(text)){
            return null;
        }
        try{
            if(text.contains("/")){//可能是yyyy/MM/dd格式
                return DateUtils.parseDate(text,"yyyy/MM/dd");
            }else if(text.contains("-")){//可能是yyyy-MM-dd格式
                return DateUtils.parseDate(text,"yyyy-MM-dd");
            }else{//可能是yyyyMMdd格式
                return DateUtils.parseDate(text,"yyyyMMdd");
            }
        }catch (Exception e){
            return null;
        }
    }

    /**
     * 采购需求生成询比价单
     *
     * @param requirementLine
     */
    @PostMapping("/requirementGenInquiry")
    public String requirementGenInquiry(@RequestBody List<RequirementLine> requirementLine) {
        return iHeaderService.requirementGenInquiry(requirementLine);
    }

}
