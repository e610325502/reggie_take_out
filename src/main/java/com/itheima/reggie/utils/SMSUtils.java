package com.itheima.reggie.utils;

import com.aliyun.auth.credentials.Credential;
import com.aliyun.auth.credentials.provider.StaticCredentialProvider;
import com.aliyun.sdk.service.dysmsapi20170525.AsyncClient;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.sdk.service.dysmsapi20170525.models.SendSmsResponse;
import com.google.gson.Gson;
import darabonba.core.client.ClientOverrideConfiguration;
import darabonba.core.exception.ClientException;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * 短信发送工具类
 */
public class SMSUtils {

	/**
	 * 发送短信
	 * @param signName 签名
	 * @param templateCode 模板
	 * @param phoneNumbers 手机号
	 * @param param 参数
	 */
	public static void sendMessage(String signName, String templateCode,String phoneNumbers,String param)    {

		StaticCredentialProvider provider = StaticCredentialProvider.create(Credential.builder()
				.accessKeyId("LTAI5tEcFseLQggdkL8GoTZy")
				.accessKeySecret("TA1IOuF7VzjbOCf8dWzUEx3LD3AAiF")
				//.securityToken("<your-token>") // use STS token
				.build());
		//ofvNpnYf4An9e0VJqLmiQf6JhC2Bbm
		// Configure the Client
		AsyncClient client = AsyncClient.builder()
				.region("cn-hangzhou") // Region ID
				//.httpClient(httpClient) // Use the configured HttpClient, otherwise use the default HttpClient (Apache HttpClient)
				.credentialsProvider(provider)
				//.serviceConfiguration(Configuration.create()) // Service-level configuration
				// Client-level configuration rewrite, can set Endpoint, Http request parameters, etc.
				.overrideConfiguration(
						ClientOverrideConfiguration.create()
								.setEndpointOverride("dysmsapi.aliyuncs.com")
						//.setConnectTimeout(Duration.ofSeconds(30))
				)
				.build();

		// Parameter settings for API request
		SendSmsRequest sendSmsRequest = SendSmsRequest.builder()
				.signName(signName)
				.templateCode(templateCode)
				.phoneNumbers(phoneNumbers)
				.templateParam("{\"code\":\""+param+"\"}")
				.build();

		CompletableFuture<SendSmsResponse> response = client.sendSms(sendSmsRequest);
		try {
			SendSmsResponse resp = response.get();
			System.out.println(new Gson().toJson(resp));
			client.close();
		}catch (ClientException | InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}





	}

}
