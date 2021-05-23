/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;


import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;


public class EarthquakeActivity extends AppCompatActivity {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";
    ListView earthquakeListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);
        earthquakeListView = findViewById(R.id.list);
        EarthquakeAsyncTask  task = new EarthquakeAsyncTask();
        task.execute(USGS_REQUEST_URL);
    }

    private class EarthquakeAsyncTask  extends AsyncTask<String, Void, List<Quake>> {

        @Override
        protected List<Quake>  doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }
            return Utils.fetchEarthquakeData(urls[0]);
        }

        @Override
        protected void onPostExecute(List<Quake> earthquakes) {

            updateUi( earthquakes);

        }
    }


    private boolean isNetworkConnected(){
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
    }
    private void  updateUi(List<Quake> earthquakes) {

        final QuakeAdapter adapter = new QuakeAdapter(this, earthquakes);

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
       // if (earthquakes != null && !earthquakes.isEmpty()) {
            earthquakeListView.setAdapter(adapter);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    Quake currentQuake = adapter.getItem(position);
                    if (isNetworkConnected()) {
                        String earthquakeURL = currentQuake.getUrl();
                        Intent USGS_WebPageIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquakeURL));
                        // Verify that the intent will resolve to an activity
                        if (USGS_WebPageIntent.resolveActivity(getPackageManager()) != null) {
                            startActivity(USGS_WebPageIntent);
                        }

                    } else {
                        Toast.makeText(EarthquakeActivity.this, "Network Error", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
   



