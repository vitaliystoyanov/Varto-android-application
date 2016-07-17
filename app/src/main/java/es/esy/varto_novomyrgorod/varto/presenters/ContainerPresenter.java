package es.esy.varto_novomyrgorod.varto.presenters;

import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import es.esy.varto_novomyrgorod.varto.view.ContainerView;

public class ContainerPresenter extends MvpBasePresenter<ContainerView> {

    public void onReceive(Bundle bundle) {
        if (getView() != null) {
            getView().displayLoading(false);
            getView().displayNotification(bundle);
            getView().goToMaimMenu(bundle);
        }
    }

    public void onRefresh() {
        if (getView() != null) {
            getView().displayLoading(true);
        }
    }

    public void onBackPressed() {
        if (getView() != null) {
            getView().back();
        }
    }

    public void onClickToolbarBackButton() {
        if (getView() != null) {
            getView().back();
        }
    }
}

