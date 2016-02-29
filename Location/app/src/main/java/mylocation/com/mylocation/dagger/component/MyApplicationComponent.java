package mylocation.com.mylocation.dagger.component;

import dagger.Component;
import javax.inject.Singleton;
import mylocation.com.mylocation.dagger.module.MyLocationApp;
import mylocation.com.mylocation.dagger.module.MyLocationModule;
import mylocation.com.mylocation.activity.BaseActivity;

/**
 * Created by vangcaway on 1/16/2016.
 */
@Singleton
@Component(
    modules = {
        MyLocationModule.class
    })
public interface MyApplicationComponent {
  void inject(MyLocationApp application);
  //activity
  void inject(BaseActivity baseActivity);

}
