package com.example.project;

import com.google.common.collect.ImmutableMap;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class WebPushIT {

	private static RestTemplate restTemplate = new RestTemplate();
	private static Number testSuiteId;

	@BeforeClass
	public static void startTestSuite() {
		Map<String, Object> startResponse = restTemplate.postForObject("http://localhost:8090/api/start-test-suite/", null, Map.class);
		System.out.println("Test Suite Started: " + startResponse);
		testSuiteId = (Number) ((Map<String, Object>) startResponse.get("data")).get("testSuiteId");
	}

	@AfterClass
	public static void stopTestSuite() {
		Map<String, Object> endRequest = ImmutableMap.of(
				"testSuiteId", testSuiteId
		);
		Map<String, Object> endResponse = restTemplate.postForObject("http://localhost:8090/api/end-test-suite/", endRequest, Map.class);
		System.out.println("Test Suite Ended: " + endResponse);
	}

	@Test
	public void test() {
		Map<String, Object> subscriptionRequest = ImmutableMap.of(
			"testSuiteId", testSuiteId,
			"browserName", "chrome",
			"browserVersion", "stable",
			// TODO Fill these fields
			"gcmSenderId", "foo",
			"vapidPublicKey", "bar"
		);
		Map<String, Object> subscriptionResponse = restTemplate.postForObject("http://localhost:8090/api/get-subscription/", subscriptionRequest, Map.class);

		Map<String, Object> subscription = (Map<String, Object>) ((Map<String, Object>) subscriptionResponse.get("data")).get("subscription");

		// TODO use subscription object
		System.out.println(subscription);
	}
}
