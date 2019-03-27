package com.bcq.oklib.net.domain;

import com.bcq.oklib.net.Parser;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

/**
 * @author: BaiCQ
 * @ClassName: DefauParser
 * @date: 2018/8/17
 * @Description: 默认解析器
 */
public class DefauParser implements Parser {
    @Override
    public NetInfo parse(JsonObject resulObj) {
        // TODO: 2018/8/17 根据自己的json格式 修改
        NetInfo netInfo = new NetInfo();
        netInfo.setStatus(resulObj.get("code").getAsInt());
        netInfo.setSysMsg(resulObj.get("codemsg").getAsString());
        JsonElement jsonElement = resulObj.get("result");
        String body = null;
        if (jsonElement.isJsonArray()) {//JsonArray 不包含list节点
            body = jsonElement.getAsJsonArray().toString();
        } else if (jsonElement.isJsonObject()) {//obj
            JsonObject dataObj = jsonElement.getAsJsonObject();
            JsonElement listObj = dataObj.get("results");
            if (null != listObj) {//包含分页 页码
                body = listObj.getAsJsonArray().toString();
            } else {
                body = dataObj.toString();
            }
        }
        netInfo.setBody(body);
        return netInfo;
    }
}
