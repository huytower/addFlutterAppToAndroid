Project Name : "load Flutter Module into Android App via Remote Maven"

### NOTICE : Below steps in document indicate how to 
import `flutter module` into native project or react native project,
via remote maven repository

Below `Example Application` can not compile because Google Cloud project not existed.
(it required linking Credit Card, so I deleted it)

If using, need create new Google Cloud project and replace corresponding fields.

---------------------------------------------------------------------------------------------------

### Example Application - Native Project
It import `flutter module` into native project or react native project,
via remote maven repository.

### Getting Started, how to included Module

##### [Android platform - Android native Project - Tools : Android Studio](https://github.com/huytower/addFlutterModuleToAndroidApp/blob/main/README_Android_Native_Project.md)
##### [Android platform - React Native Project - Tools : Visual Studio Code](https://github.com/huytower/addFlutterModuleToAndroidApp/blob/main/README_React_Native_Project.md)

#### Tools :

    Android Studio Tools : 4.3.0 (Arctic Fox)
    Visual Studio Code : 1.59.0
    Xcode : 12.5.1
    Fluter version : 2.6.0-1.0.pre.255 | Dart : 2.15.0
    Gradle version : 7.2
    Android gradle plugin version : 7.0.2
    Jvm : jdk11 | java11 (NOTICE THAT above version might get strange expception)

    Command line "flutter doctor -v" shown :

    [✓] Flutter (Channel master, 2.6.0-1.0.pre.255, on macOS 11.4 20F71 darwin-x64,
        locale en-VN)
        • Flutter version 2.6.0-1.0.pre.255 at /Users/huytd/flutter
        • Upstream repository https://github.com/flutter/flutter.git
        • Framework revision 86c79a83a2 (15 hours ago), 2021-09-11 13:27:02 -0700
        • Engine revision abb1980f65
        • Dart version 2.15.0 (build 2.15.0-96.0.dev)
    
    [!] Android toolchain - develop for Android devices (Android SDK version 30.0.3)
        • Android SDK at /Users/huytd/android-sdk
        ✗ cmdline-tools component is missing
          Run `path/to/sdkmanager --install "cmdline-tools;latest"`
          See https://developer.android.com/studio/command-line for more details.
        ✗ Android license status unknown.
          Run `flutter doctor --android-licenses` to accept the SDK licenses.
          See https://flutter.dev/docs/get-started/install/macos#android-setup for
          more details.
    
    [✓] Xcode - develop for iOS and macOS (Xcode 12.5.1)
        • Xcode at /Applications/Xcode.app/Contents/Developer
        • CocoaPods version 1.10.1
    
    [✓] Chrome - develop for the web
        • Chrome at /Applications/Google Chrome.app/Contents/MacOS/Google Chrome
    
    [✓] Android Studio (version 2020.3)
        • Android Studio at /Applications/Android Studio.app/Contents
        • Flutter plugin can be installed from:
          🔨 https://plugins.jetbrains.com/plugin/9212-flutter
        • Dart plugin can be installed from:
          🔨 https://plugins.jetbrains.com/plugin/6351-dart
        • Java version OpenJDK Runtime Environment (build 11.0.10+0-b96-7281165)
    
    [✓] VS Code (version 1.59.0)
        • VS Code at /Users/huytd/Downloads/Visual Studio Code.app/Contents
        • Flutter extension version 3.25.0
    
    [✓] Connected device (1 available)
        • Chrome (web) • chrome • web-javascript • Google Chrome 93.0.4577.63

#### Design pattern : MVC using GetX

#### Library was used :
    
[GetX](https://pub.dev/packages/get)

    - Access object everywhere, use methods : Get.find() | Get.put()

    - Notify data changed of variables to show on UI, use widget : Obx() + variables.obs

    - Locale multi-language

    - Change theme : Dark|Light

    - Show dialogs
    
[http](https://pub.dev/packages/http)

    - Using Request|Get|Post to get json response


[get_storage](https://pub.dev/packages/get_storage)
[shared_preferences](https://pub.dev/packages/shared_preferences)

    - Use to save local data in application

[encrypt](https://pub.dev/packages/encrypt)

    - Encrypt|Decrypt data

#### Notice
All modules was using MUST define minSdkVersion totally same, current `minSdkVersion 16`
(if get errors, consider `Popular Exceptions` in below)

#### Popular exceptions :

    a. MissingPluginException
    It MUST notify that you did not initialize library success.
    There is something wrong related to import library in native project.
        - Does not define library in `pubspec.yaml` file in `flutter module` project
        - Does not initialize `FlutterEngine` and call `executeDartEntrypoint` method
    
    b. Duplicate 2 files with same name exceptions
    Try using this in `app/build.gradle` file at root :
        
        packagingOptions {
            exclude 'META-INF/audio_player_debug.kotlin_module'
            exclude 'META-INF/base_debug.kotlin_module'
            exclude 'META-INF/fluttertoast_debug.kotlin_module'
        }
        
    c. zip64 method : [link](https://developer.android.com/studio/build/multidex#kts)
    MUST add multiDexEnabled = true

    d. uses-sdk:minSdkVersion 16 cannot be smaller than version 23 declared in library [:audioplayers]
    You can use either one of these ways :
    - Trick 1 : change to `minSdkVersion 23` 
    - Trick 2 : Add this line in AndroidManifest.xml : `<uses-sdk tools:overrideLibrary="xyz.luan.audioplayers"/>`
    Issue : https://stackoverflow.com/questions/66167083/uses-sdkminsdkversion-16-cannot-be-smaller-than-version-23-declared-in-library
    Docs : https://stackoverflow.com/questions/27095077/how-do-i-use-toolsoverridelibrary-in-a-build-gradle-file

    e. no-sound null safety bug
    Resolution : add this line code in `main.dart` file, at root folder.
    // @dart=2.9 
    /// TRICK : @dart=2.9, avoid bug no-sound safety,
    /// link : https://stackoverflow.com/questions/65504664/how-to-build-apk-with-no-sound-null-safety
    Sample code :

        ```
        <manifest
            xmlns:android="http://schemas.android.com/apk/res/android"
            xmlns:tools="http://schemas.android.com/tools"
            package="nct.mobile.flutter.module.host">
    
            <uses-permission android:name="android.permission.INTERNET" />
        
            <uses-sdk tools:overrideLibrary="com.ryanheise.audioservice, com.tekartik.sqflite, io.flutter.plugins.share, io.flutter.plugins.sharedpreferences, io.flutter.plugins.pathprovider,
             dev.fluttercommunity.plus.packageinfo, io.github.ponnamkarthik.toast.fluttertoast, dev.fluttercommunity.plus.device_info, io.flutter.plugins.urllauncher" />

        </manifest>
        ```

    f. FAILURE: Build failed with an exception.
    * Where:
      Script '/Users/huytd/flutter/packages/flutter_tools/gradle/flutter.gradle' line: 1035
    
    * What went wrong:
      Execution failed for task ':flutter:compileFlutterBuildDebug'.
    > Process 'command '/Users/huytd/flutter/bin/flutter'' finished with non-zero exit value 1
    
    Resolution :
    - Do `Clean project` to avoid this error
    - If not, Downgrade Android Gradle Plugin version (Latest version get error exception)

