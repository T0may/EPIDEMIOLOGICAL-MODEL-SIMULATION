package epidemicsimulation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationGUI extends JFrame{
    private JPanel gridPanel;
    private JTextField diseaseNameTextField;
    private JTextField populationSizeTextField;
    private JButton startButton;

    private List<Agent> agents;

    public SimulationGUI()
    {
        setTitle("Epidemiological Model Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);


        createGridPanel();
        createControlPanel();

        pack();
        setLocationRelativeTo(null);
    }

    private void createGridPanel()
    {
        gridPanel = new JPanel(new GridLayout(50,80));

        // LAYOUT APPEARANCE OF GRID PANEL
        for(int row = 0; row < 50; row++)
        {
            for(int col = 0; col < 80; col++)
            {

                JPanel fieldPanel = new JPanel();
                fieldPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
                fieldPanel.setBackground(Color.darkGray);
                gridPanel.add(fieldPanel);
            }
        }

        add(gridPanel, BorderLayout.CENTER);
    }
    private void createControlPanel()
    {
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 2));
        controlPanel.setBackground(Color.DARK_GRAY);


        JLabel diseaseNameLabel = new JLabel("Disease Name: ");
        diseaseNameLabel.setForeground(Color.WHITE);
        diseaseNameTextField = new JTextField();

        JLabel populationSizeLabel = new JLabel("Population Size: ");
        populationSizeLabel.setForeground(Color.WHITE);
        populationSizeTextField = new JTextField();



        controlPanel.add(diseaseNameLabel);
        controlPanel.add(diseaseNameTextField);
        controlPanel.add(populationSizeLabel);
        controlPanel.add(populationSizeTextField);


        startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String diseaseName = diseaseNameTextField.getText();
                int populationSize = Integer.parseInt(populationSizeTextField.getText());

                gridPanel.removeAll();

                initializeAgents(populationSize);

                updateGridAppearance();

                gridPanel.revalidate();
                gridPanel.repaint();



//                here parameters are going to be used
                System.out.println("Disease Name: " + diseaseName);
                System.out.println("Population size: " + populationSize);
            }
        });


        JPanel controlPanel2 = new JPanel();
        controlPanel2.setLayout(new GridLayout(1, 1));
//        controlPanel2.setBackground(Color.DARK_GRAY);
        controlPanel2.add(startButton);


        add(controlPanel, BorderLayout.SOUTH);
        add(controlPanel2, BorderLayout.NORTH);
    }

    private void initializeAgents(int populationSize)
    {
        agents = new ArrayList<>();

        // create agents and randomly assing them to grid cells
        Random random = new Random();
        for (int i = 0; i < populationSize; i++)
        {
            int row = random.nextInt(50);
            int col = random.nextInt(80);

            Agent agent = new Agent(row, col);
            agents.add(agent);

        }
    }
    private void updateGridAppearance(){

        gridPanel.removeAll();

        for (int row = 0; row < 50; row++)
        {
            for (int col = 0; col < 80; col++)
            {
                JPanel cellPanel = new JPanel();
                cellPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
                cellPanel.setBackground(Color.darkGray);

                for (Agent agent : agents)
                {
                    if(agent.getRow() == row && agent.getCol() == col)
                    {
                        cellPanel.setBackground(Color.pink);
                        break;
                    }
                }
                gridPanel.add(cellPanel);
            }
        }
        gridPanel.revalidate();
        gridPanel.repaint();
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SimulationGUI gui = new SimulationGUI();
            gui.setVisible(true);
        });
    }
}
