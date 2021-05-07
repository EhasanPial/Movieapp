package server;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import Model.MovieList;

public class TopRatedPagingFactory extends DataSource.Factory {

    private TopRatedLoadBefore pagingLoadBefore;
    private ApiInterface apiInterface;
    private Application application;
    private MutableLiveData<TopRatedLoadBefore> mutableLiveData;

    public TopRatedPagingFactory(ApiInterface apiInterface, Application application) {
        this.apiInterface = apiInterface;
        this.application = application;
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource create() {
        pagingLoadBefore = new TopRatedLoadBefore(apiInterface, application);
        mutableLiveData.postValue(pagingLoadBefore);
        return pagingLoadBefore;

    }


    public MutableLiveData<TopRatedLoadBefore> getMutableLiveData() {
        return mutableLiveData;
    }
}
