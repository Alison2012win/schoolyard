package com.schoolyard.common.init;



import java.io.File;
import java.util.TimerTask;

import com.schoolyard.common.util.FileUtil;

public class TestTimeTask extends TimerTask  {

	/**         
	 * 执行删除临时文件夹图片操作
	 */
	public void run() {
		File deleteFile = new File(FileUtil.REAL_PATH.replace("WEB-INF/classes/", "") + "/uploadFilesTemporary");
		File[] lst = deleteFile.listFiles();		
		for (File f:lst){			
			f.delete();			
		}
		
	}

}
