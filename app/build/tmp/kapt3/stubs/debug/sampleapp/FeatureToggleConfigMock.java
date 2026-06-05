package sampleapp;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000e\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u001b\u0010\u0007\u001a\u0002H\b\"\u0004\b\u0000\u0010\b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016J\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u0005\u001a\u00020\u0006H\u0016\u00a8\u0006\u000e"}, d2 = {"Lsampleapp/FeatureToggleConfigMock;", "Lbr/com/itau/ionandroidcore/remoteconfig/FeatureToggleConfig;", "()V", "featureIsEnable", "", "feature", "Lbr/com/itau/ionandroidcore/remoteconfig/Feature;", "featureJsonData", "T", "(Lbr/com/itau/ionandroidcore/remoteconfig/Feature;)Ljava/lang/Object;", "featureLongData", "", "featureStringData", "", "app_debug"})
public final class FeatureToggleConfigMock implements br.com.itau.ionandroidcore.remoteconfig.FeatureToggleConfig {
    
    public FeatureToggleConfigMock() {
        super();
    }
    
    @java.lang.Override()
    public boolean featureIsEnable(@org.jetbrains.annotations.NotNull()
    br.com.itau.ionandroidcore.remoteconfig.Feature feature) {
        return false;
    }
    
    @java.lang.Override()
    public <T extends java.lang.Object>T featureJsonData(@org.jetbrains.annotations.NotNull()
    br.com.itau.ionandroidcore.remoteconfig.Feature feature) {
        return null;
    }
    
    @java.lang.Override()
    public long featureLongData(@org.jetbrains.annotations.NotNull()
    br.com.itau.ionandroidcore.remoteconfig.Feature feature) {
        return 0L;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String featureStringData(@org.jetbrains.annotations.NotNull()
    br.com.itau.ionandroidcore.remoteconfig.Feature feature) {
        return null;
    }
}