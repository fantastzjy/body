package com.mall.search.config;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
public class MallElasticSearchConfig {

    public static final RequestOptions COMMON_OPTIONS;

    /**
     * 通用设置项   对全部请求设置  内容加上注解，暂时不设置
     */
    static {
        RequestOptions.Builder builder = RequestOptions.DEFAULT.toBuilder();
        // builder.addHeader("Authorization", "Bearer " + TOKEN);
        // builder.setHttpAsyncResponseConsumerFactory(
        //         new HttpAsyncResponseConsumerFactory
        //                 .HeapBufferedResponseConsumerFactory(30 * 1024 * 1024 * 1024));
        COMMON_OPTIONS = builder.build();
    }

    /**
     * 作为bean放到容器中  以后操作es就用引入该类即可
     * @return
     */
    @Bean
    public RestHighLevelClient esRestClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("121.43.231.169", 9200, "http")
                        // 如果此处是集群，传入多个主机就可以了
                        //,new HttpHost("192.168.56.10", 9200, "http")
                ));
        return  client;
    }

}
