package com.liferay.laocoon.analyser.domain;

import lombok.Getter;
import lombok.ToString;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@ToString
public class ModuleFramework {

    public ModuleFramework(String name) {
        this.name = name;
        this.modules = new HashSet<>();
    }

    public void addModule(Module module) {
        modules.add(module);
    }

    public void processModuleDependencies() {
        for (Module module : modules) {
            Set<Module> dependentModules =
                module.getImportPackage().stream().parallel()
                    .map(this::getPackageProvider)
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(Collectors.toSet());

            module.getDependentModules().addAll(dependentModules);
        }
    }

    private Optional<Module> getPackageProvider(final Package requiredPackage) {
        return modules.stream().parallel()
            .filter(m -> isDependent(m, requiredPackage))
            .findAny();
    }


    private boolean isDependent(
        Module module, Package importPackage) {

        Set<Package> exportPackage = module.getExportPackage();

        Optional<Package> packageOptional = exportPackage.parallelStream()
            .filter(p -> p.getName().equalsIgnoreCase(importPackage.getName()))
            .findAny();

        return packageOptional.isPresent();
    }

    private String name;
    private final Set<Module> modules;

}
