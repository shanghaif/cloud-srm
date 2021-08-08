package com.midea.cloud.log.disruptor;

import com.lmax.disruptor.EventFactory;
import org.elasticsearch.action.index.IndexRequest;



/**
 * @author tanjl11
 * @date 2020/12/18 14:03
 */
public class LogDocumentIndexFactory implements EventFactory<IndexRequest> {

    @Override
    public IndexRequest newInstance() {
        return new IndexRequest();
    }
}
