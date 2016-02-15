package com.thetonrifles.stackoverflow;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_items);

        List<ListItem> items = new ArrayList<>();
        items.add(new ListItem("Francesco", getUrl()));
        items.add(new ListItem("Daniele", null));
        items.add(new ListItem("Kevin", null));
        items.add(new ListItem("Radja", null));
        items.add(new ListItem("Miralem", null));
        items.add(new ListItem("Kostas", null));
        items.add(new ListItem("Alessandro", null));
        items.add(new ListItem("Diego", null));
        items.add(new ListItem("Stephan", null));
        items.add(new ListItem("Mohamed", null));
        items.add(new ListItem("William", null));
        items.add(new ListItem("Douglas", null));
        items.add(new ListItem("Seydou", null));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Adapter adapter = new Adapter(this, items);
        recyclerView.setAdapter(adapter);
    }

    private String getUrl() {
        return "http://gazzettaworld.gazzetta.it/wp-content/uploads/2015/08/Totti-Roma.jpg";
        //return "http://media.caranddriver.com/images/media/51/2016-10best-cars-lead-ph‌​oto-664005-s-original.jpg";
    }

}
