package com.mounika.bookingdonation;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mounika.bookingdonation.model.Charity;

import java.util.ArrayList;
import java.util.List;


public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    Button   btnDonate;
    //@BindView(R.id.log_out)
    LinearLayout logout,home,donate;
   private List<Charity> itemsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_home);
       // ButterKnife.bind(this);
        logout = (LinearLayout)  findViewById(R.id.log_out);
        home = (LinearLayout)  findViewById(R.id.home);
        donate = (LinearLayout)  findViewById(R.id.donate);
        home.setOnClickListener(this);
        donate.setOnClickListener(this);
        logout.setOnClickListener(this);





        itemsList = new ArrayList<>();
        itemsList.add(new Charity(1,R.drawable.iconone, " Life help  foundation"));
        itemsList.add(new Charity(2,R.drawable.icontwo, "Smile charity foundation"));
        itemsList.add(new Charity(3,R.drawable.iconthree, "Rangers  foundation"));
        itemsList.add(new Charity(4,R.drawable.iconfour, "Hope charity foundation"));

        GridView gridView = findViewById(R.id.grid_view);
        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.custom_view, itemsList,HomeActivity.this);
        gridView.setAdapter(customAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("Do you want to  exit ? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                HomeActivity.super.onBackPressed();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
            }
        });
        builder.show();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.home:
                break;
            case R.id.donate:
                Intent intent = new Intent(HomeActivity.this, SaveDonationFragment.class);
                intent.putExtra("ITEMID",itemsList.get(0).getText());
                startActivity(intent);
                finish();

                break;
            case R.id.log_out:
                Intent itemIntent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(itemIntent);
                finish();
                break;
        }

    }
}
