package com.oup.camellongrunning.route;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LongRunningRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
			interceptFrom().process(new org.apache.camel.Processor() {
            @Override
            public void process( org.apache.camel.Exchange exchange) throws Exception {
                if(exchange.getProperty("ALREADY_LOGGED")==null) {
                    org.slf4j.LoggerFactory.getLogger(this.getClass()).info("MESSAGE ARRIVED");
                    exchange.setProperty("ALREADY_LOGGED", "Y");
                }
                else if (exchange.getProperty("ALREADY_LOGGED").toString().equalsIgnoreCase("Y")
                        && exchange.getProperty("KAFKA_ALREADY_LOGGED")==null
                        && exchange.getFromEndpoint().getClass().getName().toLowerCase().contains("kafka")) {

                    org.slf4j.LoggerFactory.getLogger(this.getClass()).info("MESSAGE ARRIVED");
                    exchange.setProperty("KAFKA_ALREADY_LOGGED", "Y");
                }
            }
        });
        // TODO Auto-generated method stub
        from("timer://foo?repeatCount=1").routeId("id_SampleRoute")
                .log(LoggingLevel.INFO,log,"Timer Route Started")
                //.delay(120000)
                .wireTap("direct:dummy")
                .to("direct:dummy");

        from("direct:dummy")
                .log(LoggingLevel.INFO,log,"Second route");

    }



}
