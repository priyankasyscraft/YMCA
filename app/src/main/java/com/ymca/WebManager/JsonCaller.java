package com.ymca.WebManager;

/**
 * Created by Soni on 17-Aug-16.
 */
public class JsonCaller {

    private static final String TAG = "JSONFragment";
    //region Singleton method.......
    private static JsonCaller mInstance = null;

    public static JsonCaller getInstance() {
        if (mInstance == null) {
            mInstance = new JsonCaller();
        }
        return mInstance;
    }

    //endregion Single tone........
    private JsonCaller() {

    }

}
