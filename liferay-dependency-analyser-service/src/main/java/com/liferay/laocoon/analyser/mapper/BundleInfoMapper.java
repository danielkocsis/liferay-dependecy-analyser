package com.liferay.laocoon.analyser.mapper;

import com.liferay.laocoon.analyser.domain.BundleInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BundleInfoMapper implements Mapper<BundleInfo, String> {

    @Override
    public Optional<BundleInfo> map(String model) {
        if (model == null) {
            return Optional.empty();
        }

        model = model.trim();

        if(!model.contains("|") || model.startsWith("ID")) {
            return Optional.empty();
        }

        log.debug("BundleInfo: " + model);

        List<String> bundleInfoParts = Arrays.stream(model.split("\\|"))
            .map(String::trim)
            .collect(Collectors.toList());

        if (bundleInfoParts.size() != 4) {
            return Optional.empty();
        }

        final int id = Integer.parseInt(bundleInfoParts.get(0));
        final String status = bundleInfoParts.get(1);
        final int level = Integer.parseInt(bundleInfoParts.get(2));
        final String name = bundleInfoParts.get(3);

        final BundleInfo bundleInfo = new BundleInfo(id, status, level, name);

        return Optional.of(bundleInfo);
    }

}
