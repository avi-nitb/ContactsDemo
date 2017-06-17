package com.contactsdemo;

import android.app.Activity;
import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MyApp extends Application {

    private static ProgressDialog dialog;
    public static String HEIGHT = "HEIGHT";
    public static String WIDTH = "WIDTH";
    public static String SHARED_PREF_NAME = "RS_PREF";
    private static final String KEYSERVERID = "keyserverid";
    private static Context ctx;
    private static MyApp myApplication = null;
    public static String APPNAME = "bookhop";

    public String getUser_id() {
        return file.getString(MyApp.SP_USER_ID, "");
    }

    public static String SP_USER_ID = "user_id";

    public static void sleep(long timeMillis) {
        try {
            Thread.sleep(timeMillis);
        } catch (InterruptedException e) {
            throw new AssertionError(e);
        }
    }

    public String getCurrentchat() {
        return currentchat;
    }

    public void setCurrentchat(String currentchat) {
        this.currentchat = currentchat;
    }

    public String currentchat = "";

    public Context getCurrentActivity() {
        return currentActivity;
    }

    public void setCurrentActivity(Context currentActivity) {
        this.currentActivity = currentActivity;
    }

    public Context currentActivity = null;
    //    ArrayList<SelectModel> topic_name_arr = new ArrayList<>();
    public static SharedPreferences file;


    public static String getDate(long milliSeconds) {
        // Create a DateFormatter object for displaying date in specified
        // format.
        SimpleDateFormat formatter = new SimpleDateFormat("hh:mm a");

        // Create a calendar object that will convert the date and time value in
        // milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }

    @Override
    public void onLowMemory() {
        Runtime.getRuntime().gc();
        super.onLowMemory();
    }

    public static String SP_USER_NAME = "user_name";
    public static String SP_XMPP_PASSWORD = "xmpp_password";

    public static String SP_NOTIFICATION_SOUND = "notification_sound";
    public static String SP_NOTIFICATION_VIBRATE = "notification_vibrate";

    @Override
    public void onCreate() {
        super.onCreate();
        ctx = getApplicationContext();
        myApplication = this;
//        FacebookSdk.sdkInitialize(getApplicationContext());
//        AppEventsLogger.activateApp(this);
        file = ctx.getSharedPreferences(APPNAME, MODE_PRIVATE);

    }

    public static MyApp getApplication() {
        return myApplication;
    }

    private Contacts currentContact;

    public Contacts getCurrentContact() {
        return currentContact;
    }

    public void setCurrentContact(Contacts currentContact) {
        this.currentContact = currentContact;
    }

    public static String parseDateToddMMyyyy(String time) {
        String inputPattern = "dd-MM-yyyy";
        String outputPattern = "dd-MMM-yyyy";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern);

        Date date = null;
        String str = null;

        try {
            date = inputFormat.parse(time);
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }

    private static String capitalize(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }
        char first = s.charAt(0);
        if (Character.isUpperCase(first)) {
            return s;
        } else {
            return Character.toUpperCase(first) + s.substring(1);
        }
    }

    private static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        } else {
            return capitalize(manufacturer) + " " + model;
        }
    }


    public static void spinnerStart(Context context, String text) {
        String pleaseWait = text;
        dialog = ProgressDialog.show(context, "", pleaseWait, true);

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
    }

    public static void spinnerStop() {
        if (dialog != null) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
        }

    }

    public static boolean isConnectingToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }


    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
                return false;
            }

            return locationMode != Settings.Secure.LOCATION_MODE_OFF;

        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }


    }


    public static void popMessage(String titleMsg, String errorMsg,
                                  Context context) {
        // pop error message
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setTitle(titleMsg).setMessage(errorMsg)
                .setPositiveButton("OK", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void popMessageFinishable(String titleMsg, String errorMsg,
                                            final Activity context) {
        // pop error message
        AlertDialog.Builder builder = new AlertDialog.Builder(context, android.R.style.Theme_Material_Light_Dialog_Alert);
        builder.setTitle(titleMsg).setMessage(errorMsg)
                .setPositiveButton("OK", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        context.finish();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

    public static void showMassage(Context ctx, String msg) {
        Toast.makeText(ctx, msg, Toast.LENGTH_SHORT).show();
    }

    public static final String DISPLAY_MESSAGE_ACTION = "pushnotifications.DISPLAY_MESSAGE";
    public static final String EXTRA_MESSAGE = "message";

    public static void displayMessage(Context context, String message) {
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }

    public static long getSharedPrefLong(String preffConstant) {
        long longValue = 0;
        SharedPreferences sp = myApplication.getSharedPreferences(
                SHARED_PREF_NAME, 0);
        longValue = sp.getLong(preffConstant, 0);
        return longValue;
    }

    public static void setSharedPrefLong(String preffConstant, long longValue) {
        SharedPreferences sp = myApplication.getSharedPreferences(
                SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(preffConstant, longValue);
        editor.commit();
    }

    public static String getSharedPrefString(String preffConstant) {
        String stringValue = "";
        SharedPreferences sp = myApplication.getSharedPreferences(
                SHARED_PREF_NAME, 0);
        stringValue = sp.getString(preffConstant, "");
        return stringValue;
    }

    public static void setSharedPrefString(String preffConstant,
                                           String stringValue) {
        SharedPreferences sp = myApplication.getSharedPreferences(
                SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(preffConstant, stringValue);
        editor.commit();
    }

    public static int getSharedPrefInteger(String preffConstant) {
        int intValue = 0;
        SharedPreferences sp = myApplication.getSharedPreferences(
                SHARED_PREF_NAME, 0);
        intValue = sp.getInt(preffConstant, 0);
        return intValue;
    }

    public static void setSharedPrefInteger(String preffConstant, int value) {
        SharedPreferences sp = myApplication.getSharedPreferences(
                SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(preffConstant, value);
        editor.commit();
    }

    public static float getSharedPrefFloat(String preffConstant) {
        float floatValue = 0;
        SharedPreferences sp = myApplication.getSharedPreferences(
                preffConstant, 0);
        floatValue = sp.getFloat(preffConstant, 0);
        return floatValue;
    }

    public static void setSharedPrefFloat(String preffConstant, float floatValue) {
        SharedPreferences sp = myApplication.getSharedPreferences(
                preffConstant, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(preffConstant, floatValue);
        editor.commit();
    }

    public static void setSharedPrefArray(String preffConstant, float floatValue) {
        SharedPreferences sp = myApplication.getSharedPreferences(
                preffConstant, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat(preffConstant, floatValue);
        editor.commit();
    }

    public static boolean getStatus(String name) {
        boolean status;
        SharedPreferences sp = myApplication.getSharedPreferences(
                SHARED_PREF_NAME, 0);
        status = sp.getBoolean(name, false);
        return status;
    }

    public static void setStatus(String name, boolean istrue) {
        SharedPreferences sp = myApplication.getSharedPreferences(
                SHARED_PREF_NAME, 0);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(name, istrue);
        editor.commit();
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity
                .getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus()
                .getWindowToken(), 0);
    }

    public static Bitmap getimagebitmap(String imagepath) {
        Bitmap bitmap = decodeFile(new File(imagepath));

        // rotate bitmap
        Matrix matrix = new Matrix();
        // matrix.postRotate(MyApplication.getExifOrientation(imagepath));
        // create new rotated bitmap
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                bitmap.getHeight(), matrix, true);

        return bitmap;
    }

    private static Bitmap decodeFile(File F) {
        try {
            // Decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(F), null, o);

            // The new size we want to scale to
            final int REQUIRED_SIZE = 204;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE
                    && o.outHeight / scale / 2 >= REQUIRED_SIZE)
                scale *= 2;

            // Decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(F), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }

    public static String getDeviceId() {

        String android_id = "";
        final TelephonyManager tm = (TelephonyManager) ctx
                .getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = ""
                + Settings.Secure.getString(
                ctx.getContentResolver(),
                Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(),
                ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        android_id = deviceUuid.toString();
        return android_id;

    }

    public static int getDisplayWidth() {
        WindowManager wm = (WindowManager) ctx
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        return width;
    }

    public static int getDisplayHeight() {
        WindowManager wm = (WindowManager) ctx
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int height = size.y;
        return height;
    }

    public static boolean isEmailValid(String email) {
        String regExpn = "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if (matcher.matches())
            return true;
        else
            return false;
    }

    public static Date DateInGtmFormate(String date) {
        String d = date;
        Date date1 = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE - MMMM dd,yyyy");
        try {
            date1 = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date1;
    }


    public static String fullDateByMilliseconds(long mills) {

        Date d = new Date(mills);
        String format = "dd-MM-yyyy kk-mm-ss";
        return new SimpleDateFormat(format).format(d);
    }

    public static String dateByFormateMMMddyyyy(long mills) {

        Date d = new Date(mills);
        String format = "MMMM dd, yyyy";
        return new SimpleDateFormat(format).format(d);
    }

    public static String dateAssignment(long mills) {

        Date d = new Date(mills);
        String format = "EEEE - MMMM dd, yyyy";
        return new SimpleDateFormat(format).format(d);
    }

    public static String millsToDate(long mills) {

        Date d = new Date(mills);
        String format = "dd-MM-yyyy";
        return new SimpleDateFormat(format).format(d);
    }

    public static String dateOfCalendarEvents(long mills) {

        Date d = new Date(mills);
        String format = "yyyy-MM-dd";
        return new SimpleDateFormat(format).format(d);
    }

    public static String millsToTime(long mills) {

        Date d = new Date(mills);
        String format = "kk:mm";
        return new SimpleDateFormat(format).format(d);
    }

    public static String millsToDateDay(long mills) {

        Date d = new Date(mills);
        String format = "dd.EEE";
        String dateString = new SimpleDateFormat(format).format(d);
//		double hr = Double.parseDouble(dateString.split(":")[0]);
//		double min = Double.parseDouble(dateString.split(":")[1]);
        return dateString;
    }

    public static double millsToDayTime(long mills) {

        Date d = new Date(mills);
        String format = "kk:mm";
        String dateString = new SimpleDateFormat(format).format(d);
        double hr = Double.parseDouble(dateString.split(":")[0]);
        double min = Double.parseDouble(dateString.split(":")[1]);
        return hr + (min / 100d);
    }

    public static String millsToDayTimeAMPM(long mills) {

        Date d = new Date(mills);
        String format = "hh:mmaa";
        String dateString = new SimpleDateFormat(format).format(d);
//		double hr = Double.parseDouble(dateString.split(":")[0]);
//		double min = Double.parseDouble(dateString.split(":")[1]);
        return dateString;
    }

    private static final int _A_SECOND = 1000;
    /**
     * One minute (in milliseconds)
     */
    private static final int _A_MINUTE = 60 * _A_SECOND;
    /**
     * One hour (in milliseconds)
     */
    private static final int _AN_HOUR = 60 * _A_MINUTE;
    /**
     * One day (in milliseconds)
     */
    private static final int _A_DAY = 24 * _AN_HOUR;
    public String getTimeAgo(long time, Context context) {
        if (time < 1000000000000L)
            // if timestamp given in seconds, convert to millis
            time *= 1000;

        final long now = new Date().getTime();// getCurrentTime(context);
        if (time > now || time <= 0)
            return "";

        final Resources res = context.getResources();
        final long time_difference = now - time;
        if (time_difference < _A_MINUTE)
            return res.getString(R.string.just_now);
        else if (time_difference < 60 * _A_MINUTE)
            return res.getString(R.string.time_ago, res.getQuantityString(
                    R.plurals.minutes, (int) time_difference / _A_MINUTE,
                    (int) time_difference / _A_MINUTE));
        else if (time_difference < 24 * _AN_HOUR)
            return res.getString(R.string.time_ago, res.getQuantityString(
                    R.plurals.hours, (int) time_difference / _AN_HOUR,
                    (int) time_difference / _AN_HOUR));
        else if (time_difference < 48 * _AN_HOUR)
            return res.getString(R.string.yesterday);
        else
            return res.getString(
                    R.string.time_ago,
                    res.getQuantityString(R.plurals.days, (int) time_difference
                            / _A_DAY, (int) time_difference / _A_DAY));
    }

    public void writeMessages(List<Contacts> user) {
        try {
            String path = "/data/data/" + ctx.getPackageName()
                    + "/contacts.ser";
            File f = new File(path);
            if (f.exists()) {
                f.delete();
            }
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(user);
            out.close();
            fileOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Contacts> readMessages() {
        String path = "/data/data/" + ctx.getPackageName() + "/contacts.ser";
        File f = new File(path);
        List<Contacts> user = new ArrayList<>();
        if (f.exists()) {
            try {
                System.gc();
                FileInputStream fileIn = new FileInputStream(path);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                user = (List<Contacts>) in.readObject();
                in.close();
                fileIn.close();
            } catch (StreamCorruptedException e) {
                e.printStackTrace();
            } catch (OptionalDataException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return user;
    }

}
