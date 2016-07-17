package es.esy.varto_novomyrgorod.varto.activity;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import es.esy.varto_novomyrgorod.varto.NavigationManager;
import es.esy.varto_novomyrgorod.varto.R;
import es.esy.varto_novomyrgorod.varto.presenters.ContainerPresenter;
import es.esy.varto_novomyrgorod.varto.service.ContentIntentService;
import es.esy.varto_novomyrgorod.varto.view.ContainerView;
import es.esy.varto_novomyrgorod.varto.view.Toolbar;

public class ContainerActivity extends MvpActivity<ContainerView, ContainerPresenter>
        implements ContainerView, Toolbar.OnRefreshButtonListener, Toolbar.OnBackButtonListener {
    private static final String TAG = "ContainerActivity";
    private static final int NOTIFICATION_ID = 1;
    private NavigationManager navigationManager;
    private RelativeLayout foreground;
    private Toolbar toolbar;

    private BroadcastReceiver reportStatusReceiver;
    private NotificationManager notificationManager;
    private NotificationCompat.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foreground = (RelativeLayout) findViewById(R.id.foreground_loading);

        setupBroadcastReceiver();
        setupNavigationManager();
        setupToolbar();
        setupNotification();

        presenter.onRefresh(); // FIXME: 7/10/16 move
        notificationManager.cancel(NOTIFICATION_ID);
        ContentIntentService.start(getApplicationContext());
    }

    private void setupNotification() {
        notificationManager = (NotificationManager) getApplicationContext()
                .getSystemService(Context.NOTIFICATION_SERVICE);
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        Intent resultIntent = new Intent(this, ContainerActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(ContainerActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_newspaper)
                .setLargeIcon(icon)
                .setContentIntent(resultPendingIntent)
                .setContentTitle(getString(R.string.notification_content_title));
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackButtonListener(this);
        toolbar.setOnRefreshButtonListener(this);
    }

    private void setupNavigationManager() {
        navigationManager = new NavigationManager();
        navigationManager.init(getSupportFragmentManager());
        navigationManager.startMainMenu();
    }

    @NonNull
    @Override
    public ContainerPresenter createPresenter() {
        return new ContainerPresenter();
    }

    private void setupBroadcastReceiver() {
        reportStatusReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(ContentIntentService.BROADCAST_ACTION)) {
                    presenter.onReceive(intent.getExtras());
                    Log.d(TAG, "onReceive: fresh news(PLUS, DISHES) - "
                            + intent.getExtras().getInt(ContentIntentService.EXTRA_NEWS_PLUS)
                            + ", "
                            + intent.getExtras().getInt(ContentIntentService.EXTRA_NEWS_DISHES));
                    Log.d(TAG, "onReceive: new goods(PLUS, DISHES) - "
                            + intent.getExtras().getInt(ContentIntentService.EXTRA_GOODS_PLUS)
                            + ", "
                            + intent.getExtras().getInt(ContentIntentService.EXTRA_GOODS_DISHES));
                }
            }
        };
        IntentFilter statusIntentFilter = new IntentFilter(ContentIntentService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                reportStatusReceiver,
                statusIntentFilter);
    }

    @Override
    public void displayNotification(Bundle bundle) {
        String descriptionNewContent = createStringForNotify(bundle);
        if (descriptionNewContent.length() > 0) {
            builder.setStyle(new NotificationCompat
                    .BigTextStyle()
                    .bigText(descriptionNewContent));
            notificationManager.notify(NOTIFICATION_ID, builder.build());
        }
    }

    private String createStringForNotify(Bundle bundle) {
        int quantityNewsPlus = bundle.getInt(ContentIntentService.EXTRA_NEWS_PLUS);
        int quantityNewsDishes = bundle.getInt(ContentIntentService.EXTRA_NEWS_DISHES);
        int quantityGoodsPlus = bundle.getInt(ContentIntentService.EXTRA_GOODS_PLUS);
        int quantityGoodsDishes = bundle.getInt(ContentIntentService.EXTRA_GOODS_DISHES);
        StringBuilder builder = new StringBuilder();
        if (quantityNewsPlus > 0) {
            builder.append(getString(R.string.shop_varto_plus))
                    .append(quantityNewsPlus)
                    .append(getString(R.string.notification_fresh_news));
        }
        if (quantityGoodsPlus > 0) {
            builder.append(getString(R.string.shop_varto_plus))
                    .append(quantityGoodsPlus)
                    .append(getString(R.string.notification_new_goods));
        }
        if (quantityNewsDishes > 0) {
            builder.append(getString(R.string.shop_varto_dishes))
                    .append(quantityNewsDishes)
                    .append(getString(R.string.notification_fresh_news));
        }
        if (quantityGoodsDishes > 0) {
            builder.append(getString(R.string.shop_varto_dishes))
                    .append(quantityGoodsDishes)
                    .append(getString(R.string.notification_new_goods));
        }
        return builder.toString();
    }

    @Override
    public void onBack() {
        presenter.onClickToolbarBackButton();
    }

    @Override
    public void onRefresh() {
        presenter.onRefresh();
        ContentIntentService.start(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        notificationManager.cancel(NOTIFICATION_ID);
        LocalBroadcastManager.getInstance(getApplicationContext())
                .unregisterReceiver(reportStatusReceiver);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        presenter.onBackPressed();
    }

    public NavigationManager getNavigationManager() {
        return navigationManager;
    }

    @Override
    public void back() {
        navigationManager.navigateBack(this);
    }

    @Override
    public void displayLoading(boolean isVisible) {
        foreground.setVisibility(isVisible ? View.VISIBLE : View.INVISIBLE);
        toolbar.animateRefreshButton(isVisible);
    }

    @Override
    public void goToMaimMenu(Bundle bundle) {
        navigationManager.startMainMenu(bundle);
    }
}