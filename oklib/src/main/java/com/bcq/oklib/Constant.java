package com.bcq.oklib;

import android.os.Environment;

import java.io.File;

/**
 * @author: BaiCQ
 * @ClassName: NetConstant
 * @date: 2018/8/17
 * @Description: NetConstant 公共常量值
 */
public class Constant {
    public final static String ACTION_APP_EXIT = "_org_app_exit";
    /*****************存储路径 path*****************/
    public final static String ROOT_NAME = "oklib";
    public final static String ROOT = Environment.getExternalStorageDirectory() + File.separator;
    public final static String BASE_PATH = ROOT + ROOT_NAME + File.separator;

    /***************** Intent key *****************/
    public final static String KEY_BASE = "key_basis";
    public final static String KEY_OBJ = "key_obj";

    //页码sizek
    public final static String pageSize = "per_page";
    //当前页的索引key
    public final static String pageIndex = "page";
}
