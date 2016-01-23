package mylocation.com.mylocation.presenter;

import android.content.Context;
import android.location.Location;
import android.util.Log;
import java.util.Date;
import javax.inject.Inject;
import mylocation.com.mylocation.observable.ObservableLocationProvider;
import mylocation.com.mylocation.view.MyLocationView;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by vangcaway on 1/16/2016.
 */
public class MyLocationPresenterImpl implements MyLocationPresenter {

  private MyLocationView myLocationView;

  private Subscription locationUpdateSubscription;
  private ObservableLocationProvider observableLocationProvider;

  @Inject
  public MyLocationPresenterImpl(MyLocationView myLocationView) {
    this.myLocationView = myLocationView;
  }

  @Override
  public void onGetLocationClick(Context context) {
    this.observableLocationProvider = new ObservableLocationProvider(context);
    locationUpdateSubscription
        = observableLocationProvider.getObservable()
        .subscribe(
            new LocationUpdatedAction(),
            new LocationErrorAction(),
            new LocationUpdatesCompleteAction());
  }

  @Override
  public void onDestroy() {
    locationUpdateSubscription.unsubscribe();
  }

  class LocationUpdatedAction implements Action1<Location> {
    @Override
    public void call(Location location) {
      String value;
      if(location != null){
        value = "Latitude: " + location.getLatitude() +
                "\n Longitude: " + location.getLongitude() +
                "\n Provider: " + location.getProvider() +
                "\n Accuracy: " + location.getAccuracy();
      }else {
        value = "Unablr to Retrieve Location";
      }
      myLocationView.showLocation(value);
    }
  }

  class LocationErrorAction implements Action1<Throwable> {
    @Override
    public void call(Throwable throwable) {
      myLocationView.showLocation("Location Error: " + throwable.getMessage());
    }
  }

  class LocationUpdatesCompleteAction implements Action0 {
    @Override
    public void call() {
      String lastCompleted = "Completed: " + new Date().getTime()/1000;
    }
  }
}
