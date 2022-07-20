package com.company;

import com.company.entity.ProfileEntity;
import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import com.company.repository.ProfileRepository;
import com.company.util.MD5Util;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UzCardDemoApplicationTests {
    @Autowired
    private ProfileRepository profileRepository;

    @Test
    void contextLoads() {
//        String password = MD5Util.getMd5("123");
//
//        ProfileEntity admin = new ProfileEntity();
//        admin.setName("Admin");
//        admin.setSurname("adminjon");
//        admin.setUsername("uz_card_moder");
//        admin.setPassword(password);
//        admin.setStatus(GeneralStatus.ACTIVE);
//        admin.setRole(GeneralRole.ROLE_ADMIN);
//        profileRepository.save(admin);
    }

}
