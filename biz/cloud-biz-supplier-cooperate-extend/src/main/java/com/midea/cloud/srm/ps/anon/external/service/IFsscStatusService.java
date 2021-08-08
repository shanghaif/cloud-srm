package com.midea.cloud.srm.ps.anon.external.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.midea.cloud.srm.model.pm.ps.anon.external.FsscStatus;
import com.midea.cloud.srm.model.pm.ps.http.FSSCResult;

import java.util.Map;

/**
*  <pre>
 *  FSSC状态反馈表 服务类
 * </pre>
*
* @author chensl26@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-02 08:47:44
 *  修改内容:
 * </pre>
*/
public interface IFsscStatusService extends IService<FsscStatus> {

    Map<String, Object> receiveData(FsscStatus fsscStatus);

    Map<String, Object> download(String fileuploadId);
}
