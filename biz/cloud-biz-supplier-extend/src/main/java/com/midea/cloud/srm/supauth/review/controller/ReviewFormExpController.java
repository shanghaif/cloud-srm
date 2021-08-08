package com.midea.cloud.srm.supauth.review.controller;

import com.github.pagehelper.PageInfo;
import com.midea.cloud.srm.model.common.BaseController;
import com.midea.cloud.srm.model.supplier.info.dto.OrgCateServiceStatusDTO;
import com.midea.cloud.srm.model.supplierauth.review.dto.ReviewFormDTO;
import com.midea.cloud.srm.model.supplierauth.review.entity.ReviewForm;
import com.midea.cloud.srm.supauth.review.service.IReviewFormExpService;
import com.midea.cloud.srm.supauth.review.service.IReviewFormService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
*  <pre>
 *   资质审查原因 前端控制器
 * </pre>
*
* @author chensl26@meicloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2020-03-10 16:34:39
 *  修改内容:
 * </pre>
*/
@RestController
@RequestMapping("/review/reviewFormExp/")
public class ReviewFormExpController extends BaseController {

    @Autowired
    private IReviewFormExpService iReviewFormExpService;

    @GetMapping("/deleteByReviewFormExpId")
    public void deleteByReviewFormExpId(@RequestParam Long reviewFormExpId) {
        iReviewFormExpService.removeById(reviewFormExpId);
    }
}
