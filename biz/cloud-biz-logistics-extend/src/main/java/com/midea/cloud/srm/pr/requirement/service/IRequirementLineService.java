package com.midea.cloud.srm.pr.requirement.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.common.result.BaseResult;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementLineUpdateDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RequirementLineVO;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirementManageDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.dto.RequirermentLineQueryDTO;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementHead;
import com.midea.cloud.srm.model.pm.pr.requirement.entity.RequirementLine;
import com.midea.cloud.srm.model.pm.pr.requirement.param.FollowNameParam;
import com.midea.cloud.srm.model.pm.pr.requirement.param.OrderQuantityParam;
import com.midea.cloud.srm.model.pm.pr.requirement.param.SourceBusinessGenParam;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.RecommendVendorVO;
import com.midea.cloud.srm.model.pm.pr.requirement.vo.VendorAndEffectivePriceVO;
import com.midea.cloud.srm.model.rbac.user.entity.LoginAppUser;
import com.midea.cloud.srm.model.suppliercooperate.order.entry.OrderDetail;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  采购需求行表 服务类
 * </pre>
*
* @author fengdc3@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-05-12 18:46:40
 *  修改内容:
 * </pre>
*/
public interface IRequirementLineService extends IService<RequirementLine> {

    void addRequirementLineBatch(LoginAppUser loginAppUser, RequirementHead requirementHead, List<RequirementLine> requirementLineList);

    void updateBatch(RequirementHead requirementHead, List<RequirementLine> requirementLineList);

    /**
     * Description 批量修改采购需求行信息
     * @Param
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.15
     * @throws
     **/
    BaseResult<String> bachUpdateRequirementLine(List<RequirementLine> requirementLineList);

    /**
     * Description 批量驳回采购需求头信息
     * @Param requirementLineIds 多个采购需求行id
     * @param rejectReason 驳回原因
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.14
     * @throws
     **/
    BaseResult<String> bachRejectRequirement(Long[] requirementLineIds, String rejectReason);

//    /**
//     * Description 批量分配采购信息
//     * @Param requirementLineIds 多个采购需求行id
//     * @Param applyStatus 申请状态
//     * @Param buyerId 采购员ID
//     * @Param buyerId 采购员账号
//     * @Param buyerName 采购员名称
//     * @return
//     * @Author wuwl18@meicloud.com
//     * @Date 2020.05.15
//     * @throws
//     **/
//    BaseResult<String> bachAssigned(Long[] requirementLineIds, String applyStatus, String buyerId, String buyer, String buyerName);

//     ToDo longi不需要需求合并功能
//    /**
//     * Description 检查是否能何必选中的采购需求(能合并，返回true，不能合并抛出返回提示信息)
//     * @Param requirementLineIds 多个采购需求行id
//     * @return
//     * @Author wuwl18@meicloud.com
//     * @Date 2020.05.15
//     * @throws
//     **/
//    BaseResult<String> isMergeRequirement(Long[] requirementLineIds);

    /**
     * Description 获取采购需求合并信息
     * @Param requirementLineIds 多个采购需求行id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.17
     * @throws
     **/
    List<RequirementLine> findRequirementMergeList(Long[] requirementLineIds);

    /**
     * Description 预览采购需求行合并信息
     * @Param requirementLineIds 多个采购需求行id
     * @return
     * @Author wuwl18@meicloud.com
     * @Date 2020.05.15
     * @throws
     **/
    BaseResult<List<RequirementLine>> bachRequirementMergePreview(Long[] requirementLineIds);

//    /**
//     * Description 采购需求行合并信息
//     * @Param requirementLineIds 多个采购需求行id
//     * @return
//     * @Author wuwl18@meicloud.com
//     * @Date 2020.05.15
//     * @throws
//     **/
//    BaseResult<String> bachRequirementMerge(Long[] requirementLineIds);

//    隆基不需要
//    List<RequirementLine> importExcelInfo(List<Object> list);

    /**
     * 导入excel
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String, Object> importExcel(String requirementHeadId,MultipartFile file, Fileupload fileupload) throws Exception;

    PageInfo<RequirementLine> listApprovedApplyByPage(RequirementLine requirementLine);

    /**
     * 调整可下单数量
     * @param orderQuantityParam
     */
    void updateOrderQuantity(OrderQuantityParam orderQuantityParam);

    /**
     * 批量调整可下单数量
     * @param orderQuantityParamList
     */
    void updateOrderQuantityBatch(List<OrderQuantityParam> orderQuantityParamList);

    VendorAndEffectivePriceVO getVendorAndEffectivePrice(RequirementLine requirementLine);

    List<RecommendVendorVO> listRecommendVendor(List<RequirementLine> requirementLineList);

    void genOrder(List<RecommendVendorVO> recommendVendorVOList);

    void genSourceBusiness(SourceBusinessGenParam param);

    void updateIfExistRequirementLine(FollowNameParam followNameParam);

    /**
     * 订单回退
     * @param detailList
     */
    void orderReturn(List<OrderDetail> detailList);

    void bachAssigned(RequirementManageDTO requirementManageDTO);

    void batchReceive(RequirementManageDTO requirementManageDTO);

    void resubmit(List<Long> requirementLineIds);

    void batchReturn(RequirementManageDTO requirementManageDTO);

    FSSCResult cancel(RequirermentLineQueryDTO requirermentLineQueryDTO);

    void ceeaUpdateNum(RequirementLineUpdateDTO requirementLineUpdateDTO);

    PageInfo<RequirementLineVO> listPageForOrder(RequirermentLineQueryDTO params);


    /**
     * 采购需求-物料明细-导入文件模板下载
     * @param response
     */
    void importMaterialItemModelDownload(HttpServletResponse response) throws Exception;
}
