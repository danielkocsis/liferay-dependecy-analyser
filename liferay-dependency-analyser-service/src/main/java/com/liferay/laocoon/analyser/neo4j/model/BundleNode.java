package com.liferay.laocoon.analyser.neo4j.model;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@Data
@NodeEntity
public class BundleNode {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @Relationship(type = "USES")
    private List<BundleNode> dependentBundles;

    @Relationship(type = "EXPORTS")
    private List<PackageNode> exportPackages;

    @Relationship(type = "IMPORTS")
    private List<PackageNode> importPackages;

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (!(obj instanceof BundleNode))
            return false;

        if (obj == this)
            return true;

        return this.getName().equals(((BundleNode) obj).getName());
    }

}
