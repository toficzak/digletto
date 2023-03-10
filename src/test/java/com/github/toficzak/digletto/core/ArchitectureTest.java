package com.github.toficzak.digletto.core;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureTest {

    public static final String SERVICE_CLASS_NAME = "Service";
    private static final String CORE_PACKAGE = "..core..";
    private static final String WEB_PACKAGE = "..web..";
    private static final String MAIN_PACKAGE = "com.github.toficzak.digletto";
    private static final String[] PUBLIC_PACKAGES = {"..core.dto..", "..core.exception..", "..core.enums.."};
    private static final JavaClasses IMPORTED_CLASSED = new ClassFileImporter()
            .withImportOption(new ImportOption.DoNotIncludeTests()) // not including some test helpers
            .importPackages(MAIN_PACKAGE);

    @Test
    void coreHasNoDependencies() {
        ArchRule coreHasNoDependencies = noClasses().that().resideInAPackage(CORE_PACKAGE)
                .should().dependOnClassesThat().resideInAPackage(WEB_PACKAGE);

        coreHasNoDependencies.check(IMPORTED_CLASSED);
    }

    @Test
    void coreCanBeAccessedByWeb() {
        ArchRule myRule = classes()
                .that().resideInAPackage(CORE_PACKAGE)
                .should().onlyBeAccessed().byAnyPackage(WEB_PACKAGE, CORE_PACKAGE);

        myRule.check(IMPORTED_CLASSED);
    }

    @Test
    void publicityRule() {
        ArchRule publicityRule = classes()
                .that().resideInAnyPackage(PUBLIC_PACKAGES)
                .or()
                .haveSimpleNameStartingWith(SERVICE_CLASS_NAME)
                .should()
                .bePublic();

        publicityRule.check(IMPORTED_CLASSED);
    }

    @Test
    void packagePrivateRule() {
        ArchRule packagePrivateRule = classes()
                .that()
                .resideInAPackage(CORE_PACKAGE)
                .and()
                .areNotPackagePrivate()
                .should()
                .haveSimpleNameStartingWith(SERVICE_CLASS_NAME)
                .orShould()
                .resideInAnyPackage(PUBLIC_PACKAGES);

        packagePrivateRule.check(IMPORTED_CLASSED);
    }
}
