package androidx.databinding;

public class DataBinderMapperImpl extends MergedDataBinderMapper {
  DataBinderMapperImpl() {
    addMapper(new com.itau.ion.home.sample.DataBinderMapperImpl());
  }
}
