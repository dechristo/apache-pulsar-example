package app.services;

import org.apache.pulsar.client.api.PulsarClient;
import org.springframework.stereotype.Service;

@Service
public class PulsarClientService {
    private static final String SERVICE_URL = "pulsar://localhost:6650";
    private PulsarClient client;

    public PulsarClientService() {}

    public PulsarClient getClient() {
        if (this.client != null) {
            return this.client;
        }

        try {
            client = PulsarClient.builder()
                .serviceUrl(SERVICE_URL)
                .build();

            return client;
        } catch (Exception ex) {
            return null;
        }
    }
}
