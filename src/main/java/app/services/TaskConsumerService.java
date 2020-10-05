package app.services;

import app.models.Task;
import org.apache.pulsar.client.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import app.repositories.TaskRepository;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

@Service
public class TaskConsumerService {
    private static final String TOPIC_NAME = "my-tasks";
    private Consumer<byte[]> consumer;
    @Autowired
    PulsarClientService pulsarClientService;

    @Autowired
    TaskRepository repository;

    public void init() {
        try {
            consumer = pulsarClientService.getClient().newConsumer()
                .topic(TOPIC_NAME)
                .subscriptionType(SubscriptionType.Shared)
                .subscriptionName("my-tasks-subscription")
                .subscribe();

        } catch (Exception ex) {
            int a = 2;
        }
    }

    @Scheduled(fixedRate = 1)
    public void receive() throws PulsarClientException{
        Message<byte[]> msg = consumer.receive();
        try {
            // Wait for a message

            System.out.printf("Message received: %s", new String(msg.getData()));
            consumer.acknowledge(msg);

            ByteArrayInputStream bis = new ByteArrayInputStream(msg.getData());
            ObjectInput in = new ObjectInputStream(bis);
            Task task = (Task) in.readObject();
            in.close();
            repository.save(task);

        } catch (Exception e) {
            consumer.negativeAcknowledge(msg);
        }
    }
}
