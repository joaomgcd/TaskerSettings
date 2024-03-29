# TaskerSettings
Helper app for Tasker

**Important Note**: Android 14+ doesn't allow Tasker Settings to be installed normally. You have to install it via ADB using the following command:

```adb install --bypass-low-target-sdk-block FILENAME.apk``` 

Unfortunately there's nothing I can do to change that.

You can setup ADB on your PC as described [here](https://tasker.joaoapps.com/userguide/en/help/ah_adb_setup.html).

**Note:** If it gives you a permission error, make sure to enable developer options and then disable the **Check apps installed via USB** option.

**Note 2**: For reasons unknown, some antivirus programs may erroneously flag Tasker Settings as containing a virus or trojan. However, this is not the case. Tasker Settings is an open-source app, enabling you to scrutinize its code and functionality. If you opt not to install it, that decision is entirely yours to make. Rest assured, there is no actual threat. 😅


## Wifi Toggling
Google has changed the way Android works for apps that target API 29, so Tasker can't toggle wifi anymore. Check here for more details: https://issuetracker.google.com/issues/128554616 

If you want to better understand what "targeting" an API means, check here: https://tasker.joaoapps.com/userguide/en/target_api.html 
To get Wifi toggling to work again please install this app. Download here: https://github.com/joaomgcd/TaskerSettings/releases/download/v1.2.0/TaskerSettings.apk

Make sure to exempt the "Tasker Settings" app (not only Tasker itself) from battery optimization (https://tasker.joaoapps.com/userguide/en/faqs/faq-problem.html#00) so that Tasker can call it in the background. Make sure to check here too for how to exempt it from battery optimization: https://dontkillmyapp.com/

Even if you already have the "Tasker Settings" app installed from Google Play, you need to install this new version for the Wifi toggle to work.

I can't upload this version to Google Play because it targets API 21 (which is why toggling Wifi still works with it) and Google only allows apps that target API 29 and over.

Note: your phone may warn you that this app is built for an older Android version. That's normal (and precisely why this app can still toggle wifi), so please dismiss the warning because it's nothing to worry about.

Download here: https://github.com/joaomgcd/TaskerSettings/releases/download/v1.2.0/TaskerSettings.apk

## Wifi Connecting
Forcing connection to a certain WiFi Network used to be a feature in [AutoTools](https://play.google.com/store/apps/details?id=com.joaomgcd.autotools). Since AutoTools is now required to target API 29 (read above for more info about API targets) it can't do that anymore.

This feature was now added to Tasker itself in the **Connect to Wifi** action and Tasker uses the **Tasker Settings** helper app to do this.

If you want to use the **Connect To Wifi** action in Tasker download this version: https://github.com/joaomgcd/TaskerSettings/releases/download/v1.3.0/TaskerSettings.apk

**Important Note**: on some devices the app will ask you for location permissions, but when you try to grant them the system app will crash. To work around this go to Android Settings > Apps > **Tasker Settings** > Permissions > Location and enable the permission. It should correctly after you do that.

## Run Shell
Since Tasker started targeting API 30 some shell commands (like "ip neigh") stopped working.

With version 1.4.0 of **Tasker Settings** you can get that working again by selecting the **Use Tasker Settings** option in the **Run Shell** action in Tasker.

Use

- **Tasker 5.15.6** or above
- **Tasker Settings 1.4.0** or above

Download Tasker Settings 1.4.0 here: https://github.com/joaomgcd/TaskerSettings/releases/download/v1.4.0/Tasker.Settings.1.4.0.apk


## Open File action for APK files
On November 2022 Google blocked Tasker from having the **android.permission.REQUEST_INSTALL_PACKAGES** permission on Google Play which means that Tasker wasn't able to request installation of APK files anymore using the **Open File** action like before.

With version 1.5.0 of **Tasker Settings** you can get that working again because Tasker will now relay this function to it.

Use

- **Tasker 6.1.7-beta** or above
- **Tasker Settings 1.5.0** or above

Download Tasker Settings 1.5.0 here: https://github.com/joaomgcd/TaskerSettings/releases/download/v1.5.0/Tasker.Settings.1.5.0.apk

## Toggle Bluetooth
On May 2023 Tasker had to update its [Target API](https://tasker.joaoapps.com/userguide/en/target_api.html) to 33 blocking it from being able to toggle Bluetooth on your device. This is expected behaviour as documented [here](https://developer.android.com/reference/android/bluetooth/BluetoothAdapter#enable()).

Version 1.6.0 of **Tasker Settings** works around that by having Tasker relaying that feature to it. Since **Tasker Settings** still targets a lower Target API it can still perform the action normally.

Use

- **Tasker 6.2.2-beta** or above
- **Tasker Settings 1.6.0** or above

Download Tasker Settings 1.6.0 here: https://github.com/joaomgcd/TaskerSettings/releases/download/v.1.6.0/Tasker.Settings.1.6.0.apk

