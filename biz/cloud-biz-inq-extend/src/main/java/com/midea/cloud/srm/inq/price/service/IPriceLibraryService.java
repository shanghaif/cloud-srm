package com.midea.cloud.srm.inq.price.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.inq.price.domain.PriceLibraryAddParam;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryDTO;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryParam;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryPutOnShelvesDTO;
import com.midea.cloud.srm.model.inq.price.dto.PriceLibraryRequestDTO;
import com.midea.cloud.srm.model.inq.price.entity.PriceLibrary;
import com.midea.cloud.srm.model.inq.price.vo.PriceLibraryVO;
import com.midea.cloud.srm.model.pm.po.dto.NetPriceQueryDTO;
import com.midea.cloud.srm.model.supplier.info.dto.BidFrequency;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  报价-价格目录表 服务类
 * </pre>
*
* @author linxc6@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-25 11:41:48
 *  修改内容:
 * </pre>
*/
public interface IPriceLibraryService extends IService<PriceLibrary> {

    /**
     * 查询报价价格目录
     */
    List<PriceLibrary> getQuotePrice(Long organizationId, Long vendorId, Long itemId);

    /**
     * 生成价格目录
     */
    void generatePriceLibrary(List<PriceLibraryAddParam> params);

    /**
     * 删除价格目录
     */
    void delete(Long priceLibraryId);

    /**
     * 批量更新
     */
    void updateBatch(List<PriceLibraryAddParam> params);

    /**
     * 查询价格目录
     * @param netPriceQueryDTO
     * @return
     */
    PriceLibrary getPriceLibraryByParam(NetPriceQueryDTO netPriceQueryDTO);

    /**
     * @Description 查询一条价格
     * @Param [priceLibrary]
     * @return
     * @Author haiping2.li@meicloud.com
     * @Date 2020.09.25 16:50
     **/
    PriceLibrary getOnePriceLibrary(PriceLibrary priceLibrary);

    List<PriceLibrary> listPriceLibraryByParam(List<NetPriceQueryDTO> netPriceQueryDTOList);

    List<PriceLibrary> listPriceLibrary(NetPriceQueryDTO netPriceQueryDTO);

    List<PriceLibrary> queryByContract(PriceLibraryParam priceLibraryParamy);

    void ceeaGeneratePriceLibrary(List<PriceLibraryDTO> ceeaBuildPriceLibraryParam);

    String ifHasPrice(PriceLibrary priceLibrary);

    List<PriceLibrary> listEffectivePrice(PriceLibrary priceLibrary);

    /**
     * 批量更新价格库
     * @param priceLibraryList
     */
    void ceeaUpdateBatch(List<PriceLibrary> priceLibraryList);

    /**
     * 获取近三年供应商中标次数
     * @param vendorId
     * @return
     */
    List<BidFrequency> getThreeYearsBidFrequency(Long vendorId) throws ParseException;

    /**
     * 上架物料
     * @param priceLibraryPutOnShelvesDTO
     */
    void putOnShelves(PriceLibraryPutOnShelvesDTO priceLibraryPutOnShelvesDTO);

    /**
     * 下架物料
     * @param priceLibraryList
     */
    void pullOffShelves(List<PriceLibrary> priceLibraryList);

    /**
     * @Description 下载导出模板
     * @Param: [httpServletResponse]
     * @Return: void
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/29 14:58
     */
    void importModelDownload(HttpServletResponse httpServletResponse) throws Exception;

    /**
     * @Description 导入价格目录excel
     * @Param: [file]
     * @Return: void
     * @Author: dengyl23@meicloud.com
     * @Date: 2020/9/29 16:03
     */
    void importExcel(MultipartFile file) throws Exception;

    /**
     * 查询所有有效价格
     * @return
     */
    List<PriceLibrary> listAllEffective(PriceLibrary priceLibrary);

    PageInfo<PriceLibraryVO> PriceLibraryListPage(PriceLibrary priceLibrary);

    Map<String,Object> importInitDataExcel(MultipartFile file) throws Exception;

    PriceLibrary getLatest(PriceLibrary priceLibrary);

    List<PriceLibrary> getLatestFive(PriceLibraryRequestDTO priceLibraryRequestDTO);

    PageInfo<PriceLibrary> listForMaterialSecByBuyer(PriceLibrary priceLibrary);

    PageInfo<PriceLibrary> listForMaterialSecByVendor(PriceLibrary priceLibrary);
}
