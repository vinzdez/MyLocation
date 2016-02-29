package mylocation.com.mylocation.dagger.module;

import android.app.Application;
import mylocation.com.mylocation.activity.BaseActivity;
import mylocation.com.mylocation.dagger.component.DaggerMyApplicationComponent;
import mylocation.com.mylocation.dagger.component.MyApplicationComponent;

/**
 * Created by vangcaway on 1/16/2016.
 */
public class MyLocationApp extends Application {

  private MyApplicationComponent component;

  @Override
  public void onCreate() {
    super.onCreate();
    this.component = DaggerMyApplicationComponent.
        builder().myLocationModule(new MyLocationModule(this)).build();
  }

  public void inject(BaseActivity activity) {
    component.inject(activity);
  }

  public MyApplicationComponent getComponent() {
    return component;
  }
}