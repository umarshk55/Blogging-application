package com.shaik.blog;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.shaik.blog.config.AppConstants;
import com.shaik.blog.entities.Role;
import com.shaik.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogAppApisApplication implements CommandLineRunner {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepo roleRepo;

    public static void main(String[] args) {
        SpringApplication.run(BlogAppApisApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(this.passwordEncoder.encode("def"));

        try {
            if (!roleRepo.existsById(AppConstants.ADMIN_USER)) {
                Role role = new Role();
                role.setId(AppConstants.ADMIN_USER);
                role.setName("ROLE_ADMIN");
                roleRepo.save(role);
            }

            if (!roleRepo.existsById(AppConstants.NORMAL_USER)) {
                Role role1 = new Role();
                role1.setId(AppConstants.NORMAL_USER);
                role1.setName("ROLE_NORMAL");
                roleRepo.save(role1);
            }

            System.out.println("Roles initialized successfully!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
