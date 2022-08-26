package com.cqz.dao;

import com.cqz.pojo.Sort;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SortDao {
    List<Sort> findSort();
    void set(String word);
}
