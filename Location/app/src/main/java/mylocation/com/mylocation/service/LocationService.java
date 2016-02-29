package mylocation.com.mylocation.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by vangcaway on 1/16/2016.
 */
public class LocationService extends Service implements LocationListener {

  private final Context context;

  // The minimum distance to change Updates in meters
  private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

  // The minimum time between updates in milliseconds
  private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

  //Get Location through android os
  Location location;

  protected LocationManager locationManager;

  boolean isGPSEnabled;
  boolean isNetworkEnabled;

  public LocationService(Context context) {
    this.context = context;
  }

  @Override
  public void onLocationChanged(Location location) {

  }

  @Override
  public void onStatusChanged(String provider, int status, Bundle extras) {

  }

  @Override
  public void onProviderEnabled(String provider) {

  }

  @Override
  public void onProviderDisabled(String provider) {

  }

  @Nullable
  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }
}
