package com.api.apiclientsdk.client;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONUtil;
import com.api.apiclientsdk.model.User;

import java.util.HashMap;
import java.util.Map;

import static com.api.apiclientsdk.Utils.SignUtils.genSign;


public class APIclient {
    private String accessKey;
    private String secretKey;
    private String body;
    public static final String GATEWAY_HOST="http://localhost:8090";
    //
    private static final String EXTRA_BODY = "userInfoLeAPI";

    public APIclient(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    public String getnamebyGET(String name)
    {
        HashMap<String, Object> paramMap = new HashMap();
        paramMap.put("name", name);
        String result= HttpUtil.get(GATEWAY_HOST+"/api/name/", paramMap);
        System.out.println(result);
        return result;
    }
//    public String getnamebyPOST(String name)
//    {
//        HashMap<String, Object> paramMap = new HashMap();
//        paramMap.put("name", name);
//
//        String result= HttpUtil.post(GATEWAY_HOST+"/api/name/", paramMap);
//        System.out.println(result);
//        return result;
//    }
    public String getnamebyPOST(User user)
    {
        String json = JSONUtil.toJsonStr(user);
        HttpResponse post = HttpRequest.post(GATEWAY_HOST+"/api/name/user")
                .addHeaders(getHeaderMap(json))
                .body(json)
                .execute();
        System.out.println(post.getStatus());
        String body = post.body();
        System.out.println(body);
        return body;
    }

    //添加新接口
    /**
     * 随机获取一句毒鸡汤
     * @return
     */
    public String getPoisonousChickenSoup() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/name/poisonousChickenSoup")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 随机壁纸
     * @return
     */
    public String getRandomWallpaper() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/name/randomWallpaper")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 随机土味情话
     * @return
     */
    public String getLoveTalk() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/name/loveTalk")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 每日一句励志英语
     * @return
     */
    public String getDailyEnglish() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/name/en")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 随机笑话
     * @return
     */
    public String getRandomJoke() {
        HttpResponse httpResponse = HttpRequest.get(GATEWAY_HOST + "/api/name/60s")
                .addHeaders(getHeaderMap(EXTRA_BODY))
                .body(EXTRA_BODY)
                .execute();
        return httpResponse.body();
    }

    /**
     * 获取输入的名称接口
     * @param
     * @return
     */
//    public String getNameByJSON(User user) {
//        String userStr = JSONUtil.toJsonStr(user);// 将 user 转为 JSON 格式的字符串进行参数的传递
//        HttpResponse httpResponse = HttpRequest.post(GATEWAY_HOST + "/api/name")
//                .addHeaders(getHeaderMap(userStr))// 客户端在请求头中携带签名
//                .body(userStr)
//                .execute();
//        System.out.println(httpResponse.getStatus());
//        String body = httpResponse.body();
//        System.out.println(body);
//        return body;
//    }


    private Map<String, String> getHeaderMap(String body) {
        // 创建一个新的 HashMap 对象
        Map<String, String> hashMap = new HashMap();
        // 将 "accessKey" 和其对应的值放入 map 中
        hashMap.put("accessKey", accessKey);
        // 将 "secretKey" 和其对应的值放入 map 中
      //  hashMap.put("secretKey", secretKey);
        //永远不要直接发送密钥
        hashMap.put("nonce", RandomUtil.randomNumbers(4));
        hashMap.put("body",body);
        hashMap.put("timestamp",String.valueOf(System.currentTimeMillis()/1000));
        hashMap.put("sign",genSign(body,secretKey));

    // 返回构造的请求头 map
        return hashMap;
    }

}
