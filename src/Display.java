import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.LinkedList;
import static java.awt.Font.*;

/**
 * This class is used to display the robot path graphically.
 * To use this class, instantiate it, pass the
 * LinkedList of the robot path to the "putResults" method, and you get the window flip
 * open. Click on "NEXT" to gradually traverse the path.
 */
public class Display extends JFrame {

    private final Environment environment;
    private Node robot;
    private final ArrayList<Node> butterPlates;
    private final ArrayList<Node> goals;
    private JLabel nextButton;
    private JLabel depthLabel;
    private JLabel costLabel;
    private JTextArea path;
    private LinkedList<Node> robotPath;
    private int cost;
    private int depth;
    private final int blockSize;
    private final MouseHandler mouseHandler;
    private int counter;

    /**
     * Instantiating this class
     * @param environment The whole desk
     */
    public Display(Environment environment) {

        mouseHandler = new MouseHandler();
        setUpFrame();
        this.environment = environment;
        robot = environment.getBlock()[environment.getStartingNode().getY()][environment.getStartingNode().getX()];
        butterPlates = new ArrayList<>();
        goals = new ArrayList<>();
        for (Node butter:
                environment.getButterPlates()) {
            butterPlates.add(environment.getBlock()[butter.getY()][butter.getX()]);
        }
        for (Node goal:
                environment.getGoals()) {
            goals.add(environment.getBlock()[goal.getY()][goal.getX()]);
        }
        cost = 0;
        blockSize = 600 / Math.max(environment.getHeight(), environment.getWidth());
        counter = 0;
    }

    /**
     * Creates new window to graphically display the robot path
     */
    private void setUpFrame() {
        this.setSize(1280, 720);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        nextButton = new JLabel("NEXT");
        nextButton.addMouseListener(mouseHandler);
        nextButton.setBorder(BorderFactory.createBevelBorder(0));
        nextButton.setBackground(new Color(100, 30, 30));
        nextButton.setForeground(new Color(100, 30, 30));
        depthLabel = new JLabel();
        costLabel = new JLabel();
        path = new JTextArea();
        path.setEnabled(false);
        path.setDisabledTextColor(Color.BLACK);
        path.setOpaque(false);
        path.setFont(new Font("", BOLD, 13));
        path.setPreferredSize(new Dimension(300, 200));
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(400, 720));
        GridBagConstraints constraints = new GridBagConstraints();
        panel.add(nextButton, constraints);
        constraints.gridy = 1;
        panel.add(path, constraints);
        constraints.gridy = 2;
        panel.add(costLabel, constraints);
        constraints.gridy = 3;
        panel.add(depthLabel, constraints);
        this.add(panel, BorderLayout.EAST);
    }

    /**
     * This method is called to display the path.
     * @param robotPath The LinkedList representing the path.
     */
    public void putResults(LinkedList<Node> robotPath) {
        this.robotPath = robotPath;
        this.setVisible(true);
    }

    /**
     * Used to paint the window component
     * @param g Graphics object, drawer of the board
     */
    private void display(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        g2D.setFont(new Font("", BOLD, 16));
        for (int i = 0; i < environment.getHeight(); ++i) {
            for (int j = 0; j < environment.getWidth(); ++j) {
                if (robot.getX() == j && robot.getY() == i) {
                    g2D.drawImage(new ImageIcon("Pics\\Robot Place.png").getImage(),
                            100 + j * blockSize, 60 + i * blockSize, blockSize, blockSize, null);
                    g2D.drawString(robot.getExpense() + "", 110 + j * blockSize, 90 + i * blockSize);
                }
                else {
                    boolean isThere = false;
                    for (Node butter:
                            butterPlates) {
                        if (butter.getX() == j && butter.getY() == i) {
                            isThere = true;
                            break;
                        }
                    }
                    if (isThere) {
                        g2D.drawImage(new ImageIcon("Pics\\Butter Plate.png").getImage(),
                                100 + j * blockSize, 60 + i * blockSize, blockSize, blockSize, null);
                        g2D.drawString(environment.getBlock()[i][j].getExpense() + "", 110 + j * blockSize, 90 + i * blockSize);
                        continue;
                    }
                    for (Node goal:
                            goals) {
                        if (goal.getX() == j && goal.getY() == i) {
                            isThere = true;
                            break;
                        }
                    }
                    if (isThere) {
                        g2D.drawImage(new ImageIcon("Pics\\Goal.png").getImage(),
                                100 + j * blockSize, 60 + i * blockSize, blockSize, blockSize, null);
                        g2D.drawString(environment.getBlock()[i][j].getExpense() + "", 110 + j * blockSize, 90 + i * blockSize);
                        continue;
                    }
                    if (environment.getBlock()[i][j].isObstacle()) {
                        g2D.drawImage(new ImageIcon("Pics\\Obstacle.png").getImage(),
                                100 + j * blockSize, 60 + i * blockSize, blockSize, blockSize, null);
                        continue;
                    }
                    g2D.drawImage(new ImageIcon("Pics\\Free Place.png").getImage(),
                            100 + j * blockSize, 60 + i * blockSize, blockSize, blockSize, null);
                    g2D.drawString(environment.getBlock()[i][j].getExpense() + "", 110 + j * blockSize, 90 + i * blockSize);
                }
            }
        }
    }

    /**
     * Processes the next move of the agent and writes it down
     */
    private void getNext() {
        if (robotPath == null) {
            path.append("Not Possible");
            nextButton.removeMouseListener(mouseHandler);
            nextButton.setBackground(new Color(150, 150, 150));
            nextButton.setForeground(new Color(150, 150, 150));
        }
        else if (robotPath.size() > 0){
            Node nextStep = robotPath.pollFirst();
            path.append(robot.relativeDirectionOf(nextStep) + "    ");
            cost += robot.getExpense();
            depth += 1;
            for (int i = 0; i < butterPlates.size(); ++i) {
                if (butterPlates.get(i).equals(nextStep)) {
                    butterPlates.set(i, nextStep.getOppositeOf(robot));
                    break;
                }
            }
            robot = nextStep;
            counter = (counter + 1) % 12;
            if (counter == 0)
                path.append("\n");
            }
        else {
            costLabel.setText("Cost: " + cost);
            depthLabel.setText("Depth: " + depth);
        }
        this.repaint();
        this.revalidate();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        display(g);
    }

    /**
     * Class handling the mouse actions on "NEXT" button
     */
    private class MouseHandler extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getSource().equals(nextButton))
                getNext();
        }
    }
}
