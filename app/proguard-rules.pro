-keepattributes Signature
-keepattributes *Annotation*
-keep class com.movieapp.data.remote.dto.** { *; }
-keep class com.movieapp.data.local.entity.** { *; }

-dontwarn okhttp3.**
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

-keep class * extends androidx.room.RoomDatabase
-keep @androidx.room.Entity class *
