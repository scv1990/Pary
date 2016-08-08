/**
 * 项目名称: 七七同城
 * 
 * 文件名称: DataFactory.java
 * 
 * Copyright: 2015 合肥以撒网络 Inc. All rights reserved.
 */

package com.yisa.pray.utils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
/**
 *
 * 类名称: DataFactory.java
 * 类描述:	 
 * 创建人:  hq
 * 创建时间: 2016年8月8日下午5:54:45
 * -------------------------修订历史------------
 * 修改人:
 * 修改时间:
 * 修改备注:
 */

public class DataFactory
{
    public static Object getInstanceByJson(Class<?> clazz, String json)
    {
        Object obj = null;
        Gson gson = new Gson();
        obj = gson.fromJson(json, clazz);
        return obj;
    }

    /**
     * @author I321533
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> jsonToList(String json, Class<T[]> clazz)
    {
        Gson gson = new Gson();
        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }

    /**
     * @param json
     * @param clazz
     * @return
     */
    public static <T> ArrayList<T> jsonToArrayList(String json, Class<T> clazz)
    {
        Type type = new TypeToken<ArrayList<JsonObject>>()
        {}.getType();
        ArrayList<JsonObject> jsonObjects = new Gson().fromJson(json, type);

        ArrayList<T> arrayList = new ArrayList<T>();
        for (JsonObject jsonObject : jsonObjects)
        {
            arrayList.add(new Gson().fromJson(jsonObject, clazz));
        }
        return arrayList;
    }
}
