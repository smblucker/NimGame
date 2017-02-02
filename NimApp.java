/*
 * Application class that draws game panels with components,
 * runs NimGame support class, and populates game panels and
 * components with proper values returned from support class.
 */
package nim;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author seanblucker
 */
public class NimApp extends JFrame implements ActionListener {



    private static final int ROWS = 3;

    private JTextField[] gameFields; // Where sticks for each row shown

    private JTextField rowField;     // Where player enters row to select

    private JTextField sticksField;  // Where player enters sticks to take

    private JButton playButton;      // Pressed to take sticks

    private JButton AIButton;        // Pressed to make AI's move

    

    private NimGame nim;

    

    public NimApp() {

        

        // Build the fields for the game play 

        rowField = new JTextField(5);

        sticksField = new JTextField(5);

        playButton = new JButton("PLAYER");

        AIButton = new JButton("COMPUTER");

        playButton.addActionListener(this);

        AIButton.addActionListener(this);

        AIButton.setEnabled(false);

        

        // Create the layout

        JPanel mainPanel = new JPanel(new BorderLayout());

        getContentPane().add(mainPanel);

        

        JPanel sticksPanel = new JPanel(new GridLayout(3, 1));

        mainPanel.add(sticksPanel, BorderLayout.EAST);

        

        JPanel playPanel = new JPanel(new GridLayout(3, 2));

        mainPanel.add(playPanel, BorderLayout.CENTER);

        

        // Add the fields to the play panel

        playPanel.add(new JLabel("Row: ", JLabel.RIGHT));

        playPanel.add(rowField);

        playPanel.add(new JLabel("Sticks: ", JLabel.RIGHT));

        playPanel.add(sticksField);

        playPanel.add(playButton);

        playPanel.add(AIButton);

        

        // Build the array of textfields to display the sticks

        gameFields = new JTextField[ROWS];

        for (int i = 0; i < ROWS; i++) {

            gameFields[i] = new JTextField(10);

            gameFields[i].setEditable(false);

            sticksPanel.add(gameFields[i]);

        }

        setSize(350, 150);

        setVisible(true);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Try block that validates game setup--number of sticks at array index/indices >= 1
        try
        {
            nim = new NimGame(new int[]{3, 5, 7});
        }
        
        //Catches exception prompted by invalid game setup--number of sticks at array index/indices < 1 
        catch(InvalidSticksEntryException ex)
        {
           JOptionPane.showMessageDialog(this, "Invalid number of sticks at array index/indices");
           System.exit(1);
        }           

        draw();

    }

    

    // Utility function to redraw game

    private void draw() {

        for (int row = 0; row < ROWS; row++) {

            String sticks = "";

            for (int j = 0; j < nim.getRow(row); j++) {

                sticks += "|   ";

            }

            gameFields[row].setText(sticks);

        }

        rowField.setText("");

        sticksField.setText("");

    }

    

    public void actionPerformed(ActionEvent e) {

        

        // Player move

        if (e.getSource() == playButton) {

            //Try block that validates user input
            try
            {
                // Get the row and number of sticks to take

                int row = Integer.parseInt(rowField.getText())-1;

                int sticks = Integer.parseInt(sticksField.getText());
                
                // Play that move

                nim.play(row, sticks);
                
                // Redisplay the board and enable the AI button
                draw();

                playButton.setEnabled(false);

                AIButton.setEnabled(true);
            }
            
            //Catches exception prompted by invalid user input--number of sticks selected not in proper range
            catch(IllegalSticksException ex)
            {
                JOptionPane.showMessageDialog(this, "Invalid: number of sticks taken must be in range (1-3)");
                playButton.setEnabled(true);
                AIButton.setEnabled(false);
            }
            
            //Catches exception prompted by invalid user input--row number selected not in proper range
            catch(NoSuchRowException ex)
            {
                JOptionPane.showMessageDialog(this, "Invalid: row number must be in range (1-3)");
                playButton.setEnabled(true);
                AIButton.setEnabled(false);
            }
            
            //Catches exception prompted by invalid user input--number of sticks selected exceeds number available
            catch(NotEnoughSticksException ex)
            {
                JOptionPane.showMessageDialog(this, "Invalid: number of sticks taken exceeds number of sticks in row");
                playButton.setEnabled(true);
                AIButton.setEnabled(false);
            }
            
            //Catches exception prompted by invalid user input--users entered character(s) instead integers
            catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(this, "Invalid: inputs must be numeric");
                playButton.setEnabled(true);
                AIButton.setEnabled(false);
            }

            // Determine whether the game is over

            if (nim.isOver()) {

                JOptionPane.showMessageDialog(null, "You win!");

                playButton.setEnabled(false);
                
                System.exit(1);

            }

        }

        

        // Computer move

        if (e.getSource() == AIButton) {

            

            // Determine computer move

            nim.AIMove();

            

            // Redraw board

            draw();

            AIButton.setEnabled(false);

            playButton.setEnabled(true);

            

            // Is the game over?

            if (nim.isOver()) {

                JOptionPane.showMessageDialog(null, "You win!");

                playButton.setEnabled(false);

            }

        }

    }

    

    /**

     * @param args the command line arguments

     */

    public static void main(String[] args) {

        NimApp a = new NimApp();

    }

}

