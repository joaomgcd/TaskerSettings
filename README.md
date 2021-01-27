# TaskerSettings
Helper app for Tasker

Google has changed the way Android works for apps that target API 29, so Tasker can't toggle wifi anymore. Check here for more details: https://issuetracker.google.com/issues/128554616 

If you want to better understand what "targeting" an API means, check here: https://tasker.joaoapps.com/userguide/en/target_api.html 
To get Wifi toggling to work again please install this app. Download here: https://github.com/joaomgcd/TaskerSettings/releases/download/v1.2.0/TaskerSettings.apk

Make sure to exempt the "Tasker Settings" app (not only Tasker itself) from battery optimization (https://tasker.joaoapps.com/userguide/en/faqs/faq-problem.html#00) so that Tasker can call it in the background. Make sure to check here too for how to exempt it from battery optimization: https://dontkillmyapp.com/

Even if you already have the "Tasker Settings" app installed from Google Play, you need to install this new version for the Wifi toggle to work.

I can't upload this version to Google Play because it targets API 21 (which is why toggling Wifi still works with it) and Google only allows apps that target API 29 and over.

Note: your phone may warn you that this app is built for an older Android version. That's normal (and precisely why this app can still toggle wifi), so please dismiss the warning because it's nothing to worry about.

Download here: https://github.com/joaomgcd/TaskerSettings/releases/download/v1.2.0/TaskerSettings.apk
