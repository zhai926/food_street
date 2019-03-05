package com.zhaihuilin.food.front.utils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.springframework.context.annotation.Configuration;

/**
 * 发送短信工具
 * Created by zhaihuilin on 2018/12/28 10:17.
 */
@Configuration
public class SendSmsUtils {

     //产品名称:云通信短信API产品,开发者无需替换
     //@Value(value = "${aliyuncs.dysmsapi.product}")
     public  String  PRODUCT ="Dysmsapi";
     //产品域名,开发者无需替换
     //@Value(value = "${aliyuncs.dysmsapi.domain}")
     public  String DOMIAN="dysmsapi.aliyuncs.com";
     // TODO此处需要替换成开发者自己的AK(在阿里云访问控制台寻找)
     //@Value(value = "${aliyuncs.dysmsapi.accessKeyId}")
     public  String   ACCESSKEYID ="LTAIzK1iKtG5yRsI";
     //@Value(value = "${aliyuncs.dysmsapi.accessKeySecret}")
     public  String   ACCESSKEYSECRET="3gxgNSNOsqYZQwc2AFBxrqlEYcl0Hx";





     /**
      * 发送短信
      * @param PhoneNumber   必填:待发送手机号
      * @param SignName   必填:短信签名-可在短信控制台中找到
      * @param TemplateCode    必填:短信模板-可在短信控制台中找到
      * @return
      * @throws ClientException
      */
     public   SendSmsResponse sendSms(String PhoneNumber,String SignName,String TemplateCode,String code) throws ClientException {
          //可自助调整超时时间
          System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
          System.setProperty("sun.net.client.defaultReadTimeout", "10000");

          //初始化acsClient,暂不支持region化
          IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou",ACCESSKEYID,ACCESSKEYSECRET);
          DefaultProfile.addEndpoint("cn-hangzhou","cn-hangzhou",PRODUCT,DOMIAN);
          IAcsClient acsClient =  new DefaultAcsClient(profile);

          //组装请求对象-具体描述见控制台-文档部分内容
          SendSmsRequest request = new SendSmsRequest();
          //必填:待发送手机号
          request.setPhoneNumbers(PhoneNumber);
          //必填:短信签名-可在短信控制台中找到
          request.setSignName(SignName);
          //必填:短信模板-可在短信控制台中找到
          request.setTemplateCode(TemplateCode);
          //可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
          //request.setTemplateParam("{\"name\":\"Tom\", \"code\":\"\"}");
          //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
          //request.setSmsUpExtendCode("90997");
          //可选:outId为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
          request.setOutId("yourOutId");

          //hint 此处可能会抛出异常，注意catch
          SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

          return sendSmsResponse;
     }


     // 测试
     public static void main(String[] args) {

          try {
               SendSmsUtils sendSmsUtils = new SendSmsUtils();
               SendSmsResponse  response=sendSmsUtils.sendSms("17317900836","翟氏集团","SMS_153995300","123456");
               System.out.println("短信接口返回的数据----------------");
               System.out.println("Code=" + response.getCode());
               System.out.println("Message=" + response.getMessage());
               System.out.println("RequestId=" + response.getRequestId());
               System.out.println("BizId=" + response.getBizId());
          } catch (ClientException e) {
               e.printStackTrace();
          }
     }








































































}
