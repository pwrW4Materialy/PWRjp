package AnimatorApp;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JButton;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class AnimatorApp extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
			try {
				final AnimatorApp frame = new AnimatorApp();
				frame.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AnimatorApp() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int ww = 450, wh = 300;
		setBounds((screen.width-ww)/2, (screen.height-wh)/2, ww, wh);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		AnimPanel kanwa = new AnimPanel();
		kanwa.setBounds(10, 11, 422, 219);
		contentPane.add(kanwa);
		SwingUtilities.invokeLater(kanwa::initialize);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(actionEvent -> kanwa.addFig());

		btnAdd.setBounds(10, getHeight() -30, 80, 23);
		contentPane.add(btnAdd);
		
		JButton btnAnimate = new JButton("Animate");
		btnAnimate.addActionListener(actionEvent ->  kanwa.animate());

		btnAnimate.setBounds(100, getHeight()-30, 80, 43);
		contentPane.add(btnAnimate);

		JButton btnPurge = new JButton("Clear");
		btnPurge.addActionListener(actionEvent ->  kanwa.removeAllFigures());

		btnPurge.setBounds(190, getHeight()-30, 80, 27);
		contentPane.add(btnPurge);

		this.addComponentListener(new ComponentListener() {
			@Override
			public void componentShown(ComponentEvent componentEvent) {

			}

			@Override
			public void componentHidden(ComponentEvent componentEvent) {

			}

			@Override
			public void componentMoved(ComponentEvent componentEvent) {

			}

			public void componentResized(ComponentEvent e) {
				int width = getWidth()-20;
				int height = getHeight()-45;

				if(width <= 0 ){
					width = 1;
				}
				if(height <= 0){
					height = 1;
				}

				kanwa.changeSize(width, height);
				btnAdd.setBounds(10, getHeight() -25, 80, 23);
				btnAnimate.setBounds(100, getHeight()-25, 80, 23);
				btnPurge.setBounds(190, getHeight()-30, 80, 27);
			}
		});
	}

}
