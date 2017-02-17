package co.com.psl.evaluacionser.config;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class JestClientUtils {

    //Application var for the host and the port
    @Value("${elasticport}")
    private String port;

    @Value("${elastichost}")
    private String host;

    @Bean
    public JestClient getClient() {
        String elasticsearchUrl = "http://" + host + ":" + port;
        JestClient client;
        JestClientFactory factory = new JestClientFactory();
        factory.setHttpClientConfig(new HttpClientConfig.Builder(elasticsearchUrl).multiThreaded(true).build());
        client = factory.getObject();
        return client;
    }
}
