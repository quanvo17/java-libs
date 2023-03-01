package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.okhttp.*;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericDatumReader;
import org.apache.avro.generic.GenericDatumWriter;
import org.apache.avro.io.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.HttpClients;
import org.msgpack.jackson.dataformat.MessagePackFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Function {

    private static final Schema SCHEMA = new Schema.Parser().parse("{\"namespace\":\"org.fluentd.parser.avro\",\"type\":\"record\",\"name\":\"SparkQuery\",\"fields\":[{\"name\":\"username\",\"type\":\"string\"},{\"name\":\"ipAddress\",\"type\":\"string\"},{\"name\":\"sparkApp\",\"type\":\"string\"},{\"name\":\"sessionId\",\"type\":\"string\"},{\"name\":\"thriftHost\",\"type\":\"string\"},{\"name\":\"thriftPort\",\"type\":\"int\"},{\"name\":\"groupId\",\"type\":\"string\"},{\"name\":\"statement\",\"type\":\"string\"},{\"name\":\"startTime\",\"type\":\"long\"}]}");
    public static final String JSON = "{\"userName\":\"quanvd4\",\"ipAddress\":\"10.255.152.189\",\"sparkApp\":\"abc\",\"sessionId\":\"123\",\"thriftHost\":\"10.60.170.89\",\"thriftPort\":8909,\"groupId\":\"345\",\"statement\":\"select * from abc\",\"startTime\":1677670972000}";
    public static void main(String[] args) throws IOException {
//        byte[] data = jsonToAvro(JSON, SCHEMA);
//        System.out.println(data.length);

        ObjectMapper objectMapper = new ObjectMapper(new MessagePackFactory());
        Query query = new Query("quanvd8", "10.255.152.200", "abc", "123", "10.60.170.89", "8909", 345, "select * from abc", 1677670972000L);
        byte[] data = objectMapper.writeValueAsBytes(query);
        sendHttp("http://localhost:9099/test", data);
//        postAvro("http://localhost:9099/test", data);
    }
    public static String postAvro(String uri, byte[] req) {

        String queryResponse = null;
        final HttpPost httpPost = new HttpPost(uri);
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE,"application/msgpack");
        MultipartEntityBuilder builder = MultipartEntityBuilder.create();
        HttpEntity data = builder.addBinaryBody("msgpack", req).build();
        httpPost.setEntity(data);
        try {
            HttpClient httpclient = HttpClients.createDefault();
            HttpResponse response = httpclient.execute(httpPost);
            System.out.println(response.getStatusLine());
            queryResponse = response.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return queryResponse;
    }

    public static String sendHttp(String uri, byte[] req) {
        Response response = null;
        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/msgpack");
        RequestBody body = RequestBody.create(mediaType, req);
        Request request = new Request.Builder()
                .url(uri)
                .method("POST", body)
                .addHeader("Content-Type", "application/msgpack")
                .build();
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return response.message();
    }

    public static byte[] jsonToAvro(String json, Schema schema) throws IOException {
        DatumReader<Object> reader = new GenericDatumReader<>(schema);
        GenericDatumWriter<Object> writer = new GenericDatumWriter<>(schema);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        Decoder decoder = DecoderFactory.get().jsonDecoder(schema, json);
        Encoder encoder = EncoderFactory.get().binaryEncoder(output, null);
        Object datum = reader.read(null, decoder);
        writer.write(datum, encoder);
        encoder.flush();
        return output.toByteArray();
    }


}
