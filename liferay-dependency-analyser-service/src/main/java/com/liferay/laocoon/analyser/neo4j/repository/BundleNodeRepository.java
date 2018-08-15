package com.liferay.laocoon.analyser.neo4j.repository;

import com.liferay.laocoon.analyser.neo4j.model.BundleNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface BundleNodeRepository
    extends Neo4jRepository<BundleNode, Long> {

    public BundleNode findFirstByName(String name);

}