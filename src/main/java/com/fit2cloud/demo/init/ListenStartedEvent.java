package com.fit2cloud.demo.init;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 程序启动后触发执行的逻辑
 */
@Component
public class ListenStartedEvent {

    @EventListener
    public void init(ApplicationStartedEvent event) {
        System.out.println("event 启动执行");
    }
}
