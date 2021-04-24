plugins {
    idea
    java
    application
    id("com.github.johnrengelman.shadow") version ("6.1.0")
    id("com.github.ben-manes.versions") version ("0.38.0")
    id("io.freefair.lombok") version ("6.0.0-m2")
    id("org.beryx.jlink") version ("2.23.7")
}

group = "fr.raksrinana"
description = "FileSecure"

dependencies {
    implementation(libs.slf4j)
    implementation(libs.log4j2)

    implementation(libs.nameascreated)

    implementation(libs.picocli)
    implementation(libs.bundles.jackson)

    compileOnly(libs.jetbrainsAnnotations)
}

repositories {
    val githubRepoUsername: String by project
    val githubRepoPassword: String by project

    maven {
        url = uri("https://maven.pkg.github.com/RakSrinaNa/JavaUtils/")
        credentials {
            username = githubRepoUsername
            password = githubRepoPassword
        }
    }
    maven {
        url = uri("https://maven.pkg.github.com/RakSrinaNa/NameAsCreated/")
        credentials {
            username = githubRepoUsername
            password = githubRepoPassword
        }
    }
    maven {
        url = uri("https://projectlombok.org/edge-releases")
    }
    mavenCentral()
    jcenter()
}

tasks {
    processResources {
        expand(project.properties)
    }

    compileJava {
        val moduleName: String by project
        inputs.property("moduleName", moduleName)

        options.encoding = "UTF-8"
        options.isDeprecation = true

        doFirst {
            val compilerArgs = options.compilerArgs
            compilerArgs.add("--module-path")
            compilerArgs.add(classpath.asPath)
            classpath = files()
        }
    }

    shadowJar {
        archiveBaseName.set(project.name)
        archiveClassifier.set("shaded")
        archiveVersion.set("")
    }

    wrapper {
        val wrapperVersion: String by project
        gradleVersion = wrapperVersion
    }
}

application {
    val moduleName: String by project
    val className: String by project

    mainClassName = className
    mainModule.set(moduleName)
    mainClass.set(className)
}

java {
    sourceCompatibility = JavaVersion.VERSION_16
    targetCompatibility = JavaVersion.VERSION_16
}

jlink {
    val moduleName: String by project
    val className: String by project

    this.moduleName.set(moduleName)
    mainClass.set(className)
    addOptions(
        "--strip-debug",
        "--compress", "2",
        "--no-header-files",
        "--no-man-pages"
    )

    launcher {
        name = "FileSecure"
    }
}
