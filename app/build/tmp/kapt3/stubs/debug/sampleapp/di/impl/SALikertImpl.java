package sampleapp.di.impl;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\b\u0000\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0014\u0010\u0007\u001a\u00020\b2\n\b\u0002\u0010\t\u001a\u0004\u0018\u00010\nH\u0002J-\u0010\u000b\u001a\u0004\u0018\u00010\f2\b\u0010\t\u001a\u0004\u0018\u00010\n2\u0017\u0010\r\u001a\u0013\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\b0\u000e\u00a2\u0006\u0002\b\u0010H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lsampleapp/di/impl/SALikertImpl;", "Lbr/com/itau/ionandroidcore/survey/likert/SALikertIon;", "saRequestProvider", "Lbr/com/itau/feature/sa/network/interfaces/providers/SARequestProvider;", "saToggleProvider", "Lbr/com/itau/feature/sa/cache/providers/SAToggleProvider;", "(Lbr/com/itau/feature/sa/network/interfaces/providers/SARequestProvider;Lbr/com/itau/feature/sa/cache/providers/SAToggleProvider;)V", "initLikert", "", "callbacksProvider", "Lbr/com/itau/sdk/satisfacao/digital/commons/providers/SurveySatisfactionCallbacksHandler;", "requestLikert", "Landroidx/fragment/app/Fragment;", "configurator", "Lkotlin/Function1;", "Lbr/com/itau/sdk/satisfacao/digital/configurator/LikertPfConfigurator;", "Lkotlin/ExtensionFunctionType;", "app_debug"})
public final class SALikertImpl implements br.com.itau.ionandroidcore.survey.likert.SALikertIon {
    @org.jetbrains.annotations.NotNull()
    private final br.com.itau.feature.sa.network.interfaces.providers.SARequestProvider saRequestProvider = null;
    @org.jetbrains.annotations.NotNull()
    private final br.com.itau.feature.sa.cache.providers.SAToggleProvider saToggleProvider = null;
    
    public SALikertImpl(@org.jetbrains.annotations.NotNull()
    br.com.itau.feature.sa.network.interfaces.providers.SARequestProvider saRequestProvider, @org.jetbrains.annotations.NotNull()
    br.com.itau.feature.sa.cache.providers.SAToggleProvider saToggleProvider) {
        super();
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.Nullable()
    public androidx.fragment.app.Fragment requestLikert(@org.jetbrains.annotations.Nullable()
    br.com.itau.sdk.satisfacao.digital.commons.providers.SurveySatisfactionCallbacksHandler callbacksProvider, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super br.com.itau.sdk.satisfacao.digital.configurator.LikertPfConfigurator, kotlin.Unit> configurator) {
        return null;
    }
    
    private final void initLikert(br.com.itau.sdk.satisfacao.digital.commons.providers.SurveySatisfactionCallbacksHandler callbacksProvider) {
    }
}