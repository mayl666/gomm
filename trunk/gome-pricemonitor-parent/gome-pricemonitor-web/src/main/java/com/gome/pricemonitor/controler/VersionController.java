package com.gome.pricemonitor.controler;

import java.io.UnsupportedEncodingException;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.gome.pricemonitor.common.Constant;
import com.gome.pricemonitor.common.Page;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;
import com.gome.pricemonitor.domain.Version;
import com.gome.pricemonitor.service.VersionService;
import com.gome.pricemonitor.controler.base.BaseController;

/**
 * 版本控制器.
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月23日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value = "/version")
public class VersionController extends BaseController {
	
	@Resource
	private VersionService versionService;

	/**
	 * 跳转到新增版本页.
	 *
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月23日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/toAddVersionView", method={RequestMethod.GET, RequestMethod.POST})
	public ModelAndView toAddVersionView(HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/version/addVersion");
		
		String userName = getUserInfo(request, "userName");
		model.addObject("operateUser", userName);
		return model;
	}
	
	/**
	 * 
	 * 新增版本.
	 *
	 * @param content
	 * 				版本信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月23日    caowei    新建
	 * </pre>
	 */
	@RequestMapping(value="/addVersion", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO addVersion(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(content)){
				Version version = JSON.parseObject(content, Version.class);
				if(version != null){
					version.setCreateTime(new Date());
					version.setCreateUser(version.getOperateUser());
					int record = versionService.addVersion(version);
					if(record > 0){
						responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
					} else {
						responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
					}
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	
	/**
	 * 
	 * 跳转到版本列表页.
	 *
	 * @param content
	 * 				搜索条件
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月26日    caowei    新建
	 * </pre>
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/toVersionListView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toVersionListView(@Param(value="content")String content, @Param(value="page")String page, @Param(value="size")String size, HttpServletRequest request, HttpServletResponse response, ModelAndView model) throws UnsupportedEncodingException{
		if(StringUtils.isEmpty(content)){
			model.setViewName("/version/versionList");
		} else {
			model.setViewName("/version/versionTable");
		}
		
		int pageNo = 1;
		if(StringUtils.isNotEmpty(page)){
			pageNo = Integer.parseInt(page);
		}
		
		int pageSize = Constant.PAGE_SIZE;
		if(StringUtils.isNotEmpty(size)){
			pageSize = Integer.parseInt(size);
		}
		Page<Version> p = new Page<Version>(pageNo, pageSize);
		
		Version version = null;
		if(StringUtils.isNotEmpty(content)){
			version = JSON.parseObject(content, Version.class);
		} else {
			version = new Version();
		}
		p.setConditions(version);
		Page<Version> pageVersion = versionService.findVersionListByPage(p);
		model.addObject("page", pageVersion);
		return model;
	}
	
	/**
	 *
	 * 检查名称是否被使用.
	 *
	 * @param id
	 * 			版本ID（编辑时传递，添加时不传递）
	 * @param versionName
	 * 			版本名称
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 
	@RequestMapping(value="/checkIsUsed", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO checkIsUsed(@Param(value="id")String id, @Param(value="versionName")String versionName, @Param(value="categoryId")String categoryId, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try {
			if(StringUtils.isNotBlank(versionName) && StringUtils.isNotBlank(categoryId)){
				Version version = new Version();
				version.setVersionName(versionName);
				version.setCategoryId(Long.parseLong(categoryId));
				if(StringUtils.isNotEmpty(id)){
					version.setId(Long.parseLong(id));
				}
				List<Version> versionList = versionService.findVersionListByVersion(version);
				if(versionList.size() > 0){
					//名称已被使用
					responses.setReturnCode(ReturnCode.ERROR_NAME_HAS_USED);
				} else {
					//名称未被使用
					responses.setReturnCode(ReturnCode.ERROR_NAME_NOT_USED);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	*/
	
	/**
	 * 
	 * 跳转到编辑版本页.
	 *
	 * @param id
	 * 			版本ID
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 
	@RequestMapping(value="/toEditVersionView", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView toEditVersionView(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		model.setViewName("/version/editVersion");
		if(StringUtils.isNotEmpty(id)){
			Version version = versionService.findVersionById(Long.parseLong(id));
			if(version != null){
				model.addObject("version", version);
				String userName = getUserInfo(request, "userName");
				model.addObject("operateUser", userName);
				List<VersionCategory> versionCategoryList = versionCategoryService.findVersionCategoryListByVersionCategory(new VersionCategory());
				model.addObject("versionCategoryList", versionCategoryList);
			}else{
				//版本不存在
				model.addObject("moduleName", "版本列表");
				model.addObject("message", "对不起，版本不存在！");
				model.setViewName("/common/error");
			}
		}
		return model;
	}
	*/
	
	/**
	 * 
	 * 编辑版本.
	 *
	 * @param content
	 * 			版本信息
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 
	@RequestMapping(value="/editVersion", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO editVersion(@Param(value="content")String content, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			Version version = JSON.parseObject(content, Version.class);
			if(version != null){
				version.setUpdateUser(version.getOperateUser());
				version.setUpdateTime(new Date());
				int record = versionService.editVersion(version);
				if(record > 0){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	*/
	
	
	/**
	 *
	 * 删除版本.
	 *
	 * @param id
	 * 			版本ID
	 * @param request
	 * @param response
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月09日    caowei    新建
	 * </pre>
	 
	@RequestMapping(value="/deleteVersion", method={RequestMethod.GET, RequestMethod.POST}, produces="application/json;charset=utf-8")
	@ResponseBody
	public ResponsesDTO deleteVersion(@Param(value="id")String id, HttpServletRequest request, HttpServletResponse response){
		ResponsesDTO responses = new ResponsesDTO(ReturnCode.ACTIVE_FAILURE);
		try{
			if(StringUtils.isNotEmpty(id)){
				Long versionId = Long.parseLong(id);
				int record = versionService.deleteVersionById(versionId);
				if(record > 0){
					responses.setReturnCode(ReturnCode.ACTIVE_SUCCESS);
				} else {
					responses.setReturnCode(ReturnCode.ACTIVE_FAILURE);
				}
			}
		} catch(Exception e) {
			e.printStackTrace();
			responses.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		return responses;
	}
	*/
	
	/**
	 * 
	 * 版本导出.
	 *
	 * @param conditions
	 * 			搜索条件
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 *
	 * <pre>
	 * 修改日期        修改人    修改原因
	 * 2015年11月12日    caowei    新建
	 * </pre>
	 
	@RequestMapping(value="/exportExcel", method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView exportExcel(@Param(value="conditions")String conditions, HttpServletRequest request, HttpServletResponse response, ModelAndView model){
		Version version = null;
		if(StringUtils.isNotEmpty(conditions)){
			version = JSON.parseObject(conditions, Version.class);
		}else{
			version = new Version();
		}
		
		Page<Version> p = new Page<Version>(1, 999999);
		p.setConditions(version);
		Page<Version> pageVersion = versionService.findVersionListByPage(p);
		
		List<Version> versionList = pageVersion.getData();
		versionService.exportExcel(versionList, response);
		return null;
	}
	*/
	
}
