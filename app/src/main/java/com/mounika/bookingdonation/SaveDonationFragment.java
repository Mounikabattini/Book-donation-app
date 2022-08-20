package com.mounika.bookingdonation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class SaveDonationFragment extends AppCompatActivity {
    EditText edName,edAmount,edLocation;
    private static final String TAG = "SaveOrdersNew";
    AppLocationService appLocationService;


    //  private LocationManager mLocationManager;

    private long UPDATE_INTERVAL = 2 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private LocationManager locationManager;
    //FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    //DatabaseReference databaseReference;

    private Button save;
    private ImageView imLocation;
    private DonationObject donationObject;

    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_donation_activity);
        initUI();
        donationObject = new DonationObject();
        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference("donationObject");


        appLocationService = new AppLocationService(
                SaveDonationFragment.this);
        imLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Location location = appLocationService
                        .getLocation(LocationManager.GPS_PROVIDER);

                //you can hard-code the lat & long if you have issues with getting it
                //remove the below if-condition and use the following couple of lines
                //double latitude = 37.422005;
                //double longitude = -122.084095

                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LocationAddress locationAddress = new LocationAddress();
                    locationAddress.getAddressFromLocation(latitude, longitude,
                            getApplicationContext(), new GeocoderHandler());
                } else {
                    showSettingsAlert();
                }

            }

        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String donaterName = edName.getText().toString();
                String donationAmount = edAmount.getText().toString();
                String location = edLocation.getText().toString();
                addDatatoFirebase(donaterName,donationAmount,location);
                // Toast.makeText(getApplicationContext(),"Work in progress",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void initUI() {
        edName = (EditText)  findViewById(R.id.edName);
        edAmount = (EditText)  findViewById(R.id.edAmount);
        edLocation = (EditText)  findViewById(R.id.edlocation);
        imLocation = (ImageView)  findViewById(R.id.imLocation);
        save = (Button)  findViewById(R.id.save);


    }

    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                SaveDonationFragment.this);
        alertDialog.setTitle("SETTINGS");
        alertDialog.setMessage("Enable Location Provider! Go to settings menu?");
        alertDialog.setPositiveButton("Settings",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(
                                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        SaveDonationFragment.this.startActivity(intent);
                    }
                });
        alertDialog.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            edLocation.setText(locationAddress);
        }
    }

    private void addDatatoFirebase(String requestName, String amount, String address) {
        // below 3 lines of code is used to set
        // data in our object class.
        donationObject.setAmount(amount);
        donationObject.setDonorName(requestName);
        donationObject.setLocation(address);
        donationObject.setTime("need to update");

        Log.d("AddDta:::","data"+donationObject.getDonorName());

        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(donationObject);
                Log.d("AddDta:::","data"+donationObject.getDonorName());

                Toast.makeText(getApplicationContext(),"Amount Transfored",Toast.LENGTH_SHORT).show();
                donationObject = new DonationObject();


                // after adding this data we are showing toast message.
                //Toast.makeText(SaveOrderActivity.this, "Request submited ", Toast.LENGTH_SHORT).show();
                // Intent i = new Intent(SaveOrderActivity.this,MainItemActivity.class);
                // startActivity(i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // if the data is not added or it is cancelled then
                // we are displaying a failure toast message.
                Toast.makeText(SaveDonationFragment.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }





}
