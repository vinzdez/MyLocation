package mylocation.com.mylocation.observable;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action0;
import rx.subscriptions.Subscriptions;

/**
 * Created by vangcaway on 1/23/2016.
 */
public class ObservableConnection implements Observable.OnSubscribe<ObservableConnection.CONNECTION_STATUS>,
    GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks {
  public enum CONNECTION_STATUS {
    CONNECTED,
    SUSPENDED
  }

  private ObservableConnection context = this;
  private GoogleApiClient googleApiClient;
  private Subscriber<? super CONNECTION_STATUS> observer;

  public ObservableConnection(GoogleApiClient googleApiClient) {
    this.googleApiClient = googleApiClient;
  }

  @Override
  public void call(Subscriber<? super CONNECTION_STATUS> subscriber) {
    observer = subscriber;
    googleApiClient.registerConnectionCallbacks(this);
    googleApiClient.registerConnectionFailedListener(this);
    googleApiClient.connect();

    UnsubscribeAction unsubscribeAction = new UnsubscribeAction();
    observer.add(Subscriptions.create(unsubscribeAction));
  }

  @Override
  public void onConnected(@Nullable Bundle bundle) {
    observer.onNext(CONNECTION_STATUS.CONNECTED);
  }

  @Override
  public void onConnectionSuspended(int i) {
    observer.onNext(CONNECTION_STATUS.SUSPENDED);
  }

  @Override
  public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    observer.onError(new ThrowableConnectionResult(connectionResult.toString() , connectionResult));
  }

  class UnsubscribeAction implements Action0 {

    @Override
    public void call() {
      if (googleApiClient.isConnected() || googleApiClient.isConnecting()) {
        googleApiClient.disconnect();
      }
      googleApiClient.unregisterConnectionFailedListener(context);
      googleApiClient.unregisterConnectionCallbacks(context);
    }
  }

  class ThrowableConnectionResult extends Throwable {
    ConnectionResult mResult;

    ConnectionResult getResult() {
      return mResult;
    }

    ThrowableConnectionResult(String description, ConnectionResult result) {
      super(description);
      this.mResult = result;
    }
  }
}
