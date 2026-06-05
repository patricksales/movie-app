package sampleapp;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\t0\bH\u0002J\u0010\u0010\u001c\u001a\u00020\u001d2\u0006\u0010\u001e\u001a\u00020\u001fH\u0014J\u001c\u0010 \u001a\u00020\u001d2\b\u0010!\u001a\u0004\u0018\u00010\"2\b\u0010#\u001a\u0004\u0018\u00010$H\u0016J\b\u0010%\u001a\u00020\u001dH\u0014R\u0014\u0010\u0003\u001a\u00020\u0004X\u0094\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006R\u001a\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\bX\u0094\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u0014\u0010\f\u001a\u00020\rX\u0094\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001b\u0010\u0010\u001a\u00020\u00118BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u0014\u0010\u0015\u001a\u0004\b\u0012\u0010\u0013R\u001b\u0010\u0016\u001a\u00020\u00178BX\u0082\u0084\u0002\u00a2\u0006\f\n\u0004\b\u001a\u0010\u0015\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006&"}, d2 = {"Lsampleapp/SampleActivity;", "Lbr/com/itau/ionsampleapp/features/featurelist/presentation/activity/SampleAppMainActivity;", "()V", "featureToggleConfiguration", "Lsampleapp/HomeFeatureToggleConfiguration;", "getFeatureToggleConfiguration", "()Lsampleapp/HomeFeatureToggleConfiguration;", "features", "", "Lbr/com/itau/ionsampleapp/features/featurelist/presentation/model/FeatureData;", "getFeatures", "()Ljava/util/List;", "graphResId", "", "getGraphResId", "()Ljava/lang/Integer;", "homeNavigation", "Lbr/com/itau/ion/home/publ/navigation/HomeNavigation;", "getHomeNavigation", "()Lbr/com/itau/ion/home/publ/navigation/HomeNavigation;", "homeNavigation$delegate", "Lkotlin/Lazy;", "viewModel", "Lsampleapp/SampleViewModel;", "getViewModel", "()Lsampleapp/SampleViewModel;", "viewModel$delegate", "featuresData", "navigate", "", "feature", "Lbr/com/itau/ionsampleapp/features/featurelist/presentation/model/FeatureType;", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "persistentState", "Landroid/os/PersistableBundle;", "onResume", "app_debug"})
public final class SampleActivity extends br.com.itau.ionsampleapp.features.featurelist.presentation.activity.SampleAppMainActivity {
    @org.jetbrains.annotations.NotNull()
    private final sampleapp.HomeFeatureToggleConfiguration featureToggleConfiguration = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<br.com.itau.ionsampleapp.features.featurelist.presentation.model.FeatureData> features = null;
    private final int graphResId = 0;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy homeNavigation$delegate = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlin.Lazy viewModel$delegate = null;
    
    public SampleActivity() {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected sampleapp.HomeFeatureToggleConfiguration getFeatureToggleConfiguration() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected java.util.List<br.com.itau.ionsampleapp.features.featurelist.presentation.model.FeatureData> getFeatures() {
        return null;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    protected java.lang.Integer getGraphResId() {
        return null;
    }
    
    private final br.com.itau.ion.home.publ.navigation.HomeNavigation getHomeNavigation() {
        return null;
    }
    
    private final sampleapp.SampleViewModel getViewModel() {
        return null;
    }
    
    @java.lang.Override()
    public void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState, @org.jetbrains.annotations.Nullable()
    android.os.PersistableBundle persistentState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final java.util.List<br.com.itau.ionsampleapp.features.featurelist.presentation.model.FeatureData> featuresData() {
        return null;
    }
    
    @java.lang.Override()
    protected void navigate(@org.jetbrains.annotations.NotNull()
    br.com.itau.ionsampleapp.features.featurelist.presentation.model.FeatureType feature) {
    }
}