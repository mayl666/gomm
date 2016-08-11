package com.gome.pricemonitor.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.dao.VersionMapper;
import com.gome.pricemonitor.domain.Version;
import com.gome.pricemonitor.service.VersionService;

/**
 * 
 * 版本service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月23日    caowei    新建
 * </pre>
 */
@Service
public class VersionServiceImpl implements VersionService {

	@Resource
	private VersionMapper versionMapper;
	
	public int addVersion(Version version) {
		return versionMapper.insertVersion(version);
	}

	public Page<Version> findVersionListByPage(Page<Version> page) {
		List<Version> versionList = versionMapper.selectVersionListByPage(page);
		int totalResult = versionMapper.selectTotalResultByConditions(page.getConditions());
		return new Page<Version>(page.getPageNo(), page.getPageSize(), totalResult, versionList, page.getConditions());
	}

	/*
	public int editVersion(Version version) {
		return versionMapper.updateVersion(version);
	}

	public int deleteVersionById(Long id) {
		return versionMapper.deleteVersionById(id);
	}

	public Version findVersionById(Long id) {
		return versionMapper.selectVersionById(id);
	}

	public List<Version> findVersionListByVersion(Version version) {
		return versionMapper.selectVersionListByVersion(version);
	}

	@Override
	public void exportExcel(List<Version> versionList, HttpServletResponse response) {
		if(versionList != null && versionList.size() > 0){
			int num=0;
			for(Version version : versionList){
				num++;
				version.setNum(num);
				if(version.getStatus() == 1){
					version.setStatusStr("正常");
				}else{
					version.setStatusStr("失效");
				}
			}
		}
		
		String[] title ={"序号","版本类别","版本名称","参考价格","状态","创建时间"};
        String[] header = {"num","categoryName","versionName","price","statusStr","createTimeStr"};
		String fileName = "版本列表.xls";
		JSONArray array = (JSONArray) JSONArray.toJSON(versionList);
		ExcelUtil.exportExcel(response, array, title, header, fileName);
		
	}
	*/

}
