import background_locator_2
import Flutter
import UIKit

func registerPlugins(registry: FlutterPluginRegistry) {
    GeneratedPluginRegistrant.register(with: registry)
}

@main
@objc class AppDelegate: FlutterAppDelegate {
    override func application(
        _ application: UIApplication,
        didFinishLaunchingWithOptions launchOptions: [UIApplication
            .LaunchOptionsKey: Any]?
    ) -> Bool {
        GeneratedPluginRegistrant.register(with: self)
        BackgroundLocatorPlugin.setPluginRegistrantCallback(registerPlugins)

        return super
            .application(application,
                         didFinishLaunchingWithOptions: launchOptions)
    }
}