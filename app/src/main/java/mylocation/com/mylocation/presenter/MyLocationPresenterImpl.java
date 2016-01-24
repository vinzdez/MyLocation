package mylocation.com.mylocation.presenter;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.TextUtils;
import android.util.Log;
import java.io.IOException;
import java.util.Date;
import javax.inject.Inject;
import mylocation.com.mylocation.observable.ObservableLocationProvider;
import mylocation.com.mylocation.view.MyLocationView;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action0;
import rx.functions.Action1;

/**
 * Created by vangcaway on 1/16/2016.
 */
public class MyLocationPresenterImpl implements MyLocationPresenter {

  private static String TAG = MyLocationPresenterImpl.class.getName();

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
    locationUpdateSubscription = observableLocationProvider.getObservable()
        .subscribe(new LocationUpdatedAction(context), new LocationErrorAction(), new LocationUpdatesCompleteAction());
  }

  @Override
  public void onDestroy() {
    locationUpdateSubscription.unsubscribe();
  }

  class ReverseGeolocationAction implements Action1<String> {
    @Override
    public void call(String address) {
      myLocationView.showReverseGeolocation(address);
    }
  }

  class LocationUpdatedAction implements Action1<Location> {
    private Context context;

    public LocationUpdatedAction(Context context) {
      this.context = context;
    }

    @Override
    public void call(Location location) {
      String value;
      if (location != null) {
        value = "Latitude: " + location.getLatitude() +
            "\n Longitude: " + location.getLongitude() +
            "\n Provider: " + location.getProvider() +
            "\n Accuracy: " + location.getAccuracy();
        myLocationView.showLocation(value);
        observableLocationProvider.getStringRepresentationOfLocation(context, location)
            .subscribe(new ReverseGeolocationAction(), new ReverseGeolocationErrorAction(),
                new LocationUpdatesCompleteAction());
      } else {
        myLocationView.showWarningSnackbar("Unable to Retrieve Location");
      }
    }
  }


  class ReverseGeolocationErrorAction implements Action1<Throwable> {
    @Override
    public void call(Throwable throwable) {
      myLocationView.showWarningSnackbar("Unable to Retrieve Address: " + throwable.getMessage());
    }
  }
  class LocationErrorAction implements Action1<Throwable> {
    @Override
    public void call(Throwable throwable) {
      myLocationView.showWarningSnackbar("Unable to Retrieve Location: " + throwable.getMessage());
    }
  }

  class LocationUpdatesCompleteAction implements Action0 {
    @Override
    public void call() {
      Log.i(TAG, "Completed: " + new Date().getTime() / 1000);
    }
  }


}
