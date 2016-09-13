package es.esy.varto_novomyrgorod.varto;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import es.esy.varto_novomyrgorod.varto.fragments.CatalogsFragment;
import es.esy.varto_novomyrgorod.varto.fragments.GoodsFragment;
import es.esy.varto_novomyrgorod.varto.fragments.MainFragment;
import es.esy.varto_novomyrgorod.varto.fragments.MenuShopFragment;
import es.esy.varto_novomyrgorod.varto.fragments.NewsFragment;
import es.esy.varto_novomyrgorod.varto.pojo.Shop;

public class NavigationManager {
    private FragmentManager manager;

    public void init(FragmentManager fragmentManager) {
        manager = fragmentManager;
    }

    private void open(Fragment fragment) {
        if (manager != null) {
            manager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .addToBackStack(fragment.toString())
                    .commit();
        }
    }

    private void openAsRoot(Fragment fragment) {
        popEveryFragment();
        open(fragment);
    }

    private void popEveryFragment() {
        int backStackCount = manager.getBackStackEntryCount();
        for (int i = 0; i < backStackCount; i++) {
            int backStackId = manager.getBackStackEntryAt(i).getId();
            manager.popBackStack(backStackId, FragmentManager.POP_BACK_STACK_INCLUSIVE);

        }
    }

    public void navigateBack(Activity baseActivity) {
        if (manager.getBackStackEntryCount() == 1) {
            baseActivity.finish();
        } else {
            manager.popBackStackImmediate();
        }
    }

    public void startMainMenu() {
        openAsRoot(new MainFragment());
    }

    public void startMainMenu(Bundle bundle) {
        MainFragment fragment = new MainFragment();
        fragment.setArguments(bundle);
        openAsRoot(fragment);
    }

    public void startMenuShop(Shop shop) {
        open(MenuShopFragment.newInstance(shop));
    }

    public void startNews(Shop shop) {
        open(NewsFragment.newInstance(shop));
    }

    public void startCatalog(Shop shop) {
        open(CatalogsFragment.newInstance(shop));
    }

    public void startGoods(Shop shop, String catalog) {
        open(GoodsFragment.newInstance(shop, catalog));
    }
}
