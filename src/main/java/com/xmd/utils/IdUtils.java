package com.xmd.utils;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;

public class IdUtils {

    public static String getSnowflake(){
        Snowflake secondSnow = IdUtil.getSnowflake(1,1);
        String secondId = secondSnow.nextIdStr();
        return secondId;
    }

    public static void main(String[] args) {
        String json = "{\n" +
                "    \"job\":{\n" +
                "        \"setting\":{\n" +
                "            \"speed\":{\n" +
                "                \"byte\":1048576\n" +
                "            },\n" +
                "            \"errorLimit\":{\n" +
                "                \"record\":0,\n" +
                "                \"percentage\":0.02\n" +
                "            }\n" +
                "        },\n" +
                "        \"content\":[\n" +
                "            {\n" +
                "                \"reader\":{\n" +
                "                    \"name\":\"rdbmsreader\",\n" +
                "                    \"parameter\":{\n" +
                "                        \"username\":\"xxx\",\n" +
                "                        \"password\":\"xxx\",\n" +
                "                        \"column\":[\n" +
                "                            \"id\",\n" +
                "                            \"name\"\n" +
                "                        ],\n" +
                "                        \"splitPk\":\"pk\",\n" +
                "                        \"connection\":[\n" +
                "                            {\n" +
                "                                \"querySql\":[\n" +
                "                                    \"SELECT e.id,\n" +
                "       e.inner_event_id,\n" +
                "       e.scence,\n" +
                "       e.title,\n" +
                "       ext.content， e.event_time as time,\n" +
                "       e.event_place_name as location,\n" +
                "\n" +
                "CASE e.supervise_status\n" +
                "WHEN '0' THEN '待审核'\n" +
                "WHEN '1' THEN '处理中'\n" +
                "WHEN '2' THEN '已完成'\n" +
                "ELSE '待审核'\n" +
                "END\n" +
                "as status,\n" +
                "e.update_time,\n" +
                "e.create_time,\n" +
                "e.event_file,\n" +
                "LEFT(e.event_coordinate, INSTR(e.event_coordinate, ',') - 1) AS lng,\n" +
                "IF(LEFT(e.event_coordinate, INSTR(e.event_coordinate, ',') - 1) , RIGHT(e.event_coordinate, LENGTH(e.event_coordinate) - INSTR(e.event_coordinate, ',')), '') AS lat\n" +
                "，\n" +
                "\n" +
                "0 as platform_create,\n" +
                "0 as push_by_street\n" +
                "FROM DATAX.EVENT e\n" +
                "LEFT JOIN\n" +
                "DATAX.EVENT_EXT ext\n" +
                "ON e.id = ext.EVENT_ID\"\n" +
                "                                ],\n" +
                "                                \"jdbcUrl\":[\n" +
                "                                    \"jdbc:dm://ip:port/database\"\n" +
                "                                ]\n" +
                "                            }\n" +
                "                        ],\n" +
                "                        \"fetchSize\":1024,\n" +
                "                        \"where\":\"1 = 1\"\n" +
                "                    }\n" +
                "                },\n" +
                "                \"writer\":{\n" +
                "                    \"name\":\"streamwriter\",\n" +
                "                    \"parameter\":{\n" +
                "                        \"print\":true\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        ]\n" +
                "    }\n" +
                "}";
    }


}
