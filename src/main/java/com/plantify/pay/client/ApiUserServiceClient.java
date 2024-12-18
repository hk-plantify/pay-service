package com.plantify.pay.client;

import com.plantify.pay.controller.SellerResponse;
import com.plantify.pay.global.response.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "apiuser-service", url = "${apiuser.service.url}")
public interface ApiUserServiceClient {

    @GetMapping("/v1/api-users/{sellerId}")
    ApiResponse<SellerResponse> getSeller(@PathVariable Long sellerId);

    @GetMapping("/v1/api-users/{name}")
    ApiResponse<SellerResponse> getSellerByName(@PathVariable String name);
}
