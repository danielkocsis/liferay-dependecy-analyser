package com.liferay.laocoon.analyser;

import com.liferay.laocoon.analyser.domain.Module;
import com.liferay.laocoon.analyser.domain.ModuleFramework;
import com.liferay.laocoon.analyser.service.LiferayModuleFrameworkService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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

        @Override
        public void run(String... args) throws IOException {
            ModuleFramework moduleFramework =
                liferayModuleFrameworkService.buildModuleFramework();

            File logFile = new File("modules.out");

            try (BufferedWriter writer =
                     new BufferedWriter(new FileWriter(logFile))) {

                List<Module> modules = new ArrayList<>(
                    moduleFramework.getModules());

                modules.sort(
                    Comparator.comparingInt(
                        a -> a.getDependentModules().size()));

                for (Module module : modules) {
                    writer.write(printModule(module));
                    writer.newLine();
                }
            }
        }

        private String printModule(Module module) {
            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder
                .append("Name: ")
                .append(module.getName())
                .append(" (")
                .append(module.getDependentModules().size())
                .append(") {");

            module.getDependentModules()
                .forEach(m -> stringBuilder.append(m.getName()).append(", "));

            stringBuilder.append("}");

            return stringBuilder.toString();
        }
    }

}
