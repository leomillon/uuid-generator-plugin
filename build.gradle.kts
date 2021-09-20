import org.jetbrains.intellij.tasks.PublishPluginTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    // Kotlin support
    kotlin("jvm") version "1.5.31"
    // gradle-intellij-plugin - read more: https://github.com/JetBrains/gradle-intellij-plugin
    id("org.jetbrains.intellij") version "1.1.2"
    // gradle-changelog-plugin - read more: https://github.com/JetBrains/gradle-changelog-plugin
    id("org.jetbrains.changelog") version "1.1.2"
}

intellij {
    pluginName.set("UUID Generator")
    version.set("IC-2020.1") //IntelliJ IDEA 2020.1 dependency; for a full list of IntelliJ IDEA releases please see https://www.jetbrains.com/intellij-repository/releases
    updateSinceUntilBuild.set(false)

    plugins.set(
        listOf(
            "java",
            "org.jetbrains.kotlin"
        )
    )
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.f4b6a3:ulid-creator:3.2.0")
    implementation("cool.graph:cuid-java:0.1.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.7.2")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.24")
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_11.toString()
            freeCompilerArgs = listOf(
                /**
                 * Bug with Kotlin 1.5.10 -> cannot use the method reference syntax:
                 * `com.github.leomillon.uuidgenerator.parser.IdType.UUID(UUIDGenerator::generateUUID)`
                 */
                "-Xno-optimized-callable-references"
            )
        }
    }

    withType<Test> {
        useJUnitPlatform {
            includeEngines = setOf("junit-jupiter", "junit-vintage")
        }
        jvmArgs(
            "-Dspring.test.constructor.autowire.mode=ALL",
            "-Djunit.jupiter.testinstance.lifecycle.default=per_class",
            "-Duser.language=en"
        )
        testLogging {
            events("passed", "skipped", "failed")
        }
    }

    withType<PublishPluginTask> {
        token.set(provider { prop("intellijPublishToken") ?: "unknown" })
        channels.set(provider { listOf(prop("intellijPublishChannels") ?: "") })
    }

    patchPluginXml {
        fun fileInMetaInf(fileName: String) = file("src/main/resources/META-INF").resolve(fileName)

        fun String.replaceGitHubContentUrl(projectVersion: String): String = when {
            projectVersion.endsWith("-SNAPSHOT") -> "master"
            else -> projectVersion
        }
            .let { targetGitHubBranchName ->
                this.replace(
                    "{{GIT_HUB_BRANCH}}",
                    targetGitHubBranchName
                )
            }

        val version: String by project
        pluginDescription.set(provider {
            fileInMetaInf("description.html").readText().replaceGitHubContentUrl(version)
        })
        changeNotes.set(provider { fileInMetaInf("change-notes.html").readText() })
    }

    create("printVersion") {
        doLast {
            val version: String by project
            println(version)
        }
    }
}

fun prop(name: String): String? =
    extra.properties[name] as? String
