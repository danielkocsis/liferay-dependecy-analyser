package com.liferay.laocoon.analyser.neo4j.service;

import com.liferay.laocoon.analyser.domain.Module;
import com.liferay.laocoon.analyser.domain.ModuleFramework;
import com.liferay.laocoon.analyser.domain.Package;
import com.liferay.laocoon.analyser.neo4j.model.BundleNode;
import com.liferay.laocoon.analyser.neo4j.model.PackageNode;
import com.liferay.laocoon.analyser.neo4j.repository.BundleNodeRepository;
import com.liferay.laocoon.analyser.service.ExporterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Slf4j
@Service
public class GraphExporterService implements ExporterService {

    @Transactional
    public void export(ModuleFramework moduleFramework) {
        long startTime = System.currentTimeMillis();

        log.debug(
            "Exporting module framework with " +
                moduleFramework.getModules().size() + " modules started...");

        moduleFramework.getModules().stream().parallel()
            .map(this::mapModulToBundleNode)
            .forEach(bundleNodeRepository::save);

        /*for (Module module : moduleFramework.getModules()) {
            final BundleNode curBundleNode =
                bundleNodeRepository.findFirstByName(module.getName());

            if ((curBundleNode.getDependentBundles() != null) &&
                !curBundleNode.getDependentBundles().isEmpty()) {

                continue;
            }

            final List<BundleNode> dependentBundles =
                module.getDependentModules().stream()
                    .map(Module::getName)
                    .map(bundleNodeRepository::findFirstByName)
                    .collect(Collectors.toList());

            curBundleNode.setDependentBundles(dependentBundles);

            bundleNodeRepository.save(curBundleNode);
        }*/

        long totalTime = System.currentTimeMillis() - startTime;

        log.debug("Exporting module framework finished in " + totalTime + "ms");
    }

    private BundleNode mapModulToBundleNode(Module module) {
        BundleNode bundleNode = new BundleNode();

        bundleNode.setName(module.getName());

        /*List<PackageNode> exportPackageNodes =
            module.getExportPackage().stream()
                .map(this::mapToPackageNode)
                .collect(Collectors.toList());

        bundleNode.setExportPackages(exportPackageNodes);

        List<PackageNode> importPackageNodes =
            module.getImportPackage().stream()
                .map(this::mapToPackageNode)
                .collect(Collectors.toList());

        bundleNode.setImportPackages(importPackageNodes);*/

        return bundleNode;
    }

    private PackageNode mapToPackageNode(Package modulePackage) {
        PackageNode packageNode = new PackageNode();

        packageNode.setName(modulePackage.getName());
        packageNode.setVersion(modulePackage.getVersion());

        return packageNode;
    }

    private final BundleNodeRepository bundleNodeRepository;

}
