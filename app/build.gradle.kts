import com.android.build.gradle.internal.api.BaseVariantOutputImpl
import java.io.ByteArrayOutputStream
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.sdv.tree3"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.sdv.tree3"
        minSdk = 26
        targetSdk = 34
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
    applicationVariants.all {
        val variantName = name
        val apkName = "${variantName}_$versionName.apk"
        outputs.map { it as BaseVariantOutputImpl }
            .forEach { it.outputFileName = apkName }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    return "git rev-parse --abbrev-ref HEAD".runCommand()
}

fun String.runCommand(): String {
    try {
        return ByteArrayOutputStream().use {
            exec {
                commandLine(this@runCommand.split(" "))
                standardOutput = it
            }
            String(it.toByteArray()).trim()
        }
    } catch (t: Throwable) {
        throw GradleException("Error when runCommand: ${this@runCommand} - ${t.localizedMessage}")
    }
}

dependencies {
    implementation(project(":common"))
    implementation(project(":app_features:main_feature"))

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
    implementation(libs.androidx.lifecycle.runtime.ktx)
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