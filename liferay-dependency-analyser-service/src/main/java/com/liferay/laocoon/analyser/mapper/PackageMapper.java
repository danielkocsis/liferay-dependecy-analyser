package com.liferay.laocoon.analyser.mapper;

import com.liferay.laocoon.analyser.domain.Package;
import com.liferay.laocoon.analyser.mapper.util.PackageTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PackageMapper implements Mapper<Package, String> {

    public List<Package> mapAll(String packageString) {

        List<String> packageDeclarationsStrings =
            PackageTokenizer.tokenize(packageString);

        return packageDeclarationsStrings.stream().parallel()
            .map(this::map)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
    }

    /**
     * see: https://osgi.org/specification/osgi.core/7.0.0/framework.module.html#framework.module.exportpackage
     */
    @Override
    public Optional<Package> map(String model) {
        if (model == null) {
            return Optional.empty();
        }

        log.debug("Package: " + model);

        final String[] packageDefinitions = model.split(";");

        String version = null;
        String name = null;

        for (String packageDefinition : packageDefinitions) {
            if (packageDefinition.startsWith("uses") ||
                packageDefinition.startsWith("mandatory") ||
                packageDefinition.startsWith("include") ||
                packageDefinition.startsWith("exclude") ||
                packageDefinition.startsWith("resolution") ||
                packageDefinition.startsWith("x-") ||
                packageDefinition.startsWith("version")) {

                if (packageDefinition.startsWith("version")) {
                    version = getVersion(packageDefinition);
                }

                continue;
            }

            name = packageDefinition;
        }

        log.debug("Name: " + name + ", Version: " + version + "\n");

        return Optional.of(new Package(name, version));
    }

    private String getVersion(String versionString) {
        String[] versionParts = versionString.split("=");

        return versionParts[1].substring(1, versionParts[1].length() - 1);
    }

}
