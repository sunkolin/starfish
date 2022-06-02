package com.starfish;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.starfish.util.JsonUtil;
import lombok.Data;

/**
 * Test
 *
 * @author sunkolin
 * @version 1.0.0
 * @since 2020-10-28
 */
public class Test {

    public static void main(String[] args) throws JsonProcessingException {

        User u = new User();
        u.setUsername("tom");
        u.setMessageType(MessageType.message);
        u.setSex(null);
//        String json = JSON.toJSONString(u);
//        String json = JSON.toJSONString(u, JSON.DEFAULT_GENERATE_FEATURE, SerializerFeature.WriteEnumUsingToString);

//        System.out.println(MessageType.message.ordinal());

        System.out.println(JsonUtil.toJson(u));


//        JSON.toJsonString

    }

    @Data
    static
    class User {

        private String username;

        private MessageType messageType;

        private String sex;

    }

    enum MessageType {
        notify(0, "notify"),
        message(1, "message");


        private int type;

        private String name;

        MessageType(int type, String name) {
            this.type = type;
            this.name = name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
