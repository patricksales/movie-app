package com.itau.ion.home.sample;

import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.View;
import androidx.databinding.DataBinderMapper;
import androidx.databinding.DataBindingComponent;
import androidx.databinding.ViewDataBinding;
import java.lang.Integer;
import java.lang.Object;
import java.lang.Override;
import java.lang.RuntimeException;
import java.lang.String;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataBinderMapperImpl extends DataBinderMapper {
  private static final SparseIntArray INTERNAL_LAYOUT_ID_LOOKUP = new SparseIntArray(0);

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View view, int layoutId) {
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = view.getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
    }
    return null;
  }

  @Override
  public ViewDataBinding getDataBinder(DataBindingComponent component, View[] views, int layoutId) {
    if(views == null || views.length == 0) {
      return null;
    }
    int localizedLayoutId = INTERNAL_LAYOUT_ID_LOOKUP.get(layoutId);
    if(localizedLayoutId > 0) {
      final Object tag = views[0].getTag();
      if(tag == null) {
        throw new RuntimeException("view must have a tag");
      }
      switch(localizedLayoutId) {
      }
    }
    return null;
  }

  @Override
  public int getLayoutId(String tag) {
    if (tag == null) {
      return 0;
    }
    Integer tmpVal = InnerLayoutIdLookup.sKeys.get(tag);
    return tmpVal == null ? 0 : tmpVal;
  }

  @Override
  public String convertBrIdToString(int localId) {
    String tmpVal = InnerBrLookup.sKeys.get(localId);
    return tmpVal;
  }

  @Override
  public List<DataBinderMapper> collectDependencies() {
    ArrayList<DataBinderMapper> result = new ArrayList<DataBinderMapper>(12);
    result.add(new androidx.databinding.library.baseAdapters.DataBinderMapperImpl());
    result.add(new br.com.itau.investimentosui.DataBinderMapperImpl());
    result.add(new br.com.itau.ion.graficosinvestimento.DataBinderMapperImpl());
    result.add(new br.com.itau.ion.home.DataBinderMapperImpl());
    result.add(new br.com.itau.ion.ionnotificationcentral.DataBinderMapperImpl());
    result.add(new br.com.itau.ion.ionwebview.DataBinderMapperImpl());
    result.add(new br.com.itau.ionandroidcore.DataBinderMapperImpl());
    result.add(new br.com.itau.ionandroidnavigation.DataBinderMapperImpl());
    result.add(new br.com.itau.ionandroidstories.DataBinderMapperImpl());
    result.add(new br.com.itau.ioncriptoativos.DataBinderMapperImpl());
    result.add(new br.com.itau.ionformalizacaoremota.DataBinderMapperImpl());
    result.add(new br.com.itau.ionsampleapp.DataBinderMapperImpl());
    return result;
  }

  private static class InnerBrLookup {
    static final SparseArray<String> sKeys = new SparseArray<String>(41);

    static {
      sKeys.put(0, "_all");
      sKeys.put(1, "backImageViewState");
      sKeys.put(2, "badgeData");
      sKeys.put(3, "bottomImageViewState");
      sKeys.put(4, "closeStories");
      sKeys.put(5, "closeStoriesOnError");
      sKeys.put(6, "closeStoriesOnErrorAction");
      sKeys.put(7, "closeStoriesOnLoading");
      sKeys.put(8, "closeStoriesOnLoadingAction");
      sKeys.put(9, "compareBarItemEntity");
      sKeys.put(10, "drawableId");
      sKeys.put(11, "feedbackViewModel");
      sKeys.put(12, "feedbackViewState");
      sKeys.put(13, "fetchStories");
      sKeys.put(14, "highlightedTextState");
      sKeys.put(15, "investmentHighlightTemplateState");
      sKeys.put(16, "isLoading");
      sKeys.put(17, "itemData");
      sKeys.put(18, "itemTitle");
      sKeys.put(19, "managerViewModel");
      sKeys.put(20, "markupParser");
      sKeys.put(21, "notificationHistoryViewModel");
      sKeys.put(22, "notificationHistoryViewState");
      sKeys.put(23, "obj");
      sKeys.put(24, "productEntity");
      sKeys.put(25, "progressItemEntity");
      sKeys.put(26, "retryFetchStories");
      sKeys.put(27, "retryGetMediaAction");
      sKeys.put(28, "sandwichMediaViewModel");
      sKeys.put(29, "sandwichMediaViewState");
      sKeys.put(30, "scoreList");
      sKeys.put(31, "scoreNumber");
      sKeys.put(32, "selected");
      sKeys.put(33, "shouldPresent");
      sKeys.put(34, "shouldShow");
      sKeys.put(35, "showBottomBar");
      sKeys.put(36, "storiesManagerViewModel");
      sKeys.put(37, "storiesManagerViewState");
      sKeys.put(38, "textViewState");
      sKeys.put(39, "topImageViewState");
      sKeys.put(40, "vm");
    }
  }

  private static class InnerLayoutIdLookup {
    static final HashMap<String, Integer> sKeys = new HashMap<String, Integer>(0);
  }
}
