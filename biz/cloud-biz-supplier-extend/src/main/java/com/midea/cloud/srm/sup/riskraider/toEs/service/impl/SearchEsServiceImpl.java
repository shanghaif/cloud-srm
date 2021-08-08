package com.midea.cloud.srm.sup.riskraider.toEs.service.impl;

import com.midea.cloud.common.enums.sup.RiskIcToIndexEnum;
import com.midea.cloud.common.enums.sup.RiskInfoEnum;
import com.midea.cloud.srm.model.supplier.riskraider.toEs.dto.RaiderEsDto;
import com.midea.cloud.srm.sup.info.service.impl.CompanyInfoServiceImpl;
import com.midea.cloud.srm.sup.riskraider.handle.HandleRaiderInfo;
import com.midea.cloud.srm.sup.riskraider.monitor.service.impl.HaveMonitorServiceImpl;
import com.midea.cloud.srm.sup.riskraider.toEs.service.SearchFromEsService;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
*  <pre>
 *  企业财务信息表 服务实现类
 * </pre>
*
* @author chenwt24@meiCloud.com
* @version 1.00.00
*
*  <pre>
 *  修改记录
 *  修改后版本:
 *  修改人:
 *  修改日期: 2021-01-18 10:42:20
 *  修改内容:
 * </pre>
*/
@Service
@Slf4j
public class SearchEsServiceImpl implements SearchFromEsService {
    @Autowired
    private HandleRaiderInfo handleRaiderInfo;
    @Autowired
    private CompanyInfoServiceImpl companyInfoService;
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private HaveMonitorServiceImpl haveMonitorService;



    @Override
    public Collection<Object> search(RaiderEsDto raiderEsDto) throws IOException {
        Objects.requireNonNull(raiderEsDto,"参数为空");
        String index = RiskIcToIndexEnum.getValueByCode(raiderEsDto.getInterfaceCode());
        MatchAllQueryBuilder allQueryBuilder = QueryBuilders.matchAllQuery();

//        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
//        MatchQueryBuilder matchQuery = QueryBuilders.matchQuery("registerInfo.enterpriseName", raiderEsDto.getCompanyName());
//        IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery().addIds(String.valueOf(raiderEsDto.getCompanyId()));
//        boolQuery.should(matchQuery).should(idsQueryBuilder);
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder().query(allQueryBuilder);

//        sourceBuilder.sort(SortBuilders.fieldSort())
//        sourceBuilder.from(1).size(15);
        SearchRequest request = new SearchRequest(index).searchType(SearchType.DEFAULT).source(sourceBuilder);
        SearchResponse response = restHighLevelClient.search(request, null); // 依赖版本不同，第二个参数暂时传nul
        SearchHits hits = response.getHits();
        List<Object> collect = Arrays.stream(hits.getHits()).map(SearchHit::getSourceAsMap).collect(Collectors.toList());
        return collect;
    }





}
