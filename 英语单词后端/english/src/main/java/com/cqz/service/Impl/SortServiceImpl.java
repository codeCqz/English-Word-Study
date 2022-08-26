package com.cqz.service.Impl;

import com.cqz.dao.SortDao;
import com.cqz.pojo.Sort;
import com.cqz.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortServiceImpl implements SortService {
    @Autowired
    private SortDao sortDao;

    @Override
    public List<Sort> findSort() {
        return sortDao.findSort();
    }

    @Override
    public void set(String word) {
        sortDao.set(word);
    }
}
