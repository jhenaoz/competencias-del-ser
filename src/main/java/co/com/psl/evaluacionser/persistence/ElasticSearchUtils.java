package co.com.psl.evaluacionser.persistence;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;

public class ElasticSearchUtils {

    // TODO: Parameterize me!
    private static final String ELASTIC_SEARCH_URL = "http://localhost:9200";

    private static JestClientFactory factory = new JestClientFactory();

    public static synchronized JestClient getClient() {
        factory.setHttpClientConfig(new HttpClientConfig.Builder(ELASTIC_SEARCH_URL).multiThreaded(true).build());
        return factory.getObject();
    }

    public static void closeClient(JestClient client) {
        if (client != null) {
            try {
                client.shutdownClient();
            } catch (Exception ex) {
                // TODO: Log me!
            }
        }
    }
}