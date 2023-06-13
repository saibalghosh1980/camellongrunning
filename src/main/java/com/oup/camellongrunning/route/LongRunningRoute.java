package com.oup.camellongrunning.route;

import lombok.extern.slf4j.Slf4j;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class LongRunningRoute extends RouteBuilder {

    @Override
    public void configure() throws Exception {
       
        // TODO Auto-generated method stub
        from("timer://foo?repeatCount=1").routeId("id_SampleRoute")
                .log(LoggingLevel.INFO, log, "Timer Route Started")
                //.delay(120000)
                .wireTap("direct:dummy")
                .to("direct:dummy");

        from("direct:dummy")
                .log(LoggingLevel.INFO, log, "Second route");

    }


}
