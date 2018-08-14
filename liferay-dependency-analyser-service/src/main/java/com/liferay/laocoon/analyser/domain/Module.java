package com.liferay.laocoon.analyser.domain;

import lombok.Builder;
import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Builder
@Getter
public class Module {

    private final String name;
    private final String symbolicName;
    private final String version;

    private final Set<Module> dependentModules = new HashSet<>();

    private final Set<Package> importPackage = new HashSet<>();
    private final Set<Package> exportPackage = new HashSet<>();

}
