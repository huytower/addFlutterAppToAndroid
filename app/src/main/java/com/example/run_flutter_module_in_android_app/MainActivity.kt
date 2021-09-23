package com.example.run_flutter_module_in_android_app

import android.content.Context
import com.ryanheise.audioservice.AudioServicePlugin
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine


class MainActivity : FlutterActivity() {
    @Nullable
    fun provideFlutterEngine(@NonNull context: Context?): FlutterEngine {
        return AudioServicePlugin.getFlutterEngine(context)
    }
}
