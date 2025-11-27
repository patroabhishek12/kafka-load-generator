package com.example.kafkaload;

import com.example.kafkaload.load.KafkaPublishTask;
import com.github.myzhan.locust4j.Locust;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class KafkaLoadGeneratorApplication implements CommandLineRunner {

    private final KafkaPublishTask kafkaPublishTask;

    @Value("${locust.master-host}")
    private String masterHost;

    @Value("${locust.master-port}")
    private int masterPort;

    public KafkaLoadGeneratorApplication(KafkaPublishTask kafkaPublishTask) {
        this.kafkaPublishTask = kafkaPublishTask;
    }

    public static void main(String[] args) {
        SpringApplication.run(KafkaLoadGeneratorApplication.class, args);
    }

    @Override
    public void run(String... args) {
        // Configure and run Locust worker with our Kafka task.
        // Note: This uses the locust4j 2.2.5 API (Locust singleton, setMasterHost/setMasterPort, run(AbstractTask...)).
        Locust locust = Locust.getInstance();
        locust.setMasterHost(masterHost);
        locust.setMasterPort(masterPort);
        locust.run(kafkaPublishTask);
    }
}
