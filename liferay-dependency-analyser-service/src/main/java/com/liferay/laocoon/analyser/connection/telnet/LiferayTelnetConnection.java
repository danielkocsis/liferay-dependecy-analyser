package com.liferay.laocoon.analyser.connection.telnet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.telnet.TelnetClient;
import org.apache.commons.net.telnet.TelnetCommand;
import org.apache.commons.net.telnet.TelnetOption;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

@Slf4j
public class LiferayTelnetConnection implements AutoCloseable {

    private static final String PROMPT = "g!";
    private final TelnetClient telnetClient = new TelnetClient("dumb");

    private InputStream inputStream;
    private PrintStream outputStream;

    public void connect(final String host, final int port) {
        try {
            log.debug("Attempting to connect to Liferay...");

            telnetClient.setConnectTimeout(10000);
            telnetClient.connect(host, port);
            telnetClient.setSoTimeout(10000);
            telnetClient.sendCommand(
                (byte) (TelnetCommand.DONT | TelnetOption.ECHO));

            inputStream = telnetClient.getInputStream();
            outputStream = new PrintStream(telnetClient.getOutputStream());

            String s = readUntil(PROMPT);

            log.debug("Welcome: " + s);

            sendCommand("stty -echo");
        } catch (IOException e) {
            log.error("Communication exception", e);
        }
    }

    @Override
    public void close() throws Exception {
        telnetClient.disconnect();
    }

    public String sendCommand(String command) {
        try {
            write(command);

            return readUntil(PROMPT + " ");
        } catch (Exception e) {
            log.error("Unable to send Telnet command", e);
        }

        return null;
    }

    private String readUntil(String pattern) {
        final StringBuilder stringBuilder = new StringBuilder();
        final char lastChar = pattern.charAt(pattern.length() - 1);

        try {
            char ch = (char) inputStream.read();

            while (true) {
                stringBuilder.append(ch);

                if (ch == lastChar &&
                    stringBuilder.toString().endsWith(pattern)) {

                    break;
                }

                ch = (char) inputStream.read();
            }
        } catch (Exception e) {
            log.error("Unable to read from Telnet connection", e);
        }

        String response = stringBuilder.toString();

        log.debug("Telnet response received:\n" + response);

        return response;
    }

    private void write(String value) {
        try {
            outputStream.println(value);
            outputStream.flush();

            log.debug("Telnet message sent: " + value);
        } catch (Exception e) {
            log.error("Unable to write to Telnet connection", e);
        }
    }

}
