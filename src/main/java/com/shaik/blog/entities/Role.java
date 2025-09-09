package com.shaik.blog.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data   // generates getters, setters, toString, equals, hashCode
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles") // 👈 plural table name is standard
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 👈 auto-generate IDs
    private Integer id;

    @Column(nullable = false, unique = true, length = 50) // 👈 roles should be unique
    private String name;
}
