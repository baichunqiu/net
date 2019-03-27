package com.bcq.oklib;


import com.bcq.oklib.net.domain.DefauParser;
import com.bcq.oklib.net.Processor;
import com.bcq.oklib.net.Parser;

import java.io.File;

public class OKHelper {
    private static Parser parser;
    private static Processor processor;
    private static String ROOT_NAME = Constant.ROOT_NAME;

    public static void setBasePath(String rootName) {
        ROOT_NAME = rootName;
    }

    public static String getBasePath(){
        return Constant.ROOT + ROOT_NAME + File.separator;
    }
    /**
     * 设置默认Json解析器
     * @param defaultParser
     */
    public static void setDefaultParser(Parser defaultParser) {
        OKHelper.parser = defaultParser;
    }

    public static Parser getParser() {
        if (null == parser) {//使用默认解析器
            parser = new DefauParser();
        }
        return parser;
    }

    /**
     * 设置错误处理器
     * @param processor
     */
    public static void setProcessor(Processor processor) {
        OKHelper.processor = processor;
    }

    public static Processor getProcessor() {
        return processor;
    }
}
