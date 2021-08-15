package nct.mobile.android.myapplication

import android.app.Application
import com.ryanheise.audioservice.AudioServicePlugin
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

const val ENGINE_ID = "my_engine_id";

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        val flutterEngine: FlutterEngine = AudioServicePlugin.getFlutterEngine(this)
//        val flutterEngine = FlutterEngine(this)

        // Start executing Dart code in the FlutterEngine.
        flutterEngine.dartExecutor.executeDartEntrypoint(
            DartExecutor.DartEntrypoint.createDefault()
        )

        // Cache the pre-warmed FlutterEngine to be used later by FlutterFragment.
        FlutterEngineCache
            .getInstance()
            .put(ENGINE_ID, flutterEngine)
    }

}