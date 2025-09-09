package com.shaik.blog.test;

import com.shaik.blog.entities.Role;

public class TestLombok {
    public static void main(String[] args) {
        Role role = new Role();
        role.setName("Admin");
        System.out.println(role.getName());
    }
}
