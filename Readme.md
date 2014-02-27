# PanicAR Framework

![PanicAR Image](https://raw.github.com/doPanic/PanicAR/beta/Screenshots/product.png)

by doPanic GmbH - for sales and support contact info@dopanic.com

## Why PanicAR?

* It’s fast, simple and affordable
* No monthly or annual fees
* It’s completely customizable
* It’s small (only ~200 kb)
* It’s ridiculously easy to integrate

## Why PanicAR – really?

* does the same things all the other frameworks do: even some more (i.e. "altitude")
* maintained for you by us: new devices and new Android releases will be supported
* pay on a per-app basis: a white-label license is really cheap (see here: www.dopanic.com/ar)

## Get started now

Download necessary files at Bitbucket. This should contain

- PanicAR.jar 
- PanicSensorKit.jar
- activity_camera.xml
- 3x poi.png

Prepare your project

- Create a folder called `libs` in your project’s root folder
- Copy both jar files PanicAR.jar and PanicSensorKit.jar to the libs folder
- Now right click on PanicAr.jar file and then select `Add As Library`
- Now right click on PanicSensorKit.jar file and then select `Add As Library`
- Copy activity_camera.xml to project’s `res/layout` folder
- Copy the images to project’s `res/drawable` folder

Add relevant source code

- Extend your Application from `PARApplication` and add following call in onCreate()

       public class App extends PARApplication {

           @Override
           public void onCreate() {
               super.onCreate();
               PARController.getInstance().init(PSKApplication.getAppContext(), ""); 
           }
       }
- Make sure the AndroidManifest knows about the ApplicationClass by adding following tag in your `<application>` tag:
        
        android:name=„.YourAppClassName"
        
- Add permission to be able to have access to the camera of your testing device. Set following tag in your manifest’s root tag

        <uses-permission android:name="android.permission.CAMERA"></uses-permission>
        

- Add following code to your `style.xml` in `res/values` folder

        <style name="poiTitleStyle" parent="@android:style/TextAppearance.Medium">
            <item name="android:layout_width">fill_parent</item>
            <item name="android:layout_height">wrap_content</item>
            <item name="android:paddingTop">30dp</item>
            <item name="android:textSize">20sp</item>
            <item name="android:textColor">#00FF00</item>
            <item name="android:textStyle">bold</item>
            <item name="android:typeface">monospace</item>
        </style>
        <style name="poiDescriptionStyle">
            <item name="android:textSize">8dp</item>
            <item name="android:textStyle">italic</item>
        </style>

- Create a new Activity and extend it from `PARARActivity`

        public class YourActivity extends PARARActivity

- Tell your AndroidManifest about the Activity. Add a tag in `<application>` tag
  
        <activity
         android:name=".YourActivityClassName"
        </activity>
        
- Add following code to your Activity

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            PARPoiLabel label = createPoi("Regensburger Dom", "Reg Desc", 49.019512f, 12.097709f);
                        label.backgroundImageResource = R.drawable.poi;
                        label = createPoi("Berlin",  "Berlin Desc", 52.523402f, 13.41141f);
                        label.backgroundImageResource = R.drawable.poi;
                        label.iconImageViewResource = R.drawable.ic_launcher;
                        label = createPoi("Rom",  "Rome Desc", 41.890156f, 12.492304f);
                        label.backgroundImageResource = R.drawable.poi;

            setContentView(R.layout.activity_camera);
            set_cameraView((PARCameraView) findViewById(R.id.cameraView));
            set_arContainer((RelativeLayout) findViewById(R.id.ARScreen));
            set_headingText((TextView) findViewById(R.id.headingText));
            set_rotationText((TextView) findViewById(R.id.rotationText));
            set_PoiDescriptionStyle(R.style.poiDescriptionStyle);
            set_PoiTitleStyle(R.style.poiTitleStyle);
        }

 That’s it! Now just call the Activity somewhere in your project and the PanicAR will start.




[![Analytics](https://ga-beacon.appspot.com/UA-47538502-1/panicar/home)](https://github.com/dopanic/panicar)
