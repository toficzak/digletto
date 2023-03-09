package com.github.toficzak.digletto.core;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchitectureTest {

    private static final String CORE_PACKAGE = "..core..";
    private static final String WEB_PACKAGE = "..web..";
    private static final String MAIN_PACKAGE = "com.github.toficzak.digletto";

    private static final JavaClasses IMPORTED_CLASSED = new ClassFileImporter().importPackages(MAIN_PACKAGE);

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
}
