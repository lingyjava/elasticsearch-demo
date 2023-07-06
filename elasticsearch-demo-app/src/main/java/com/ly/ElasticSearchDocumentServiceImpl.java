package com.ly;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch.core.BulkResponse;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.UpdateResponse;
import co.elastic.clients.elasticsearch.core.bulk.BulkOperation;
import co.elastic.clients.elasticsearch.core.search.Hit;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import com.ly.api.ElasticSearchDocumentServiceI;
import com.ly.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Ly
 */
@Service
public class ElasticSearchDocumentServiceImpl implements ElasticSearchDocumentServiceI {

    @Autowired
    private ElasticsearchClient client;

    private final String indexName = "users";

    @Override
    public void add(UserDTO user) throws IOException {
        IndexResponse indexResponse = client.index(i -> i
                .index(indexName)
                //设置id
                .id(user.getUserId())
                //传入对象
                .document(user));
    }

    @Override
    public void update(UserDTO user) throws IOException {
        UpdateResponse<UserDTO> updateResponse = client.update(u -> u
                        .index(indexName)
                        .id(user.getUserId())
                        .doc(user), UserDTO.class);
    }

    @Override
    public Boolean exist(UserDTO user) throws IOException {
        BooleanResponse indexResponse = client.exists(e -> e.index(indexName).id(user.getUserId()));
        return indexResponse.value();
    }

    @Override
    public UserDTO get(UserDTO user) throws IOException {
        GetResponse<UserDTO> getResponse = client.get(g -> g
                        .index(indexName)
                        .id(user.getUserId())
                , UserDTO.class
        );
        return getResponse.source();
    }

    @Override
    public void delete(UserDTO user) throws IOException {
        DeleteResponse deleteResponse = client.delete(d -> d
                .index(indexName)
                .id(user.getUserId())
        );
        System.out.println(deleteResponse.id());
    }

    @Override
    public void bulk(List<UserDTO> userList) throws IOException {
        List<BulkOperation> bulkOperationArrayList = new ArrayList<>();
        //遍历添加到bulk中
        for(UserDTO user : userList){
            bulkOperationArrayList.add(BulkOperation.of(o->o.index(i->i.document(user))));
        }
        BulkResponse bulkResponse = client.bulk(b -> b.index(indexName)
                .operations(bulkOperationArrayList));
    }

    @Override
    public List<UserDTO> search(UserDTO user) throws IOException {
        SearchResponse<UserDTO> search = client.search(s -> s
                .index(indexName)
                //查询name字段包含hello的document(不使用分词器精确查找)
                .query(q -> q
                        .term(t -> t
                                .field("username")
                                .value(v -> v.stringValue(user.getUsername()))
                        ))
                //分页查询，从第0页开始查询3个document
                .from(0)
                .size(3)
                //按age降序排序
                .sort(f->f.field(o->o.field("age").order(SortOrder.Desc))),UserDTO.class
        );

        List<UserDTO> result = new ArrayList<UserDTO>();
        for (Hit<UserDTO> hit : search.hits().hits()) {
            System.out.println(hit.source());
            result.add(hit.source());
        }
        return result;
    }
}
