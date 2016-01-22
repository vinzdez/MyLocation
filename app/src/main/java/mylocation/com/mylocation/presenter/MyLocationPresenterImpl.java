package mylocation.com.mylocation.presenter;

import android.content.Context;
import javax.inject.Inject;
import mylocation.com.mylocation.view.MyLocationView;

/**
 * Created by vangcaway on 1/16/2016.
 */
public class MyLocationPresenterImpl implements MyLocationPresenter {

  private MyLocationView myLocationView;

  @Inject
  public MyLocationPresenterImpl(MyLocationView myLocationView) {
    this.myLocationView = myLocationView;
  }

  @Override
  public void onResume(Context context) {
    myLocationView.showSnackbar();
  }
}
