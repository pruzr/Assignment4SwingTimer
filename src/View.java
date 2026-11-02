
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class View implements ActionListener {

	private Ball ball;

	private JFrame frame = new JFrame("Shrinking Ball Animation");

	//  Control buttons
	private JButton startButton = new JButton("Start");
	private JButton pauseResumeButton = new JButton("Pause");
	private JButton resetButton = new JButton("Reset");

	// Time input
	private JTextField input = new JTextField(5);

	// Panels and button layout
	private JPanel controlPanel =
			new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

	// ProgressBar and countdown
	private JLabel countdownLabel = new JLabel("Time left: 0.00s");
	private JProgressBar progressBar = new JProgressBar(0, 100);

	// Timer variables
	private Timer shrinkTimer;
	private Timer animationTimer;
	private double totalTime = 0.0;
	private double remainingTime = 0.0;
	//Updates the timer every 0.05 secs
	private final int timerDelay = 50;

	public View() {

		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setLayout(new BorderLayout());
		frame.setBackground(Color.WHITE);

		ball = new Ball(400, 300, 10, 10, Color.RED, 100);
		ball.setVisible(true);
		frame.add(ball, BorderLayout.CENTER);
		
		
	

		// Set up button controls
		controlPanel.add(new JLabel("Shrink Time (secs):"));
		controlPanel.add(input);
		controlPanel.add(startButton);
		controlPanel.add(pauseResumeButton);
		controlPanel.add(resetButton);
		controlPanel.add(countdownLabel);
		

		progressBar.setValue(0);
		// Sets text inside progress bar
		progressBar.setStringPainted(true);
		controlPanel.add(progressBar);
		// Adds the buttons to the bottom of the window
		frame.add(controlPanel, BorderLayout.SOUTH);

		// Initially disable Pause
		pauseResumeButton.setEnabled(false);

		// Button listeners
		startButton.addActionListener(this);
		pauseResumeButton.addActionListener(this);
		resetButton.addActionListener(this);

		//shrinking ball timer
		shrinkTimer = new Timer(timerDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
								if (remainingTime <= 0) {
					remainingTime = 0;
					animationTimer.stop();

					input.setEnabled(true);
					startButton.setEnabled(true);
					pauseResumeButton.setEnabled(false);
					pauseResumeButton.setText("Pause");
								}
				updateBallSize();
			}
		}
	);

		// Timer
		animationTimer = new Timer(timerDelay, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				// Decreases the remaining time based on the timer delay
				remainingTime -= timerDelay / 1000.0;
				// Stops the timer when the time hits 0
				if (remainingTime <= 0) {
					remainingTime = 0;
					animationTimer.stop();

					input.setEnabled(true);
					startButton.setEnabled(true);
					pauseResumeButton.setEnabled(false);
					pauseResumeButton.setText("Pause");
				}
				updateUIState();
				
			}
		});

		frame.setVisible(true);
		frame.setResizable(false);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {


		if (arg0.getSource() == startButton) {
			try {
				// Converts input into double
				totalTime = Double.parseDouble(input.getText().trim());

				if (totalTime <= 0) {
					JOptionPane.showMessageDialog(frame,
							"Enter a valid time greater than 0 seconds.",
							"Invalid Input",
							JOptionPane.ERROR_MESSAGE);
					return;
				}

				remainingTime = totalTime;

				input.setEnabled(false);
				startButton.setEnabled(false);
				pauseResumeButton.setEnabled(true);
				pauseResumeButton.setText("Pause");

				animationTimer.start();
				shrinkTimer.start();

			}
			// Displays error message if input is less than 0
			catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(
						frame,
						"Input must be a valid number.",
						"Invalid Input",
						JOptionPane.ERROR_MESSAGE
				);
			}
		}

		else if (arg0.getSource() == pauseResumeButton) {
			if (animationTimer.isRunning()) {
				animationTimer.stop();
				pauseResumeButton.setText("Resume");
			} else {
				animationTimer.start();
				pauseResumeButton.setText("Pause");
			}
		}

		else if (arg0.getSource() == resetButton) {
			animationTimer.stop();

			totalTime = 0.0;
			remainingTime = 0.0;

			countdownLabel.setText("Time left: 0.00s");
			progressBar.setValue(0);

			input.setEnabled(true);
			startButton.setEnabled(true);
			pauseResumeButton.setEnabled(false);
			pauseResumeButton.setText("Pause");
		}
	}

	private void updateUIState() {
		// Rounds time to two decimal places
		countdownLabel.setText(String.format("Time left: %.2fs", remainingTime));

		if (totalTime > 0) {
			double elapsedTime = totalTime - remainingTime;

			// Rounds progress percent to nearest whole number
			int progressPercent = (int) Math.round((elapsedTime / totalTime) * 100);
			progressBar.setValue(progressPercent);


		}
	}
	private void updateBallSize() {
		if (totalTime > 0) {

			
			double elapsedTime = totalTime - remainingTime;
			double shrinkFactor = 1 - (elapsedTime / totalTime);
			int newRadius = (int) (10 * shrinkFactor);
			ball.setRadius(newRadius);
		}
	}
}

