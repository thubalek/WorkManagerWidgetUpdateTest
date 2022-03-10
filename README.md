# WorkManagerWidgetUpdateTest

This is compilable project illustrating behavior of Work Magager that update from Work Manager causes update of the widget every a few seconds. 

There is super simple function [updateWidget](app/src/main/java/com/example/workmanagerwidgets/UpdateWidget.kt) that updates number of calls on widget every time widget update is called.

Calling this function from [AppWidget](app/src/main/java/com/example/workmanagerwidgets/AppWidget.kt) works as expected. 

Calling the same function from [AppWidgetUpdateWorker](app/src/main/java/com/example/workmanagerwidgets/AppWidgetUpdateWorker.kt) causes that widget update is called every 5 to 10 seconds.

It looks like `WorkManager` is somehow causing `android.intent.action.PACKAGE_CHANGED` as I see following lines in `logcat`:

```
2022-03-10 11:51:10.479 5436-28964/? I/Fitness: OnPackageChangedOperation got intent: Intent { act=android.intent.action.PACKAGE_CHANGED dat=package:com.example.workmanagerwidgets flg=0x45000010 pkg=com.google.android.gms cmp=com.google.android.gms/.chimera.PersistentIntentOperationService (has extras) } [CONTEXT service_id=17 ]
2022-03-10 11:51:11.506 5436-28941/? I/Fitness: OnPackageChangedOperation got intent: Intent { act=android.intent.action.PACKAGE_CHANGED dat=package:com.example.workmanagerwidgets flg=0x45000010 pkg=com.google.android.gms cmp=com.google.android.gms/.chimera.PersistentIntentOperationService (has extras) } [CONTEXT service_id=17 ]
2022-03-10 11:51:12.588 5436-28941/? I/Fitness: OnPackageChangedOperation got intent: Intent { act=android.intent.action.PACKAGE_CHANGED dat=package:com.example.workmanagerwidgets flg=0x45000010 pkg=com.google.android.gms cmp=com.google.android.gms/.chimera.PersistentIntentOperationService (has extras) } [CONTEXT service_id=17 ]
2022-03-10 11:51:13.687 5436-28941/? I/Fitness: OnPackageChangedOperation got intent: Intent { act=android.intent.action.PACKAGE_CHANGED dat=package:com.example.workmanagerwidgets flg=0x45000010 pkg=com.google.android.gms cmp=com.google.android.gms/.chimera.PersistentIntentOperationService (has extras) } [CONTEXT service_id=17 ]
2022-03-10 11:51:14.705 5436-28761/? I/Fitness: OnPackageChangedOperation got intent: Intent { act=android.intent.action.PACKAGE_CHANGED dat=package:com.example.workmanagerwidgets flg=0x45000010 pkg=com.google.android.gms cmp=com.google.android.gms/.chimera.PersistentIntentOperationService (has extras) } [CONTEXT service_id=17 ]
2022-03-10 11:51:15.903 5436-28986/? I/Fitness: OnPackageChangedOperation got intent: Intent { act=android.intent.action.PACKAGE_CHANGED dat=package:com.example.workmanagerwidgets flg=0x45000010 pkg=com.google.android.gms cmp=com.google.android.gms/.chimera.PersistentIntentOperationService (has extras) } [CONTEXT service_id=17 ]
2022-03-10 11:51:16.967 5436-28761/? I/Fitness: OnPackageChangedOperation got intent: Intent { act=android.intent.action.PACKAGE_CHANGED dat=package:com.example.workmanagerwidgets flg=0x45000010 pkg=com.google.android.gms cmp=com.google.android.gms/.chimera.PersistentIntentOperationService (has extras) } [CONTEXT service_id=17 ]
```

# Explanation
After asking at ASG Slack I got [following explanation](https://androidstudygroup.slack.com/archives/CAYMXJE0H/p1646925106996509):
> Using a Worker with a widget is something that requires a bit more hand holding given the way we disable constraint trackers when there is no more pending work. Constraint trackers are broadcast receivers we use to track things like Network availability, battery etc. We try and disable them when there us no more pending work that requires their use.
> One of the side effects of enabling and disabling these broadcast receivers is that an AppWidget.update(...) gets triggered. This is why when you schedule a single request in update() this becomes recursive given a subsequent update schedules a new work request and so on.
To work around this problem. I would recommend creating a sentinel work request with a KEEP policy which is far out into the future (eg a 10 year initial delay). That way you always have a pending work request and we won't enable and disable receivers that aggressively.
