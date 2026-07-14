plugins {
    id( "java" )
    id( "maven-publish")
}

group = "me.apotterhead"
version = "1.2.0"

repositories {
    mavenCentral()
}

dependencies {
    implementation( "org.objenesis:objenesis:3.5" )
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.javadoc {
    destinationDir = file( layout.projectDirectory.dir( "docs" ) )
}

tasks.test {
    useJUnitPlatform()
    jvmArgs = listOf(
        "--add-opens=java.base/java.util=ALL-UNNAMED",
        "--add-opens=java.base/java.lang=ALL-UNNAMED",
        "--add-opens=java.base/java.io=ALL-UNNAMED",
        "--add-opens=java.base/java.nio=ALL-UNNAMED",
        "--add-opens=java.base/java.util.regex=ALL-UNNAMED",
        "--add-opens=java.base/sun.nio.cs=ALL-UNNAMED",
        "--add-opens=java.base/java.nio.charset=ALL-UNNAMED",
//        "--add-opens=java.base/java.net=ALL-UNNAMED",
//        "--add-opens=java.base/java.text=ALL-UNNAMED",
//        "--add-opens=java.base/java.time=ALL-UNNAMED",
//        "--add-opens=java.base/java.math=ALL-UNNAMED",
//        "--add-opens=java.base/java.lang.reflect=ALL-UNNAMED",
//        "--add-opens=java.base/java.lang.invoke=ALL-UNNAMED",
//        "--add-opens=java.base/java.util.concurrent=ALL-UNNAMED",
//        "--add-opens=java.base/java.util.concurrent.atomic=ALL-UNNAMED",
//        "--add-opens=java.base/java.util.function=ALL-UNNAMED",
//        "--add-opens=java.base/java.util.stream=ALL-UNNAMED",
//        "--add-opens=java.base/java.util.regex=ALL-UNNAMED",
//        "--add-opens=java.base/java.util.zip=ALL-UNNAMED",
//        "--add-opens=java.base/java.util.jar=ALL-UNNAMED",
//        "--add-opens=java.base/jdk.internal.reflect=ALL-UNNAMED",
//        "--add-opens=java.base/jdk.internal.misc=ALL-UNNAMED",
//        "--add-opens=java.base/jdk.internal.loader=ALL-UNNAMED"
    )
}

java {
    withJavadocJar()
    withSourcesJar()
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/APotterhead1/cheers")
            credentials {
                username = findProperty("gpr.user") as String
                password = findProperty("gpr.key") as String
            }
        }
    }
}