package com.liferay.laocoon.analyser.service;

import com.liferay.laocoon.analyser.connection.telnet.LiferayTelnetConnection;
import com.liferay.laocoon.analyser.connection.telnet.LiferayTelnetConnectionFactory;
import com.liferay.laocoon.analyser.domain.BundleInfo;
import com.liferay.laocoon.analyser.domain.Module;
import com.liferay.laocoon.analyser.domain.ModuleFramework;
import com.liferay.laocoon.analyser.mapper.BundleInfoMapper;
import com.liferay.laocoon.analyser.mapper.ModuleMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Slf4j
@Service
public class LiferayModuleFrameworkService {

    public Optional<ModuleFramework> buildModuleFramework() {
        ModuleFramework moduleFramework = new ModuleFramework(
            UUID.randomUUID().toString());

        try {
            readFromLiferay(moduleFramework);
        } catch (Exception e) {
            log.error("Unable to read from Liferay.", e);

            return Optional.empty();
        }

        return Optional.of(moduleFramework);
    }

    private void readFromLiferay(ModuleFramework moduleFramework)
        throws Exception {

        try (LiferayTelnetConnection liferayTelnetConnection =
                 liferayTelnetConnectionFactory.getConnection()) {

            List<BundleInfo> bundleInfos = getBundleInfos(
                liferayTelnetConnection);

            log.debug("Number of bundles: " + bundleInfos.size());

            bundleInfos.stream()
                .map(i -> getModule(liferayTelnetConnection, i))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(moduleFramework::addModule);

            moduleFramework.processModuleDependencies();
        } finally {
            log.debug("Reading from Liferay Portal has ended.");
        }
    }

    private Optional<Module> getModule(
        final LiferayTelnetConnection liferayTelnetConnection,
        final BundleInfo bundleInfo) {

        final String telnetCommand = "headers " + bundleInfo.getId();
        final String telnetResponse = liferayTelnetConnection.sendCommand(
            telnetCommand);

        return moduleMapper.map(telnetResponse);
    }

    private List<BundleInfo> getBundleInfos(
        final LiferayTelnetConnection liferayTelnetConnection) {

        final List<BundleInfo> bundleInfos = new ArrayList<>();
        final String telnetResponse = liferayTelnetConnection.sendCommand(
            "lb");

        Arrays.stream(telnetResponse.split("\r\n"))
            .map(bundleInfoMapper::map)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .forEach(bundleInfos::add);

        return bundleInfos;
    }

    private final ModuleMapper moduleMapper;
    private final BundleInfoMapper bundleInfoMapper;
    private final LiferayTelnetConnectionFactory liferayTelnetConnectionFactory;

}
