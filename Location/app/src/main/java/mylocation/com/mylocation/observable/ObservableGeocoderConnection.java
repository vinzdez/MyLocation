package mylocation.com.mylocation.observable;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.text.TextUtils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by vangcaway on 1/24/2016.
 */
public class ObservableGeocoderConnection implements Observable.OnSubscribe<String> {

  private ObservableGeocoderConnection observableGeocoderConnection = this;
  private Context context;
  private Location location;
  private Subscriber<? super String> observer;

  public ObservableGeocoderConnection(Context context, Location location) {
    this.context = context;
    this.location = location;
  }

  @Override
  public void call(Subscriber<? super String> subscriber) {
    observer = subscriber;
    Geocoder geocoder = new Geocoder(context);
    List<Address> addresses = new ArrayList<>();
    try {
      addresses.addAll(geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1));
      getStringLocation(addresses);
    } catch (IOException e) {
      observer.onError(e);
    }
  }

  private void getStringLocation(List<Address> addresses) {
    if (!addresses.isEmpty()) {
      observer.onNext(getStringAddress(addresses.get(0)));
    } else {
      observer.onError(new Throwable("Unable To Retrieve Location Check Your Internet Connection"));
    }
  }

  public String getStringAddress(Address address) {
    String value = "";

    if (!TextUtils.isEmpty(address.getLocality())) {
      value = appendString(value, address.getLocality());
    }
    if (!TextUtils.isEmpty(address.getAdminArea())) {
      value = appendString(value, address.getAdminArea());
    }

    if (!TextUtils.isEmpty(address.getThoroughfare())) {
      value = appendString(value, address.getThoroughfare());
    }
    if (!TextUtils.isEmpty(address.getSubLocality())) {
      value = appendString(value,address.getSubLocality());
    }
    if (!TextUtils.isEmpty(address.getCountryCode())) {
      value = appendString(value, address.getCountryCode());
    }
    if (!TextUtils.isEmpty(address.getSubThoroughfare())) {
      value = appendString(value,address.getSubThoroughfare());
    }
    if (!TextUtils.isEmpty(address.getPostalCode())) {
      value = appendString(value,address.getPostalCode());
    }

    return value;
  }

  private String appendString(String value, String addressInfo) {
    if (TextUtils.isEmpty(value)) {
      return value + addressInfo;
    } else {
      return value + " " + addressInfo;
    }
  }
}
