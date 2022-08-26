package com.cqz.service;

import com.cqz.pojo.Sort;

import java.util.List;

public interface SortService {
    List<Sort> findSort();

    void set(String word);
}
