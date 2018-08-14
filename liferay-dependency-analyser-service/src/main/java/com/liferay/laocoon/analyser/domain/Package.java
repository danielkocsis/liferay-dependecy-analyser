package com.liferay.laocoon.analyser.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Builder
@EqualsAndHashCode
@Getter
@AllArgsConstructor
public class Package {

    private final String name;
    private final String version;

}
