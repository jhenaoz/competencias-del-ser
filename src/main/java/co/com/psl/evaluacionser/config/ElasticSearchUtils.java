package co.com.psl.evaluacionser.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

/**
 * ElasticSearchUtils class provide a singleton client for elasticsearch as a bean
 */
@Configuration
public class ElasticSearchUtils {

	//TODO To define witch class we're going to use, JestCLientUtils or ElasticSearchUtils, because are the same
	
	/**
	 * Application property for the host and the port
	 */
	@Value("${elasticport}")
	private String port;

	@Value("${elastichost}")
	private String host;

	/**
	 * This method calls the properties file to define the host and the port
	 * @return client Bean for elasticsearch who can be consumed in all the app
	 */
	@Bean
	public JestClient getClient() {
		String url = host + ":" + port;
		JestClient client;
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(url).multiThreaded(true).build());
		client = factory.getObject();
		return client;
	}

}