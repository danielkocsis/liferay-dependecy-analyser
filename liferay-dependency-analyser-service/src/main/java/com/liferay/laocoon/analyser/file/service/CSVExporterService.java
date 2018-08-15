package com.liferay.laocoon.analyser.file.service;

import com.liferay.laocoon.analyser.domain.Module;
import com.liferay.laocoon.analyser.domain.ModuleFramework;
import com.liferay.laocoon.analyser.service.ExporterService;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class CSVExporterService implements ExporterService {

    @Override
    public void export(ModuleFramework moduleFramework) throws Exception {
        File file = new File(
            "liferay-modules-" + System.currentTimeMillis() + ".csv");

        writeToFile(moduleFramework, file);
    }

    private void writeToFile(ModuleFramework moduleFramework, File file)
        throws IOException {

        try (BufferedWriter writer =
                 new BufferedWriter(new FileWriter(file))) {

            List<Module> modules = new ArrayList<>(
                moduleFramework.getModules());

            modules.sort(
                Comparator.comparingInt(
                    module -> module.getDependentModules().size()));

            for (Module module : modules) {
                writer.write(printModule(module));
                writer.newLine();
            }
        }
    }

    private String printModule(Module module) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder
            .append(module.getName())
            .append(',');

        module.getDependentModules().forEach(
            m -> stringBuilder.append(m.getName()).append(','));

        String line = stringBuilder.toString();

        return line.substring(0, line.length() - 1);
    }

}
