package com.rysgalbank.RysgalPay;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.widget.RemoteViews;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;


public class RysqalAppWidgetProvider extends AppWidgetProvider {

  @Override
  public void onReceive(Context context, Intent intent) {
    super.onReceive(context, intent);//add this line

    if (intent.getAction() == "qr") {
      Log.d("onreceive", "^^^^^^^^^^^^^^^^^ QR");

      File file = new File(context.getFilesDir().getPath() + "/qr.txt");
      if (!file.exists()) {
        try {
          file.createNewFile();
          Log.d("onCreateFile","file created");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      Intent intent1 = new Intent(context, MainActivity.class);
      intent1.setAction("qr");
      intent1.putExtra("DataFromWidget", "qr");
      intent1.setData(Uri.parse("http://qr"));
      intent1.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent1);

    }

    if (intent.getAction() == "scan") {
      Log.d("onreceive", "^^^^^^^^^^^^^^^^^ SCAN");


      String WidgetConfigText = readFromFile(context);
      String UserType = "";
      Log.d("on Create File Scan", "onUpdate: " + WidgetConfigText);
      if (!WidgetConfigText.equals("")){
        try {
          JSONObject jsonObj = new JSONObject(WidgetConfigText);
          UserType = jsonObj.getString("UserType");
        } catch (JSONException e) {
          e.printStackTrace();
        }
        Log.d("on Create File Scan", "onUpdate: User Type: " + UserType);
      }

      if (UserType.equals("individual")) {
        File file = new File(context.getFilesDir().getPath() + "/scan.txt");
        if (!file.exists()) {
          try {
            file.createNewFile();
            Log.d("onCreateFile","file created");
          } catch (IOException e) {
            e.printStackTrace();
          }
        }

        Intent intent1 = new Intent(context, MainActivity.class);
        intent1.setAction("scan");
        intent1.putExtra("DataFromWidget", "scan");
        intent1.setData(Uri.parse("http://scan"));
        intent1.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent1);
      }
    }

    if (intent.getAction() == "index") {
      Log.d("onreceive", "^^^^^^^^^^^^^^^^^ INDEX");

      File file = new File(context.getFilesDir().getPath() + "/index.txt");
      if (!file.exists()) {
        try {
          file.createNewFile();
          Log.d("onCreateFile","file created");
        } catch (IOException e) {
          e.printStackTrace();
        }
      }

      Intent intent1 = new Intent(context, MainActivity.class);
      intent1.setAction("index");
      intent1.putExtra("DataFromWidget", "index");
      intent1.setData(Uri.parse("http://index"));
      intent1.setFlags (Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(intent1);

    }

    Timer timer = new Timer();
    timer.schedule(new TimerTask() {
      @Override
      public void run() {
        String WidgetConfigText = readFromFile(context);

        String ActiveUserID = "";
        String ActiveUserAccountID = "";
        String SessionToken = "";
        String UserType = "";
        String ShowBalance = "";
        String WidgetStyleDark = "";

//        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
//        Log.d("TAG", "run: " + WidgetConfigText);
        if (!WidgetConfigText.equals("")){
          Log.d( "Widget","!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

          try {
            JSONObject jsonObj = new JSONObject(WidgetConfigText);
            ActiveUserID = jsonObj.getString("UserId");
            ActiveUserAccountID = jsonObj.getString("UserAccountId");
            SessionToken = jsonObj.getString("Token");
            UserType = jsonObj.getString("UserType");
            ShowBalance = jsonObj.getString("ShowBalance");
            WidgetStyleDark = jsonObj.getString("WidgetDarkStyle");
          } catch (JSONException e) {
            e.printStackTrace();
          }

          ComponentName thisWidget = new ComponentName(context, RysqalAppWidgetProvider.class);
          RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.rysqal_widget);

//          views.setTextViewText(R.id.widget_horizontal_cards_info, UserType);


          if (WidgetStyleDark.equals("true")){
//            Log.d("WIDGET DARK STYLE", "1111111111111111111111111111111111");

            views.setImageViewResource(R.id.rysqal_pay, R.drawable.rysgalpaydark);
            views.setInt(R.id.rysqal_pay_txt,"setTextColor", Color.parseColor("#000000"));
            views.setImageViewResource(R.id.qrcode, R.drawable.qrcodedark);
            views.setInt(R.id.qrcode_widget_text,"setTextColor", Color.parseColor("#000000"));
            views.setImageViewResource(R.id.qrscan, R.drawable.qrscandark);
            views.setInt(R.id.qrscan_widget_text,"setTextColor", Color.parseColor("#000000"));

            views.setInt(R.id.widget_vertical_white_bar0,"setBackgroundColor", Color.parseColor("#80000000"));
            views.setInt(R.id.widget_vertical_white_bar,"setBackgroundColor", Color.parseColor("#80000000"));
            views.setInt(R.id.widget_horizontal_white_bar,"setBackgroundColor", Color.parseColor("#80000000"));

            views.setInt(R.id.widget_cards_balance_info,"setTextColor", Color.parseColor("#000000"));

//            views.setInt(R.id.widget_layout, "setBackgroundColor", Color.parseColor("#80FFFFFF"));
//            views.setBackgroundResource(R.id.widget_layout, R.drawable.shapedark);
            views.setInt(R.id.widget_layout,"setBackgroundResource", R.drawable.shapedark);

            if (UserType.equals("legalEntity") || UserType.equals("cashier")){
              Log.d("NOT INDIVIDUAL", "run: 55555555555555555555555555555555555555555");
              views.setInt(R.id.qrscan_widget_text,"setTextColor", Color.parseColor("#80000000"));
              views.setImageViewResource(R.id.qrscan, R.drawable.canceldark);
            } else {
              views.setInt(R.id.qrscan_widget_text, "setTextColor", Color.parseColor("#000000"));
              views.setImageViewResource(R.id.qrscan, R.drawable.qrscandark);
            }

          } else {
//            Log.d("WIDGET DEFAULT STYLE", "2222222222222222222222222222222222");

            views.setImageViewResource(R.id.rysqal_pay, R.drawable.rysgalpay);
            views.setInt(R.id.rysqal_pay_txt,"setTextColor", Color.parseColor("#FFFFFF"));
            views.setImageViewResource(R.id.qrcode, R.drawable.qrcode);
            views.setInt(R.id.qrcode_widget_text,"setTextColor", Color.parseColor("#FFFFFF"));
            views.setImageViewResource(R.id.qrscan, R.drawable.qrscan);
            views.setInt(R.id.qrscan_widget_text,"setTextColor", Color.parseColor("#FFFFFF"));

            views.setInt(R.id.widget_vertical_white_bar0,"setBackgroundColor", Color.parseColor("#80FFFFFF"));
            views.setInt(R.id.widget_vertical_white_bar,"setBackgroundColor", Color.parseColor("#80FFFFFF"));
            views.setInt(R.id.widget_horizontal_white_bar,"setBackgroundColor", Color.parseColor("#80FFFFFF"));

            views.setInt(R.id.widget_cards_balance_info,"setTextColor", Color.parseColor("#FFFFFF"));

//            views.setInt(R.id.widget_layout, "setBackgroundColor", Color.parseColor("#33000000"));
            views.setInt(R.id.widget_layout,"setBackgroundResource", R.drawable.shape);

            if (UserType.equals("legalEntity") || UserType.equals("cashier")){
//              Log.d("NOT INDIVIDUAL", "run: 55555555555555555555555555555555555555555");
              views.setInt(R.id.qrscan_widget_text,"setTextColor", Color.parseColor("#80FFFFFF"));
              views.setImageViewResource(R.id.qrscan, R.drawable.cancel);
            } else {
              views.setInt(R.id.qrscan_widget_text, "setTextColor", Color.parseColor("#FFFFFF"));
              views.setImageViewResource(R.id.qrscan, R.drawable.qrscan);
            }
          }

          (AppWidgetManager.getInstance(context)).updateAppWidget( thisWidget, views );
        }
      }
    }, 0 , 5000);

  }

  @Override
  public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds){

    ComponentName thisWidget = new ComponentName(context, RysqalAppWidgetProvider.class);
    RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.rysqal_widget);

    Intent intent = new Intent(context, getClass());
    intent.setAction("qr");
    intent.putExtra("DataFromWidget", "qr");
    intent.setData(Uri.parse("http://qr"));
    PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    views.setOnClickPendingIntent(R.id.QRCode_widget_button, pendingIntent);


    Intent intent1 = new Intent(context, getClass());
    intent1.setAction("scan");
    intent1.putExtra("DataFromWidget", "scan");
    intent1.setData(Uri.parse("http://scan"));
    PendingIntent pendingIntent1 = PendingIntent.getBroadcast(context, 0, intent1, PendingIntent.FLAG_UPDATE_CURRENT);
    views.setOnClickPendingIntent(R.id.QRCodeScanner_widget_button, pendingIntent1);

    Intent intent2 = new Intent(context, getClass());
    intent2.setAction("index");
    intent2.putExtra("DataFromWidget", "index");
    intent2.setData(Uri.parse("http://index"));
    PendingIntent pendingIntent2 = PendingIntent.getBroadcast(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
    views.setOnClickPendingIntent(R.id.rysqal_widget_button, pendingIntent2);

    appWidgetManager.updateAppWidget(thisWidget, views);

  }

  public String readFromFile(Context context) {

    String ret = "";

    try {
      InputStream inputStream = context.openFileInput("Config.txt");

      if (inputStream != null) {
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String receiveString = "";
        StringBuilder stringBuilder = new StringBuilder();

        while ((receiveString = bufferedReader.readLine()) != null) {
          stringBuilder.append(receiveString);
        }

        inputStream.close();
        ret = stringBuilder.toString();

        JSONObject jsonObj = new JSONObject(ret);

        Log.d("widget activity", "Founded file: " + jsonObj);
      }
    } catch (FileNotFoundException e) {
      Log.e("widget activity", "File not found: " + e.toString());
    } catch (IOException e) {
      Log.e("widget activity", "Can not read file: " + e.toString());
    } catch (JSONException e) {
      Log.e("widget activity", "JSON error: " + e.toString());
    }

    return ret;
  }

}
