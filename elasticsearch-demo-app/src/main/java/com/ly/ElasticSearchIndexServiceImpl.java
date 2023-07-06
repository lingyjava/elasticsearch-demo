package com.ly;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.indices.CreateIndexResponse;
import co.elastic.clients.elasticsearch.indices.DeleteIndexResponse;
import co.elastic.clients.elasticsearch.indices.GetIndexResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.ly.api.ElasticSearchIndexServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ElasticSearchIndexServiceImpl implements ElasticSearchIndexServiceI {

    @Autowired
    private ElasticsearchClient client;

    /**
     * create index
     * */
    @Override
    public void create(String indexName) throws IOException {
        //写法比RestHighLevelClient更加简洁
        CreateIndexResponse indexResponse = client.indices().create(c -> c.index(indexName));
    }

    /**
     * query index
     * */
    @Override
    public void query(String indexName) throws IOException {
        GetIndexResponse getIndexResponse = client.indices().get(i -> i.index(indexName));
    }

    @Override
    public void exists(String indexName) throws IOException {
        BooleanResponse booleanResponse = client.indices().exists(e -> e.index(indexName));
        System.out.println(booleanResponse.value());
    }

    @Override
    public void delete(String indexName) throws IOException {
        DeleteIndexResponse deleteIndexResponse = client.indices().delete(d -> d.index(indexName));
        System.out.println(deleteIndexResponse.acknowledged());
    }
}
