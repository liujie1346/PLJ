package com.example.util;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @descriptions <p>全局配置类</p>
 */
public final class Global {

    /**
     * 默认字符集
     */
    public final static Charset Charset = StandardCharsets.UTF_8;

    /**
     * 空字符串
     */
    public final static String Empty = StringUtils.EMPTY;

    /**
     * 空数据对象
     */
    public final static Object EmptyObject = new Object();

    /**
     * 空列表数据对象
     */
    public final static List<Object> EmptyListObject = new ArrayList<>();

}
