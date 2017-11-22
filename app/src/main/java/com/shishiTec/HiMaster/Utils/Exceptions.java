package com.shishiTec.HiMaster.Utils;

/**
 * Created by Pursue on 16/4/21.
 */
public class Exceptions {
    public static void illegalArgument(String msg, Object... params)
    {
        throw new IllegalArgumentException(String.format(msg, params));
    }

}
