package mylocation.com.mylocation.observable;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by vangcaway on 1/23/2016.
 */
public class ObservableLocationProvider {

  public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

  private Context context;
  private GoogleApiClient googleApiClient;

  public ObservableLocationProvider(Context context) {
    this.context = context;
    this.googleApiClient = new GoogleApiClient.Builder(context).addApi(LocationServices.API).build();
  }

  public Observable<Location> getOndemandObservable() {
    return Observable.create(new ObservableConnection(googleApiClient))
        .filter(new ConnectionStatusFilter())
        .flatMap(new ObservableLocationMap());
  }

  public Observable<String> getStringRepresentationOfLocation(Context context,Location location){
    return Observable.create(new ObservableGeocoderConnection(context, location));
  }


  public Observable<Location> getObservable() {
    return getOndemandObservable().first();
  }

  public class ConnectionStatusFilter implements Func1<ObservableConnection.CONNECTION_STATUS, Boolean> {

    @Override
    public Boolean call(ObservableConnection.CONNECTION_STATUS connection_status) {
      return connection_status.compareTo(ObservableConnection.CONNECTION_STATUS.CONNECTED) == 0;
    }
  }

  public class ObservableLocationMap implements Func1<ObservableConnection.CONNECTION_STATUS, Observable<Location>> {

    @Override
    public Observable<Location> call(ObservableConnection.CONNECTION_STATUS connection_status) {

      return Observable.create(new ObservableLocation(googleApiClient, LocationServices.FusedLocationApi));
    }
  }

  public class ObservableLocation implements Observable.OnSubscribe<Location> {

    private FusedLocationProviderApi fusedLocationProviderApi;
    private GoogleApiClient googleApiClient;

    private Subscriber<? super Location> observer;

    public ObservableLocation(GoogleApiClient googleApiClient, FusedLocationProviderApi fusedLocationProviderApi) {
      this.fusedLocationProviderApi = fusedLocationProviderApi;
      this.googleApiClient = googleApiClient;
    }

    @Override
    public void call(Subscriber<? super Location> subscriber) {
      observer = subscriber;
      if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
          != PackageManager.PERMISSION_GRANTED) {

        // Should we show an explanation?
        if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context,
            Manifest.permission.ACCESS_FINE_LOCATION)) {

          // Show an expanation to the user *asynchronously* -- don't block
          // this thread waiting for the user's response! After the user
          // sees the explanation, try again to request the permission.

          //Prompt the user once explanation has been shown
          ActivityCompat.requestPermissions((Activity) context,
              new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
          // No explanation needed, we can request the permission.
          ActivityCompat.requestPermissions((Activity) context,
              new String[] { Manifest.permission.ACCESS_FINE_LOCATION }, MY_PERMISSIONS_REQUEST_LOCATION);
        }
      } else {
        Location location = fusedLocationProviderApi.getLastLocation(googleApiClient);
        observer.onNext(location);
      }
    }
  }
}
