package com.esoxjem.movieguide.listing;

import com.esoxjem.movieguide.Movie;
import com.esoxjem.movieguide.util.RxUtils;

import java.util.List;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author arun
 */
class MoviesListingPresenterImpl implements MoviesListingPresenter
{
    private MoviesListingView view;
    private MoviesListingInteractor moviesInteractor;
    private Subscription fetchSubscription;

    MoviesListingPresenterImpl(MoviesListingInteractor interactor)
    {
        moviesInteractor = interactor;
    }

    @Override
    public void setView(MoviesListingView view)
    {
        this.view = view;
        displayMovies(1);}

    @Override
    public void destroy()
    {
        view = null;
        RxUtils.unsubscribe(fetchSubscription);
    }

    @Override
    public void displayMovies(int page)
    {
        if(page == 1)
            view.onSortingChanged();
        showLoading();
        fetchSubscription = moviesInteractor.fetchMovies(page).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onMovieFetchSuccess, this::onMovieFetchFailed);
    }

    @Override

    public void resetScroll(){
        view.resetScroll();
    }

    private void showLoading()
    {
        if (isViewAttached())
        {
            view.loadingStarted();
        }
    }

    private void onMovieFetchSuccess(List<Movie> movies)
    {
        if (isViewAttached())
        {
            view.showMovies(movies);
        }
    }

    private void onMovieFetchFailed(Throwable e)
    {
        view.loadingFailed(e.getMessage());
    }

    private boolean isViewAttached()
    {
        return view != null;
    }
}
