package app.services;

import app.models.Task;
import org.apache.pulsar.client.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

@Service
public class TaskProducerService {

    private static final String TOPIC_NAME = "my-tasks";
    private Producer<byte[]> producer = null;

    @Autowired
    PulsarClientService pulsarClientService;

    public boolean init() {
       try {

           this.producer = pulsarClientService.getClient()
               .newProducer()
               .topic(TOPIC_NAME)
               .compressionType(CompressionType.LZ4)
               .create();

           return true;
       } catch (Exception ex) {
            return false;
       }
   }

   public String produce(Task task) {
       try {
           ByteArrayOutputStream bos = new ByteArrayOutputStream();
           ObjectOutputStream oos = new ObjectOutputStream(bos);
           oos.writeObject(task);
           oos.flush();
           oos.close();
           byte [] data = bos.toByteArray();

           TypedMessageBuilder<byte[]> message = producer.newMessage()
               .key("newTask")
               .value(data);
           message.send();

           return "Msg sent.";
       } catch (Exception ex) {
        return ex.getMessage();
       }
   }
}
