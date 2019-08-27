package com.scatler.ttable.controller;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.scatler.ttable.dto.StationTimeTableWrapper;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeoutException;

@Startup
@Singleton
public class MainController {

    private final static String QUEUE_NAME = "hello";
    private static final String COMMAND_UPDATE = "Update";
    private static final Integer STATION_ID = 1011; // Ишим

    @Inject
    StationTimeTableWrapper wrapper;

    @Inject
    RestService restService;

    @Inject
    TimeTableEndpoint timeTableEndpoint;

    @PostConstruct
    void init() throws IOException, TimeoutException {

        wrapper = restService.requestUpdate(STATION_ID);

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = null;
        connection = factory.newConnection();
        Channel channel = null;
        channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages.");
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
/*                //------------------------------------------------------------------------------------
                List<TrainDTO> dtoList = new ArrayList<>();
                dtoList.add(new TrainDTO(1,5247, "Train 1", new Date(2019, 8, 26)));
                dtoList.add(new TrainDTO(1, 5247,"Train 2", new Date(2019, 8, 27)));
                TestMessage testMessage = new TestMessage(dtoList);
                //------------------------------------------------------------------------------------*/
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
                if (message.equals(COMMAND_UPDATE)) {
                    //Request new data
                    wrapper = restService.requestUpdate(STATION_ID);
                    //Find changes
                    //Display with changes
                    timeTableEndpoint.send(wrapper);
                }
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
    }
}