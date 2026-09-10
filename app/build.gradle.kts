import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.sdv.tree3"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.sdv.tree3"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = getVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_21
        targetCompatibility = JavaVersion.VERSION_21
    }
    buildFeatures {
        compose = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
androidComponents {
    onVariants { variant ->
        val variantName = variant.name
        val versionName = getVersionName()
        val apkName = "${variantName}_${versionName}.apk"

        variant.outputs.forEach { output ->
            (output as? BaseVariantOutputImpl)?.outputFileName = apkName
        }
    }
}
kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_21)
    }
}

fun getVersionName(): String {
    val date = DateTimeFormatter
        .ofPattern("yyyy-MM-dd_HHmm")
        .withZone(ZoneOffset.UTC)
        .format(Instant.now())
    val version = getWorkingBranch().split("/").lastOrNull()
    return "${version}_$date"
}

fun getWorkingBranch(): String {
    val githubHeadRef = System.getenv("GITHUB_HEAD_REF")
    val githubRef = System.getenv("GITHUB_REF_NAME")

    return when {
        !githubHeadRef.isNullOrEmpty() -> githubHeadRef
        !githubRef.isNullOrEmpty() -> githubRef
        else -> "git rev-parse --abbrev-ref HEAD".runCommand()
    }
}

//fun String.runCommand(project: Project): String {
//    try {
//        return ByteArrayOutputStream().use { output ->
//            project.exec {
//                commandLine(this@runCommand.split(" "))
//                standardOutput = output
//            }
//            String(output.toByteArray()).trim()
//        }
//    } catch (t: Throwable) {
//        throw GradleException("Error when runCommand: ${this@runCommand} - ${t.localizedMessage}")
//    }
//}

fun String.runCommand(): String {
    return try {
        val process = ProcessBuilder(*this.split(" ").toTypedArray())
            .redirectErrorStream(true)
            .start()
        val output = process.inputStream.bufferedReader().readText()
        val exitCode = process.waitFor()
        if (exitCode != 0) {
            throw GradleException("Command failed with exit code $exitCode: $this")
        }
        output.trim()
    } catch (t: Throwable) {
        throw GradleException("Error when runCommand: $this - ${t.localizedMessage}")
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":app_features:main_feature"))

    //Logging
    implementation(libs.timber)

    // Kotlin coroutines with lifecycle
    implementation(libs.bundles.lifecycle)

    // Dagger Hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.viewmodel.android)
    ksp(libs.hilt.compiler)

    // Room
    implementation(libs.androidx.room.ktx)
    annotationProcessor(libs.androidx.room.annotation.processor)
    ksp(libs.androidx.room.annotation.processor)

    // navigation
    implementation(libs.androidx.navigation.compose)

    // DataStore
    implementation(libs.bundles.datastore)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}