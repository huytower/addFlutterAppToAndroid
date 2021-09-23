# Android platform - Android native Project - Tools : Android Studio

Document : [Add to App](https://flutter.dev/docs/development/add-to-app/android/project-setup#add-the-flutter-module-as-a-dependency)

NOTICE : Read corresponding section.

### Section A : For Partner (external side)

Import `flutter module` via remote maven repository.

+ Advantage is `flutter module` with close source code (does not import source code directly).

+ Disadvantage is `flutter module` need update version via `flutter build aar` if feature got any changes.

NOTICE that it depends on `Gradle 7.0` and above. (use below version, its setting is different)

In the android project,

1. At root folder, make sure to `/settings.gradle` use `RepositoriesMode.PREFER_PROJECT` mode :

   Sample code :
   ```
   import org.gradle.api.initialization.resolve.RepositoriesMode
   
   dependencyResolutionManagement {
      repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
   }
   
   rootProject.name = "run_flutter_module_in_android_app"
   include ':app'
   ```

2. At `/app/build.gradle` file,

   2. a. Define where to install library via maven.

   ```
   plugins {
      id "com.google.cloud.artifactregistry.gradle-plugin" version "2.1.1"
   }
   
   repositories {
      google()
      mavenCentral()
   
      /// MUST include remote maven repository, details in Google Cloud console
      /// sample url : "artifactregistry://REGION-maven.pkg.dev/REPOSITORY-URL/REPOSITORY-ID"
   
      maven { 
         /// Remote maven repository at Google Cloud Platform
         /// REGION : asia-southeast1
         /// REPOSITORY-URL : nct-flutter-module-mobile
         /// REPOSITORY-ID : release
   
         url "artifactregistry://asia-southeast1-maven.pkg.dev/nct-flutter-module-mobile/release"
      }
      maven {
         /// MUST add to include Flutter sdk in native platform
   
         url "https://storage.googleapis.com/download.flutter.io"
      }
   }
   ```

   2. b. Define `profile` config :

   ```
   buildTypes {
      release {
         signingConfig signingConfigs.debug  // Using ".debug" for easy building app
      }
      
      /// MUST add as Guide indicated
      profile {
         initWith debug
      }
   }
   ```

   2. c. Define dependency :

   ```
   dependencies {
      /// SHOULD get *.aar from remote maven repository in here
      debugImplementation 'nct.mobile.flutter.module:flutter_debug:1.0.0'
      profileImplementation 'nct.mobile.flutter.module:flutter_profile:1.0.0'
      releaseImplementation 'nct.mobile.flutter.module:flutter_release:1.0.0'
   
      /// MUST define directly since `audio_service` library in `flutter module` included it,
      /// otherwise, compile-time errors appear
      implementation "com.google.android.exoplayer:exoplayer-core:2.15.0"
      implementation "com.google.android.exoplayer:exoplayer-smoothstreaming:2.15.0"
      implementation "com.google.android.exoplayer:exoplayer-hls:2.15.0"
      implementation "com.google.android.exoplayer:exoplayer-dash:2.15.0"
   }   
   ```

3. [How to show `flutter module` on Android UI](https://gitlab.nct.vn/nhaccuatui/frontend/iot-projects/nhaccuatui/plugin/blob/dev/README_Android_Native_Project.md#iii-how-to-show-flutter-module-on-android-ui)

4. Run Android App

### Section B : For internal company developers (internal)

Advantage is `flutter module` able to get update directly (MUST import source code directly).

You can use either I. or II. way to include `flutter module` into `Android project`

##### I. Import `flutter module` project into android project directly.

1. Create Android Application project
2. Select `File -> New -> New Module -> Import Flutter module` to import `flutter module` (`Module` folder)
3. After imported,

    3.a. Make sure `/app/build.gradle` file, already added :
    
        dependencies {
           implementation project(path: ':flutter')
        }
        
    3.b. Make sure `/settings.gradle` file, already added :

        setBinding(new Binding([gradle: this]))
        evaluate(new File(
          settingsDir,
          'module/.android/include_flutter.groovy'
        ))

4. [How to show `flutter module` on Android UI](https://gitlab.nct.vn/nhaccuatui/frontend/iot-projects/nhaccuatui/plugin/blob/dev/README_Android_Native_Project.md#iii-how-to-show-flutter-module-on-android-ui)

##### II. Import `flutter module` via local maven repository.

NOTICE that it depends on `Gradle 7.0` and above. (use below version, its setting is different)

In the android project,

1. At root folder, make sure to `/settings.gradle` use `RepositoriesMode.PREFER_PROJECT` mode :

   Sample code :
   ```
   import org.gradle.api.initialization.resolve.RepositoriesMode
   
   dependencyResolutionManagement {
      repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
   }
   
   rootProject.name = "run_flutter_module_in_android_app"
   include ':app'
   ```

2. At `/app/build.gradle` file, 
   
   2. a. Define where to install library via maven.

   ```
   repositories {
      google()
      mavenCentral()
      maven {  // Local maven repository
         url '/Users/huytd/Desktop/import_module_to_android/module/build/host/outputs/repo'
      }
      maven {
         url "https://storage.googleapis.com/download.flutter.io"
      }
   }
   ```
   (This is local maven, appear after calling `flutter build aar` command line)

   2. b. Define `profile` config :

   ```
   buildTypes {
      release {
         signingConfig signingConfigs.debug  // Using ".debug" for easy building app
      }
      profile {
         initWith debug
      }
   }
   ```
   
   2. c. Define dependency :
   
   ```
   dependencies {
      debugImplementation 'nct.mobile.flutter.module:flutter_debug:1.0'
      profileImplementation 'nct.mobile.flutter.module:flutter_profile:1.0'
      releaseImplementation 'nct.mobile.flutter.module:flutter_release:1.0'
   }   
   ```

3. [How to show `flutter module` on Android UI](https://gitlab.nct.vn/nhaccuatui/frontend/iot-projects/nhaccuatui/plugin/blob/dev/README_Android_Native_Project.md#iii-how-to-show-flutter-module-on-android-ui)

4. Run Android App

## How to show `flutter module` on Android UI

It indicate that replace Flutter page on UI as Fragment page. 

Documents : [Add as flutter fragment](https://flutter.dev/docs/development/add-to-app/android/add-flutter-fragment?tab=use-prewarmed-engine-kotlin-tab#add-a-flutterfragment-to-an-activity-with-a-new-flutterengine)

Sample code : [Add to App](https://github.com/flutter/samples/tree/master/add_to_app)

1. MUST Define `MainApplication.kt` class, attach `FlutterEngine` to able execute `dart` code.

   Using these code to initialize and pre-warm up `flutter module`.
   ```
   val flutterEngine = AudioServicePlugin.getFlutterEngine(this)
    
   flutterEngine
       .dartExecutor
       .executeDartEntrypoint(
           DartExecutor.DartEntrypoint.createDefault()
       )
   ```

   See the sample code :
   ```
   import android.app.Application
   import io.flutter.embedding.engine.FlutterEngine
   import io.flutter.embedding.engine.FlutterEngineCache
   import io.flutter.embedding.engine.dart.DartExecutor
   
   import com.ryanheise.audioservice.AudioServicePlugin;
    
   const val ENGINE_ID = "1"
   
   class MainApplication : Application() {
   
     override fun onCreate() {
         super.onCreate()
   
         /// IF USE `audio_service` LIBRARY, [DOCUMENT](https://pub.dev/packages/audio_service#android-setup)
         /// Initialize Flutter Engine : "import com.ryanheise.audioservice.AudioServicePlugin;"
         FlutterEngine flutterEngine = AudioServicePlugin.getFlutterEngine(this)
         // FlutterEngine flutterEngine = new FlutterEngine(this)
   
         /// MUST ADD THIS
         flutterEngine
             .dartExecutor
             .executeDartEntrypoint(
                 DartExecutor.DartEntrypoint.createDefault()
             )
   
         /// Using cache for loading faster
         FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine)
     }
   
   }
   ```

2. Declare external attributes in `AndroidManifest.xml` also when using external library. 

   ```
   <manifest>
      <uses-permission android:name="android.permission.WAKE_LOCK"/>
      <uses-permission android:name="android.permission.FOREGROUND_SERVICE"/>
      
      <application>
         <service android:name="com.ryanheise.audioservice.AudioService">
            <intent-filter>
               <action android:name="android.media.browse.MediaBrowserService" />
            </intent-filter>
         </service>
         
         <receiver android:name="com.ryanheise.audioservice.MediaButtonReceiver" >
            <intent-filter>
              <action android:name="android.intent.action.MEDIA_BUTTON" />
            </intent-filter>
         </receiver> 
      
      </application>
   </manifest>
   ```
   
   [audio_service : android-setup document](https://pub.dev/packages/audio_service#android-setup)

3. MUST use `MainActivity extends AppCompatActivity` to include `flutter module`
   as `Fragment page` in Android App, 
   
   Replace `FlutterFragment` in `onCreate()` method :

   /// Kotlin code
   ```
   getFlutterFragment(FLUTTER_CACHE_ENGINE_ID)?.let { replaceFragment(it, R.id.id_fragment_need_replace) }
   ```
   /// Java code
   ```
   replaceFragment(getFlutterFragment(FLUTTER_CACHE_ENGINE_ID), R.id.id_fragment_need_replace);
   ```

   Sample code :
   ```
   import androidx.appcompat.app.AppCompatActivity
   import android.os.Bundle
   import androidx.fragment.app.FragmentManager
   import io.flutter.embedding.android.FlutterFragment
   
   class MainActivity : AppCompatActivity() {
     companion object {
         // Define a tag String to represent the FlutterFragment within this
         // Activity's FragmentManager. This value can be whatever you'd like.
         private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
     }
   
     // Declare a local variable to reference the FlutterFragment so that you
     // can forward calls to it later.
     private var flutterFragment: FlutterFragment? = null
   
     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         setContentView(R.layout.activity_main)
         
         /// INSTALL FLUTTER PAGE, AS NEW SDK INSTANCE
         getFlutterFragment(FLUTTER_CACHE_ENGINE_ID)?.let { replaceFragment(it, R.id.id_fragment_need_replace) }
     }
   }

   private fun getFlutterFragment(flutterCacheEngineTag: String): FlutterFragment? {
        // Declare a local variable to reference the FlutterFragment so that you
        // can forward calls to it later.
        var flutterFragment: FlutterFragment? = null

        // Get a reference to the Activity's FragmentManager to add a new
        // FlutterFragment, or find an existing one.
        val fragmentManager: FragmentManager = supportFragmentManager

        // Attempt to find an existing FlutterFragment, in case this is not the
        // first time that onCreate() was run.
        flutterFragment = fragmentManager
            .findFragmentByTag("flutter_fragment") as FlutterFragment?

        // Create and attach a FlutterFragment if one does not exist.
        if (flutterFragment == null) {
            val newFlutterFragment =
                FlutterFragment.withCachedEngine(flutterCacheEngineTag).build<FlutterFragment>()

            flutterFragment = newFlutterFragment
        }

        return flutterFragment;
    }

    private fun replaceFragment(fragment: FlutterFragment, replaceResId: Int) {
        val fragmentManager: FragmentManager = supportFragmentManager

        fragmentManager
            .beginTransaction()
            .replace(
                replaceResId,
                fragment,
                "flutter_fragment"
            )
            .commit()
    }
   ```
    
4. Run Android App