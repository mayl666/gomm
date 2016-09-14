package com.gome.upm.service;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.Asm;
import com.gome.upm.domain.Tbs;

/**
 * ASM空间service
 * @author caowei-ds1
 *
 */
public interface AsmService {
	
	/**
	 *
	 * 根据条件查询ASM空间列表,不分页.
	 *
	 * @param condition
	 * 				查询条件
	 * @return
	 * 				ASM空间列表
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2016年07月18日    caowei-ds1    新建
	 * </pre>
	 */
	List<Asm> findAsmListByCondition(Asm condition);
	
	/**
	 * 分页查询ASM空间表记录
	 * @param p
	 * @return
	 * 2016年08月26日 下午4:02:22   caowei-ds1
	 */
	Page<Asm> findAsmListByPage(Page<Asm> page);

}
