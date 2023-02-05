package com.zy.travel.service;


import com.zy.travel.domain.Category;

import java.util.List;


public interface ICategoryService {
    List<Category> findAll();
}
