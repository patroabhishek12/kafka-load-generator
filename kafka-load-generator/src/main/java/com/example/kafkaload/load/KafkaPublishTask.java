package com.example.kafkaload.load;

import com.example.kafkaload.service.KafkaLoadService;

import com.github.myzhan.locust4j.AbstractTask;
import com.github.myzhan.locust4j.stats.Stats;
import org.springframework.stereotype.Component;

@Component
// Locust task implemented against locust4j 2.2.5 API.
// Task metadata is provided via getName()/getWeight() rather than annotations.
public class KafkaPublishTask extends AbstractTask {

    private final KafkaLoadService kafkaLoadService;

    public KafkaPublishTask(KafkaLoadService kafkaLoadService) {
        this.kafkaLoadService = kafkaLoadService;
    }

    @Override
    public String getName() {
        return "KafkaPublishTask";
    }

    @Override
    public int getWeight() {
        return 1;
    }

    @Override
    public void execute() {
        long start = System.currentTimeMillis();
        try {
            String payload = buildPayload();

            kafkaLoadService.sendTestMessage(payload);

            long elapsed = System.currentTimeMillis() - start;
            Stats.getInstance().logRequest("kafka", "publish", elapsed, 0L);
        } catch (Exception e) {
            long elapsed = System.currentTimeMillis() - start;
            Stats.getInstance().logError("kafka", "publish", e.toString());
        }
    }

    private String buildPayload() {
        return "{\"event\":\"load-test\",\"timestamp\":" + System.currentTimeMillis() + "}";
    }
}
