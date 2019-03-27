package com.bcq.oklib.net;

import com.bcq.oklib.net.domain.NetInfo;
import com.google.gson.JsonObject;

/**
 * @author: BaiCQ
 * @ClassName: NetInfo
 * @date: 2018/6/27
 * @Description: Parser 公共信息解析器
 */
public interface Parser {
    NetInfo parse(JsonObject resulObj);
}
