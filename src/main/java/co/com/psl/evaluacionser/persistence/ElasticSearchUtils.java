package co.com.psl.evaluacionser.persistence;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.config.HttpClientConfig;
import org.springframework.beans.factory.annotation.Value;

import java.net.Inet4Address;

public class ElasticSearchUtils {

    // TODO: Parameterize me!
    @Value("${elasticport}")
    private static String port;

    @Value("${elastichost}")
    private static String host;

    private static String ELASTIC_SEARCH_URL = host + ":" + port;

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