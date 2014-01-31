import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class VirtualScreen implements Display{
	private boolean debug;
	private JTextArea outputFeild;
	private JTextArea[] wheelVels = {new JTextArea("0"), new JTextArea("0"), new JTextArea("0"), new JTextArea("0")};
	private JTextArea[] wheelStates = {new JTextArea("OK"), new JTextArea("OK"), new JTextArea("OK"), new JTextArea("OK")};
	private JTextArea mode;
	private JTextArea idiotStop;
	private JTextArea eStop;
	private JScrollPane scroll;
	private JScrollBar vert;
		
	public VirtualScreen(boolean debug){
		this.debug = debug;
		initialiseDisp();
	}
	
	private void initialiseDisp(){
		JPanel output = new JPanel();
		output.setLayout(new BorderLayout());
			outputFeild = new JTextArea();
			scroll = new JScrollPane(outputFeild, 
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scroll.setPreferredSize(new Dimension(500, 400));
			vert = scroll.getVerticalScrollBar();
		output.add(new JLabel("Output:"), BorderLayout.NORTH);
		output.add(scroll, BorderLayout.CENTER);
		
		JPanel wheelDisp = new JPanel();
		wheelDisp.setLayout(new GridLayout(2, 2, 2, 2));
			JPanel[] wheel = {new JPanel(), new JPanel(), new JPanel(), new JPanel()};
			String[] wheelNames = {"Left Front", "Right Front", "Left Rear", "Right Rear"};
			//TODO put resets on controller
			JButton[] resets = {new JButton("Reset"), new JButton("Reset"), new JButton("Reset"), new JButton("Reset")};
				resets[0].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//wheels.resetMotor(0);
					}
				});
				resets[1].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//wheels.resetMotor(1);
					}
				});
				resets[2].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//wheels.resetMotor(2);
					}
				});
				resets[3].addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						//wheels.resetMotor(3);
					}
				});
		for (int i = 0; i < 4; i++){
			wheel[i].setLayout(new GridLayout(3,2));
			wheel[i].add(new JLabel(wheelNames[i]));
			wheel[i].add(resets[i]);
			wheel[i].add(new JLabel("Velocity"));
			wheel[i].add(new JLabel("Status"));
			wheel[i].add(wheelVels[i]);
			wheel[i].add(wheelStates[i]);
			wheelDisp.add(wheel[i]);
		}
		
		JPanel couchDisp = new JPanel();
		couchDisp.setLayout(new GridLayout(3, 2));
		couchDisp.add(new JLabel("Mode"));
		mode = new JTextArea("Running");
		couchDisp.add(mode);
		couchDisp.add(new JLabel("Idiot Stop"));
		idiotStop = new JTextArea("OFF");
		couchDisp.add(idiotStop);
		couchDisp.add(new JLabel("E-Stop"));
		eStop = new JTextArea("OFF");
		couchDisp.add(eStop);
		
		
		
		JFrame display = new JFrame();
		display.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		display.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0.3;
		c.gridy = 0;
		display.add(wheelDisp, c);
		c.weighty = 0.3;
		c.gridy = 1;
		display.add(couchDisp, c);
		c.weighty = 0.4;
		c.gridy = 2;
		display.add(output, c);
		display.pack();
		display.setVisible(true);
		//display.setBounds(100, 00, 512, 950);
	}

	@Override
	public void print(String s) {
		outputFeild.append(s);
		vert.setValue(vert.getMaximum());
	}

	@Override
	public void printLine(String s) {
		outputFeild.append("\n");
		outputFeild.append(s);
		vert.setValue(vert.getMaximum());
	}
	
	@Override
	public void print(String s, boolean isDebugging) {
		outputFeild.append(s);
		vert.setValue(vert.getMaximum());
	}

	@Override
	public void printLine(String s, boolean isDebugging) {
		outputFeild.append("\n");
		outputFeild.append(s);
		vert.setValue(vert.getMaximum());
	}

	@Override
	public void updateWheelData(double[] vel, String[] faultStatus) {
		for(int i = 0; i < 4; i++){
			wheelVels[i].setText(String.format("%.2f" , vel[i]));
			wheelStates[i].setText(faultStatus[i]);
		}
	}
	
	public void updateCouchData(String mode, boolean idiot, boolean e){
		this.mode.setText(mode);
		if(idiot){
			idiotStop.setText("ON");
		} else {
			idiotStop.setText("OFF");
		}
		if(e) {
			eStop.setText("ON");
		} else {
			eStop.setText("OFF");
		}
	}
	
	public void updateChargeData(){
		//TODO
	}
	
	

}
