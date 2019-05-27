import org.jetbrains.intellij.tasks.PublishTask
import se.bjurr.gitchangelog.plugin.gradle.GitChangelogTask

plugins {
    idea
    kotlin("jvm") version "1.3.31"
    id("org.jetbrains.intellij") version "0.4.8"
    id("se.bjurr.gitchangelog.git-changelog-gradle-plugin") version "1.60"
}

intellij {
    version = "IC-2018.3" //IntelliJ IDEA 2018.3 dependency; for a full list of IntelliJ IDEA releases please see https://www.jetbrains.com/intellij-repository/releases
    pluginName = "UUID Generator"
    updateSinceUntilBuild = false //Disables updating since-build attribute in plugin.xml
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("org.junit.jupiter:junit-jupiter:5.4.2")
}

tasks.withType<PublishTask> {
    token(prop("intellijPublishToken") ?: "unknown")
    channels(prop("intellijPublishChannels") ?: "")
}

tasks.create("printVersion") {
    doLast {
        val version: String by project
        println(version)
    }
}

tasks.create<GitChangelogTask>("gitChangelogTask") {
    file = File("CHANGELOG.md")
    templateContent = file("template_changelog.mustache").readText()
}

fun prop(name: String): String? =
    extra.properties[name] as? String
