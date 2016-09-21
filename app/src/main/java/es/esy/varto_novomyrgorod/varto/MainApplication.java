package es.esy.varto_novomyrgorod.varto;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import es.esy.varto_novomyrgorod.varto.database.Database;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";

    @Override
    public void onTerminate() {
        Database.close();
        super.onTerminate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
        Database.getInstance(this); // FIXME: 6/29/16 change to a correct name
    }
}
