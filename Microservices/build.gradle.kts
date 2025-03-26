plugins {
    id("org.springframework.boot") version "3.2.0" apply false
    id("io.spring.dependency-management") version "1.1.4" apply false
    id("java")
}

allprojects {
    group = "ru.feolex.microservices"
    version = "0.0.1-SNAPSHOT"
    
    repositories {
        mavenCentral()
    }
}

subprojects {
    apply(plugin = "java")
    
    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    
    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

dependencies {
    implementation('org.springframework.boot:spring-boot-starter-web');
    implementation('org.springframework.boot:spring-boot-starter-data-jpa');
    implementation('org.springframework.boot:spring-boot-starter-validation');
    implementation('org.springframework.cloud:spring-cloud-starter-netflix-eureka-client');

    implementation('org.springframework.kafka:spring-kafka');

    runtimeOnly('com.h2database:h2');
    // Uncomment for MySQL
    // runtimeOnly 'mysql:mysql-connector-java'
    
    testImplementation('org.springframework.boot:spring-boot-starter-test');
    testImplementation('org.springframework.kafka:spring-kafka-test');
    testImplementation("io.rest-assured:rest-assured");
    testImplementation("io.rest-assured:spring-mock-mvc");
    testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier");
    testImplementation("org.springframework.cloud:spring-cloud-contract-spec");
    testImplementation("org.testcontainers:testcontainers");
    testImplementation("org.testcontainers:junit-jupiter");
    testImplementation("io.gatling:gatling-app:3.9.0");
    testImplementation("io.gatling:gatling-http:3.9.0");
    testImplementation("io.gatling:gatling-core:3.9.0");
    testImplementation("io.gatling:gatling-javaapi:3.9.0");
}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:2023.0.0");
    }
}

contracts {
    packageWithBaseClasses.set("com.example.cat.contract")
}

tasks {
    test {
        useJUnitPlatform()
        finalizedBy("jacocoTestReport")
    }

    register<JavaExec>("loadTest") {
        description = "Run Gatling load tests"
        group = "verification"
        mainClass.set("io.gatling.app.Gatling")
        classpath = sourceSets["test"].runtimeClasspath
        args = listOf(
            "--simulation", "com.example.performance.LoadTest",
            "--results-folder", "${buildDir}/gatling-results"
        )
    }
}
