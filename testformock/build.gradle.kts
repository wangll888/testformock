/*
 * This file was generated by the Gradle 'init' task.
 *
 * This generated file contains a sample Java project to get you started.
 * For more details take a look at the Java Quickstart chapter in the Gradle
 * User Manual available at https://docs.gradle.org/6.5/userguide/tutorial_java_projects.html
 */

plugins {
    // Apply the java plugin to add support for Java
    java
}

repositories {
    maven {
        url = uri("https://maven.oschina.net/content/groups/public/")
    }
    maven {
        url = uri("https://maven.aliyun.com/nexus/content/groups/public/")
    }
    maven {
        url = uri("https://repo1.maven.org/maven2/")
    }
    maven {
        url = uri("https://af-biz.qianxin-inc.cn/artifactory/maven-release-remorte/")
    }
    mavenCentral()
}

dependencies {
    // This dependency is used by the application.
    implementation("com.google.guava:guava:31.1-jre")
    implementation("com.github.java-json-tools:json-patch:1.13")

    testImplementation("org.junit.jupiter:junit-jupiter:5.9.0")

    testImplementation("org.mockito:mockito-inline:4.7.0")
//    testImplementation("org.mockito:mockito-core:4.7.0")
    testImplementation("org.mockito:mockito-junit-jupiter:4.7.0")

    testImplementation("com.github.java-json-tools:json-patch:1.13")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
    testLogging.showStandardStreams = true
//    minHeapSize = "128m"
//    maxHeapSize = "512m"
    failFast = true
    maxParallelForks = 5
}

sourceSets {
    main {
        java.srcDirs(
                "src/main/java")
    }

    test {
        java.srcDirs(
                "src/test/java")
    }
}