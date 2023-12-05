package com.jnu.android_test_demo.data;

import junit.framework.TestCase;

import org.junit.Assert;

import java.util.ArrayList;

public class DataDownloadTest extends TestCase {

    // run before every test
    public void setUp() throws Exception {
        super.setUp();
    }

    // run after every test
    public void tearDown() throws Exception {
    }

    public void testDownload() {
        DataDownload dataDownload = new DataDownload();
        String content = dataDownload.download("http://file.nidama.net/class/mobile_develop/data/bookstore2023.json");

        // if data is unexpected, the test will fail
        Assert.assertTrue(content.contains("\"name\": \"暨珠海\","));
        Assert.assertTrue(content.contains("\"latitude\": \"22.255925\","));
        Assert.assertTrue(content.contains("\"longitude\": \"113.541112\","));
        Assert.assertTrue(content.contains("\"memo\": \"暨南大学珠海校区\""));

    }

    public void testParseJsonObjects() {
        DataDownload dataDownload = new DataDownload();
        String content = "{\n" +
                "    \"shops\": [\n" +
                "        {\n" +
                "            \"name\": \"暨珠海\",\n" +
                "            \"latitude\": \"22.255925\",\n" +
                "            \"longitude\": \"113.541112\",\n" +
                "            \"memo\": \"暨南大学珠海校区\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"沃尔玛\",\n" +
                "            \"latitude\": \"22.261365\",\n" +
                "            \"longitude\": \"113.532989\",\n" +
                "            \"memo\": \"沃尔玛(前山店)\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"明珠商\",\n" +
                "            \"latitude\": \"22.251953\",\n" +
                "            \"longitude\": \"113.526421\",\n" +
                "            \"memo\": \"珠海二城广场\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        // this func(parseJsonObjects) use org.json.JSONObject, which is not a part of Java SE
        // need to add Library dependency
        ArrayList<MyLocation> locations = dataDownload.parseJsonObjects(content);
        Assert.assertEquals(3, locations.size());
        Assert.assertEquals("暨珠海", locations.get(0).getName());
        Assert.assertEquals(22.255925, locations.get(0).getLatitude(),1e-6);
        Assert.assertEquals(113.541112, locations.get(0).getLongitude(),1e-6);
    }
}