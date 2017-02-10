# DevAlert
This lightweight library provides visual alerts to developers and QA when an issue happens during development/testing phase.

In traditional android development, when an issue occurs, we use logs to dump the unexpected state or exception trace. Printing to logcat is not enough at times as:

-  Logs can be overlooked by developers if we are not constantly monitoring.
-  Logs maybe on a remote device which is inaccessible.

Using this library, you can provide visual warning to the developer/ QA when something goes wrong on your test or internal builds so that critical issues can be highlighted as and when they happen.

In the example below, the `ParseException` may occur only for some specific response from server and is not always reproducible.


    try {
      Data data = parser.parse(response.body().json());
    } catch(ParseException ex) {
      Log.d(TAG, ex);
      DevAlert.reportError(TAG, "Response data is corrupt", ex);
    }

While development or testing, when the issue happens, even if the developer/QA is not actively looking or monitoring the log for this issue, will get instantly notified.


![](https://d2mxuefqeaa7sj.cloudfront.net/s_ABDF4F424136B8B0F9673254AE6B8A1466ED84424CC71F8A757B150C498F59F9_1486712407990_output.gif)


How to Use

Initialise the library in the `Application` class:


    DevAlert.init(getApplication(), BuildConfig.DEBUG);

Report errors or warning from anywhere in the app:

    // report error
    DevAlert.reportError(TAG, message, exception);

    // report warning
    DevAlert.reportWarning(TAG, message, exception);

Other Possible Use-Cases


1. Report exceptions from background threads without crashing the app.

2. If you are building a library or SDK, you can provide better feedback to other developers who are going to use it. (If you have worked with React-Native, it provides a similar functionality.)

3. Create custom rules for the health check of your app and visually alert the developer/QA when these rules are violated. For example: database calls on UI thread or adding heavy code in onCreate.

Advance Usage
You can configure the library to show selective errors or warnings use the `DevAlertConfig:`


    DevAlertConfig config = new DevAlertConfig.Builder()
          .showErrors(true)
          .showWarnings(false) // hide warnings
          .ignoredTags(Arrays.asList("SUB_MODULE_1", "SUB_MODULE_2")) // hide these tags
          .build();
    DevAlert.init(getApplication(), isDevMode, config);
