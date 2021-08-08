package com.midea.cloud.srm.inq.inquiry.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.midea.cloud.common.utils.IdGenrator;
import com.midea.cloud.srm.inq.inquiry.mapper.AgreementRatioMapper;
import com.midea.cloud.srm.inq.inquiry.service.IAgreementRatioService;
import com.midea.cloud.srm.model.inq.inquiry.entity.AgreementRatio;
import com.midea.cloud.srm.model.inq.inquiry.entity.QuotaPreinstall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <pre>
 *  配额-协议比例 服务实现类
 * </pre>
 *
 * @author yourname@meiCloud.com
 * @version 1.00.00
 *
 * <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-09-05 16:40:36
 *  修改内容:
 * </pre>
 */
@Service
public class AgreementRatioServiceImpl extends ServiceImpl<AgreementRatioMapper, AgreementRatio> implements IAgreementRatioService {

    @Override
    public List<AgreementRatio> agreementRatioList(AgreementRatio agreementRatio) {
        QueryWrapper<AgreementRatio> wrapper = new QueryWrapper<>();
        //配额条件查询
        wrapper.eq(agreementRatio.getQuotaId() != null, "QUOTA_ID", agreementRatio.getQuotaId());
        return this.list(wrapper);
    }

    /**
     * 新增或保存
     * @param AgreementRatioList
     */
    @Transactional
    @Override
    public void agreementRatioAdd(List<AgreementRatio> AgreementRatioList) {
        for (AgreementRatio agreementRatio : AgreementRatioList) {
            if (agreementRatio.getAgreementRatioId() == null) {
                Long id = IdGenrator.generate();
                agreementRatio.setAgreementRatioId(id);
                this.save(agreementRatio);
            } else {
                this.updateById(agreementRatio);
            }
        }
    }
}
