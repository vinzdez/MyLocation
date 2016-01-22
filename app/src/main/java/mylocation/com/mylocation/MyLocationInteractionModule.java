package mylocation.com.mylocation;

import dagger.Module;
import dagger.Provides;
import mylocation.com.mylocation.dagger.PerApp;
import mylocation.com.mylocation.presenter.MyLocationPresenter;
import mylocation.com.mylocation.presenter.MyLocationPresenterImpl;
import mylocation.com.mylocation.view.MyLocationView;

/**
 * Created by vangcaway on 1/16/2016.
 */
@Module
public class MyLocationInteractionModule {

  private MyLocationView view;

  public MyLocationInteractionModule(MyLocationView view) {
    this.view = view;
  }

  @Provides
  @PerApp
  public MyLocationView provideView() {
    return view;
  }

  @Provides
  @PerApp
  public MyLocationPresenter providePresenter(MyLocationView myLocationView) {
    return new MyLocationPresenterImpl(myLocationView);
  }
}
