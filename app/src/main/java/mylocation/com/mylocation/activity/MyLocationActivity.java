package mylocation.com.mylocation.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import javax.inject.Inject;
import mylocation.com.mylocation.MyLocationInteractionModule;
import mylocation.com.mylocation.R;
import mylocation.com.mylocation.dagger.component.DaggerMyLocationComponent;
import mylocation.com.mylocation.presenter.MyLocationPresenterImpl;
import mylocation.com.mylocation.view.MyLocationView;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;

public class MyLocationActivity extends BaseActivity implements MyLocationView {

  @Inject
  MyLocationPresenterImpl myLocationPresenterImpl;

  @InjectView(R.id.btn_get_loc)
  Button getLocBtn;

  @InjectView(R.id.samp_text)
  TextView sampText;

  @InjectView(R.id.main_loc_layout)
  CoordinatorLayout coordinatorLayout;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_my_location);

    DaggerMyLocationComponent.builder()
        .myApplicationComponent(getApplicationComponent())
        .myLocationInteractionModule(new MyLocationInteractionModule(this))
        .build()
        .inject(this);

    ButterKnife.inject(this);
  }

  @Override
  protected void onResume() {
    super.onResume();
  }

  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  public void onDestroy(){
    super.onDestroy();
    myLocationPresenterImpl.onDestroy();
  }

  @OnClick(R.id.btn_get_loc)
  void getLocationBtn() {
    myLocationPresenterImpl.onGetLocationClick(this);
  }

  @Override
  public void showLocation(String location) {
    sampText.setText(location);
    SpannableStringBuilder snackbarText = new SpannableStringBuilder();
    snackbarText.append("Add ");
    int boldStart = snackbarText.length();
    snackbarText.append(location);
    snackbarText.setSpan(new ForegroundColorSpan(0xFFFF0000), boldStart, snackbarText.length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    snackbarText.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), boldStart, snackbarText.length(),
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
    Snackbar.make(coordinatorLayout, snackbarText, Snackbar.LENGTH_LONG).show();

  }

}
