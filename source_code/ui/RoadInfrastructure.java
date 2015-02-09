package ui;

import javax.swing.*;
import java.awt.*;


public class RoadInfrastructure {

	private JFrame frmTrafficSimulator;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RoadInfrastructure window = new RoadInfrastructure();
					window.frmTrafficSimulator.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public RoadInfrastructure() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTrafficSimulator = new JFrame();
		frmTrafficSimulator.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
		frmTrafficSimulator.setTitle("TRAFFIC SIMULATOR");
		frmTrafficSimulator.getContentPane().setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 16));
		frmTrafficSimulator.setBounds(500, 500, 340, 547);
		frmTrafficSimulator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTrafficSimulator.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(238, 238, 238));
		panel.setBounds(6, 6, 328, 513);
		frmTrafficSimulator.getContentPane().add(panel);
		panel.setLayout(null);
		
		JTextArea txtrRoadInfrastructure = new JTextArea();
		txtrRoadInfrastructure.setBackground(new Color(128, 128, 128));
		txtrRoadInfrastructure.setForeground(new Color(255, 255, 255));
		txtrRoadInfrastructure.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 20));
		txtrRoadInfrastructure.setText("ROAD INFRASTRUCTURE");
		txtrRoadInfrastructure.setBounds(22, 6, 278, 23);
		panel.add(txtrRoadInfrastructure);
		
		JButton single_lane = new JButton();
		single_lane.setToolTipText("Add a Single Lane Road");
		single_lane.setBounds(34, 54, 70, 70);
		single_lane.setIcon(new ImageIcon("/Users/somya/Desktop/Single Lane.png"));
		panel.add(single_lane);
		

		JButton double_lane = new JButton();
		double_lane.setToolTipText("Add a Double Lane Road");
		double_lane.setBounds(124, 54, 70,70);
		double_lane.setIcon(new ImageIcon("/Users/somya/Desktop/Double Lane.png"));
		panel.add(double_lane);
		
		JButton traffic_light = new JButton();
		traffic_light.setToolTipText("Add a Traffic Light");
		traffic_light.setBounds(214, 54, 70,70);
		traffic_light.setIcon(new ImageIcon("/Users/somya/Desktop/Traffic Light.png"));
		panel.add(traffic_light);
		
		JButton zebra_crossing = new JButton();
		zebra_crossing.setToolTipText("Add a Zebra Crossing");
		zebra_crossing.setBounds(34, 144, 70,70);
		zebra_crossing.setIcon(new ImageIcon("/Users/somya/Desktop/Zebra Crossing.png"));
		panel.add(zebra_crossing);
		
		JButton road_blockages = new JButton();
		road_blockages.setToolTipText("Add Road Obstruction");
		road_blockages.setBounds(124, 144, 70,70);
		road_blockages.setIcon(new ImageIcon("/Users/somya/Desktop/Construction.png"));
		panel.add(road_blockages);
		
		JButton vehicle_factory = new JButton();
		vehicle_factory.setToolTipText("Vehicle Factory");
		vehicle_factory.setBounds(214, 144, 70,70);
		vehicle_factory.setIcon(new ImageIcon("/Users/somya/Desktop/Vehicle Factory.png"));
		panel.add(vehicle_factory);
		
		JTextArea txtrTrafficSignBoards = new JTextArea();
		txtrTrafficSignBoards.setBackground(new Color(255, 215, 0));
		txtrTrafficSignBoards.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
		txtrTrafficSignBoards.setText("TRAFFIC SIGN BOARDS");
		txtrTrafficSignBoards.setBounds(82, 228, 158, 16);
		panel.add(txtrTrafficSignBoards);
		
		JButton stop_sign = new JButton();
		stop_sign.setToolTipText("Add a Stop Sign");
		stop_sign.setBounds(6, 256, 70,70);
		stop_sign.setIcon(new ImageIcon("/Users/somya/Desktop/Stop.png"));
		panel.add(stop_sign);
		
		JButton left_sign = new JButton();
		left_sign.setToolTipText("Add a Go Left Sign");
		left_sign.setBounds(88, 256, 70,70);
		left_sign.setIcon(new ImageIcon("/Users/somya/Desktop/Left.png"));
		panel.add(left_sign);
		
		
		JButton up_sign = new JButton();
		up_sign.setToolTipText("Add a Go Straight Sign");
		up_sign.setBounds(170, 256, 70,70);
		up_sign.setIcon(new ImageIcon("/Users/somya/Desktop/Straight.png"));
		panel.add(up_sign);
		
		JButton right_sign = new JButton();
		right_sign.setToolTipText("Add a Go Right Sign");
		right_sign.setBounds(252, 256, 70,70);
		right_sign.setIcon(new ImageIcon("/Users/somya/Desktop/Right.png"));
		panel.add(right_sign);
		
		JTextArea txtrSpeedLimitSigns = new JTextArea();
		txtrSpeedLimitSigns.setForeground(new Color(255, 255, 255));
		txtrSpeedLimitSigns.setText("SPEED LIMIT SIGNS");
		txtrSpeedLimitSigns.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
		txtrSpeedLimitSigns.setBackground(new Color(255, 0, 0));
		txtrSpeedLimitSigns.setBounds(98, 338, 130, 14);
		panel.add(txtrSpeedLimitSigns);
		
		JButton speed_20 = new JButton();
		speed_20.setToolTipText("Speed Limit 20");
		speed_20.setBounds(6, 356, 50,50);
		speed_20.setIcon(new ImageIcon("/Users/somya/Desktop/20.png"));
		panel.add(speed_20);
		
		JButton speed_30 = new JButton();
		speed_30.setToolTipText("Speed Limit 30");
		speed_30.setBounds(60, 356, 50,50);
		speed_30.setIcon(new ImageIcon("/Users/somya/Desktop/30.png"));
		panel.add(speed_30);
		
		JButton speed_50 = new JButton();
		speed_50.setToolTipText("Speed Limit 50");
		speed_50.setBounds(114, 356, 50,50);
		speed_50.setIcon(new ImageIcon("/Users/somya/Desktop/50.png"));
		panel.add(speed_50);
		
		JButton speed_60 = new JButton();
		speed_60.setToolTipText("Speed Limit 60");
		speed_60.setBounds(168, 356, 50,50);
		speed_60.setIcon(new ImageIcon("/Users/somya/Desktop/60.png"));
		panel.add(speed_60);
		
		JButton speed_70 = new JButton();
		speed_70.setToolTipText("Speed Limit 70");
		speed_70.setBounds(222, 356, 50,50);
		speed_70.setIcon(new ImageIcon("/Users/somya/Desktop/70.png"));
		panel.add(speed_70);
		
		JButton speed_90 = new JButton();
		speed_90.setToolTipText("Speed Limit 90");
		speed_90.setBounds(276, 356, 50,50);
		speed_90.setIcon(new ImageIcon("/Users/somya/Desktop/90.png"));
		panel.add(speed_90);
		
		JTextArea txtrDestinationSignBoard = new JTextArea();
		txtrDestinationSignBoard.setText("DESTINATION SIGN BOARD");
		txtrDestinationSignBoard.setForeground(Color.WHITE);
		txtrDestinationSignBoard.setFont(new Font("Copperplate Gothic Bold", Font.BOLD, 12));
		txtrDestinationSignBoard.setBackground(new Color(34, 139, 34));
		txtrDestinationSignBoard.setBounds(70, 414, 181, 14);
		panel.add(txtrDestinationSignBoard);
		
		JButton welcome_board = new JButton();
		welcome_board.setToolTipText("Add Welcome Board");
		welcome_board.setBounds(6, 437, 305, 70);
		welcome_board.setIcon(new ImageIcon("/Users/somya/Desktop/Welcome Board.png"));
		panel.add(welcome_board);
		
		

		
		
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
