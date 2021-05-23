package com.bouger.bouger_etl.Controller;

import ch.qos.logback.core.joran.conditional.ElseAction;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.lang.Console;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.Request;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.PutIndexTemplateRequest;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.jboss.jandex.Index;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

/**
 * @program:bouger_etl
 * @description:
 * @author:Bouger
 * @date:2021-05-23 18:17:38
 **/
@Controller
@RequestMapping("/elasticsearch_index")
public class EsCURDController {
    @Autowired
    RestHighLevelClient highLevelClient;

    private ElasticsearchOperations elasticsearchOperations;

    public EsCURDController(ElasticsearchOperations elasticsearchOperations){
        this.elasticsearchOperations = elasticsearchOperations;
    }

    @PostMapping("/create")
    public void createIndex() throws IOException{
        CreateIndexRequest request = new CreateIndexRequest("goods_index_tmp");
        CreateIndexResponse createIndexResponse = highLevelClient.indices().create(request, RequestOptions.DEFAULT);
        if (createIndexResponse.isAcknowledged()) {
//            Console.log("创建index成功！");
            Console.log("创建index成功！");
        }else {
            Console.log("创建index失败！");
        }
    }
    @GetMapping("/template")
    public void putTemplate() throws IOException {
        PutIndexTemplateRequest request = new PutIndexTemplateRequest("goods_template");
        request.alias(new Alias("goods_index"));
        request.order(10);
        request.patterns(CollUtil.newArrayList("goods_index*"));
        request.settings(Settings.builder()
        .put("index.refresh_interval","1s")
        .put("index.translog.durability","async")
        .put("index.translog.sync_interval","120s")
        .put("index.number_of_shards","5")
        .put("index.number_of_replicas","0")
        .put("index.max_result_window","100000"));

        XContentBuilder jsonMapping = XContentFactory.jsonBuilder();
        jsonMapping.startObject().startObject("properties")
                .startObject("goodsName")
                .field("type","text")
                .field("analyzer","ik_max_word").endObject()
                .startObject("goodsId").field("type","integer").endObject()
                .startObject("connet").field("type","text").endObject()
                .endObject().endObject();
        request.mapping(jsonMapping);
        request.create(false);
        AcknowledgedResponse response = highLevelClient.indices().putTemplate(request, RequestOptions.DEFAULT);
        if (response.isAcknowledged()) {
            Console.log("创建模板成功！");
        }else {
            Console.log("创建模板失败！");
        }
    }
/*
    @GetMapping("/template2")
    public void putTemplate2()throws IOException{
        PutIndexTemplateRequest request = new PutIndexTemplateRequest("goods_template");
        //别名，所有根据该模版创建的index 都会添加这个别名。查询时可查询别名，就可以同时查询多个名称不同的index，根据此方法可实现index每天或每月生成等逻辑。
        request.alias(new Alias("goods_index"));
        request.order(10);
        //匹配哪些index。在创建index时会生效。
        request.patterns(CollUtil.newArrayList("goods_index*"));
        request.settings(Settings.builder()
                //数据插入后多久能查到，实时性要求高可以调低
                .put("index.refresh_interval", "1s")
                //传输日志，对数据安全性要求高的 设置 request，默认值:request
                .put("index.translog.durability", "async")
                .put("index.translog.sync_interval", "120s")
                //分片数量
                .put("index.number_of_shards", "5")
                //副本数量
                .put("index.number_of_replicas", "0")
                //单次最大查询数据的数量。默认10000。不要设置太高，如果有导出需求可以根据查询条件分批次查询。
                .put("index.max_result_window", "100000"));
        //使用官方提供的工具构建json。可以直接拼接一个json字符串，也可以使用map嵌套。
        XContentBuilder jsonMapping = XContentFactory.jsonBuilder();
        //所有数据类型 看官方文档:https://www.elastic.co/guide/en/elasticsearch/reference/7.4/mapping-types.html#_core_datatypes
        jsonMapping.startObject().startObject("properties")
                .startObject("goodsName").field("type", "text").field("analyzer", "ik_max_word").endObject()
                .startObject("goodsId").field("type", "integer").endObject()
                //keyword类型不会分词存储
                .startObject("connet").field("type", "text").endObject()
                //指定分词器
                .endObject().endObject();
        request.mapping(jsonMapping);
        //设置为true只强制创建，而不是更新索引模板。如果它已经存在，它将失败
        request.create(false);
        AcknowledgedResponse response = highLevelClient.indices().putTemplate(request, RequestOptions.DEFAULT);
        if (response.isAcknowledged()) {
            Console.log("创建模版成功!");
        } else {
            Console.log("创建模版失败!");
        }
    }*/
}
