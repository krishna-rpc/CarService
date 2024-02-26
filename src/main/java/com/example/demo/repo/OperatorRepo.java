package com.example.demo.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Operator;

public interface OperatorRepo extends JpaRepository<Operator, Integer>{

}
