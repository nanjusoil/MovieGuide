package com.esoxjem.movieguide.listing;

/**
 * @author arun
 */
public interface MoviesListingPresenter
{
    void displayMovies(int page);

    void setView(MoviesListingView view);

    void resetScroll();

    void destroy();
}
