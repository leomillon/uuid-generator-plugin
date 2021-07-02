import org.jetbrains.intellij.tasks.PublishTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    idea
    kotlin("jvm") version "1.5.20"
    id("org.jetbrains.intellij") version "1.1.2"
}

intellij {
    version =
        "IC-2020.1" //IntelliJ IDEA 2020.1 dependency; for a full list of IntelliJ IDEA releases please see https://www.jetbrains.com/intellij-repository/releases
    pluginName = "UUID Generator"
    updateSinceUntilBuild = false //Disables updating since-build attribute in plugin.xml

    setPlugins(
        "java",
        "org.jetbrains.kotlin"
    )
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("com.github.f4b6a3:ulid-creator:3.1.0")
    implementation("cool.graph:cuid-java:0.1.1")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.2")
    testImplementation("junit:junit:4.13.2")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.7.2")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.24")
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions {
            jvmTarget = JavaVersion.VERSION_1_8.toString()
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

    withType<PublishTask> {
        token(prop("intellijPublishToken") ?: "unknown")
        channels(prop("intellijPublishChannels") ?: "")
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
        pluginDescription(fileInMetaInf("description.html").readText().replaceGitHubContentUrl(version))
        changeNotes(fileInMetaInf("change-notes.html").readText())
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
