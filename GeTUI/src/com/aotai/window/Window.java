package com.aotai.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.TextArea;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.aotai.JDBC.JdbcUtils;
import com.aotai.getui.GeTuiAccount;
import com.aotai.getui.GeTuiPush;
import com.aotai.getui.GeTuiThread;
import com.aotai.mail.MailGeTuiExcel;
import com.aotai.util.CMD;
import com.aotai.util.UtilTools;

public class Window {
	
	private static Timer timer = new Timer();
	
	public Window() {
		JFrame frame = new JFrame("个推平台推送服务");
		frame.setBounds(500, 200, 800, 500);
		frame.setResizable(false);
		JPanel leftPanel = new JPanel(new BorderLayout());
		leftPanel.setPreferredSize(new Dimension(200,300));
		frame.add(leftPanel, BorderLayout.WEST);
		JPanel textPanel = new JPanel(new FlowLayout());
		textPanel.setBorder(BorderFactory.createTitledBorder("信息"));
		UtilTools.textInfor = new TextArea("", 27, 80, TextArea.SCROLLBARS_BOTH);
		textPanel.add(UtilTools.textInfor);
		frame.add(textPanel);
		frame.setVisible(true);
		frame.addWindowListener(new WindowListener());
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JdbcUtils.jdbcInit();
		new Window();
		createLogPath();
		
		
		timer.schedule(new TimerTask(){
			@Override
			public void run() {
				new GeTuiThread().start();
				MailGeTuiExcel.MailRoutine();
			}
		}, 60*1000, 2*60*60*1000);
		
	}
	
	private static void createLogPath() {
		String dir = System.getProperty("user.dir");
		dir = dir + java.io.File.separator + "pvLogs" + java.io.File.separator;
		CMD.LOG_PATH = dir;
	}
	
	

}

class WindowListener extends WindowAdapter {
	
	public void windowClosing(WindowEvent e) {
		System.exit(0);
	}
}
