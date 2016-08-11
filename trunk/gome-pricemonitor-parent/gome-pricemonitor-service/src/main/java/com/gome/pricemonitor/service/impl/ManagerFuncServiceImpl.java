package com.gome.pricemonitor.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.gome.pricemonitor.dao.ManagerFuncMapper;
import com.gome.pricemonitor.domain.ManagerFunc;
import com.gome.pricemonitor.service.ManagerFuncService;

@Service("managerFuncService")
public class ManagerFuncServiceImpl implements ManagerFuncService {
	
	@Resource
	private ManagerFuncMapper managerFuncMapper;

	@Override
	public List<ManagerFunc> query(Long userId, Long parentId) {

		return managerFuncMapper.query();
	}

	@Override
	public String queryForRoleIdNot(Long roleId) {
		List<Long> listLeft = new ArrayList<Long>();
		
		List<Long> listAll = managerFuncMapper.queryNormal();//获取全部一级二级权限
		List<Long> listUser = managerFuncMapper.queryByRoleIdAndParentId(roleId);
		for(Long funcId : listAll){
			if(!listUser.contains(funcId)){
				listLeft.add(funcId);
			}
		}
//		listAll.removeAll(listUser);
//		if(listAll != null && listUser != null){
//			for(int i=0; i<listAll.size();i++){
//				if(!listUser.contains(listAll.get(i))){
//					listAll.re
//				}
//			}
//		}
		

		return JSON.toJSONString(listLeft);
	}
	
	
	@Override
	public String queryForRoleId(Long roleId) {
		List<Long> listUser = managerFuncMapper.queryByRoleIdAndParentId(roleId);

		return JSON.toJSONString(listUser);
	}

}
