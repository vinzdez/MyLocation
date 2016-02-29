package mylocation.com.mylocation.dagger.component;

import dagger.Component;
import mylocation.com.mylocation.MyLocationInteractionModule;
import mylocation.com.mylocation.activity.MyLocationActivity;
import mylocation.com.mylocation.dagger.PerApp;
import mylocation.com.mylocation.presenter.MyLocationPresenter;

/**
 * Created by vangcaway on 1/16/2016.
 */
@PerApp
@Component(dependencies = MyApplicationComponent.class,
    modules = MyLocationInteractionModule.class)
public interface MyLocationComponent {
  void inject(MyLocationActivity myLocationActivity);

  MyLocationPresenter myLocationPresenter();
}
