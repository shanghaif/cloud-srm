package com.midea.cloud.srm.supauth.quasample.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.base.work.dto.WorkCount;
import com.midea.cloud.srm.model.supplierauth.quasample.dto.QuaSampleDTO;
import com.midea.cloud.srm.model.supplierauth.quasample.dto.RequestSampleDTO;
import com.midea.cloud.srm.model.supplierauth.quasample.entity.QuaSample;

/**
 *  <pre>
 *  样品确认表 服务类
 * </pre>
 *
 * @author zhuwl7
 * @version 1.00.00
 *
 *  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-17 19:04:43
 *  修改内容:
 * </pre>
 */
public interface IQuaSampleService extends IService<QuaSample> {

    PageInfo<QuaSample> listPageByParam(RequestSampleDTO requestSampleDTO);

    Long addQuaSample(QuaSample quaSample);

    Long modifyQuaSample(QuaSample quaSample);

    void commonCheck(QuaSample quaSample, String orderStatus);


    Long saveOrUpdateSample(QuaSampleDTO quaSampleDTO, String orderStatus);

    void updateQuaSample(QuaSample quaSample, String orderStatus);

    void bathDeleteByList(List<Long> sampleIds);

    void submittedSave(QuaSampleDTO quaSampleDTO);

    WorkCount countConfirmed();

    Map<String,Object> updateQuaSampleWithFlow(QuaSampleDTO quaSampleDTO, String orderStatus);

    void saveOrUpdateCataLogAfterFlow(QuaSample quaSample);

    QuaSampleDTO getQualifiedSample(Long sampleId);
}
