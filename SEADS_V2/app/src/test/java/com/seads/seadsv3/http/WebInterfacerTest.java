package com.seads.seadsv3.http;

import org.json.JSONObject;
import org.junit.Test;

/**
 * TODO implement this task with SuperMock
 */
public class WebInterfacerTest implements WebInterface {
    @Test
    public void test() throws Exception {
        System.out.printf("IN TEST!");
        WebInterfacer test = new WebInterfacer(this);
        test.getJSONObject(1519862400,1519948800,"energy",60,"Panel3", "P");
    }

    @Override
    public void onJSONRetrieved(JSONObject result) {
        System.out.printf("Test success");
    }
}