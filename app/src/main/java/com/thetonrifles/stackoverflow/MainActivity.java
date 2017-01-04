package com.thetonrifles.stackoverflow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.thetonrifles.stackoverflow.adapter.EventItem;
import com.thetonrifles.stackoverflow.adapter.EventsAdapter;
import com.thetonrifles.stackoverflow.adapter.HeaderItem;
import com.thetonrifles.stackoverflow.adapter.ListItem;
import com.thetonrifles.stackoverflow.model.Event;
import com.thetonrifles.stackoverflow.util.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

public class MainActivity extends AppCompatActivity {

	@NonNull
	private List<ListItem> items = new ArrayList<>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Map<Date, List<Event>> events = toMap(loadEvents());

		for (Date date : events.keySet()) {
			HeaderItem header = new HeaderItem(date);
			items.add(header);
			for (Event event : events.get(date)) {
				EventItem item = new EventItem(event);
				items.add(item);
			}
		}

		RecyclerView recyclerView = (RecyclerView) findViewById(R.id.lst_items);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new EventsAdapter(items));
	}

	@NonNull
	private List<Event> loadEvents() {
		List<Event> events = new ArrayList<>();
		for (int i = 1; i < 50; i++) {
			events.add(new Event("event " + i, buildRandomDateInCurrentMonth()));
		}
		return events;
	}

	private Date buildRandomDateInCurrentMonth() {
		Random random = new Random();
		return DateUtils.buildDate(random.nextInt(31) + 1);
	}

	@NonNull
	private Map<Date, List<Event>> toMap(@NonNull List<Event> events) {
		Map<Date, List<Event>> map = new TreeMap<>();
		for (Event event : events) {
			List<Event> value = map.get(event.getDate());
			if (value == null) {
				value = new ArrayList<>();
				map.put(event.getDate(), value);
			}
			value.add(event);
		}
		return map;
	}

}
