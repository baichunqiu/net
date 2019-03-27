package com.bcq.oklib.net.utils;


import com.bcq.oklib.utils.Logger;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;


import java.util.ArrayList;
import java.util.List;


/**
 * @author: BaiCQ
 * @ClassName: GsonUtil
 * @Description: Gson解析相关工具类
 */
public class GsonUtil {
    // Gson实体
    private static Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS").create();
    /**
     * JSON串解析成集合
     * @param json  待解析的json串
     * @param clazz 字节码文件
     * @param <T>
     * @throws Exception
     */
    public static <T> List<T> json2List(String json, Class<T> clazz) {
        if (null == clazz){
            Logger.e("GsonUtil","the clazz can not null!");
            return null;
        }
        List<T> lst = new ArrayList<T>();
        try {
            JsonElement jsonElement = new JsonParser().parse(json);
            if (jsonElement instanceof JsonArray) {//兼融list
                JsonArray array = jsonElement.getAsJsonArray();
                for (JsonElement elem : array) {
                    lst.add(new Gson().fromJson(elem, clazz));
                }
            } else if (jsonElement instanceof JsonObject){//obj
                lst.add(gson.fromJson(json, clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lst;
    }

    /**
     * JSON串解析成集合
     * @param jsonArray  待解析的json串
     * @param clazz 字节码文件
     * @param <T>
     * @throws Exception
     */
    public static <T> List<T> jsonArr2List(JsonArray jsonArray, Class<T> clazz) {
        List<T> lst =  new ArrayList<T>();
        try {
            for(JsonElement elem : jsonArray) {
                lst.add(new Gson().fromJson(elem, clazz));
            }
        }catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return lst;
    }
    /**
     *  JSON串解析成实体Bean
     * @param json  待解析的json串
     * @param cls   对应实体Bean的class类型
     * @param <T>   泛型
     * @return      实体Bean
     */
    public static <T> T json2Object(String json,Class<T> cls) {
        return gson.fromJson(json,cls);
    }

    public static String obj2Json(Object object) {
        if (null == object){
            return "";
        }
        return gson.toJson(object);
    }

}
