package com.liferay.laocoon.analyser.domain;

import lombok.Data;

@Data
public class BundleInfo {

    private final int id;
    private final String status;
    private final int level;
    private final String name;

}
