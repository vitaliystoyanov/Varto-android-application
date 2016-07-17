package es.esy.varto_novomyrgorod.varto.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import es.esy.varto_novomyrgorod.varto.R;

public class Toolbar extends RelativeLayout {
    private static final int DURATION_MILLIS = 700;

    private LinearLayout refreshLayout;
    private LinearLayout backLayout;
    private ImageView refreshImage;
    private TextView backTitle;
    private LinearLayout logo;

    private OnBackButtonListener backButtonListener;
    private OnRefreshButtonListener onRefreshButtonListener;

    public Toolbar(Context context) {
        super(context);
    }

    public Toolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.toolbar, this, true);

        refreshImage = (ImageView) findViewById(R.id.toolbar_image_refresh);
        backLayout = (LinearLayout) findViewById(R.id.toolbar_back_layout);
        backTitle = (TextView) findViewById(R.id.textview_status);
        logo = (LinearLayout) findViewById(R.id.toolbar_logo_layout);

        setupButtons();
    }

    private void setupButtons() {
        LinearLayout back = (LinearLayout) findViewById(R.id.toolbar_back_layout);
        back.setOnClickListener(v -> backButtonListener.onBack());
        refreshLayout = (LinearLayout) findViewById(R.id.toolbar_button_refresh);
        refreshLayout.setOnClickListener(v -> onRefreshButtonListener.onRefresh());
    }

    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public Toolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setBackButtonListener(OnBackButtonListener backButtonListener) {
        this.backButtonListener = backButtonListener;
    }

    public void setOnRefreshButtonListener(OnRefreshButtonListener onRefreshButtonListener) {
        this.onRefreshButtonListener = onRefreshButtonListener;
    }

    public void animateRefreshButton(Boolean state) {
        refreshLayout.setClickable(!state);
        if (state) {
            RotateAnimation anim = new RotateAnimation(0.0f, 360.0f,
                    Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            anim.setInterpolator(new LinearInterpolator());
            anim.setRepeatCount(Animation.INFINITE);
            anim.setDuration(DURATION_MILLIS);
            refreshImage.startAnimation(anim);
        } else {
            refreshImage.setAnimation(null);
        }
    }

    public void setVisibleLogo(boolean state) {
        logo.setVisibility(state ? VISIBLE : INVISIBLE);
    }

    public void setVisibleBackButton(boolean state) {
        backLayout.setVisibility(state ? VISIBLE : INVISIBLE);
    }

    public void setBackTitle(String text) {
        backTitle.setText(text);
    }

    public interface OnBackButtonListener {
        void onBack();
    }

    public interface OnRefreshButtonListener {
        void onRefresh();
    }
}
