# WorkManagerWidgetUpdateTest

This is compilable project illustrating behavior of Work Magager that update from Work Manager causes update of the widget every 5 or 10 seconds. 

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
``
