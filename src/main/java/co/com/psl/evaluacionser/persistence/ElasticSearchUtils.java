package co.com.psl.evaluacionser.persistence;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ElasticSearchUtils {

    @Value("${elasticport}")
    private String port;

    @Value("${elastichost}")
    private String host;

    private String ELASTIC_SEARCH_URL = host + ":" + port;

    private JestClientFactory factory = new JestClientFactory();

    @Bean
    public JestClient getClient() {
        factory.setHttpClientConfig(new HttpClientConfig.Builder(ELASTIC_SEARCH_URL).multiThreaded(true).build());
        return factory.getObject();
    }
}