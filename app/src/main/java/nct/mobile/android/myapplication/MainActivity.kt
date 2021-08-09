package nct.mobile.android.myapplication

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.android.FlutterFragment
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugins.GeneratedPluginRegistrant
import com.ryanheise.audioservice.AudioServicePlugin
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class MainActivity : AppCompatActivity() {
//class MainActivity : FlutterFragmentActivity() {
    companion object {
        private const val TAG_FLUTTER_FRAGMENT = "flutter_fragment"
    }

    private var flutterFragment: FlutterFragment? = null

//    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
//        Log.d("","configureFlutterEngine() : $flutterEngine");
//
//        GeneratedPluginRegistrant.registerWith(flutterEngine)
//    }
//
//    override fun provideFlutterEngine(context: Context): FlutterEngine? {
//        val flutterEngine = AudioServicePlugin.getFlutterEngine(context);
//
//        Log.d("","provideFlutterEngine() : $flutterEngine");
//
//        return flutterEngine
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        val flutterEngine = AudioServicePlugin.getFlutterEngine(this);
//
//        Log.d("","getFlutterEngine() : $flutterEngine");
//
//        GeneratedPluginRegistrant.registerWith(flutterEngine)
//
//        Log.d("","registerWith() : $flutterEngine");
//
//        // Start executing Dart code in the FlutterEngine.
//        flutterEngine.dartExecutor.executeDartEntrypoint(
//            DartExecutor.DartEntrypoint.createDefault()
//        )
//
//        // Cache the pre-warmed FlutterEngine to be used later by FlutterFragment.
//        FlutterEngineCache
//            .getInstance()
//            .put("my_engine_id", flutterEngine)

        setContentView(R.layout.activity_main)

        val fragmentManager: FragmentManager = supportFragmentManager

        flutterFragment = fragmentManager
            .findFragmentByTag(TAG_FLUTTER_FRAGMENT) as FlutterFragment?

        if (flutterFragment == null) {
            val newFlutterFragment = FlutterFragment.createDefault()

//            flutterFragment = FlutterFragment.withNewEngine()
//                .shouldAttachEngineToActivity(false)
//                .build();

            fragmentManager
                .beginTransaction()
                .replace(
                    R.id.id_f1,
                    newFlutterFragment,
                    TAG_FLUTTER_FRAGMENT
                )
                .addToBackStack(null)
                .commit()
        }

//        val newFragment: Fragment = F1()
//        val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
//
//        transaction.replace(R.id.id_f1, newFragment)
//        transaction.addToBackStack(null)
//
//        transaction.commit()
    }
}