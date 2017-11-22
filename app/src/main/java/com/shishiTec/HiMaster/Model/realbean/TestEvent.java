package com.shishiTec.HiMaster.Model.realbean;

/**
 * Created by Pursue on 16/4/14.
 */
public class TestEvent {
    private String tag;
    private String value;

    public TestEvent(String Tag,String Value){
        tag = Tag;
        value = Value;
    }

    public String getTag() {
        return tag;
    }

    public String getValue() {
        return value;
    }

}
