package com.liferay.laocoon.analyser;

import com.liferay.laocoon.analyser.domain.ModuleFramework;
import com.liferay.laocoon.analyser.service.ExporterService;
import com.liferay.laocoon.analyser.service.LiferayModuleFrameworkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class LaocoonAnalyserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaocoonAnalyserServiceApplication.class, args);
    }

    @AllArgsConstructor
    @Slf4j
    @Component
    public static class CommandLineAppStartupRunner
        implements CommandLineRunner {

        private final LiferayModuleFrameworkService
            liferayModuleFrameworkService;

        private List<ExporterService> exporterServices;
        private final ConfigurableApplicationContext
            configurableApplicationContext;

        @Override
        public void run(String... args) {
            Optional<ModuleFramework> moduleFramework =
                liferayModuleFrameworkService.buildModuleFramework();

            if (!moduleFramework.isPresent()) {
                log.error(
                    "Unable to process Liferay module framework. Exiting...");

                return;
            }

            for (ExporterService exporterService : exporterServices) {
                try {
                    exporterService.export(moduleFramework.get());

                    log.debug(
                        "Liferay module framework was successfully exported " +
                            "with " + exporterService.getClass().getName());
                } catch (Exception e) {
                    log.error(
                        "Unable to export module framework with " +
                            exporterService.getClass().getName() + " exporter",
                        e);
                }
            }

            configurableApplicationContext.close();
            System.exit(0);
        }
    }

}
