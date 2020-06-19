package com.example.demo1_flange.service;

import com.example.demo1_flange.bean.CalculatBean;

import java.util.Map;

/**
 * @author why
 */
public interface ICalculatService {


     Map<String, Double> getResult(CalculatBean calculatBean);
}
