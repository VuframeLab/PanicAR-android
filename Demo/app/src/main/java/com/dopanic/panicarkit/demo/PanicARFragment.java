package com.dopanic.panicarkit.demo;

import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.dopanic.panicarkit.lib.PARFragment;
import com.dopanic.panicarkit.lib.PARController;
import com.dopanic.panicarkit.lib.PARPoiLabel;
import com.dopanic.panicarkit.lib.PARPoiLabelAdvanced;
import com.dopanic.panicsensorkit.PSKDeviceAttitude;
import com.dopanic.panicsensorkit.enums.PSKDeviceOrientation;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by doPanic on 21.02.14.
 * Fragment providing AR functionality
 * configure and add content here
 */
public class PanicARFragment extends PARFragment {

    private String TAG = "PARController";
    private static ArrayList<PARPoiLabel> labelRepo = new ArrayList<PARPoiLabel>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

        // add content using helper methods defined below
        PARPoiLabel label = createPoi("Regensburger Dom", "(NORD-OST) Regensburg", 49.01947485100683f, 12.097727581858635f);
        label.setBackgroundImageResource(R.drawable.custom_poi_label);
        label.setSize(384, 192);
        //label.setObserved(true);
        PARController.getInstance().addPoi(label);

        label = createPoi("Berlin",  "Berlin", 52.523402f, 13.41141f, 35.0f);
        label.setIconImageViewResource(R.drawable.poi_icon);
        label.setOffset(new Point(0, 100));
        PARController.getInstance().addPoi(label);

        PARController.getInstance().addPoi(createPoi("Neupfarrkirche", "Am Neupfarrplatz", 49.018269042391786f, 12.09614172577858f));
        PARController.getInstance().addPoi(createPoi("Kaufhof", "(OST) Am Neupfarrplatz", 49.01848012697194f, 12.097044289112091f));
        PARController.getInstance().addPoi(createPoi("Swarovski", "(NW) Gesandtenstr.", 49.018366669121356f, 12.09497831761837f));
        PARController.getInstance().addPoi(createPoi("Alex Cafe", "(NNO) Am Neupfarrplatz", 49.018718475567624f, 12.096399888396263f));
        PARController.getInstance().addPoi(createPoi("Hemingway", "(S) Augustinerstr.", 49.01795681147559f, 12.094875052571297f));

        initLabelRepo();

        // set fake location
        //PSKDeviceAttitude.sharedDeviceAttitude().setFakeLocation(49.018269042391786f, 12.09614172577858f, 360.0f);
        //PSKDeviceAttitude.sharedDeviceAttitude().setUseFakeLocation(false);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // FIRST: setup default resource IDs
        // IMPORTANT: call before super.onCreate()
        this.viewLayoutId = R.layout.panicar_view;

        View view = super.onCreateView(inflater, container, savedInstanceState);

        getRadarView().setRadarRange(500);

        return view;
    }

    @Override
    public void onDeviceOrientationChanged(PSKDeviceOrientation newOrientation) {
        super.onDeviceOrientationChanged(newOrientation);
        Toast.makeText(getActivity(), "onDeviceOrientationChanged: " + PSKDeviceAttitude.rotationToString(newOrientation), Toast.LENGTH_LONG).show();
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.wtf(TAG, "Old size of POIs " + PARController.getInstance().numberOfObjects());
        switch (item.getItemId()){
            case R.id.action_add_random_poi:
                PARController.getInstance().addPoi(labelRepo.get(new Random().nextInt(labelRepo.size())+0));
                Log.wtf(TAG, "New size of POIs " + PARController.getInstance().numberOfObjects());
                return super.onOptionsItemSelected(item);
            case R.id.action_delete_last_poi:
                PARController.getInstance().removeObject(PARController.getInstance().numberOfObjects()-1);
                Log.wtf(TAG, "Remove object at index: " + (PARController.getInstance().numberOfObjects()-1));
                return super.onOptionsItemSelected(item);
            case R.id.action_delete_all_pois:
                PARController.getInstance().clearObjects();
                Log.wtf(TAG, "New size of POIs " + PARController.getInstance().numberOfObjects());
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Create a poi with title, description and position
     *
     * @param title       Title of poi
     * @param description Description of poi (if you want none, set this to "")
     * @param lat         Latitude of poi
     * @param lon         Longitude of poi
     * @return PARPoiLabel which is a subclass of PARPoi (extended for title, description and so on)
     */
    public PARPoiLabel createPoi(String title, String description, float lat, float lon) {
        Location poiLocation = new Location(title);
        poiLocation.setLatitude(lat);
        poiLocation.setLongitude(lon);

        final PARPoiLabel parPoiLabel = new PARPoiLabel(poiLocation, title, description, R.layout.panicar_poilabel, R.drawable.radar_dot);
        PARController.getInstance().addPoi(parPoiLabel);

        parPoiLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), parPoiLabel.getTitle() + " - " + parPoiLabel.getDescription(), Toast.LENGTH_LONG).show();
            }
        });

        return parPoiLabel;
    }

    /**
     * Create a poi with title, description and position
     *
     * @param title       Title of poi
     * @param description Description of poi (if you want none, set this to "")
     * @param lat         Latitude of poi
     * @param lon         Longitude of poi
     * @return PARPoiLabelAdvanced which is a subclass of PARPoiLabel (extended for altitude support)
     */
    public PARPoiLabelAdvanced createPoi(String title, String description, float lat, float lon, float alt) {
        Location poiLocation = new Location(title);
        poiLocation.setLatitude(lat);
        poiLocation.setLongitude(lon);
        poiLocation.setAltitude(alt);

        final PARPoiLabelAdvanced parPoiLabel = new PARPoiLabelAdvanced(poiLocation, title, description, R.layout.panicar_poilabel, R.drawable.radar_dot);
        parPoiLabel.setIsAltitudeEnabled(true);

        parPoiLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), parPoiLabel.getTitle() + " - " + parPoiLabel.getDescription(), Toast.LENGTH_LONG).show();
            }
        });

        return parPoiLabel;
    }
    

    private PARPoiLabel createRepoPoi(String title, String description, float latitude, float longitude, float accuracy) {
        return createPoi(title, description, latitude, longitude, 0);
    }

    // holds a set of example pois, which are gonna created randomly
    private void initLabelRepo(){
        labelRepo.add(createRepoPoi("Regensburg", "doPanic Headquarter, Germany", 49.01824f, 12.0953f, 5));
        labelRepo.add(createRepoPoi("Munic", "Germany", 48.1351253f, 11.581980599999952f, 5));
        labelRepo.add(createRepoPoi("Nuernberg", "Germany", 49.45203f, 11.07675f, 5));
        labelRepo.add(createRepoPoi("Frankfurt", "Germany", 50.1109221f, 8.682126700000026f, 5));
        labelRepo.add(createRepoPoi("Hamburg", "Germany", 53.551085f, 9.993682f, 5));
        labelRepo.add(createRepoPoi("Berlin", "Capital City, Germany", 52.520007f, 13.404954f, 5));
        labelRepo.add(createRepoPoi("Wien", "Capital City, Austria", 48.208174f, 16.373819f, 5));
        labelRepo.add(createRepoPoi("Graz", "Austria", 47.070714f, 15.439504f, 5));
        labelRepo.add(createRepoPoi("Bern", "Switzerland", 46.947922f, 7.444608f, 5));
        labelRepo.add(createRepoPoi("Zurich", "Switzerland", 47.36865f, 8.539183f, 5));
        labelRepo.add(createRepoPoi("Paris", "Capital City, France", 48.856614f, 2.352222f, 5));
        labelRepo.add(createRepoPoi("Marseille", "France", 43.296482f, 5.36978f, 5));
        labelRepo.add(createRepoPoi("Rome", "Capital City, Italy", 41.872389f, 12.48018f, 5));
        labelRepo.add(createRepoPoi("Milan", "Italy", 45.465422f, 9.185924f, 5));
        labelRepo.add(createRepoPoi("Madrid", "Capital City, Spain", 40.416775f, -3.70379f, 5));
        labelRepo.add(createRepoPoi("Barcelona", "Spain", 41.385064f, 2.173403f, 5));
        labelRepo.add(createRepoPoi("Lisbon", "Capital City, Portugal", 38.722252f, -9.139337f, 5));
        labelRepo.add(createRepoPoi("London", "Capital City, United Kingdom", 51.507351f, -0.127758f, 5));
        labelRepo.add(createRepoPoi("Manchester", "United Kingdom", 53.479324f, -2.248485f, 5));
        labelRepo.add(createRepoPoi("Dublin", "Capital City, Ireland", 53.349805f, -6.26031f, 5));
        labelRepo.add(createRepoPoi("Copenhagen", "Capital City, Denmark", 55.676097f, 12.568337f, 5));
        labelRepo.add(createRepoPoi("Oslo", "Capital City, Norway", 59.913869f, 10.752245f, 5));
        labelRepo.add(createRepoPoi("Stockholm", "Capital City, Sweden", 59.329323f, 18.068581f, 5));
        labelRepo.add(createRepoPoi("New York", "United States", 40.712784f, -74.005941f, 5));
        labelRepo.add(createRepoPoi("Washington, D.C.", "Capital City, United States", 38.907192f, -77.036871f, 5));
        labelRepo.add(createRepoPoi("Miami", "United States", 25.789097f, -80.204044f, 5));
        labelRepo.add(createRepoPoi("Las Vegas", "United States", 36.169941f, -115.13983f, 5));
        labelRepo.add(createRepoPoi("Silicon Valley", "United States", 37.362517f, -122.03476f, 5));
        labelRepo.add(createRepoPoi("Ottawa", "Capital City, Canada", 45.42153f, -75.697193f, 5));
        labelRepo.add(createRepoPoi("Vancouver", "Canada", 49.261226f, -123.1139268f, 5));
    }
}
