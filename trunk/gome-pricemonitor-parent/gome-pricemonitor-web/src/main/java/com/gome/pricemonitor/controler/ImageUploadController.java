package com.gome.pricemonitor.controler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.gome.pricemonitor.common.fastdfs.FastDFSFile;
import com.gome.pricemonitor.common.fastdfs.FileManager;
import com.gome.pricemonitor.common.util.ResponsesDTO;
import com.gome.pricemonitor.constants.ReturnCode;

@Controller
@RequestMapping(value="/upload")
public class ImageUploadController{
	
	private Logger logger = LoggerFactory.getLogger(ImageUploadController.class);
	
	
	@RequestMapping(value="/toUpload")
	public ModelAndView toUpload(HttpServletRequest request,HttpServletResponse response,ModelAndView model){
		model.setViewName("upload");
		return model;
	}
	
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
		model.setViewName("uploadpic");
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
	
	
	@RequestMapping(value="/common")
	@ResponseBody
	public ResponsesDTO common(@RequestParam("file")MultipartFile file,HttpServletRequest request,HttpServletResponse response){
		ResponsesDTO res = new ResponsesDTO(ReturnCode.ACTIVE_SUCCESS);
		//图片路径
		String filepath=null;
		try {
			if(file!=null){
				filepath=upLoadFile(file, request);
				res.setAttach(filepath);
			}
		} catch (Exception e) {
			logger.error("上传文件出现异常",e);
			res.setReturnCode(ReturnCode.ACTIVE_EXCEPTION);
		}
		
		return res;
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
		model.setViewName("/uploadpic");
		return  model;
	}
	
	

	
	
}
