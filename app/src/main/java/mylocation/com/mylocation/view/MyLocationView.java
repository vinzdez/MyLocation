package mylocation.com.mylocation.view;

/**
 * Created by vangcaway on 1/16/2016.
 */
public interface MyLocationView {

  void showLocation(String location);
  void showWarningSnackbar(String cause);
  void showReverseGeolocation(String address);

}
