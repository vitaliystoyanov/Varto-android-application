package es.esy.varto_novomyrgorod.varto;

import android.app.Application;
import android.util.Log;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import es.esy.varto_novomyrgorod.varto.database.DatabaseProvider;

public class MainApplication extends Application {
    private static final String TAG = "MainApplication";

    @Override
    public void onTerminate() {
        DatabaseProvider.close();
        super.onTerminate();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .build();
        ImageLoader.getInstance().init(config);
        DatabaseProvider.getInstance(this); // FIXME: 6/29/16 change to a correct name
    }
}
