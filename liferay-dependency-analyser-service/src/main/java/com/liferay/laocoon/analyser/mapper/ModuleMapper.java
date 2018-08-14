package com.liferay.laocoon.analyser.mapper;

import com.liferay.laocoon.analyser.domain.Module;
import com.liferay.laocoon.analyser.domain.Package;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Slf4j
@Service
public class ModuleMapper implements Mapper<Module, String> {

    private final PackageMapper packageMapper;

    public Optional<Module> map(String model) {
        if (model == null) {
            return Optional.empty();
        }

        final List<String> valueLines =
            Arrays.stream(model.split("\r\n"))
                .filter(l -> l.contains("="))
                .collect(Collectors.toList());

        final Map<String, String> attributes = new HashMap<>();

        valueLines.forEach(l -> {
            String[] split = l.split(" = ");

            attributes.put(split[0].trim(), split[1].trim());
        });

        final String moduleName = attributes.get("Bundle-Name");
        final String moduleSymbolicName = attributes.get("Bundle-SymbolicName");
        final String moduelVersion = attributes.get("Bundle-Version");

        final Module module = Module.builder()
            .name(moduleName)
            .symbolicName(moduleSymbolicName)
            .version(moduelVersion)
            .build();

        if (attributes.containsKey("Import-Package")) {
            String importPackageAttribute = attributes.get("Import-Package");

            log.debug("Import-Package: " + importPackageAttribute);

            final List<Package> importedPackages = packageMapper.mapAll(
                importPackageAttribute);

            module.getImportPackage().addAll(importedPackages);
        }

        if (attributes.containsKey("Export-Package")) {
            String exportPackageAttribute = attributes.get("Export-Package");

            log.debug("Export-Package: " + exportPackageAttribute);

            final List<Package> exportedPackages = packageMapper.mapAll(
                exportPackageAttribute);

            module.getExportPackage().addAll(exportedPackages);
        }

        return Optional.of(module);
    }

}