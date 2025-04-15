import com.example.buildsrc.UnzipTransformAction
import org.gradle.api.artifacts.type.ArtifactTypeDefinition.*
import org.gradle.api.attributes.Bundling.BUNDLING_ATTRIBUTE
import org.gradle.api.attributes.Bundling.EXTERNAL
import org.gradle.api.attributes.Category.CATEGORY_ATTRIBUTE
import org.gradle.api.attributes.Category.LIBRARY
import org.gradle.api.attributes.LibraryElements.LIBRARY_ELEMENTS_ATTRIBUTE
import org.gradle.api.attributes.LibraryElements.RESOURCES
import org.gradle.api.attributes.Usage.JAVA_RUNTIME
import org.gradle.api.attributes.Usage.USAGE_ATTRIBUTE

plugins {
    kotlin("jvm")
    id("application")
}

application {
    mainClass = "com.example.MainKt"
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {

    registerTransform(UnzipTransformAction::class.java) {
        from.attribute(ARTIFACT_TYPE_ATTRIBUTE, ZIP_TYPE)
        to.attribute(ARTIFACT_TYPE_ATTRIBUTE, DIRECTORY_TYPE)
    }

    implementation("com.example:producer:1.0-SNAPSHOT")
    {
        attributes {
            attribute(BUNDLING_ATTRIBUTE, objects.named(EXTERNAL))
            attribute(CATEGORY_ATTRIBUTE, objects.named(LIBRARY))
            attribute(LIBRARY_ELEMENTS_ATTRIBUTE, objects.named(RESOURCES))
            attribute(USAGE_ATTRIBUTE, objects.named(JAVA_RUNTIME))
            attribute(ARTIFACT_TYPE_ATTRIBUTE, DIRECTORY_TYPE)
        }
    }
}
