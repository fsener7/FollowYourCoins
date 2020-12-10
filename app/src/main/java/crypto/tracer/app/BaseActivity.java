package crypto.tracer.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.LayoutRes;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import butterknife.ButterKnife;
import crypto.tracer.app.about.AboutMeActivity;
import crypto.tracer.app.webservice.APIService;
import crypto.tracer.app.webservice.RestClient;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
public class BaseActivity extends AppCompatActivity {

    private TextView tvActionButton;
    private TextView tvActionDescription;
    private RelativeLayout rlUserAction;
    private Toolbar toolbar;
    private TextView tvToolbarTitle;
    private RelativeLayout baseLayout;
    public RelativeLayout rlContainer;
    private ImageView ivToolbarImage;
    public static boolean isCoinAdded = false;
    public APIService service = RestClient.getAPIService();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        baseLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_base, null);
        setContentView(baseLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbarTitle = (TextView) findViewById(R.id.tvToolbarTitle);
        tvActionButton = (TextView) findViewById(R.id.tvActionButton);
        tvActionDescription = (TextView) findViewById(R.id.tvActionDescription);
        rlUserAction = (RelativeLayout) findViewById(R.id.rlUserAction);
        rlContainer = (RelativeLayout) findViewById(R.id.rlContainer);
        ivToolbarImage = (ImageView) findViewById(R.id.ivToolbarImage);
        setSupportActionBar(toolbar);
        ViewStub stub = (ViewStub) baseLayout.findViewById(R.id.container);
        stub.setLayoutResource(layoutResID);
        stub.inflate();
        ButterKnife.bind(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    public void initToolbar(String title, int drawable) {
        if (this instanceof AboutMeActivity) {

        } else {
            toolbar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(BaseActivity.this, AboutMeActivity.class));
                    overridePendingTransition(R.anim.slide_from_bottom, R.anim.stay);
                }
            });
        }

        tvToolbarTitle.setText(title);
        if (drawable != 0) {
            Drawable drawable1 = ContextCompat.getDrawable(this, drawable);
            ivToolbarImage.setImageDrawable(drawable1);

            ivToolbarImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });
        } else {
            Drawable drawable1 = ContextCompat.getDrawable(this, R.drawable.ic_logo_no_background);
            ivToolbarImage.setImageDrawable(drawable1);
        }
    }

    public void initUserAction(String action,
                               int actionIcon,
                               boolean showUserAction) {
        if (!showUserAction) {
            rlUserAction.setVisibility(View.GONE);
        }
    }
}
