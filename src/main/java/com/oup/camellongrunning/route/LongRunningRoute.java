package com.oup.camellongrunning.route;

import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LongRunningRoute extends RouteBuilder{

    @Override
    public void configure() throws Exception {
        interceptSendToEndpoint("*").log(LoggingLevel.INFO, org.slf4j.LoggerFactory.getLogger(this.getClass()),"OUTGOING :: RouteID :: ${routeId}\nMessageID ::${id}\nHeaders ::${headers}\nBODY ::${body}");
        interceptFrom("*").log(LoggingLevel.INFO, org.slf4j.LoggerFactory.getLogger(this.getClass()),"INCOMING :: RouteID :: ${routeId}\nMessageID ::${id}\nHeaders ::${headers}\nBODY ::${body}");
        
        // TODO Auto-generated method stub
        from("timer://foo?repeatCount=1").routeId("id_SampleRoute")
        .log(LoggingLevel.INFO,log,"Timer Route Started")
        .delay(120000)
        .to("mock:test");
        
    }

    
    
}
