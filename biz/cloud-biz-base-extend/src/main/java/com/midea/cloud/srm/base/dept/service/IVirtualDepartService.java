package com.midea.cloud.srm.base.dept.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.dept.dto.DeptDto;
import com.midea.cloud.srm.model.base.dept.entity.VirtualDepart;
import com.midea.cloud.srm.model.file.upload.entity.Fileupload;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
*  <pre>
 *  虚拟组织表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-22 10:53:05
 *  修改内容:
 * </pre>
*/
public interface IVirtualDepartService extends IService<VirtualDepart> {
    /**
     * 分页查询
     * @param deptDto
     * @return
     */
    PageInfo<DeptDto> pageDept(DeptDto deptDto);

    /**
     * 新增
     * @param virtualDepart
     * @return
     */
    void add(@RequestBody VirtualDepart virtualDepart);

    /**
     * 根据条件查询所有
     * @param deptDto
     * @return
     */
    List<DeptDto> getAll(@RequestBody DeptDto deptDto);

    /**
     * 修改
     * @param virtualDepart
     */
    void modify(VirtualDepart virtualDepart);

    /**
     * 虚拟部门导入模板下载
     * @param response
     * @throws IOException
     */
    void importModelDownload(HttpServletResponse response) throws IOException;

    /**
     * 虚拟部门导入
     * @param file
     * @param fileupload
     * @return
     * @throws Exception
     */
    Map<String,Object> importExcel(@RequestParam("file") MultipartFile file, Fileupload fileupload) throws Exception;
}
