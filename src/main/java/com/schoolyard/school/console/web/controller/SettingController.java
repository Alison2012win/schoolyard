package com.schoolyard.school.console.web.controller;



/** 
 * 
 * Copyright (c) 1995-2012 Wonders Information Co.,Ltd. 
 * 1518 Lianhang Rd,Shanghai 201112.P.R.C.
 * All Rights Reserved.
 * 
 * This software is the confidential and proprietary information of Wonders Group.
 * (Social Security Department). You shall not disclose such
 * Confidential Information and shall use it only in accordance with 
 * the terms of the license agreement you entered into with Wonders Group. 
 *
 * Distributable under GNU LGPL license by gnu.org
 */



import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.schoolyard.common.util.RestMsg;
import com.schoolyard.common.util.UserInfo;

/**
 * <p>
 * Title: schoolyard_[个人设置]
 * </p>
 * <p>
 * Description: []
 * </p>
 * 
 * @author 	XXX
 * @version Created at 2014-07-14
 * @author  Lastest modification by []
 */
@Controller
@RequestMapping("api/setting")
public class SettingController {
	
//	protected Logger log = Logger.getLogger(SettingController.class);
	private static final String UPLOAD_FILE_TEMPORARY = "uploadFilesTemporary/";//临时上传文件路径
	private static final String UPLOAD_FILE = "uploadFiles/";//临时上传文件路径
	
	/**
	 * <p>
	 * Description:[添加预览图片]
	 * </p>
	 * 
	 * @author GLJ
	 * @param  request
	 * @return RestMsg<String>
	 */
	@RequestMapping(value = "/uploadImg", method = RequestMethod.POST)
	@ResponseBody
	public RestMsg<String> uploadImg(HttpServletRequest request,HttpServletResponse response, HttpSession session)
			throws Exception, IOException {
		
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件：
		MultipartFile file = multipartRequest.getFile("imgfile");
		RestMsg<String> rm = new RestMsg<String>();
		String fileType = file.getContentType();
		if(fileType.toLowerCase().indexOf("image")<0){
			rm = rm.errorMsg();
			rm.setMsg("该文件不是图片类型！");
			return rm;
		}
		// 获得文件名：
		String filename = file.getOriginalFilename();
		String[] temp1 = filename.split("\\.");
		String extenName = temp1[temp1.length - 1];
		StringBuffer fileNameBuffer = new StringBuffer();
		fileNameBuffer.append(UUID.randomUUID()).append(".").append(extenName);
		
		InputStream input = file.getInputStream();
		String actionPath = request.getSession().getServletContext().getRealPath("");
		StringBuffer path = new StringBuffer();
		path.append(actionPath);
		path.append(File.separator);
		path.append(UPLOAD_FILE_TEMPORARY);
		path.append(fileNameBuffer);
		
		File savePath = new File(path.toString());
		if (!savePath.getParentFile().exists()) { // 文件夹
			savePath.getParentFile().mkdir();
		}		
		BufferedImage sourceImg = ImageIO.read(input);
		float heigth = sourceImg.getHeight();
		float width = sourceImg.getWidth();
		double scale = 1;
		double sH = heigth / 100;
		double sW = width / 100;
		scale = sH < sW ? sH : sW;
		int rheigth = new Double(scale * 100).intValue();
		int rwidth = new Double(scale * 100).intValue();

		Thumbnails.of(sourceImg).sourceRegion(Positions.CENTER, rwidth, rheigth).size(100, 100)
				.keepAspectRatio(false).toFile(path.toString());
		
		StringBuffer imageServicePath = new StringBuffer();
		imageServicePath.append(actionPath.replace("-s-console", ""));
		imageServicePath.append("-images");
		imageServicePath.append(File.separator);
		imageServicePath.append("s");
		imageServicePath.append(File.separator);
		imageServicePath.append("users");
		imageServicePath.append(File.separator);
		imageServicePath.append(getUid());
		imageServicePath.append(".jpg");
		
		savePath = new File(imageServicePath.toString());
		if (!savePath.getParentFile().exists()) { // 文件夹
			savePath.getParentFile().mkdirs();
		}
		Thumbnails.of(path.toString()).size(100, 100).toFile(imageServicePath.toString());
		
		
		String filePath = "uploadFilesTemporary/" + fileNameBuffer;
		rm = rm.successMsg();
		rm.setResult(filePath);
		return rm;
	}
	
	//获取当前用户ID
	public Integer getUid() {
		UserInfo user = (UserInfo) SecurityContextHolder.getContext()
				.getAuthentication().getPrincipal();
		return user.getUid();
	}
	
	//更新用户头像
	public void refreshPhoto(String url){
		UserInfo user = (UserInfo) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();
		user.setPhoto(url);
	}
	
	//更新用户姓名
	public void refreshUsername(String username){
		UserInfo user = (UserInfo) SecurityContextHolder.getContext()
			.getAuthentication().getPrincipal();
		user.setName(username);
	}
}