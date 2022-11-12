package ru.timotege.vk.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "${Vk.name}", url = "https://api.vk.com/method")
public interface VkApi {

    @GetMapping("/users.get")
    String getUserData(@RequestParam(name = "access_token") String token,
                            @RequestParam(name = "user_ids") String id,
                            @RequestParam(name = "fields") String fields,
                            @RequestParam(name = "v") String version
    );

    @GetMapping("/groups.isMember")
    String isMember(@RequestParam(name = "access_token", defaultValue = "${Vk.token}") String token,
                                   @RequestParam(name = "group_id") String groupId,
                                   @RequestParam(name = "user_id") int userId,
                                   @RequestParam(name = "v", defaultValue = "${Vk.version}") String version
    );
}
