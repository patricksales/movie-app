package sampleapp;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\u0018\u00002\u00020\u00012\u00020\u00022\u00020\u0003B\u0005\u00a2\u0006\u0002\u0010\u0004J\u0019\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0096\u0001J\u000b\u0010\u000b\u001a\u0004\u0018\u00010\bH\u0096\u0001J\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0096\u0001J\b\u0010\u000f\u001a\u00020\u0010H\u0002JS\u0010\u0011\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0007\u001a\u00020\b2$\b\u0002\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00150\u0014j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0015`\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00062\b\b\u0002\u0010\u0018\u001a\u00020\u0006H\u0097\u0001JS\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2$\b\u0002\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00190\u0014j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0019`\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00062\b\b\u0002\u0010\u0018\u001a\u00020\u0006H\u0097\u0001J\b\u0010\u001a\u001a\u00020\u0012H\u0016JS\u0010\u001b\u001a\u00020\u00122\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2$\b\u0002\u0010\u0013\u001a\u001e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00190\u0014j\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u0019`\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u00062\b\b\u0002\u0010\u0018\u001a\u00020\u0006H\u0096\u0001\u00a8\u0006\u001c"}, d2 = {"Lsampleapp/Application;", "Lbr/com/itau/ionsampleapp/SampleApplication;", "Lbr/com/itau/navigator/register/NavigatorRegister;", "Lbr/com/itau/sdk/sa/navigator/provider/SANavigator;", "()V", "canOpen", "", "route", "", "context", "Landroid/content/Context;", "getFallbackRoute", "getRedirects", "", "Lbr/com/itau/navigator/model/BaseNavRedirect;", "getSALikert", "Lorg/koin/core/module/Module;", "navigateTo", "", "params", "Ljava/util/HashMap;", "Ljava/io/Serializable;", "Lkotlin/collections/HashMap;", "shouldCallFallbackOnError", "cleanStack", "", "onCreate", "open", "app_debug"})
public final class Application extends br.com.itau.ionsampleapp.SampleApplication implements br.com.itau.navigator.register.NavigatorRegister, br.com.itau.sdk.sa.navigator.provider.SANavigator {
    
    public Application() {
        super();
    }
    
    @java.lang.Override()
    public void onCreate() {
    }
    
    private final org.koin.core.module.Module getSALikert() {
        return null;
    }
    
    @java.lang.Override()
    public boolean canOpen(@org.jetbrains.annotations.NotNull()
    java.lang.String route, @org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public java.lang.String getFallbackRoute() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.util.List<br.com.itau.navigator.model.BaseNavRedirect> getRedirects() {
        return null;
    }
    
    @java.lang.Override()
    @java.lang.Deprecated()
    public void navigateTo(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String route, @org.jetbrains.annotations.NotNull()
    java.util.HashMap<java.lang.String, java.io.Serializable> params, boolean shouldCallFallbackOnError, boolean cleanStack) {
    }
    
    @java.lang.Override()
    @java.lang.Deprecated()
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