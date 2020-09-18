package com.lc.springboot.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.springboot.user.dto.request.DictAddRequest;
import com.lc.springboot.user.dto.request.DictQueryRequest;
import com.lc.springboot.user.dto.request.DictUpdateRequest;
import com.lc.springboot.user.mapper.DictMapper;
import com.lc.springboot.user.model.Dict;
import com.lc.springboot.common.api.MyPageInfo;
import com.lc.springboot.common.api.ResultCode;
import com.lc.springboot.common.error.ServiceException;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* 字典业务处理类
*
* @author: liangc
* @date: 2020-09-18 10:34
* @version 1.0
*/
@Service
@Slf4j
public class DictService extends ServiceImpl<DictMapper, Dict> {

     @Autowired private ModelMapper modelMapper;
     @Resource private DictMapper dictMapper;

     /**
     * 创建字典
     *
     * @param dictAddRequest 字典新增对象
     * @return 字典更新对象
     */
     @Transactional(rollbackFor = Exception.class)
     public DictUpdateRequest create(DictAddRequest dictAddRequest) {
       Dict dict = convertToModel(dictAddRequest);

       dictMapper.insert(dict);
       log.info("创建字典,{}", dict);

       return convertToDto(dict);
     }

     /**
     * 更新字典信息
     *
     * @param dictUpdateRequest 字典更新对象
     * @return 返回更新条数
     */
     @Transactional(rollbackFor = Exception.class)
     public int updateDict(DictUpdateRequest dictUpdateRequest) {
       // 先取回之前数据
       Dict dict = getById(dictUpdateRequest.getId());

       // 如果不存在，需要报异常
       if (dict == null) {
       throw new ServiceException(ResultCode.RECORD_NOT_FOUND);
       }

       dict.setDictCode(dictUpdateRequest.getDictCode());
       dict.setDictName(dictUpdateRequest.getDictName());
       dict.setDictTypeCode(dictUpdateRequest.getDictTypeCode());
       dict.setStatus(dictUpdateRequest.getStatus());

       return dictMapper.updateById(dict);
     }

     /**
     * 获取字典列表信息
     *
     * @param dictQueryRequest 字典查询对象
     * @param pageable 分页信息
     * @return 字典结果集
     */
     public MyPageInfo<Dict> list(DictQueryRequest dictQueryRequest, Pageable pageable) {
        if (pageable != null) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        }
        QueryWrapper<Dict> mapper = queryWrapper(dictQueryRequest);
        mapper.orderByDesc(Dict.COL_CREATED_TIME);
        return new MyPageInfo(dictMapper.selectList(mapper));
     }

    /**
    * 获取字典列表个数
    *
    * @param dictQueryRequest 字典查询对象
    * @return 字典结果集个数
    */
    public int count(DictQueryRequest dictQueryRequest) {
        return dictMapper.selectCount(queryWrapper(dictQueryRequest));
    }

    private QueryWrapper<Dict> queryWrapper(DictQueryRequest dictQueryRequest) {
        QueryWrapper<Dict> mapper = new QueryWrapper(convertToModel(dictQueryRequest));
        mapper.ge(dictQueryRequest.getQueryStartDate() != null ,Dict.COL_CREATED_TIME,dictQueryRequest.getQueryStartDate());
        mapper.le(dictQueryRequest.getQueryEndDate()!= null ,Dict.COL_CREATED_TIME,dictQueryRequest.getQueryEndDate());
        return mapper;
    }

     private DictUpdateRequest convertToDto(Dict dict) {
        return modelMapper.map(dict, DictUpdateRequest.class);
     }

     private Dict convertToModel(DictAddRequest dictAddRequest) {
        return modelMapper.map(dictAddRequest, Dict.class);
     }

     private Dict convertToModel(DictQueryRequest dictQueryRequest) {
        return modelMapper.map(dictQueryRequest, Dict.class);
     }
}
