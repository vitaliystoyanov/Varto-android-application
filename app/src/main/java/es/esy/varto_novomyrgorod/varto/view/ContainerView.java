package es.esy.varto_novomyrgorod.varto.view;

import android.os.Bundle;

import com.hannesdorfmann.mosby.mvp.MvpView;

public interface ContainerView extends MvpView {

    void displayLoading(boolean isVisible);

    void back();

    void displayNotification(Bundle bundle);

    void goToMaimMenu(Bundle bundle);
}
