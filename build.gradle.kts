import org.jetbrains.intellij.tasks.PublishTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import se.bjurr.gitchangelog.plugin.gradle.GitChangelogTask

plugins {
    idea
    kotlin("jvm") version "1.3.72"
    id("org.jetbrains.intellij") version "0.6.5"
    id("se.bjurr.gitchangelog.git-changelog-gradle-plugin") version "1.65"
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
    implementation("com.github.f4b6a3:ulid-creator:2.0.2")

    testImplementation("org.junit.jupiter:junit-jupiter:5.7.0")
    testImplementation("junit:junit:4.13.1")
    testRuntimeOnly("org.junit.vintage:junit-vintage-engine:5.7.0")
    testImplementation("com.willowtreeapps.assertk:assertk-jvm:0.23")
}

tasks {

    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_1_8.toString()
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

    create<GitChangelogTask>("gitChangelogTask") {
        file = File("CHANGELOG.md")
        templateContent = file("template_changelog.mustache").readText()
    }
}

fun prop(name: String): String? =
    extra.properties[name] as? String
