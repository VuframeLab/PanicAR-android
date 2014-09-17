package com.dopanic.panicarkit.demo;

import android.graphics.Point;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.dopanic.panicarkit.lib.PARFragment;
import com.dopanic.panicarkit.lib.PARController;
import com.dopanic.panicarkit.lib.PARPoi;
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

    //==============================================================================================
    // Variable declaration
    //==============================================================================================
    private static ArrayList<PARPoiLabel> labelRepo = new ArrayList<PARPoiLabel>();

    //==============================================================================================
    // Lifecycle
    //==============================================================================================
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        // add content using helper methods defined below
        // example to add costume drawable
        PARPoiLabel label = createPoi("Regensburg", "doPanic Headquarter, Germany", 49.01824, 12.0953);
        label.setBackgroundImageResource(R.drawable.custom_poi_label);
        label.setSize(384, 192);
        PARController.getInstance().addPoi(label);

        label = createPoi("Berlin", "Germany", 52.523402, 13.41141, 35.0); // altitude
        label.setIconImageViewResource(R.drawable.poi_icon);
        label.setOffset(new Point(0, 100));
        PARController.getInstance().addPoi(label);
        PARController.getInstance().addPoi(createPoi("Paris", "France", 48.856614, 2.352222));
        PARController.getInstance().addPoi(createPoi("London", "United Kingdom", 51.507351, -0.127758));

        initLabelRepo();

        // set fake location
        //PSKDeviceAttitude.sharedDeviceAttitude().setFakeLocation(49.018269042391786, 12.09614172577858, 360.0);
        //PSKDeviceAttitude.sharedDeviceAttitude().setUseFakeLocation(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // FIRST: setup default resource IDs
        // IMPORTANT: call before super.onCreate()
        this.viewLayoutId = R.layout.panicar_view;
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getRadarView().setRadarRange(500);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add_random_poi:
                int random = (new Random().nextInt(labelRepo.size()-1)+0);
                PARController.getInstance().addPoi(labelRepo.get(random));
                Toast.makeText(this.getActivity(),"Added: " + labelRepo.get(random).getTitle(), Toast.LENGTH_SHORT).show();
                return super.onOptionsItemSelected(item);
            case R.id.action_add_cardinal_pois:
                createCDPOIs();
                return super.onOptionsItemSelected(item);
            case R.id.action_delete_last_poi:
                if (PARController.getInstance().numberOfObjects() > 0){
                    int lastObject = PARController.getInstance().numberOfObjects()-1;
                    Toast.makeText(this.getActivity(),"Removing: " + ((PARPoiLabel)PARController.getInstance().getObject(lastObject)).getTitle(), Toast.LENGTH_SHORT).show();
                    PARController.getInstance().removeObject(lastObject);
                }
                return super.onOptionsItemSelected(item);
            case R.id.action_delete_all_pois:
                PARController.getInstance().clearObjects();
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    //==============================================================================================
    // Callback
    //==============================================================================================
    @Override
    public void onDeviceOrientationChanged(PSKDeviceOrientation newOrientation) {
        super.onDeviceOrientationChanged(newOrientation);
        Toast.makeText(getActivity(), "onDeviceOrientationChanged: " + PSKDeviceAttitude.rotationToString(newOrientation), Toast.LENGTH_LONG).show();
    }

    //==============================================================================================
    // Create Label
    //==============================================================================================
    /**
     * Create a poi with title, description and position
     *
     * @param title       Title of poi
     * @param description Description of poi (if you want none, set this to "")
     * @param lat         Latitude of poi
     * @param lon         Longitude of poi
     * @return PARPoiLabel which is a subclass of PARPoi (extended for title, description and so on)
     */
    public PARPoiLabel createPoi(String title, String description, double lat, double lon) {
        Location poiLocation = new Location(title);
        poiLocation.setLatitude(lat);
        poiLocation.setLongitude(lon);

        final PARPoiLabel parPoiLabel = new PARPoiLabel(poiLocation, title, description, R.layout.panicar_poilabel, R.drawable.radar_dot);

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
    public PARPoiLabelAdvanced createPoi(String title, String description, double lat, double lon, double alt) {
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

    //==============================================================================================
    // Helper methods
    //==============================================================================================
    private PARPoiLabel createRepoPoi(
            String title,
            String description,
            double latitude,
            double longitude) {
        return createPoi(title, description, latitude, longitude, 0);
    }

    // holds a set of example pois, which are gonna created randomly
    private void initLabelRepo(){
        labelRepo.add(createRepoPoi("Regensburg", "doPanic Headquarter, Germany", 49.01824, 12.0953));
        labelRepo.add(createRepoPoi("Munic", "Germany", 48.1351253, 11.581980599999952));
        labelRepo.add(createRepoPoi("Nuernberg", "Germany", 49.45203, 11.07675));
        labelRepo.add(createRepoPoi("Frankfurt", "Germany", 50.1109221, 8.682126700000026));
        labelRepo.add(createRepoPoi("Hamburg", "Germany", 53.551085, 9.993682));
        labelRepo.add(createRepoPoi("Berlin", "Capital City, Germany", 52.520007, 13.404954));
        labelRepo.add(createRepoPoi("Wien", "Capital City, Austria", 48.208174, 16.373819));
        labelRepo.add(createRepoPoi("Graz", "Austria", 47.070714, 15.439504));
        labelRepo.add(createRepoPoi("Bern", "Switzerland", 46.947922, 7.444608));
        labelRepo.add(createRepoPoi("Zurich", "Switzerland", 47.36865, 8.539183));
        labelRepo.add(createRepoPoi("Paris", "Capital City, France", 48.856614, 2.352222));
        labelRepo.add(createRepoPoi("Marseille", "France", 43.296482, 5.36978));
        labelRepo.add(createRepoPoi("Rome", "Capital City, Italy", 41.872389, 12.48018));
        labelRepo.add(createRepoPoi("Milan", "Italy", 45.465422, 9.185924));
        labelRepo.add(createRepoPoi("Madrid", "Capital City, Spain", 40.416775, -3.70379));
        labelRepo.add(createRepoPoi("Barcelona", "Spain", 41.385064, 2.173403));
        labelRepo.add(createRepoPoi("Lisbon", "Capital City, Portugal", 38.722252, -9.139337));
        labelRepo.add(createRepoPoi("London", "Capital City, United Kingdom", 51.507351, -0.127758));
        labelRepo.add(createRepoPoi("Manchester", "United Kingdom", 53.479324, -2.248485));
        labelRepo.add(createRepoPoi("Dublin", "Capital City, Ireland", 53.349805, -6.26031));
        labelRepo.add(createRepoPoi("Copenhagen", "Capital City, Denmark", 55.676097, 12.568337));
        labelRepo.add(createRepoPoi("Oslo", "Capital City, Norway", 59.913869, 10.752245));
        labelRepo.add(createRepoPoi("Stockholm", "Capital City, Sweden", 59.329323, 18.068581));
        labelRepo.add(createRepoPoi("New York", "United States", 40.712784, -74.005941));
        labelRepo.add(createRepoPoi("Washington, D.C.", "Capital City, United States", 38.907192, -77.036871));
        labelRepo.add(createRepoPoi("Miami", "United States", 25.789097, -80.204044));
        labelRepo.add(createRepoPoi("Las Vegas", "United States", 36.169941, -115.13983));
        labelRepo.add(createRepoPoi("Silicon Valley", "United States", 37.362517, -122.03476));
        labelRepo.add(createRepoPoi("Ottawa", "Capital City, Canada", 45.42153, -75.697193));
        labelRepo.add(createRepoPoi("Vancouver", "Canada", 49.261226, -123.1139268));
    }

    /**
     * adds 4 points in cardinal direction at current location
     */
    private void createCDPOIs(){

        double degreeCorrection = 0.1;    // approx 7700 meter
        //double degreeCorrection = 0.01; // approx 1000 meter
        //double degreeCorrection = 0.001; // approx 121 meter
        //double degreeCorrection = 0.0001; // approx 12 meter
        Location currentLocation = PSKDeviceAttitude.sharedDeviceAttitude().getLocation();

        PARController.getInstance().addPoi(createPoi("North", "", currentLocation.getLatitude()+degreeCorrection, currentLocation.getLongitude()));
        PARController.getInstance().addPoi(createPoi("South", "", currentLocation.getLatitude()-degreeCorrection, currentLocation.getLongitude()));
        PARController.getInstance().addPoi(createPoi("West", "",  currentLocation.getLatitude(),                  currentLocation.getLongitude()-degreeCorrection));
        PARController.getInstance().addPoi(createPoi("East", "",  currentLocation.getLatitude(),                  currentLocation.getLongitude()+degreeCorrection));
    }
}
