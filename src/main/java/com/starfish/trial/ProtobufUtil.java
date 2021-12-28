//package com.starfish.trial;
//
//import com.alibaba.fastjson.JSON;
//import com.google.protobuf.InvalidProtocolBufferException;
//import com.google.protobuf.Message;
//import com.google.protobuf.util.JsonFormat;
//
///**
// * ProtobufUtil
// *
// * @author neacle
// * @version 1.0.0
// * @since 2019-07-05
// */
//public class ProtobufUtil {
//
//    private static JsonFormat.TypeRegistry typeRegistry;
//
//    static {
//        typeRegistry = JsonFormat.TypeRegistry
//                .newBuilder()
//                // 如果有Any类型，需要在此处注册
////                .add(MdpManagerOuterClass.DownloadResult.getDescriptor())
////                .add(MdpManagerOuterClass.VideoExtractResult.getDescriptor())
////                .add(MdpManagerOuterClass.MediaExtractRequest.getDescriptor())
////                .add(MdpManagerOuterClass.DownloadRequest.getDescriptor())
//                .build();
//    }
//
//    /**
//     * pb对象转json
//     *
//     * @param message 参数
//     * @return 结果
//     */
//    public static String toJson(Message message) {
//        try {
//            return JsonFormat.printer().includingDefaultValueFields().usingTypeRegistry(typeRegistry).print(message);
//        } catch (InvalidProtocolBufferException e) {
//            throw new RuntimeException("Protobuf message to json error.", e);
//        }
//    }
//
//    /**
//     * json数据转换为pb对象
//     *
//     * @param json    参数
//     * @param builder 参数
//     * @param <T>     T
//     * @return 结果
//     * @throws InvalidProtocolBufferException 异常
//     */
//    @SuppressWarnings("unchecked")
//    public static <T> T jsonToProtobuf(String json, Message.Builder builder) throws InvalidProtocolBufferException {
//        if (builder == null) {
//            return null;
//        }
//        JsonFormat.parser().ignoringUnknownFields().merge(json, builder);
//        return (T) builder.build();
//    }
//
//    /**
//     * object数据转换为pb对象
//     *
//     * @param entity  参数
//     * @param builder 参数
//     * @param <T>     T
//     * @return 结果
//     * @throws InvalidProtocolBufferException 异常
//     */
//    public static <T> T objectToProtobuf(Object entity, Message.Builder builder) throws InvalidProtocolBufferException {
//        if (builder == null || entity == null) {
//            return null;
//        }
//        return jsonToProtobuf(JSON.toJSONString(entity), builder);
//    }
//
//}