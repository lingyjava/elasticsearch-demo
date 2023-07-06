package com.ly.api;

import java.io.IOException;

/**
 * @author Ly
 * ProductSearchServiceI
 */
public interface ElasticSearchIndexServiceI {

    /**
     * create index for product
     * @throws IOException if an error occurs , create failed
     */
    void create(String indexName) throws IOException;


    /**
     * query index
     * @throws IOException if an error occurs , query failed
     * */
    void query(String indexName) throws IOException;

    /**
     * exists index
     * */
    public void exists(String indexName) throws IOException;

    /**
     * delete index
     * */
    public void delete(String indexName) throws IOException;
}
