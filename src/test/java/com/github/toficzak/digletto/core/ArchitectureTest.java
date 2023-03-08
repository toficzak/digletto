package com.github.toficzak.digletto.core;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ArchitectureTest {

    @Test
    void Services_should_only_be_accessed_by_Controllers() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("com.github.toficzak.digletto");

        ArchRule myRule = classes()
                .that().resideInAPackage("..core..")
                .should().onlyBeAccessed().byAnyPackage("..web..", "..core..");

//        ArchRule another = classes()
//                .that().resideInAPackage("..core..")
//                        .should().depen

        myRule.check(importedClasses);
    }
}
