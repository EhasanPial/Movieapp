package server;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class TypeMoviePagingFactory extends   DataSource.Factory {

    private TypeLoadBefore pagingLoadBefore;
    private ApiInterface apiInterface;
    private Application application;
    private MutableLiveData<TypeLoadBefore> mutableLiveData;

    public TypeMoviePagingFactory(ApiInterface apiInterface, Application application) {
        this.apiInterface = apiInterface;
        this.application = application;
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        pagingLoadBefore = new TypeLoadBefore(apiInterface, application);
        mutableLiveData.postValue(pagingLoadBefore);
        return pagingLoadBefore;

    }


    public MutableLiveData<TypeLoadBefore> getMutableLiveData() {
        return mutableLiveData;
    }
}
