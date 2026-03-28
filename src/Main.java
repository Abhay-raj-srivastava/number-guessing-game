import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Random;

public class Main extends JFrame {
    private final JLabel titleLabel;
    private final JLabel infoLabel;
    private final JLabel attemptsLabel;
    private final JLabel scoreLabel;
    private final JLabel bestScoreLabel;
    private final JTextField guessField;
    private final JButton guessButton;
    private final JButton restartButton;
    private final JComboBox<String> difficultyBox;

    private int number;
    private int maxAttempts;
    private int attemptsLeft;
    private int high;
    private int bestScore = 0;

    public Main() {
        setTitle("Number Guessing Game");
        setSize(520, 420);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        titleLabel = new JLabel("🎮 Number Guessing Game");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        difficultyBox = new JComboBox<>(new String[]{"Easy (1-30)", "Medium (1-100)", "Hard (1-1000)"});
        difficultyBox.setMaximumSize(new Dimension(200, 35));
        difficultyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        difficultyBox.addActionListener(e -> startNewGame());

        infoLabel = new JLabel("Select difficulty and start guessing!");
        infoLabel.setFont(new Font("SansSerif", Font.PLAIN, 16));
        infoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        attemptsLabel = new JLabel();
        attemptsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        scoreLabel = new JLabel("Score: 0");
        scoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        bestScoreLabel = new JLabel("Best Score: 0");
        bestScoreLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        guessField = new JTextField();
        guessField.setMaximumSize(new Dimension(200, 35));
        guessField.setHorizontalAlignment(JTextField.CENTER);
        guessField.setFont(new Font("SansSerif", Font.PLAIN, 18));

        guessButton = new JButton("Submit Guess");
        guessButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessButton.addActionListener(e -> checkGuess());

        restartButton = new JButton("Restart Game");
        restartButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        restartButton.addActionListener(e -> startNewGame());

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(difficultyBox);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(attemptsLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(scoreLabel);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(bestScoreLabel);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(guessField);
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(guessButton);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(restartButton);

        add(mainPanel);
        startNewGame();
    }

    private void startNewGame() {
        int level = difficultyBox.getSelectedIndex();

        switch (level) {
            case 0:
                high = 30;
                maxAttempts = 7;
                break;
            case 1:
                high = 100;
                maxAttempts = 5;
                break;
            default:
                high = 1000;
                maxAttempts = 3;
                break;
        }

        number = new Random().nextInt(high) + 1;
        attemptsLeft = maxAttempts;
        infoLabel.setText("Guess a number between 1 and " + high);
        attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        scoreLabel.setText("Score: 0");
        guessField.setText("");
        guessField.setEnabled(true);
        guessButton.setEnabled(true);
    }

    private void checkGuess() {
        String input = guessField.getText().trim();

        if (input.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a number.");
            return;
        }

        int guess;
        try {
            guess = Integer.parseInt(input);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input! Enter numbers only.");
            return;
        }

        attemptsLeft--;

        if (guess == number) {
            int score = attemptsLeft * 10 + 10;
            scoreLabel.setText("Score: " + score);

            if (score > bestScore) {
                bestScore = score;
                bestScoreLabel.setText("Best Score: " + bestScore);
            }

            infoLabel.setText("🎉 Correct! The number was " + number);
            endGame();
            return;
        }

        if (attemptsLeft == 0) {
            infoLabel.setText("❌ Game Over! Number was " + number);
            attemptsLabel.setText("Attempts Left: 0");
            endGame();
            return;
        }

        if (guess > number) {
            infoLabel.setText("📈 Too High! Try again.");
        } else {
            infoLabel.setText("📉 Too Low! Try again.");
        }

        attemptsLabel.setText("Attempts Left: " + attemptsLeft);
        guessField.setText("");
    }

    private void endGame() {
        guessField.setEnabled(false);
        guessButton.setEnabled(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}

