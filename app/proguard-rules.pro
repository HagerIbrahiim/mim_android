# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-keep class com.google.android.gms.maps.model.Marker { *; }
-keep,includecode class com.google.android.libraries.maps.** { *; }
-keep,includecode class com.google.android.apps.gmm.** { *; }
-keep,includecode class com.google.common.logging.** { *; }

# Retrofit
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# OkHttp
-dontwarn okhttp3.**
-keep class okhttp3.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Gson
-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keepattributes Signature
-keepattributes Exceptions

# Moshi
-dontwarn javax.annotation.**
-keepclasseswithmembers class * {
    @com.squareup.moshi.* <methods>;
}
-keep @com.squareup.moshi.JsonQualifier interface *
-keepclassmembers class ** {
    @com.squareup.moshi.FromJson *;
    @com.squareup.moshi.ToJson *;
}

-keep class com.trianglz.core.modules.** { *; }
-keep class com.trianglz.core_compose.modules.** { *; }
-keep class com.trianglz.mimar.modules.** { *; }
-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }