package com.liferay.laocoon.analyser.mapper.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PackageTokenizer {

    public static List<String> tokenize(String packageDeclaration) {
        packageDeclaration = packageDeclaration.replaceAll(
            "(uses:=\".*?\")", "");

        packageDeclaration = packageDeclaration.replaceAll(
            "(\\D),(\\D)", "$1&$2");

        String[] exportedPackagesStrings = packageDeclaration.split("&");

        return new ArrayList<>(Arrays.asList(exportedPackagesStrings));
    }

    private PackageTokenizer() {
    }

}
