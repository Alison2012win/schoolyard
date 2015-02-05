package com.schoolyard.common.init;



import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.stereotype.Service;

/**
 * <p>
 * Title: schoolyard_[客户端]
 * </p>
 * <p>
 * Description: 初始化服务器启动信息
 * </p>
 * 
 * @author XXX
 * @version $Revision$ 2014-9-1
 * @author (lastest modification by GLJ)
 * @since 20130601
 */
@Service("initTask")
public class InitTask {

	public void init() {
		Timer timer = new Timer();               
		TimerTask task = new TestTimeTask();    
		   
		Calendar todayEnd = Calendar.getInstance();  
	    todayEnd.set(Calendar.HOUR, 23);  
	    todayEnd.set(Calendar.MINUTE, 59);  
	    todayEnd.set(Calendar.SECOND, 59);  
	    todayEnd.set(Calendar.MILLISECOND, 999);  
	    long stime = todayEnd.getTime().getTime()-new Date().getTime();
		timer.schedule(task, stime, 86400000);
	}

}
