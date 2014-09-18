# PanicAR Framework

![PanicAR Image](https://raw.github.com/doPanic/PanicAR/beta/Screenshots/product.png)

by doPanic GmbH - read more at http://panicar.dopanic.com

## Why PanicAR?

* It’s fast, simple and affordable
* No monthly or annual fees
* It’s completely customizable
* It’s small (only ~200 kb)
* It’s ridiculously easy to integrate

## Why PanicAR – really?

* does the same things all the other frameworks do: even some more (i.e. "altitude")
* maintained for you by us: new devices and new Android releases will be supported
* pay on a per-app basis: a white-label license is really cheap (see here: http://panicar.dopanic.com/)

## Get started now

### Add to your own app

- Create a folder called libs in your project’s root folder
- Copy both jar files `PanicAR.jar` and `PanicSensorKit.jar` from `Framework/libs` to the libs folder
- Make sure you add `PanicAr.jar` and  `PanicSensorKit.jar` as Library to your project
- Add all files form the `Framework/res` folders to the corresponding folders of your app
- Add permissions to your `AndroidManifest.xml`
 
		 <uses-feature
		       android:name="android.hardware.camera"
		       android:required="false" />
		 <uses-feature android:name="android.hardware.camera.autofocus" />
		
		<uses-permission android:name="android.permission.INTERNET" />
		<uses-permission android:name="android.permission.CAMERA" />
		<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
		<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
		
- Make sure your App class derives from `PARApplication`, don't forget to write your application class into your manifest file

	    <application 
		    android:name=".MyAppClass" 
		    ... 
		</application>
		
### Fragments

Your AR Fragment has to extend `PARFragment` and override the `onCreateView` method to set the `viewLayoutId`

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		this.viewLayoutId = R.layout.panicar_view;
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		return view;
	}

### Add point of interest Label
You can use the `PARPoiLabel`  class for adding labels to the camera view.

First thing you need is a `Location` e.g.

	Location poiLocation = new Location("aLocationTitle");
	poiLocation.setLatitude(49.018269042391786f);
	poiLocation.setLongitude(12.09614172577858f);

Add this location to the constructor of `PARPoiLabel` and some further details like title and description. The resources `panicar_poilabel` and `radar_dot` should be already in your res folder

    final PARPoiLabelAdvanced label = new PARPoiLabelAdvanced(poiLocation, "title", "description", R.layout.panicar_poilabel, R.drawable.radar_dot);
		
To optinally add a background use

	label.setBackgroundImageResource(R.drawable.custom_poi_label);
and use 

    label.setIconImageViewResource(R.drawable.poi_icon);
to add a image.


The label has to be added to the `PARController` instance

    PARController.getInstance().addPoi(label);

To handle touch events use something like this

	label.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            ...
        }
	});

To customise the appearance of your label feel free to edit the `panicar_poilabel.xml` 

### Demo Project

Please checkout the demo project included in this repository for further details. It requires Google Android Studio 0.8.x or later.

----------

## Frequently asked questions

### Where do i put the API key?
You need to set your API key within your App class (your App class has to derive from `PARApplication`) by overwriting the setApiKey method.

	@Override
	    public String setApiKey() {
        return "yourAPIKey";
    }

### How to send Bugs to doPanic?
The best way to communicate bugs is sending us an email with information on these topics:

- Which devices do you use?
- Which Android version is installed?
- Does it work in our demo?
- What is your workflow when using PanicAR?
- Which function is affected?
- Which methods are overwritten?
- Can you send your logcat output?
- If it is a graphical problem: can you send a screenshot additionally?
- Better: can you send a video?
- Your own comments:
- Give us feedback for PanicAR:


[![Analytics](https://ga-beacon.appspot.com/UA-47538502-1/panicar/home)](https://github.com/dopanic/panicar)