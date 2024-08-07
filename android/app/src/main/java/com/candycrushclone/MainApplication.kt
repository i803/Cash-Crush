package com.cashcrush

import android.app.Application
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.load
import com.facebook.react.defaults.DefaultReactHost.getDefaultReactHost
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.soloader.SoLoader
import com.unity3d.ads.IUnityAdsListener
import com.unity3d.ads.UnityAds

class MainApplication : Application(), ReactApplication {

  override val reactNativeHost: ReactNativeHost =
      object : DefaultReactNativeHost(this) {
        override fun getPackages(): List<ReactPackage> =
            PackageList(this).packages.apply {
              // Packages that cannot be autolinked yet can be added manually here, for example:
              // add(MyReactNativePackage())
              add(UnityAdsPackage()) // Add the Unity Ads package
            }

        override fun getJSMainModuleName(): String = "index"

        override fun getUseDeveloperSupport(): Boolean = BuildConfig.DEBUG

        override val isNewArchEnabled: Boolean = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
        override val isHermesEnabled: Boolean = BuildConfig.IS_HERMES_ENABLED
      }

  override val reactHost: ReactHost
    get() = getDefaultReactHost(applicationContext, reactNativeHost)

  override fun onCreate() {
    super.onCreate()
    SoLoader.init(this, false)
    if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
      // If you opted-in for the New Architecture, we load the native entry point for this app.
      load()
    }
    // Initialize Unity Ads
    UnityAds.initialize(this, "f3be9bac-442a-4e18-bc8c-a644cce919d7", object : IUnityAdsListener {
      override fun onUnityAdsReady(placementId: String?) {
        // Called when Unity Ads is ready to show an ad.
      }

      override fun onUnityAdsStart(placementId: String?) {
        // Called when an ad starts showing.
      }

      override fun onUnityAdsFinish(placementId: String?, result: UnityAds.FinishState?) {
        // Called when an ad finishes showing.
      }

      override fun onUnityAdsError(error: UnityAds.UnityAdsError?, message: String?) {
        // Called when an error occurs with Unity Ads.
      }
    })
  }
}
