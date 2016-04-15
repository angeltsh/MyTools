package ghost.writer.gui;

import ghost.writer.data.WordMap;
import ghost.writer.logic.BPFactory;
import ghost.writer.logic.WordDataFactory;
import ghost.writer.logic.WordDataMap;
import ghost.writer.util.Util;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MyJFrame extends JFrame implements WindowListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private MyJPanel panel;
	private JButton jbu_tell, jbu_learn, jbu_clean, jbu_other;

	/**
	 * 窗口句柄
	 */
	public static JFrame context;

	/**
	 * 数据文件名称
	 */
	private final String BPFileName = "DPData.ser";
	private final String WordFileName = "WordData.ser";

	public MyJFrame() {
		super("手写识别");

		context = this;
		this.setLocation(200, 100);
		/**
		 * 触摸板界面
		 */
		panel = new MyJPanel();
		this.add(panel);
		/**
		 * 右侧功能栏界面
		 */
		JPanel func_panel = new JPanel();
		jbu_tell = new JButton("识别");
		jbu_tell.setEnabled(false);
		jbu_learn = new JButton("学习");
		jbu_learn.setEnabled(false);
		jbu_clean = new JButton("清屏");
		jbu_clean.setEnabled(false);
		jbu_other = new JButton("其他");
		jbu_other.setEnabled(false);
		func_panel.setLayout(new GridLayout(8, 1));
		addButtonHelper(func_panel, jbu_tell);
		addButtonHelper(func_panel, jbu_learn);
		addButtonHelper(func_panel, jbu_clean);
		addButtonHelper(func_panel, jbu_other);
		jbu_clean.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.clearScreen();
			}
		});
		jbu_other.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				panel.analysis();
			}
		});
		jbu_learn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new LearnJFrame(context, panel.getMap()).setVisible(true);
			}
		});
		jbu_tell.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String result[] = Util.getMatchString(panel.getMap());
				for (int i = 0; i < 5; ++i) {
					System.out.print(result[i] + "|");
				}
				System.out.println();
				new ExamJFrame(context, panel.getMap(), result[0])
						.setVisible(true);
			}
		});
		this.add(func_panel, BorderLayout.EAST);
		this.setResizable(false);
		this.pack();
		this.setVisible(true);
		this.addWindowListener(this);
		new Thread(new Runnable() {

			@Override
			public void run() {
				long oldTime = System.currentTimeMillis();
				File file = new File(BPFileName);
				if (file.exists()) {
					try {
						BPFactory.initialization(file);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					BPFactory.initialization(WordMap.unit_width
							* WordMap.unit_height, WordMap.unit_width
							* WordMap.unit_height + 16 * WordDataMap.MAX_LENTH,
							16 * WordDataMap.MAX_LENTH);
				}

				file = new File(WordFileName);
				if (file.exists()) {
					try {
						WordDataFactory.initialization(file);
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				} else {
					WordDataFactory.initialization();
				}

				jbu_tell.setEnabled(true);
				jbu_learn.setEnabled(true);
				jbu_clean.setEnabled(true);
				jbu_other.setEnabled(true);
				JOptionPane.showMessageDialog(context, "BP神经网络初始化完成,耗时:"
						+ (System.currentTimeMillis() - oldTime) + "毫秒", "提示",
						JOptionPane.NO_OPTION);
			}

		}).start();
	}

	/**
	 * 添加按钮辅助函数
	 * 
	 * @param base
	 * @param button
	 */
	private void addButtonHelper(JPanel base, JButton button) {
		JPanel temp_panel = new JPanel();
		base.add(temp_panel);
		temp_panel.add(button);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowClosing(WindowEvent e) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				File file = new File(BPFileName);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				try {
					BPFactory.save(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				file = new File(WordFileName);
				if (!file.exists()) {
					try {
						file.createNewFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				try {
					WordDataFactory.save(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.exit(0);
			}

		}).start();
		JOptionPane.showMessageDialog(context, "请等待BP神经网络完成数据保存...", "提示",
				JOptionPane.NO_OPTION);
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub

	}

}
