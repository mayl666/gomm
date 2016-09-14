package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.Asm;
import com.gome.upm.domain.Tbs;

/**
 * 
 * 表空间dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月18日    caowei    新建
 * </pre>
 */
public interface TbsMapper {

	/**
	 *
	 * 查询表空间.
	 *
	 * @param condition
	 * 			条件
	 * @return
	 * 			记录列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月18日    caowei    新建
	 * </pre>
	 */
	List<Tbs> selectTbsListByConditions(Tbs tbs);
	
	/**
	 * 根据条件分页查询表空间记录
	 * @param page
	 * @return
	 * 2016年7月21日 下午4:07:30   caowei-ds1
	 */
	List<Tbs> selectTbsListByPage(Page<Tbs> page);

	/**
	 * 根据条件查询表空间表记录数
	 * @param page
	 * @return
	 * 2016年7月21日 下午4:07:30   caowei-ds1
	 */
	int selectTotalResultByConditions(Tbs tbs);

}
