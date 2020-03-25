package com.practical.rabbitmq.headers.controller;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/rabbitmq/headers")
public class RabbitMQHeadersWebController {
  @Autowired private AmqpTemplate amqpTemplate;

  @GetMapping(value = "/producer")
  public String producer(
      @RequestParam("exchangeName") String exchange,
      @RequestParam("department") String department,
      @RequestParam("messageData") String messageData) {

    MessageProperties messageProperties = new MessageProperties();
    messageProperties.setHeader("department", department);
    MessageConverter messageConverter = new SimpleMessageConverter();
    Message message = messageConverter.toMessage(messageData, messageProperties);
    amqpTemplate.send(exchange, "", message);

    return "message sent to rabbitmq";
  }
}
