package server;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

public class PagingFactory extends DataSource.Factory {

    private PagingLoadBefore pagingLoadBefore;
    private ApiInterface apiInterface;
    private Application application;
    private MutableLiveData<PagingLoadBefore> mutableLiveData;

    public PagingFactory(ApiInterface apiInterface, Application application) {
        this.apiInterface = apiInterface;
        this.application = application;
        mutableLiveData = new MutableLiveData<PagingLoadBefore>();
    }

    @Override
    public DataSource create() {
        pagingLoadBefore = new PagingLoadBefore(apiInterface, application);
        mutableLiveData.postValue(pagingLoadBefore);
        return pagingLoadBefore;

    }


    public MutableLiveData<PagingLoadBefore> getMutableLiveData() {
        return mutableLiveData;
    }
}
