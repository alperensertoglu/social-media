package com.alperensertoglu.manager;

import com.alperensertoglu.constant.EndPoints;
import com.alperensertoglu.dto.request.AuthUpdateRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "http://localhost:7071/api/v1/auth", decode404 = true, name = "userprofile-auth")
public interface IAuthManager {

    @PutMapping(EndPoints.UPDATE)
    public ResponseEntity<String> updateAuth(@RequestBody AuthUpdateRequestDto dto);

}

