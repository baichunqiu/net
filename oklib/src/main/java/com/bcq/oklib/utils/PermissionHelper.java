package com.bcq.oklib.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

import java.util.ArrayList;

public class PermissionHelper {
    public final static int REQUEST_CODE = 1000;

//    group:android.permission-group.CONTACTS
//    permission:android.permission.WRITE_CONTACTS
//    permission:android.permission.GET_ACCOUNTS
//    permission:android.permission.READ_CONTACTS
//
//    group:android.permission-group.PHONE
//    permission:android.permission.READ_CALL_LOG
//    permission:android.permission.READ_PHONE_STATE
//    permission:android.permission.CALL_PHONE
//    permission:android.permission.WRITE_CALL_LOG
//    permission:android.permission.USE_SIP
//    permission:android.permission.PROCESS_OUTGOING_CALLS
//    permission:com.android.voicemail.permission.ADD_VOICEMAIL
//
//    group:android.permission-group.CALENDAR
//    permission:android.permission.READ_CALENDAR
//    permission:android.permission.WRITE_CALENDAR
//
//    group:android.permission-group.CAMERA
//    permission:android.permission.CAMERA
//
//    group:android.permission-group.SENSORS
//    permission:android.permission.BODY_SENSORS
//
//    group:android.permission-group.LOCATION
//    permission:android.permission.ACCESS_FINE_LOCATION
//    permission:android.permission.ACCESS_COARSE_LOCATION
//
//    group:android.permission-group.STORAGE
//    permission:android.permission.READ_EXTERNAL_STORAGE
//    permission:android.permission.WRITE_EXTERNAL_STORAGE
//
//    group:android.permission-group.MICROPHONE
//    permission:android.permission.RECORD_AUDIO

//    group:android.permission-group.SMS
//    permission:android.permission.READ_SMS
//    permission:android.permission.RECEIVE_WAP_PUSH
//    permission:android.permission.RECEIVE_MMS
//    permission:android.permission.RECEIVE_SMS
//    permission:android.permission.SEND_SMS
//    permission:android.permission.READ_CELL_BROADCASTS
    public final static String[] PERMISSIONS = new String[]{
            Manifest.permission.READ_CALENDAR,//日历
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.CAMERA,//相机
            Manifest.permission.READ_CONTACTS,//联系人
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.LOCATION_HARDWARE,//定位
            Manifest.permission.RECORD_AUDIO,//麦克相关

            Manifest.permission.CALL_PHONE,//手机状态
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.BODY_SENSORS, //传感器
            Manifest.permission.READ_EXTERNAL_STORAGE, //存储权限
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_SMS,//短信
            Manifest.permission.SEND_SMS,
    };

    public static void checkPermissions(Activity activity, String[] permissions) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                ArrayList<String> requestPerssions = new ArrayList<>();
                int len = permissions.length;
                for (String permission : permissions) {
                    if (PackageManager.PERMISSION_GRANTED != activity.checkSelfPermission(permission)) {
                        requestPerssions.add(permission);
                    }
                }
                int size = requestPerssions.size();
                if (size > 0) {
                    activity.requestPermissions(requestPerssions.toArray(new String[size]), REQUEST_CODE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void checkPermission(Activity activity, String permission, int requestCode) {
        try {
            if (Build.VERSION.SDK_INT >= 23) {
                if (PackageManager.PERMISSION_GRANTED != activity.checkSelfPermission(permission)) {
                    activity.requestPermissions(new String[]{permission}, requestCode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取拒绝权限
     * @param context
     * @param permissions
     * @return
     */
    public static String[] getDeniedPermissions(Context context, String[] permissions){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> deniedPermissionList = new ArrayList<>();
            for(String permission : permissions){
                if(context.checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED){
                    deniedPermissionList.add(permission);
                }
            }
            int size = deniedPermissionList.size();
            if(size > 0){
                return deniedPermissionList.toArray(new String[size]);
            }
        }
        return null;
    }
}
