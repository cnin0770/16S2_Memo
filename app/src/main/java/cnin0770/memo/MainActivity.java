package cnin0770.memo;

/**
 * Created by cnin0770 on 16/8/20.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Throwables;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceAuthenticationProvider;
import com.microsoft.windowsazure.mobileservices.authentication.MobileServiceUser;
import com.microsoft.windowsazure.mobileservices.http.NextServiceFilterCallback;
import com.microsoft.windowsazure.mobileservices.http.OkHttpClientFactory;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilter;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterRequest;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.MobileServiceClient;
import com.microsoft.windowsazure.mobileservices.table.MobileServiceTable;

import com.squareup.okhttp.OkHttpClient;

public class MainActivity extends Activity {

//        ListView listView;
//        ArrayList<String> items;
//        ArrayList<String> dateStr;
//        ArrayAdapter<String> itemsAdapter;
    public final int EDIT_ITEM_REQUEST_CODE = 647;
    GridView gridView;
    GridViewAdapter gridViewAdapter;
    TextView switchStatus;
    Switch mySwitch;
    MobileServiceClient mClient;
    ProgressBar mProgressBar;
    final String AZURE_URL = "https://gridviewwithfb.azurewebsites.net/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchStatus = (TextView) findViewById(R.id.switchStatus);
        mySwitch = (Switch) findViewById(R.id.mySwitch);
        gridView = (GridView) findViewById(R.id.gridView);
        gridViewAdapter = new GridViewAdapter(this, R.layout.grid_item_layout, getData());
        gridView.setAdapter(gridViewAdapter);

        try {
            mClient = new MobileServiceClient(AZURE_URL, this)
                    .withFilter(new ProgressFilter());

            authenticate();

            mClient.setAndroidHttpClientFactory(new OkHttpClientFactory() {
                @Override
                public OkHttpClient createOkHttpClient() {
                    OkHttpClient client = new OkHttpClient();
                    client.setReadTimeout(20, TimeUnit.SECONDS);
                    client.setWriteTimeout(20, TimeUnit.SECONDS);
                    return client;
                }
            });
        } catch (MalformedURLException e){
            createAndShowDialog(new Exception("Verify the URL"), "Error");
        } catch (Exception e) {
            createAndShowDialog(e, "Error");
        }

        mySwitch.setChecked(false);
        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    switchStatus.setText(R.string.listViewOn);
                    gridView.setNumColumns(1);
                    gridView.setPadding(10, 10, 10, 10);
                } else {
                    switchStatus.setText(R.string.gridViewOn);
                    gridView.setNumColumns(4);
                }
            }
        });

        if (mySwitch.isChecked()) {
            switchStatus.setText(R.string.listViewOn);
        } else {
            switchStatus.setText(R.string.gridViewOn);
        }
    }

    //need further steps
    public void onAddGridItemClick(View view) {
        Log.i("MainAct", "AddNew");

        Intent intent = new Intent(MainActivity.this, AddGridItem.class);

        startActivity(intent);

        gridViewAdapter.notifyDataSetChanged();
    }

    private void authenticate() {
        ListenableFuture<MobileServiceUser> mLogin = mClient.login(MobileServiceAuthenticationProvider.Facebook);

        Futures.addCallback(mLogin, new FutureCallback<MobileServiceUser>() {
            @Override
            public void onSuccess(MobileServiceUser result) {
                createAndShowDialog(String.format(
                        "You are now logged in - %1$2s",
                        result.getUserId()), "Success");
            }

            @Override
            public void onFailure(Throwable t) {
                createAndShowDialog((Exception) t, "Error");

            }
        });
    }

    private ArrayList<GridItem> getData() {
        final ArrayList<GridItem> gridItems = new ArrayList<GridItem>();

        TypedArray images = getResources().obtainTypedArray(R.array.image_ids);

        for (int i = 0; i < images.length(); i++) {
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), images.getResourceId(i, -1));
            gridItems.add(new GridItem(bitmap, "Item " + i));
        }

        return gridItems;
    }

    private class ProgressFilter implements ServiceFilter {
        @Override
        public ListenableFuture<ServiceFilterResponse> handleRequest(ServiceFilterRequest request,
                                                                     NextServiceFilterCallback nextServiceFilterCallback) {
            final SettableFuture<ServiceFilterResponse> resultFuture = SettableFuture.create();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.VISIBLE);
                }
            });

            ListenableFuture<ServiceFilterResponse> future = nextServiceFilterCallback.onNext(request);

            Futures.addCallback(future, new FutureCallback<ServiceFilterResponse>() {
                @Override
                public void onSuccess(ServiceFilterResponse result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mProgressBar != null) mProgressBar.setVisibility(ProgressBar.GONE);
                        }
                    });
                    resultFuture.set(result);
                }

                @Override
                public void onFailure(Throwable t) {
                    resultFuture.setException(t);
                }
            });

            return resultFuture;
        }
    }
    private void createAndShowDialog(Exception exception, String title) {
        Throwable e = exception;
        if (exception.getCause() != null) {
            e = exception.getCause();
        }
        createAndShowDialog(e.getMessage(), title);
    }

    private void createAndShowDialog(final String message, final String title) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message);
        builder.setTitle(title);
        builder.create().show();
    }
}
