package cnin0770.memo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

/**
 * Created by cnin0770 on 08/10/2016.
 */
public class AddGridItem extends Activity{
    public final String TAG = "AddGridItem";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
//    GoogleApiClient mGoogleApiClient;
//    TextView locationTextView;
//    private GoogleMap mMap;
//    private Location mLastLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_grid_item);

        retriveMsg();

//        locationTextView = (TextView)findViewById(R.id.locationOutput);

//        Location mCurrentLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
//        double lat = mCurrentLocation.getLatitude();
//        double lon = mCurrentLocation.getLongitude();
//        String msg = "Current Location: " +
//                Double.toString(lat) + "," +
//                Double.toString(lon);
//        locationTextView.setText(msg);
    }

    public void onTakePhotoClick(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getFileUri(photoFileName));
        startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
    }

    public void onCancel(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddGridItem.this);
        builder.setTitle(R.string.quit_edit)
                .setMessage(R.string.dialog_ed_delete_msg)
                .setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_CANCELED);
                        finish();
                    }
                })
                .setPositiveButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        builder.create().show();
    }

    public Uri getFileUri(String fileName) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),TAG);

        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.d(TAG, "fail to create dir");
        }

        return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setVisibility(View.GONE);

        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Uri takenPhotoUri = getFileUri(photoFileName);
                Bitmap takenIamge = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                imageView.setImageBitmap(takenIamge);
                imageView.setVisibility(View.VISIBLE);
            } else {
                Toast.makeText(this, "Picture not taken.", Toast.LENGTH_SHORT).show();
            }
        } else {
            setResult(RESULT_CANCELED);
            finish();
        }
    }

    public void retriveMsg() {
        Intent intent = new Intent(AddGridItem.this, MapsActivity.class);
        startActivity(intent);
    }
}
