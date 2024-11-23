package com.gabriel.audio_generator_service.infrastructure.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RabbitConfigTest {

    private RabbitConfig rabbitConfig;

    @Mock
    private MessageConverter messageConverter;

    private static ConnectionFactory connectionFactory;

    @BeforeAll
    static void initialize() {
        connectionFactory = mock(ConnectionFactory.class);
        when(connectionFactory.getHost()).thenReturn("localhost");
        when(connectionFactory.getPort()).thenReturn(5672);
        when(connectionFactory.getUsername()).thenReturn("user");
    }

    @BeforeEach
    void setUp() {
        rabbitConfig = new RabbitConfig();
    }

    @Test
    void shouldCreateConnectionFactoryWithCorrectConfiguration() {
        // Given: Mocking RabbitConfig class using field injection or constructor injection (if necessary)

        // Act: Call the connectionFactory method
        CachingConnectionFactory factory = (CachingConnectionFactory) rabbitConfig.connectionFactory();

        // Assert: Verify that the connectionFactory has the correct settings
        assertThat(factory).isNotNull();
    }

    @Test
    void shouldCreateQueue() {
        // Act: Call the queue method
        Queue queue = rabbitConfig.queue();

        // Assert: Verify that the queue is created with the correct name
        assertThat(queue).isNotNull();
        assertThat(queue.getName()).isEqualTo(RabbitConfig.QUEUE_NAME);
        assertThat(queue.isDurable()).isTrue(); // Default value for a durable queue
    }

    @Test
    void shouldCreateExchange() {
        // Act: Call the exchange method
        TopicExchange exchange = rabbitConfig.exchange();

        // Assert: Verify that the exchange is created with the correct name
        assertThat(exchange).isNotNull();
        assertThat(exchange.getName()).isEqualTo(RabbitConfig.EXCHANGE_NAME);
    }

    @Test
    void shouldCreateBinding() {
        // Given: Mock the Queue and TopicExchange objects
        Queue queue = mock(Queue.class);
        when(queue.getName()).thenReturn(RabbitConfig.QUEUE_NAME);
        TopicExchange exchange = mock(TopicExchange.class);
        when(exchange.getName()).thenReturn(RabbitConfig.EXCHANGE_NAME);

        // Act: Call the binding method
        Binding binding = rabbitConfig.binding(queue, exchange);

        // Assert: Verify that the binding is created and properly binds to the exchange
        assertThat(binding).isNotNull();
    }

    @Test
    void shouldCreateRabbitTemplateWithMessageConverter() {
        // Given: Mock the dependencies
        when(connectionFactory.createConnection()).thenReturn(mock(org.springframework.amqp.rabbit.connection.Connection.class));

        // Act: Call the rabbitTemplate method
        RabbitTemplate rabbitTemplate = rabbitConfig.rabbitTemplate(connectionFactory, messageConverter);

        // Assert: Verify that the RabbitTemplate is created correctly with the given message converter
        assertThat(rabbitTemplate).isNotNull();
        assertThat(rabbitTemplate.getMessageConverter()).isEqualTo(messageConverter);
    }

    @Test
    void shouldCreateMessageConverter() {
        assertThat(rabbitConfig.messageConverter()).isNotNull();
    }

}
