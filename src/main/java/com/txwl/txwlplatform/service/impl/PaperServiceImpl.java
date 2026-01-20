package com.txwl.txwlplatform.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.txwl.txwlplatform.mapper.PaperMapper;
import com.txwl.txwlplatform.model.entity.Paper;
import com.txwl.txwlplatform.service.IPaperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaperServiceImpl extends ServiceImpl<PaperMapper, Paper> implements IPaperService {

    @Autowired
    private PaperMapper paperMapper;

    @Override
    public Page<Paper> getPapersPage(int pageNum, int pageSize) {
        Page<Paper> page = new Page<>(pageNum, pageSize);
        return paperMapper.selectPage(page, null);
    }

    @Override
    public List<Paper> getAllPapers() {
        return paperMapper.selectList(null);
    }

    @Override
    public Paper getPaperById(Long id) {
        return paperMapper.selectById(id);
    }

    @Override
    public void createPaper(Paper paper) {
        paperMapper.insert(paper);
    }

    @Override
    public void updatePaper(Paper paper) {
        paperMapper.updateById(paper);
    }

    @Override
    public void deletePaper(Long id) {
        paperMapper.deleteById(id);
    }
}