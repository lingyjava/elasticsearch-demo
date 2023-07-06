# ElasticSearch-demo



## 官方文档

[ElasticSearch权威指南（基本2.x版本，部分内容过时，但文档比较全）](https://www.elastic.co/guide/cn/elasticsearch/guide/current/running-elasticsearch.html)

[ElasticSearch各版本文档，遇到版本差异时查阅](https://www.elastic.co/guide/en/elasticsearch/reference/8.7/sort-search-results.html)

[Elastic各工具软件下载](https://www.elastic.co/cn/downloads/)

## 安装 & 运行

1. [下载ElasticSearch](https://www.elastic.co/cn/downloads/elasticsearch)
2. 解压文件之后，启动`bin\elasticsearch` ，如果是在`Windows`上运行，应该启动 `bin\elasticsearch.bat` 。
3. 访问`http://localhost:9200/?pretty`测试是否启动成功。
4. [下载Kibana（实现Es数据可视化），高版本中提供DevTool，已不需要安装Sense](https://www.elastic.co/cn/downloads/kibana)
5. 启动`./bin/kibana`，如果是在`Windows`上运行，应该启动 `bin\kibana.bat` 。
6. 打开网页`http://localhost:5601/`访问Kibana。

## 索引 & 搜索命令

在Kibana中可以配置变量，可以缩短常用命令，以下为定义变量和使用变量的方式案例

`${exampleVariable1}` -> `_search`

`${exampleVariable2}` -> `match_all`

```D
# Click the Variables button, above, to create your own variables.
    
GET ${exampleVariable1} // _search
{
  "query": {
    "${exampleVariable2}": {} // match_all
  }
}
```



以下为参照官方文档使用搜索的命令

```D
// 查询所有记录总数
GET /_count
{
    "query": {
        "match_all": {}
    }
}
// 索引一条记录，/megacorp/代表索引名称，不存在时自动创建，/1/代表记录id
PUT /megacorp/_doc/1
{
    "first_name" : "John",
    "last_name" :  "Smith",
    "age" :        25,
    "about" :      "I love to go rock climbing",
    "interests": [ "sports", "music" ]
}
PUT /megacorp/_doc/2
{
    "first_name" :  "Jane",
    "last_name" :   "Smith",
    "age" :         32,
    "about" :       "I like to collect rock albums",
    "interests":  [ "music" ]
}
PUT /megacorp/_doc/3
{
    "first_name" :  "Douglas",
    "last_name" :   "Fir",
    "age" :         35,
    "about":        "I like to build cabinets",
    "interests":  [ "forestry" ]
}

// 搜索一条记录 /megacorp/代表索引名称，_doc代表搜索方式，/1/代表搜索id
GET /megacorp/_doc/1

// 按条件搜索 /_search代表搜索方式，使用条件搜索，bool用于包含多重条件，
GET /megacorp/_search
{
  "query": {
    "bool": {
      "must": [
        {
          "match": {
            "last_name": "Smith"
          }
        }
        
      ],
      "filter": [
        {
          "range": {
            "age": {
              "gte": 30,
              "lte": 100
            }
          }
        }
      ]
    }
  }
}

// 按条件搜索，match匹配，在rock climbing其一存在时就能搜索出来，拥有不同的相关性评分
GET /megacorp/_search
{
    "query" : {
        "match" : {
            "about" : "rock climbing"
        }
    }
}

// 按条件搜索，match_phrase代表短语匹配，必须当rock climbing都存在时才搜索出来
GET /megacorp/_search
{
    "query" : {
        "match_phrase": {
            "about" : "rock climbing"
        }
    },
    "highlight": {
      "fields": {
        "about": {}
      }
    }
}

// 按条件搜索，aggs代表数据分组统计，可以嵌套使用
GET /megacorp/_search
{
  "aggs": {
    "all_interests": {
      "terms": 
      { 
        "field": "interests.keyword" 
      }
    }
  }
}



```

## 其他来源文档

[p-dai，ElasticSearch知识体系详解](https://pdai.tech/md/db/nosql-es/elasticsearch.html)

[使用 Spring Boot 创建 Elasticsearch API Client(8.0.1) 对象时出错](https://discuss.elastic.co/t/error-while-creating-elasticsearch-api-client-8-0-1-object-using-spring-boot/300263)

[【ElasticSearch8】SpringBoot集成ElasticSearch8.x 基本应用](https://blog.csdn.net/yscjhghngh/article/details/123620860)