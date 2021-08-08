# addFlutterAppToAndroid : Android project name
# module : Flutter module name (as Flutter project)

## Getting Started
Open `Android Studio 4.2.2` (If tried with Android Studio latest might be got strange error)

Open this `module` folder as `Flutter project` :
1. run `flutter clean`
2. run `flutter pub get`
3. in `.android/app/src/main/`

    3.a `AndroidManifest.xml` file, paste these code lines :
    
    
        <?xml version="1.0" encoding="utf-8"?>
        <manifest xmlns:android="http://schemas.android.com/apk/res/android"
            package="nct.mobile.flutter.module.host">
        
            <!-- The INTERNET permission is required for development. Specifically,
                 flutter needs it to communicate with the running application
                 to allow setting breakpoints, to provide hot reload, etc.
            -->
            <uses-permission android:name="android.permission.INTERNET" />
            <uses-permission android:name="android.permission.WAKE_LOCK"/>
            <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
        
            <application
                android:icon="@mipmap/ic_launcher"
                android:label="module">
                <activity
                    android:name=".MainActivity"
                    android:configChanges="orientation|keyboardHidden|keyboard|screenSize|smallestScreenSize|locale|layoutDirection|fontScale|screenLayout|density|uiMode"
                    android:hardwareAccelerated="true"
                    android:launchMode="singleTop"
                    android:theme="@style/LaunchTheme"
                    android:windowSoftInputMode="adjustResize">
                    <!-- This keeps the window background of the activity showing
                         until Flutter renders its first frame. It can be removed if
                         there is no splash screen (such as the default splash screen
                         defined in @style/LaunchTheme). -->
                    <meta-data
                        android:name="io.flutter.app.android.SplashScreenUntilFirstFrame"
                        android:value="true" />
                    <intent-filter>
                        <action android:name="android.intent.action.MAIN" />
                        <category android:name="android.intent.category.LAUNCHER" />
                    </intent-filter>
                </activity>
                <!-- EDIT THE android:name ATTRIBUTE IN YOUR EXISTING "ACTIVITY" ELEMENT -->
                <activity android:name="com.ryanheise.audioservice.AudioServiceActivity" />
        
                <!-- ADD THIS "SERVICE" element -->
                <service android:name="com.ryanheise.audioservice.AudioService">
                    <intent-filter>
                        <action android:name="android.media.browse.MediaBrowserService" />
                    </intent-filter>
                </service>
        
                <!-- ADD THIS "RECEIVER" element -->
                <receiver android:name="com.ryanheise.audioservice.MediaButtonReceiver">
                    <intent-filter>
                        <action android:name="android.intent.action.MEDIA_BUTTON" />
                    </intent-filter>
                </receiver>
                <!-- Don't delete the meta-data below.
                     This is used by the Flutter tool to generate GeneratedPluginRegistrant.java -->
                <meta-data
                    android:name="flutterEmbedding"
                    android:value="2" />
            </application>
        </manifest>

   3.b `../MainActivity.java` file, paste this :
   
        package nct.mobile.flutter.module.host;
        
        import android.content.Context;
        
        import io.flutter.embedding.android.FlutterActivity;
        import io.flutter.embedding.engine.FlutterEngine;
        
        import com.ryanheise.audioservice.AudioServicePlugin;
        
        public class MainActivity extends FlutterActivity {
        //    @Override
        //    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        //        GeneratedPluginRegistrant.registerWith(flutterEngine);
        //    }
        //
            @Override
            public FlutterEngine provideFlutterEngine(Context context) {
                return AudioServicePlugin.getFlutterEngine(context);
            }
        }

Open `Android project` with `addFlutterAppToAndroid` folder,

then run its application on emulator