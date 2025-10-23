import 'dart:io';
import 'dart:ui';

import 'package:background_locator_2/keys.dart';
import 'package:background_locator_2/location_dto.dart';
import 'package:background_locator_2/settings/android_settings.dart';
import 'package:background_locator_2/settings/ios_settings.dart';

class SettingsUtil {
  static Map<String, dynamic> getArgumentsMap(
      {required void Function(LocationDto) callback,
      void Function(Map<String, dynamic>)? initCallback,
      Map<String, dynamic>? initDataCallback,
      void Function()? disposeCallback,
      AndroidSettings androidSettings = const AndroidSettings(),
      IOSSettings iosSettings = const IOSSettings()}) {
    final args = _getCommonArgumentsMap(
        callback: callback,
        initCallback: initCallback,
        initDataCallback: initDataCallback,
        disposeCallback: disposeCallback);

    if (Platform.isAndroid) {
      args.addAll(_getAndroidArgumentsMap(androidSettings));
    } else if (Platform.isIOS) {
      args.addAll(_getIOSArgumentsMap(iosSettings));
    }

    return args;
  }

  static Map<String, dynamic> _getCommonArgumentsMap({
    required void Function(LocationDto) callback,
    void Function(Map<String, dynamic>)? initCallback,
    Map<String, dynamic>? initDataCallback,
    void Function()? disposeCallback,
  }) {
    final Map<String, dynamic> args = {};

    final callbackHandle = PluginUtilities.getCallbackHandle(callback);
    if (callbackHandle != null) {
      args[Keys.ARG_CALLBACK] = callbackHandle.toRawHandle();
    } else {
      throw Exception(
        'Failed to get callback handle for location callback. '
        'Make sure the callback is a top-level or static function with @pragma("vm:entry-point")',
      );
    }

    // Init callback (opcional)
    if (initCallback != null) {
      final initCallbackHandle =
          PluginUtilities.getCallbackHandle(initCallback);
      if (initCallbackHandle != null) {
        args[Keys.ARG_INIT_CALLBACK] = initCallbackHandle.toRawHandle();
      }
    }

    // Dispose callback (opcional)
    if (disposeCallback != null) {
      final disposeCallbackHandle =
          PluginUtilities.getCallbackHandle(disposeCallback);
      if (disposeCallbackHandle != null) {
        args[Keys.ARG_DISPOSE_CALLBACK] = disposeCallbackHandle.toRawHandle();
      }
    }

    // Init data callback (opcional)
    if (initDataCallback != null) {
      args[Keys.ARG_INIT_DATA_CALLBACK] = initDataCallback;
    }

    return args;
  }

  static Map<String, dynamic> _getAndroidArgumentsMap(
      AndroidSettings androidSettings) {
    final Map<String, dynamic> args = {
      Keys.ARG_SETTINGS: androidSettings.toMap()
    };

    if (androidSettings.androidNotificationSettings.notificationTapCallback !=
        null) {
      final notificationCallbackHandle = PluginUtilities.getCallbackHandle(
          androidSettings.androidNotificationSettings.notificationTapCallback!);

      if (notificationCallbackHandle != null) {
        args[Keys.ARG_NOTIFICATION_CALLBACK] =
            notificationCallbackHandle.toRawHandle();
      }
    }

    return args;
  }

  static Map<String, dynamic> _getIOSArgumentsMap(IOSSettings iosSettings) {
    return iosSettings.toMap();
  }
}
