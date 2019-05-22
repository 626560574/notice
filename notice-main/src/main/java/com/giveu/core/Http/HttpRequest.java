package com.giveu.core.Http;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

@Component
public class HttpRequest {
    @Autowired
    private RestTemplate restTemplate;

    public String HttpGet(String url, Map<String, Object> uriVariables) {
        String json = restTemplate.getForEntity(url, String.class,uriVariables).getBody();
        return json;
    }
    public String HttpPost(String url,MultiValueMap<String, String> uriVariables) {
       HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

//        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
//        headers.setContentType(type);
//        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(uriVariables, headers);
        String json = restTemplate.postForEntity( url, request , String.class).getBody();
        return json;
    }
    public  JSONObject HttpPostPara(String url,Object data) throws Exception{
        //1.生成一个请求
        HttpPost httpPost = new HttpPost(url);

        //2.配置请求属性
        //2.1 设置请求超时时间
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(100000).setConnectTimeout(100000).build();
        httpPost.setConfig(requestConfig);
        //2.2 设置数据传输格式-json
        httpPost.addHeader("Content-Type", "application/json");
        //2.3 设置请求实体，封装了请求参数
        StringEntity requestEntity = new StringEntity(JSON.toJSONString(data), "utf-8");
        httpPost.setEntity(requestEntity);


        //3.发起请求，获取响应信息
        //3.1 创建httpClient
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;

        try {


            //3.3 发起请求，获取响应
            response = httpClient.execute(httpPost, new BasicHttpContext());

            if (response.getStatusLine().getStatusCode() != 200) {

                System.out.println("request url failed, http code=" + response.getStatusLine().getStatusCode()
                        + ", url=" + url);
                return null;
            }

            //获取响应内容
            org.apache.http.HttpEntity entity = response.getEntity();
            if (entity != null) {
                String resultStr = EntityUtils.toString(entity, "utf-8");
                System.out.println("POST请求结果："+resultStr);

                //解析响应内容
                JSONObject result = JSON.parseObject(resultStr);

                if(result.getInteger("errcode")==null) {
                    return result;
                }else if (0 == result.getInteger("errcode")) {
                    return result;
                }else {
                    System.out.println("request url=" + url + ",return value=");
                    System.out.println(resultStr);
                    int errCode = result.getInteger("errcode");
                    String errMsg = result.getString("errmsg");
                    throw new Exception("error code:"+errCode+", error message:"+errMsg);
                }
            }
        } catch (IOException e) {
            System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
            e.printStackTrace();
        } finally {
            if (httpClient != null) try {
                httpClient.close();              //释放资源

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (response != null) try {
                response.close();              //释放资源

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;

    }
}
