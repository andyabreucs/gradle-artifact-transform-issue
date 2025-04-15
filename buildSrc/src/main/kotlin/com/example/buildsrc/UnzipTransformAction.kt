package com.example.buildsrc

import org.gradle.api.artifacts.transform.InputArtifact
import org.gradle.api.artifacts.transform.TransformAction
import org.gradle.api.artifacts.transform.TransformOutputs
import org.gradle.api.artifacts.transform.TransformParameters.None
import org.gradle.api.artifacts.type.ArtifactTypeDefinition.ZIP_TYPE
import org.gradle.api.file.ArchiveOperations
import org.gradle.api.file.FileSystemLocation
import org.gradle.api.provider.Provider
import org.gradle.api.tasks.PathSensitive
import org.gradle.api.tasks.PathSensitivity
import java.io.File
import javax.inject.Inject

/**
 * [TransformAction] that extracts the contents of Zip files into a directory.
 */
abstract class UnzipTransformAction : TransformAction<None> {

    /**
     * The Zip file artifact to be transformed into a directory containing
     * extracted files.
     */
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    @get:InputArtifact
    abstract val inputArtifact: Provider<FileSystemLocation>

    /**
     * Operates on archives such as ZIP or TAR files.
     */
    @get:Inject
    abstract val archiveOperations: ArchiveOperations

    override
    fun transform(outputs: TransformOutputs) {

        val originalFile = inputArtifact.get().asFile

        if (originalFile.extension != ZIP_TYPE) {

            outputs.file(inputArtifact)

        } else {

            // Use the file name, without the zip extension, for the name of the
            // unzip directory to ensure different directories for similar
            // artifacts with different classifiers and/or types.
            val unzipDirectoryName = originalFile.name
                .substring(0, originalFile.name.length - ZIP_TYPE.length - 1)

            val unzipDirectory: File = outputs.dir(unzipDirectoryName)

            // Extract the unzipped files to the output directory
            archiveOperations.zipTree(originalFile).forEach { zipEntry: File ->
                zipEntry.copyTo(File(unzipDirectory, zipEntry.name))
            }
        }
    }
}
