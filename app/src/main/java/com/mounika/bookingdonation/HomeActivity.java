package com.mounika.bookingdonation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.mounika.bookingdonation.model.Charity;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    Button   btnDonate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiivty_home);
        List<Charity> itemsList = new ArrayList<>();
        itemsList.add(new Charity(1,R.drawable.ic_baseline_multiline_chart_24, "MN foundation"));
        itemsList.add(new Charity(2,R.drawable.ic_baseline_multiline_chart_24, "MN foundation2"));
        itemsList.add(new Charity(3,R.drawable.ic_baseline_multiline_chart_24, "MN foundation3"));
        itemsList.add(new Charity(4,R.drawable.ic_baseline_multiline_chart_24, "MN foundation4"));

        GridView gridView = findViewById(R.id.grid_view);
        CustomAdapter customAdapter = new CustomAdapter(this, R.layout.custom_view, itemsList);
        gridView.setAdapter(customAdapter);
    }
}
