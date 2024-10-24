import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Layout implements ActionListener {
        private JFrame mainFrame;
        private JLabel statusLabel;
        private JLabel statusLabel1;
        private JLabel statusLabel2;
        private JTextArea ta; // Text area in the middle
        private JTextArea ta1;
        private JTextArea ta2;
        private JPanel gamePanel;
        private JPanel smallPanel;
        private JMenuBar mb;
        private JMenu file, edit, help;
        private JScrollPane scroll;
        private JMenuItem cut, copy, paste, selectAll;
        private int WIDTH=800;
        private int HEIGHT=700;


        public Layout() {
            prepareGUI();
        }

        public static void main(String[] args) {
            Layout Layout = new Layout();
            Layout.showEventDemo();
        }

        private void prepareGUI() {
            mainFrame = new JFrame("Pokédex");
            mainFrame.setSize(WIDTH, HEIGHT);
            mainFrame.setLayout(new GridLayout());

            //menu at top
            cut = new JMenuItem("cut");
            copy = new JMenuItem("copy");
            paste = new JMenuItem("paste");
            selectAll = new JMenuItem("selectAll");
            cut.addActionListener(this);
            copy.addActionListener(this);
            paste.addActionListener(this);
            selectAll.addActionListener(this);

            // Adding JTextArea in the middle
            ta = new JTextArea();
            ta.setRows(3); // Adjust rows for size
            ta.setColumns(20); // Adjust columns for width


//            mb = new JMenuBar();
//            file = new JMenu("File");
//            edit = new JMenu("Edit");
//            help = new JMenu("Help");
//            edit.add(cut);
//            edit.add(copy);
//            edit.add(paste);
//            edit.add(selectAll);
//            mb.add(file);
//            mb.add(edit);
//            mb.add(help);
////            //end menu at top


//            mainFrame.add(ta);
//            mainFrame.add(mb);  //add menu bar
//            mainFrame.add(ta);//add typing area
//            mainFrame.setJMenuBar(mb); //set menu bar

            statusLabel = new JLabel("Type in your Pokemon", JLabel.CENTER);
            statusLabel.setSize(350, 100);

//            statusLabel1 = new JLabel("input a search term", JLabel.CENTER);
//            statusLabel1.setSize(350, 100);

            statusLabel2 = new JLabel("Abilities", JLabel.CENTER);
            statusLabel2.setSize(350, 100);

            statusLabel.setFont(new Font("Silom", Font.PLAIN,14));
//            statusLabel1.setFont(new Font("Silom", Font.PLAIN,14));
            statusLabel2.setFont(new Font("Silom", Font.PLAIN,14));




            mainFrame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent windowEvent) {
                    System.exit(0);
                }
            });

            gamePanel = new JPanel();
            smallPanel = new JPanel();

            gamePanel.setLayout(new GridLayout(7,1));
            smallPanel.setLayout(new GridLayout(1,2));
            //   mainFrame.add(scrollPane);

            ta = new JTextArea(); //A text area so the user can input an url
            ta.setBounds(300, 5, WIDTH-100, HEIGHT-100);

//            ta1 = new JTextArea();  // A text area so the user can input a search term
//            ta1.setBounds(300, 5, WIDTH-100, HEIGHT-100);

            ta2 = new JTextArea();  // A text area that has the links from the url that contain the search term displayed
            ta2.setBounds(300, 5, WIDTH-100, HEIGHT-100);

            mainFrame.add(gamePanel);
            mainFrame.setVisible(true);
        }
    public  void pull() throws ParseException {
        String output = "abc";
        String totlaJson="";
        try {

            URL url = new URL("https://pokeapi.co/api/v2/pokemon/ditto");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {

                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));


            System.out.println("Output from Server .... \n");
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                totlaJson+=output;
            }

            conn.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONParser parser = new JSONParser();
        //System.out.println(str);
        org.json.simple.JSONObject jsonObject = (org.json.simple.JSONObject) parser.parse(totlaJson);
        System.out.println(jsonObject);

        try {

            String name = (String)jsonObject.get("name");
            System.out.println(name);

            org.json.simple.JSONArray msg = (org.json.simple.JSONArray) jsonObject.get("abilities");
            int n =   msg.size(); //(msg).length();
            for (int i = 0; i < n; ++i) {
                JSONObject test =(JSONObject) msg.get(i);
                System.out.println(test);
                JSONObject hb =(JSONObject) test.get("ability");
                System.out.println(hb);
                String benz =(String) hb.get("name");
                System.out.println(benz);
                // System.out.println(person.getInt("key"));
            }


            //  String height = (String)jsonObject.get("height");
            //    String birthYear = (String)jsonObject.get("birth_year");
//            System.out.println(birthYear);
//            System.out.println(height);
//
//            String eyecolor = (String)jsonObject.get("brown");
//            System.out.println(eyecolor);
//
//            String mass = (String)jsonObject.get("151");
//            System.out.println(mass);


        }

        catch (Exception e) {
            e.printStackTrace();
        }




    }

        public void showEventDemo() {

            JButton startButton = new JButton("Start");

            startButton.setActionCommand("Start");

            JButton resetButton = new JButton("Reset");

            resetButton.setActionCommand("Reset");

            startButton.addActionListener(new ButtonClickListener());

            resetButton.addActionListener(new ButtonClickListener());

            scroll = new JScrollPane(ta2);
            gamePanel.add(statusLabel);
            gamePanel.add(ta);
//            gamePanel.add(statusLabel1);
//            gamePanel.add(ta1);
            gamePanel.add(statusLabel2);
            gamePanel.add(scroll);
            gamePanel.add(smallPanel);
            smallPanel.add(resetButton);
            smallPanel.add(startButton);

            mainFrame.setVisible(true);
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cut)
                ta.cut();
            if (e.getSource() == paste)
                ta.paste();
            if (e.getSource() == copy)
                ta.copy();
            if (e.getSource() == selectAll)
                ta.selectAll();

        }

        private class ButtonClickListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("Reset")) {
                    ta.setText("");
                    ta1.setText("");
                    ta2.setText("");

                }
                if (command.equals("Start")) {
//                statusLabel.setText(");
                    try {
                        pull();
                    } catch (ParseException ex) {
                        throw new RuntimeException(ex);
                    }
                } else if (command.equals("Submit")) {
                    statusLabel.setText("Submit Button clicked.");
                } else {
                    statusLabel.setText("Reset Button clicked.");
                }
            }
        }
    }

