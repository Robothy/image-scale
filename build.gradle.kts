plugins {
    kotlin("jvm") version "1.9.21"
    application
}

group = "io.github.robothy"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}


dependencies {
    val twelvemonkeysImageIOVersion = "3.10.1"
    testImplementation(kotlin("test"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("com.twelvemonkeys.imageio:imageio-metadata:$twelvemonkeysImageIOVersion")
    implementation("com.twelvemonkeys.imageio:imageio-jpeg:$twelvemonkeysImageIOVersion")
    implementation("com.twelvemonkeys.imageio:imageio-bmp:$twelvemonkeysImageIOVersion")
    implementation("com.twelvemonkeys.imageio:imageio-pnm:$twelvemonkeysImageIOVersion")
    implementation("com.twelvemonkeys.imageio:imageio-tiff:$twelvemonkeysImageIOVersion")

//    implementation("com.twelvemonkeys.imageio:imageio-psd:$twelvemonkeysImageIOVersion")
//    implementation("com.twelvemonkeys.imageio:imageio-pcx:$twelvemonkeysImageIOVersion")
//    implementation("com.twelvemonkeys.imageio:imageio-pict:$twelvemonkeysImageIOVersion")
//    implementation("com.twelvemonkeys.imageio:imageio-sgi:$twelvemonkeysImageIOVersion")
//    implementation("com.twelvemonkeys.imageio:imageio-tga:$twelvemonkeysImageIOVersion")
//    implementation("com.twelvemonkeys.imageio:imageio-iff:$twelvemonkeysImageIOVersion")
//    implementation("com.twelvemonkeys.imageio:imageio-icns:$twelvemonkeysImageIOVersion")
    //implementation("com.twelvemonkeys.imageio:imageio-dcx:$twelvemonkeysImageIOVersion")
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(8)
}

application {
    mainClass.set("MainKt")
}

tasks.register(name = "runSliceImage", type = JavaExec::class) {
    classpath = sourceSets.main.get().runtimeClasspath
    mainClass = "MainKt"
    //jvmArgs = listOf("-Xmx16M", "-Xlog:gc")
}