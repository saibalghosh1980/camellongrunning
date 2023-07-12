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

        onException(Exception.class).useOriginalMessage()

                .log(LoggingLevel.ERROR, log, "Excepion occurred in Route. Exception : ${exception.message}")

                .log(LoggingLevel.WARN, log, "${exception.stacktrace}")

                .end();
        // TODO Auto-generated method stub
        from("timer://foo?fixedRate=true&period=300000").routeId("id_SampleRoute")
                .log(LoggingLevel.INFO, log, "Timer Route Started")
                //.delay(120000)
                //.wireTap("direct:dummy")
                .to("direct:dummy");

        from("direct:dummy")
                .to("https://reqres.in/api/users/2")
                .log(LoggingLevel.INFO, log, "${body}");

    }


}
