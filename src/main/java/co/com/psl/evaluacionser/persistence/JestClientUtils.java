package co.com.psl.evaluacionser.persistence;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class JestClientUtils {

    private static final String ELASTIC_SEARCH_URL = "http://localhost:9200";

	private static JestClient client;

	private JestClientUtils() {
	}

	public static synchronized JestClient getClient() {
		if (client == null) {
			JestClientFactory factory = new JestClientFactory();
			factory.setHttpClientConfig(new HttpClientConfig.Builder(ELASTIC_SEARCH_URL).multiThreaded(true).build());
			client = factory.getObject();
		}

		return client;
	}

	public static void closeClient() {
		if (client != null) {
			try {
				client.shutdownClient();
			} catch (Exception e) {
			}
		}
	}
}

