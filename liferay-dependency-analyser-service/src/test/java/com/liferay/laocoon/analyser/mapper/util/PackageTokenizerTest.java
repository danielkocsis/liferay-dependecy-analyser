package com.liferay.laocoon.analyser.mapper.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class PackageTokenizerTest {

    @Test
    public void testTokenizer() {
        List<String> tokenizedStrings = PackageTokenizer.tokenize(
            testExportPackage);

        Assert.assertEquals(6, tokenizedStrings.size());
    }

    private final String testExportPackage = "com.liferay.commerce.price.list.constants;version=\"1.0.0\",com.liferay.commerce.price.list.exception;version=\"1.0.0\";uses:=\"com.liferay.portal.kernel.exception\",com.liferay.commerce.price.list.model;version=\"1.0.0\";uses:=\"com.liferay.commerce.currency.model,com.liferay.commerce.product.model,com.liferay.expando.kernel.model,com.liferay.exportimport.kernel.lar,com.liferay.portal.kernel.annotation,com.liferay.portal.kernel.bean,com.liferay.portal.kernel.exception,com.liferay.portal.kernel.model,com.liferay.portal.kernel.service,com.liferay.portal.kernel.service\",com.liferay.commerce.price.list.service;version=\"1.0.0\";uses:=\"com.liferay.commerce.price.list.model,com.liferay.exportimport.kernel.lar,com.liferay.portal.kernel.dao.orm,com.liferay.portal.kernel.exception,com.liferay.portal.kernel.jsonwebservice,com.liferay.portal.kernel.model,com.liferay.portal.kernel.search,com.liferay.portal.kernel.security.access.control,com.liferay.portal.kernel.service,com.liferay.portal.kernel.spring.osgi,com.liferay.portal.kernel.systemevent,com.liferay.portal.kernel.transaction,com.liferay.portal.kernel.service\",com.liferay.commerce.price.list.service.persistence;version=\"1.0.0\";uses:=\"com.liferay.commerce.price.list.exception,com.liferay.commerce.price.list.model,com.liferay.portal.kernel.dao.orm,com.liferay.portal.kernel.service,com.liferay.portal.kernel.service.persistence,com.liferay.portal.kernel.service\",com.liferay.commerce.price.list.service.comparator;version=\"1.0.0\";uses:=\"com.liferay.commerce.price.list.model,com.liferay.portal.kernel.service\"";
}