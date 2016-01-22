package mylocation.com.mylocation.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import butterknife.ButterKnife;
import mylocation.com.mylocation.dagger.component.MyApplicationComponent;
import mylocation.com.mylocation.dagger.module.MyLocationApp;

/**
 * Created by vangcaway on 1/16/2016.
 */
public class BaseActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState){
    super.onCreate(savedInstanceState);
  }


  @Override
  public void onPause() {
    super.onPause();
  }

  @Override
  protected void onDestroy(){
    super.onDestroy();
    ButterKnife.reset(this);
  }
  protected MyApplicationComponent getApplicationComponent(){
    return ((MyLocationApp)getApplication()).getComponent();
  }
}
