
-keep class top.huahaizhi.onlyu.bean.** {*;}
-keep class top.huahaizhi.onlyu.widget.** {*;}

-keep class com.google.gson.** {*;}
-keep class sun.misc.Unsafe { *; }
-keep class com.google.gson.stream.** { *; }
-keep class com.google.gson.examples.android.model.** { *; }
-keep class com.google.** {
    <fields>;
    <methods>;
}

-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-dontwarn com.google.gson.**

-keep class com.j256.ormlite.** {*;}
-keep class com.j256.ormlite.** {
    <fields>;
    <methods>;
}
-dontwarn com.j256.ormlite.**
