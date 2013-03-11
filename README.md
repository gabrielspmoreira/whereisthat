Introduction
===========

**Where is That?** is an Android game to test your geographic knowledge. Players are challenged to find where is a location in an interative map.
The distance from the real location and time to answer counts in the player score.

If you enjoy programming, mobile, games and geography, please join us and have fun!

Screenshots
-----------

![Where Is That? screenshots](/gabrielspmoreira/whereisthat/master/resources/screenshots/screenshots.png) 

Roadmap
-----------
Currently, the main menu, game playing and score have already been developed.

There are a list of opened [issues](/gabrielspmoreira/whereisthat/issues) of improvement for the project. In our vision, the next main steps are:

**Version 1**
* New location datasets - Currently questions are related only to world cities. We intend to ask a player new location types, like World-known points of interest (e.g. Pisa Tower, Golden-Gate Bridge, ...) and Historic Events (e.g. Where occured the D-Day in Second World War?)
* Game levels - As player achieves a minimum score, it is expected to proceed to next level with different questions (other location datasets), maybe harder.
* Support to different screen sizes and resolutions - Currently the game is being tested on a Samsung Galaxy Tab 10.1 tablet. It is necessary to adapt it for medium and small device screens, like smartphones).
* Usability improvements - Buttons to return to main menu, to set sound on/off, to quit the game, ...

**Version 2**
* Storing player score on a game central
* Retrieving locations from a webservice where locations are frequently updated (not more from static XML), so that players feel constantly challenged.
* and so on...

The project is open for new ideas - fell free to suggest!

Environment
-----------
This is the current development environment we are using:

*  Eclipse Classic IDE 3.7 (Indigo) - http://www.eclipse.org/downloads/
*	Oracle Java Development Kit (JDK) 6 - http://www.oracle.com/technetwork/java/javase/downloads/index.html
*	Android SDK r18 - http://developer.android.com/sdk/index.html
*	Android Development Tools (ADT) Plugin for Eclipse - http://developer.android.com/sdk/installing.html
* ArcGIS Runtime SDK for Android 1.1.1 - http://resources.arcgis.com/content/arcgis-android/sdk
* Wireframe Sketcher - http://wireframesketcher.com/index.html
                    
P.s. In order to make ESRI Runtime SDK work on Android Emulator, follow the instructions in http://blogs.esri.com/esri/arcgis/2012/05/02/arcgis-runtime-sdk-for-android-v1-1-supports-android-emulator/

Dependencies
------------
* The app requires Android 2.2 as minimun platform (also minimun required by ArcGIS Runtime for Android), so the app is compatible with more than 95% of current Android devices.
* The game map is a widget of ArcGIS Runtime SDK for Android.
* The World basemap is provided by ESRI ArcGIS Online plaform, so the device must be online in order to see the basemap.
* Android-Query (AQuery) project is been used to work with user interface, xml parsing and networking.

Resources
---------
*	[Android Developers official site](http://developer.android.com/) is a great resource
*	[ArcGIS Runtime SDK for Android](http://resources.arcgis.com/content/arcgis-android/sdk) is the main resource for this ESRI API
