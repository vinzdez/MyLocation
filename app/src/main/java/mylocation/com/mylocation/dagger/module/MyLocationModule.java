package mylocation.com.mylocation.dagger.module;

import android.app.Application;
import dagger.Module;
import dagger.Provides;
import mylocation.com.mylocation.dagger.PerApp;

/**
 * Created by vangcaway on 1/16/2016.
 */
@Module
public class MyLocationModule {

  private MyLocationApp app;

  public MyLocationModule(MyLocationApp app){
    this.app = app;
  }

  @Provides
  @PerApp
  MyLocationApp provideMyLocationApp(){
    return app;
  }

  @Provides
  @PerApp
  Application provideApplication(MyLocationApp app){
    return app;
  }
}
