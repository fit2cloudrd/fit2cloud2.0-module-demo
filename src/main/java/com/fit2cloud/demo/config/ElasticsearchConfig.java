package com.fit2cloud.demo.config;

import org.elasticsearch.common.settings.Settings;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

import javax.annotation.Resource;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.fit2cloud.commons.server.elastic.dao")
public class ElasticsearchConfig {

    @Resource
    private Environment environment;

    private Settings settings() {
        return Settings.builder()
                .put("cluster.name", environment.getProperty("spring.data.elasticsearch.cluster-name"))
                .put("client.transport.sniff", true).build();
    }

    /*@Bean
    public TransportClient elasticsearchClient() throws Exception {
        TransportClient preBuiltTransportClient = new PreBuiltTransportClient(settings());
        preBuiltTransportClient.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("43.254.45.84"), 53396));
        return preBuiltTransportClient;
    }*/
}
