package com.gome.pricemonitor.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gome.pricemonitor.common.fastdfs.FastDFSFile;
import com.gome.pricemonitor.common.fastdfs.FileManager;
/**
 * 
 * 图片上传控制器.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月3日    caowei    新建
 * </pre>
 */
@Controller
@RequestMapping(value="/imageUpload")
public class UploadController {

	/**
	 * 上传图片
	 * @param file
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/uploadimg",method={RequestMethod.POST})
	//@ResponseBody
	public ModelAndView uploadImg(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response,ModelAndView model){
		//图片路径
		String filepath=null;
		model.setViewName("/common/uploadpic");
		try {
			if(file!=null){
				filepath=upLoadFile(file, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("msg", filepath);
		return model;
	}
	
	public static String upLoadFile(MultipartFile file,HttpServletRequest request) throws Exception{
		String tempFileName = file.getOriginalFilename();  
		String ext = tempFileName.substring(tempFileName.lastIndexOf(".")+1);
		FastDFSFile targetfile = new FastDFSFile(tempFileName, file.getBytes(), ext);
		String filePath = FileManager.upload(targetfile );
		return filePath; 
	}
	
	/**上传图片页面,采用iframe
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/touploadimg")
	public ModelAndView getToUploadImg(ModelAndView model){
		model.setViewName("/common/uploadpic");
		return  model;
	}
	
	/**上传文件页面,采用iframe
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/touploadfile")
	public ModelAndView getToUploadFile(ModelAndView model){
		model.setViewName("/common/uploadfile");
		return  model;
	}
	
	/**
	 * 上传文件
	 * @param file
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/uploadfile",method={RequestMethod.POST})
//	@ResponseBody
	public ModelAndView uploadFile(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response,ModelAndView model){
		//图片路径
		String filepath=null;
		model.setViewName("/common/uploadfile");
		try {
			if(file!=null){
				filepath=upLoadFile(file, request);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addObject("msg", filepath);
		return model;
	}
	
}
