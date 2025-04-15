@file:Suppress("UnstableApiUsage")

import org.gradle.api.artifacts.type.ArtifactTypeDefinition.ZIP_TYPE
import org.gradle.api.attributes.Bundling.BUNDLING_ATTRIBUTE
import org.gradle.api.attributes.Bundling.EXTERNAL
import org.gradle.api.attributes.Category.CATEGORY_ATTRIBUTE
import org.gradle.api.attributes.Category.LIBRARY
import org.gradle.api.attributes.LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE
import org.gradle.api.attributes.LibraryElements.RESOURCES
import org.gradle.api.attributes.Usage.JAVA_RUNTIME
import org.gradle.api.attributes.Usage.USAGE_ATTRIBUTE

plugins {
    `maven-publish`
}

version = "1.0-SNAPSHOT"
group = "com.example"


configurations {
    @Suppress("UnstableApiUsage")
    consumable("zippedElements") {
        attributes {
            attribute(BUNDLING_ATTRIBUTE, objects.named(EXTERNAL))
            attribute(CATEGORY_ATTRIBUTE, objects.named(LIBRARY))
            attribute(LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(RESOURCES))
            attribute(USAGE_ATTRIBUTE, objects.named(JAVA_RUNTIME))
        }
    }
}

val zipArtifact = artifacts.add(
    "zippedElements",
    layout.projectDirectory.file("zipmeup.zip")
) {
    type = ZIP_TYPE
}

publishing {

    publications {

        create<MavenPublication>("maven") {
            artifact(zipArtifact)
        }
    }
}
