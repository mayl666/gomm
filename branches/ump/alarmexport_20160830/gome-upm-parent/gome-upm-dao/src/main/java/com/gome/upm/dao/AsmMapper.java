package com.gome.upm.dao;

import java.util.List;

import com.gome.upm.common.Page;
import com.gome.upm.domain.Asm;
import com.gome.upm.domain.DBConnection;

/**
 * 
 * ASM空间dao接口.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2016年07月18日    caowei    新建
 * </pre>
 */
public interface AsmMapper {

	/**
	 *
	 * 查询ASM空间.
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
	List<Asm> selectAsmListByConditions(Asm asm);
	
	/**
	 * 根据条件分页查询ASM空间记录
	 * @param page
	 * @return
	 * 2016年7月21日 下午4:07:30   caowei-ds1
	 */
	List<Asm> selectAsmListByPage(Page<Asm> page);

	/**
	 * 根据条件查询连接表记录数
	 * @param page
	 * @return
	 * 2016年7月21日 下午4:07:30   caowei-ds1
	 */
	int selectTotalResultByConditions(Asm asm);

}
