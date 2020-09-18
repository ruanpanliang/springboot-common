package com.lc.springboot.user.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lc.springboot.user.dto.request.DictTypeAddRequest;
import com.lc.springboot.user.dto.request.DictTypeQueryRequest;
import com.lc.springboot.user.dto.request.DictTypeUpdateRequest;
import com.lc.springboot.user.mapper.DictTypeMapper;
import com.lc.springboot.user.model.DictType;
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
* 字典类型业务处理类
*
* @author: liangc
* @date: 2020-09-18 10:34
* @version 1.0
*/
@Service
@Slf4j
public class DictTypeService extends ServiceImpl<DictTypeMapper, DictType> {

     @Autowired private ModelMapper modelMapper;
     @Resource private DictTypeMapper dictTypeMapper;

     /**
     * 创建字典类型
     *
     * @param dictTypeAddRequest 字典类型新增对象
     * @return 字典类型更新对象
     */
     @Transactional(rollbackFor = Exception.class)
     public DictTypeUpdateRequest create(DictTypeAddRequest dictTypeAddRequest) {
       DictType dictType = convertToModel(dictTypeAddRequest);

       dictTypeMapper.insert(dictType);
       log.info("创建字典类型,{}", dictType);

       return convertToDto(dictType);
     }

     /**
     * 更新字典类型信息
     *
     * @param dictTypeUpdateRequest 字典类型更新对象
     * @return 返回更新条数
     */
     @Transactional(rollbackFor = Exception.class)
     public int updateDictType(DictTypeUpdateRequest dictTypeUpdateRequest) {
       // 先取回之前数据
       DictType dictType = getById(dictTypeUpdateRequest.getId());

       // 如果不存在，需要报异常
       if (dictType == null) {
       throw new ServiceException(ResultCode.RECORD_NOT_FOUND);
       }

       dictType.setDictTypeCode(dictTypeUpdateRequest.getDictTypeCode());
       dictType.setDictTypeName(dictTypeUpdateRequest.getDictTypeName());
       dictType.setStatus(dictTypeUpdateRequest.getStatus());

       return dictTypeMapper.updateById(dictType);
     }

     /**
     * 获取字典类型列表信息
     *
     * @param dictTypeQueryRequest 字典类型查询对象
     * @param pageable 分页信息
     * @return 字典类型结果集
     */
     public MyPageInfo<DictType> list(DictTypeQueryRequest dictTypeQueryRequest, Pageable pageable) {
        if (pageable != null) {
            PageHelper.startPage(pageable.getPageNumber(), pageable.getPageSize());
        }
        QueryWrapper<DictType> mapper = queryWrapper(dictTypeQueryRequest);
        mapper.orderByDesc(DictType.COL_CREATED_TIME);
        return new MyPageInfo(dictTypeMapper.selectList(mapper));
     }

    /**
    * 获取字典类型列表个数
    *
    * @param dictTypeQueryRequest 字典类型查询对象
    * @return 字典类型结果集个数
    */
    public int count(DictTypeQueryRequest dictTypeQueryRequest) {
        return dictTypeMapper.selectCount(queryWrapper(dictTypeQueryRequest));
    }

    private QueryWrapper<DictType> queryWrapper(DictTypeQueryRequest dictTypeQueryRequest) {
        QueryWrapper<DictType> mapper = new QueryWrapper(convertToModel(dictTypeQueryRequest));
        mapper.ge(dictTypeQueryRequest.getQueryStartDate() != null ,DictType.COL_CREATED_TIME,dictTypeQueryRequest.getQueryStartDate());
        mapper.le(dictTypeQueryRequest.getQueryEndDate()!= null ,DictType.COL_CREATED_TIME,dictTypeQueryRequest.getQueryEndDate());
        return mapper;
    }

     private DictTypeUpdateRequest convertToDto(DictType dictType) {
        return modelMapper.map(dictType, DictTypeUpdateRequest.class);
     }

     private DictType convertToModel(DictTypeAddRequest dictTypeAddRequest) {
        return modelMapper.map(dictTypeAddRequest, DictType.class);
     }

     private DictType convertToModel(DictTypeQueryRequest dictTypeQueryRequest) {
        return modelMapper.map(dictTypeQueryRequest, DictType.class);
     }
}
