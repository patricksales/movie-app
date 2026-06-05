package sampleapp;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016JL\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00062\"\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r0\fj\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\r`\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016JL\u0010\t\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\"\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00110\fj\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0011`\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016JL\u0010\u0012\u001a\u00020\n2\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\"\u0010\u000b\u001a\u001e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00110\fj\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u0011`\u000e2\u0006\u0010\u000f\u001a\u00020\u00042\u0006\u0010\u0010\u001a\u00020\u0004H\u0016\u00a8\u0006\u0013"}, d2 = {"Lsampleapp/FakeSaNavigator;", "Lbr/com/itau/sdk/sa/navigator/provider/SANavigator;", "()V", "canOpen", "", "route", "", "context", "Landroid/content/Context;", "navigateTo", "", "params", "Ljava/util/HashMap;", "Ljava/io/Serializable;", "Lkotlin/collections/HashMap;", "shouldCallFallbackOnError", "cleanStack", "", "open", "app_debug"})
public final class FakeSaNavigator implements br.com.itau.sdk.sa.navigator.provider.SANavigator {
    
    public FakeSaNavigator() {
        super();
    }
    
    @java.lang.Override()
    public boolean canOpen(@org.jetbrains.annotations.NotNull()
    java.lang.String route, @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    @java.lang.Override()
    public void navigateTo(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String route, @org.jetbrains.annotations.NotNull()
    java.util.HashMap<java.lang.String, java.io.Serializable> params, boolean shouldCallFallbackOnError, boolean cleanStack) {
    }
    
    @java.lang.Override()
    public void navigateTo(@org.jetbrains.annotations.NotNull()
    java.lang.String route, @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.HashMap<java.lang.String, java.lang.Object> params, boolean shouldCallFallbackOnError, boolean cleanStack) {
    }
    
    @java.lang.Override()
    public void open(@org.jetbrains.annotations.NotNull()
    java.lang.String route, @org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.util.HashMap<java.lang.String, java.lang.Object> params, boolean shouldCallFallbackOnError, boolean cleanStack) {
    }
}