package com.liferay.laocoon.analyser.service;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LiferayModuleFrameworkServiceTest {

    @Test
    public void testRegex() {
        final String line =
            "1179|Active     |   10|commerce-theme-breccia (1.0.0)";

        final String lbLinePattern = "^(\\d+).*";

        System.out.println(line.matches(lbLinePattern));

        Pattern pattern = Pattern.compile(lbLinePattern);

        Matcher matcher = pattern.matcher(line);

        System.out.println(matcher.groupCount());
    }

}
