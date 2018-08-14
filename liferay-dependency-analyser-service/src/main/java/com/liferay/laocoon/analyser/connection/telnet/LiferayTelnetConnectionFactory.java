package com.liferay.laocoon.analyser.connection.telnet;

import com.liferay.laocoon.analyser.configuration.LiferayConnectionConfigurtation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Slf4j
@Service
public class LiferayTelnetConnectionFactory {

    public LiferayTelnetConnection getConnection() {
        LiferayTelnetConnection liferayTelnetConnection =
            new LiferayTelnetConnection();

        liferayTelnetConnection.connect(
            liferayConnectionConfigurtation.getHost(),
            liferayConnectionConfigurtation.getPort());

        log.debug("Successfully connected to Liferay");

        return liferayTelnetConnection;
    }

    private final LiferayConnectionConfigurtation
        liferayConnectionConfigurtation;

}
