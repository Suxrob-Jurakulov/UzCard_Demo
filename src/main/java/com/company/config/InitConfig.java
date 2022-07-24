package com.company.config;

import com.company.entity.CompanyEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.GeneralRole;
import com.company.enums.GeneralStatus;
import com.company.repository.CompanyRepository;
import com.company.repository.ProfileRepository;
import com.company.util.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class InitConfig {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Bean
    CommandLineRunner ok() {
        return args -> {
            Optional<ProfileEntity> optional = profileRepository.findByUsername("uz_card_moder");
            if (optional.isPresent()){
                return;
            }
            String md5 = MD5Util.getMd5("123");

            ProfileEntity admin = new ProfileEntity();
            admin.setName("Admin");
            admin.setSurname("adminjon");
            admin.setUsername("uz_card_admin");
            admin.setPassword(md5);
            admin.setVisible(true);
            admin.setStatus(GeneralStatus.ACTIVE);
            admin.setRole(GeneralRole.ROLE_ADMIN);
            profileRepository.save(admin);

            ProfileEntity moderator = new ProfileEntity();
            moderator.setName("Moderator");
            moderator.setSurname("moderatorjon");
            moderator.setUsername("uz_card_moder");
            moderator.setPassword(md5);
            moderator.setVisible(true);
            moderator.setStatus(GeneralStatus.ACTIVE);
            moderator.setRole(GeneralRole.ROLE_MODERATOR);
            profileRepository.save(moderator);

            CompanyEntity bank = new CompanyEntity();
            bank.setName("QORA BANK");
            bank.setAddress("Tashkent");
            bank.setContact("qora_bank@gmail.com");
            bank.setUsername("bank");
            bank.setPassword(md5);
            bank.setVisible(true);
            bank.setStatus(GeneralStatus.ACTIVE);
            bank.setRole(GeneralRole.ROLE_BANK);
            companyRepository.save(bank);

            CompanyEntity payment = new CompanyEntity();
            payment.setName("CLICK");
            payment.setAddress("Tashkent");
            payment.setContact("click@gmail.com");
            payment.setUsername("click");
            payment.setPassword(md5);
            payment.setVisible(true);
            payment.setStatus(GeneralStatus.ACTIVE);
            payment.setRole(GeneralRole.ROLE_PAYMENT);
            companyRepository.save(payment);

            CompanyEntity uzCard = new CompanyEntity();
            uzCard.setName("UZ_CARD");
            uzCard.setAddress("Tashkent");
            uzCard.setContact("uzCard@gmail.com");
            uzCard.setUsername("uzCard");
            uzCard.setPassword(md5);
            uzCard.setVisible(true);
            uzCard.setStatus(GeneralStatus.ACTIVE);
            uzCard.setRole(GeneralRole.ROLE_UZ_CARD);
            companyRepository.save(uzCard);
        };
    }
}
