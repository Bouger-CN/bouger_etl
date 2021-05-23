package com.bouger.bouger_etl.config;

import com.sun.deploy.config.ClientConfig;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;

/**
 * @program:bouger_etl
 * @description:
 * @author:Bouger
 * @date:2021-05-23 18:06:29
 **/
public class RestClientConfig extends AbstractElasticsearchConfiguration {
    @Override
    public RestHighLevelClient elasticsearchClient() {
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("localhost:9200").
                        build();
        return RestClients.create(clientConfiguration).rest();
    }
}
