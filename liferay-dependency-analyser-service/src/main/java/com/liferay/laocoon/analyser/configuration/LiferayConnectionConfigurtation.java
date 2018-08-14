package com.liferay.laocoon.analyser.configuration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "liferay.connection")
@Component
public class LiferayConnectionConfigurtation {

    private String host;
    private int port;

}
