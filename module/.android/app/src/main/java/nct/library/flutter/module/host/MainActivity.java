package nct.library.flutter.module.host;

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
