# Android platform - React Native Project - Tools : Visual Studio Code

The implement is same about section I. or II. in [Android Native Project](https://gitlab.nct.vn/nhaccuatui/frontend/iot-projects/nhaccuatui/plugin/blob/dev/README_Android_Native_Project.md)

You can use either section I. or II. way, to include `flutter module` into `Android project`.

III. How to show `flutter module` on Android UI :

1. MUST Define `MainApplication.kt` class, attach `FlutterEngine` to able execute `dart` code.

   Sample code :
    ```
   import android.app.Application;
   import android.content.Context;
   
   import com.facebook.react.PackageList;
   import com.facebook.react.ReactApplication;
   import com.facebook.react.ReactInstanceManager;
   import com.facebook.react.ReactNativeHost;
   import com.facebook.react.ReactPackage;
   import com.facebook.soloader.SoLoader;
   
   import java.lang.reflect.InvocationTargetException;
   import java.util.List;
   
   import io.flutter.embedding.engine.FlutterEngine;
   import io.flutter.embedding.engine.FlutterEngineCache;
   import io.flutter.embedding.engine.dart.DartExecutor;
   
   import com.ryanheise.audioservice.AudioServicePlugin;
   
   public class MainApplication extends Application implements ReactApplication {
   
      public static final String ENGINE_ID = "1";
      
      private final ReactNativeHost mReactNativeHost =
            new ReactNativeHost(this) {
                @Override
                public boolean getUseDeveloperSupport() {
                    return BuildConfig.DEBUG;
                }
      
                @Override
                protected List<ReactPackage> getPackages() {
                    @SuppressWarnings("UnnecessaryLocalVariable")
                    List<ReactPackage> packages = new PackageList(this).getPackages();
                    // Packages that cannot be autolinked yet can be added manually here, for example:
                    // packages.add(new MyReactNativePackage());
                    return packages;
                }
      
                @Override
                protected String getJSMainModuleName() {
                    return "index";
                }
            };
      
      @Override
      public ReactNativeHost getReactNativeHost() {
         return mReactNativeHost;
      }
      
      @Override
      public void onCreate() {
         super.onCreate();
         SoLoader.init(this, /* native exopackage */ false);
         initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
           
         /// Initialize Flutter Engine : "import com.ryanheise.audioservice.AudioServicePlugin;"
         FlutterEngine flutterEngine = AudioServicePlugin.getFlutterEngine(this);
         // FlutterEngine flutterEngine = new FlutterEngine(this);
         
         flutterEngine
                .getDartExecutor()
                .executeDartEntrypoint(
                        DartExecutor.DartEntrypoint.createDefault()
                );
         
         FlutterEngineCache.getInstance().put(ENGINE_ID, flutterEngine);
      }
      
      private static void initializeFlipper(
            Context context, ReactInstanceManager reactInstanceManager) {
         if (BuildConfig.DEBUG) {
            try {
                Class<?> aClass = Class.forName("com.awesomeproject.ReactNativeFlipper");
                aClass
                        .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
                        .invoke(null, context, reactInstanceManager);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
         }
      }
   }
   ```

2. `MainActivity.kt` class MUST `extends ReactActivity` for including `flutter module`
   as `Fragment page` in Android App.

   Sample code :   
   ```
   import static com.awesomeproject.MainApplication.ENGINE_ID;
   
   import android.os.Bundle;
   import android.widget.Toast;
   
   import androidx.annotation.NonNull;
   import androidx.annotation.Nullable;
   import androidx.fragment.app.Fragment;
   import androidx.fragment.app.FragmentManager;
   import androidx.fragment.app.FragmentPagerAdapter;
   import androidx.viewpager.widget.ViewPager;
   
   import com.facebook.react.ReactActivity;
   
   import io.flutter.embedding.android.FlutterFragment;
   
   public class MainActivity extends ReactActivity {
      final String TAG_FLUTTER_FRAGMENT = "flutter_fragment";
      
      private FlutterFragment flutterFragment;
      
      @Override
      protected String getMainComponentName() {
         return "AwesomeProject";
      }
      
      @Override
      protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         
         setContentView(R.layout.activity_main);
         
         CustomViewPager vp = findViewById(R.id.vpPager);
         MyPagerAdapter adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
         
         vp.setAdapter(adapterViewPager);
         vp.setOffscreenPageLimit(2);
         
         vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
         
            }
         
            @Override
            public void onPageSelected(int position) {
                Toast.makeText(
                        getApplicationContext(),
                        "Selected page position: " + position, Toast.LENGTH_SHORT
                ).show();
         
                // Replace flutter fragment here
                if (position == 1) {
                    replaceFragment(getFlutterFragment());
                }
            }
         
            @Override
            public void onPageScrollStateChanged(int state) {
         
            }
         });
      }
      
      private FlutterFragment getFlutterFragment() {
         FragmentManager fm = getSupportFragmentManager();
         
         FlutterFragment flutterFragment = (FlutterFragment) fm.findFragmentByTag(TAG_FLUTTER_FRAGMENT);
         
         if (flutterFragment == null) {
         
            flutterFragment = FlutterFragment.withCachedEngine(ENGINE_ID).build();
         }
         
         return flutterFragment;
      }
      
      private void replaceFragment(FlutterFragment flutterFragment) {
         FragmentManager fm = getSupportFragmentManager();
         
         fm.beginTransaction()
                .replace(R.id.id_f2,
                        flutterFragment,
                        TAG_FLUTTER_FRAGMENT
                ).commit();
      }
   
      static class MyPagerAdapter extends FragmentPagerAdapter {
   
         MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
         }
         
         @NonNull
         @Override
         public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return F1.newInstance("0", "Page #1");
                case 1:
                    return F2.newInstance("0", "Page #2");
            }
         
            return F1.newInstance("0", "Page 1");
         }
         
         @Override
         public int getCount() {
            return 2;
         }
         
         @Nullable
         @Override
         public CharSequence getPageTitle(int position) {
            return "Page" + position;
         }
      }
   }
   ```

3. Run Android App by using these command lines (stay at root folder in `Visual Studio Code` tool) :

    3.a Start Metro, the JavaScript bundler that ships with React Native :
    ```
    npx react-native start
    ```
    
    3.b. Run Android application :
    ```
    npx react-native run-android --no-jetifier
    ```

    Or to [avoid exception `androidX`](https://stackoverflow.com/questions/57048978/failed-to-run-jetifier-react-native), use this command :
    ```
    npx react-native run-android --no-jetifier
    ```

# iOs platform - React Native Project - Tools : Visual Studio Code
Same as [iOs Native Project](https://gitlab.nct.vn/nhaccuatui/frontend/iot-projects/nhaccuatui/plugin/blob/dev/README_iOs_Native_Project.md)

## Notice

To create `react native` project, use this command line :

    npx react-native init ReactNativeProject
