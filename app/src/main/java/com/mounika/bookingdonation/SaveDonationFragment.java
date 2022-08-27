package com.mounika.bookingdonation;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;




public class SaveDonationFragment extends AppCompatActivity implements View.OnClickListener {
   private TextInputEditText edFirstName,edAmount,edLocation,edPhone,edLastName,edEmail;
   private LinearLayout logout,home,donate;
   TextView title;
    private static final String TAG = "SaveOrdersNew";
    AppLocationService appLocationService;


    //  private LocationManager mLocationManager;

    private LocationManager locationManager;
    //FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    //DatabaseReference databaseReference;

  //  private Button save;
   // private ImageView imLocation;
    private DonationObject donationObject;
  /*  @BindView(R.id.save) Button save;
    @BindView(R.id.imLocation) ImageView imLocation;
    @BindView(R.id.edName) EditText edName;
    @BindView(R.id.edAmount) EditText edAmount;
    @BindView(R.id.edPhone) EditText edPhone;
    @BindView(R.id.edlocation) EditText edLocation;
    @BindView(R.id.log_out)*/
    ImageView imLocation;
    LinearLayout save;

    FirebaseDatabase firebaseDatabase;

    // creating a variable for our Database
    // Reference for Firebase.
    DatabaseReference databaseReference;
    String charityName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.save_donation_activity);
       // ButterKnife.bind(this);
        initUI();
        if(getIntent().getExtras() != null) {
            charityName = getIntent().getExtras().getString("ITEMID");
            title.setText(charityName);
        }

        //initUI();
        donationObject = new DonationObject();
        firebaseDatabase = FirebaseDatabase.getInstance();



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
                if (validate()) {
                    databaseReference = firebaseDatabase.getReference(edFirstName.getText().toString()+""+edLastName.getText().toString());


                    String donaterName = edFirstName.getText().toString()+" "+edLastName.getText().toString();
                    String donationAmount = edAmount.getText().toString();
                    String location = edLocation.getText().toString();
                    String edPhoneNumber = edPhone.getText().toString();
                    String edMail = edEmail.getText().toString();
                    addDatatoFirebase(donaterName, donationAmount, location, edPhoneNumber,edMail);
                    // Toast.makeText(getApplicationContext(),"Work in progress",Toast.LENGTH_SHORT).show();

                }

            }

            });




    }
    private boolean validate() {
        if(edFirstName.getText().toString().trim() == null || edFirstName.getText().toString().trim().length() == 0){
            Toast.makeText(this,"please enter Name",Toast.LENGTH_LONG).show();
            return false;
        }else if(edAmount.getText().toString().trim() == null || edAmount.getText().toString().trim().length() == 0){
            Toast.makeText(this,"please enter Amount",Toast.LENGTH_LONG).show();
            return false;
        }else if(edPhone.getText().toString().trim() == null || edPhone.getText().toString().trim().length() == 0){
            Toast.makeText(this,"please enter phone number",Toast.LENGTH_LONG).show();
            return false;
        }else if(edLocation.getText().toString().trim() == null || edLocation.getText().toString().trim().length() == 0){
            Toast.makeText(this,"please enter Location",Toast.LENGTH_LONG).show();
            return false;
        }else if(edEmail.getText().toString().trim() == null || edEmail.getText().toString().trim().length() == 0){
            Toast.makeText(this,"please enter Mail",Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Do you want to  exit ? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SaveDonationFragment.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }
        public void showDialog(Activity activity){
            final Dialog dialog = new Dialog(activity);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.custom_alert);

           // TextView text = (TextView) dialog.findViewById(R.id.text_dialog);
           // text.setText(msg);

            Button dialogButton = (Button) dialog.findViewById(R.id.btn_dialog);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent itemIntent = new Intent(SaveDonationFragment.this, HomeActivity.class);
                    startActivity(itemIntent);
                    finish();
                }
            });

            dialog.show();

        }

    private void initUI() {
        edFirstName = (TextInputEditText)  findViewById(R.id.edFisrtName);
        edLastName = (TextInputEditText)  findViewById(R.id.edLastName);
        edEmail = (TextInputEditText)  findViewById(R.id.edEmail);
        edAmount = (TextInputEditText)  findViewById(R.id.edAmount);
        edLocation = (TextInputEditText)  findViewById(R.id.edlocation);
        edPhone = (TextInputEditText)  findViewById(R.id.edPhone);
        imLocation = (ImageView)  findViewById(R.id.imLocation);
        save = (LinearLayout)  findViewById(R.id.save);
        logout = (LinearLayout)  findViewById(R.id.log_out);
        home = (LinearLayout)  findViewById(R.id.home);
        donate = (LinearLayout)  findViewById(R.id.donate);
        title = (TextView)  findViewById(R.id.title);
        home.setOnClickListener(this);
        donate.setOnClickListener(this);
        logout.setOnClickListener(this);

        //logout.setVisibility(View.GONE);


    }
    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.home:
                Intent intent = new Intent(SaveDonationFragment.this, HomeActivity.class);
                //intent.putExtra("ITEMID",itemsList.get(0).getText());
                startActivity(intent);
                finish();
                break;
            case R.id.donate:


                break;
            case R.id.log_out:
                Intent itemIntent = new Intent(SaveDonationFragment.this, MainActivity.class);
                startActivity(itemIntent);
                break;
        }

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

    private void addDatatoFirebase(String donaterName, String amount, String address, String phone, String edMail) {
        // below 3 lines of code is used to set
        // data in our object class.
        donationObject.setCharityName(charityName);
        donationObject.setEmail(edMail);
        donationObject.setAmount(amount);
        donationObject.setDonarPhoneNummber("+44"+phone);
        //donationObject.setDonorName(requestName);
        donationObject.setLocation(address);
        donationObject.setTime(""+Calendar.getInstance().getTime());


        // we are use add value event listener method
        // which is called with database reference.
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                // inside the method of on Data change we are setting
                // our object class to our database reference.
                // data base reference will sends data to firebase.
                databaseReference.setValue(donationObject);
               // Log.d("AddDta:::","data"+donationObject.getDonorName());

             //   databaseReference = null;
                databaseReference.removeEventListener(this);

               // Toast.makeText(getApplicationContext(),"Amount Transford",Toast.LENGTH_SHORT).show();
                //donationObject = new DonationObject();
                showDialog(SaveDonationFragment.this);



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
