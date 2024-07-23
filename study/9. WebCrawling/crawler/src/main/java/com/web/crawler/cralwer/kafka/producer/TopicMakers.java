package com.web.crawler.cralwer.kafka.producer;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.DeleteTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.concurrent.ExecutionException;

@Service
public class TopicMakers {

    private final AdminClient adminClient;

    @Autowired
    public TopicMakers(AdminClient adminClient) {
        this.adminClient = adminClient;
    }

    public void createTopic(String topicName, int partitions, int replicas) throws ExecutionException, InterruptedException {
        NewTopic newTopic = new NewTopic(topicName, partitions, (short) replicas);
        CreateTopicsResult result = adminClient.createTopics(Collections.singleton(newTopic));
        result.all().get(); // Block until the operation is completed
        System.out.println("Topic created: " + topicName);
    }

    public void deleteTopic(String topicName) throws ExecutionException, InterruptedException {
        DeleteTopicsResult result = adminClient.deleteTopics(Collections.singleton(topicName));
        result.all().get(); // Block until the operation is completed
        System.out.println("Topic deleted: " + topicName);
    }


}
