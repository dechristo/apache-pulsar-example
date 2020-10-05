package app.services;

import app.models.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class TaskService {

    @Autowired
    TaskProducerService taskProducerService;

    @Autowired
    TaskConsumerService taskConsumerService;

    @PostConstruct
    public void init() {
        taskConsumerService.init();
        taskProducerService.init();
    }

    public String createTask(Task task) {
        this.taskProducerService.produce(task);
        return "Added.";
    }
}
