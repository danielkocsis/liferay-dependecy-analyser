package com.liferay.laocoon.analyser.neo4j.model;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class PackageNode {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private String version;

}
