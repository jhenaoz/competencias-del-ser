package co.com.psl.evaluacionser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

/**
 * This class provide a singleton client for elasticsearch as a bean
 * 
 * @author salveara
 *
 */
@Configuration
public class JestClientUtils {

	// TODO this has to be parametrized in a properties file
	private static final String ELASTIC_SEARCH_URL = "http://localhost:9200";

	//Creates the bean for be consumed in all the app
	@Bean
	public JestClient getClient() {
		JestClient client;
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(ELASTIC_SEARCH_URL).multiThreaded(true).build());
		client = factory.getObject();

		return client;
	}

}