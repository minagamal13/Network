
import javax.swing.*;
import jpcap.*;
import java.io.*;
//////package PkPirate_Interface;

public class PkPirate_Interface extends javax.swing.JFrame
{
    //Globals
    NetworkInterface[] NETWORK_INTERFACES;
    JpcapCaptor CAP;
    PkPirate_CaptureThread CAPTAIN;
    int INDEX = 0;
    int COUNTER = 0;
    boolean CaptureState = false;
    
    
    public void CapturePackets()
    {
        CAPTAIN = new PkPirate_CaptureThread()
        {
            @Override
            public Object construct()
            {
                TA_OUTPUT.setText("\nNow CAPTURING on Interface" + INDEX + "..."
                +"\n--------------------------------------------------" +
                        "-----------------------------------------------\n\n");
                try
                {
                CAP = JpcapCaptor.openDevice(
                NETWORK_INTERFACES[INDEX], 65535, false, 20);
                
                while(CaptureState)
                {
                    CAP.processPacket(1, new PkPirate_PacketContents());
                    //CAP.loopPacket(1,PkPirate_PacketContents());
                  // CAP.getPacket();
                }
                CAP.close();
            }
                catch(Exception X)
                {System.out.print(X);}
                return 0;
        }
            //--------------------------
            public void finished ()
            {
                this.interrupt();
            }
            //------------------------------------------------
        };
        CAPTAIN.start();
    }
    //----------------------------------------------------------------------

 

    /**
     * Creates new form PkPirate_Interface
     */
    public PkPirate_Interface() {
       initComponents();
        this.setTitle("Packets Interface");
        this.setSize(765,480);
        this.setLocation(200,200);
        DisableButtons();
    }
    //----------------------------------------------------------------

    
     public void DisableButtons()
    {
    
        B_CAPTURE.setEnabled(false);
        B_STOP.setEnabled(false);
        B_SELECT.setEnabled(false);
        B_FILTER.setEnabled(false);
        B_SAVE.setEnabled(false);
        B_LOAD.setEnabled(false);
    
    }
    
    public void EnableButtons()
    {
    
        B_CAPTURE.setEnabled(true);
        B_STOP.setEnabled(true);
        B_SELECT.setEnabled(true);
        B_FILTER.setEnabled(true);
        B_SAVE.setEnabled(true);
        B_LOAD.setEnabled(true);
    
    }
    
    //------------------------------------------
    public void ListNetworkInterfaces ()
    {
        //fill an array with all the networks interfaces on the host
        NETWORK_INTERFACES = JpcapCaptor.getDeviceList();
        
        TA_OUTPUT.setText(""); //clear text area
        
        for(int i = 0;i < NETWORK_INTERFACES.length;i++)
        {
            //display interface's info
            TA_OUTPUT.append(
            "\n\n---------------------------------------------interface"+i+
                    "Info----------------------------------------");
            
            TA_OUTPUT.append("\nInterface Number: " + i);
            TA_OUTPUT.append("\nDescription: "+
                    NETWORK_INTERFACES[i].name+ "("+
                    NETWORK_INTERFACES[i].description+")");
            TA_OUTPUT.append("\nDatalink Name: "+
                    NETWORK_INTERFACES[i].datalink_name+ "("+
                    NETWORK_INTERFACES[i].datalink_description+")");
            TA_OUTPUT.append("\nMAC address: ");
            
            
            //Example 1
            byte[] R = NETWORK_INTERFACES[i].mac_address;
            for(int A =0;A<=NETWORK_INTERFACES.length;A++)
            {TA_OUTPUT.append(Integer.toHexString(R[A] & 0xff) + ":");}
            
            //Example 2
            //for(byte X : NETWORK_INTERFACES[i].mac_address)
            //{System.out.print(Integer.toHexString(X & 0xff)+ ":");}
            
            //Example 1
            
            NetworkInterfaceAddress [] INT = NETWORK_INTERFACES[i].addresses;
            TA_OUTPUT.append("\nIP Address: " + INT[0].address);
            TA_OUTPUT.append("\nSubnet Mask: " + INT[0].subnet);
            TA_OUTPUT.append("\nBroadcast Address: " + INT[0].broadcast);
            
            
            //Example 2 does the same as above but different way
            //for(NetworkInterfaceAddress INIF : NETWORK_INTERFACES[i].addresses)
            //{
            //System.out.println("IP Address: " + INIF.address);
            //System.out.println("Subnet Mask: " + INIF.subnet);
            //System.out.println("Broadcast Address: " + INIF.broadcast);
            //}
            COUNTER ++;
            
            
            
      
        }
    }
    
    //-----------------------------------------------------------------
    public void ChooseInterface()
    {
       int TEMP = Integer.parseInt(TF_SelectInterface.getText());
       if(TEMP > -1 && TEMP < COUNTER)
       {
          INDEX = TEMP;
          EnableButtons();
          
       
       }
       
       else
       {
           JOptionPane.showMessageDialog(
           null,"Outside of Range. #interfaces = 0-" + (COUNTER - 1) + ".");
        
          
       
       }
         TF_SelectInterface.setText("");
    }
    
    //---------------------------------------------------------------------
    
    public static void SaveCaptureData()
    {
        String CaptureData = TA_OUTPUT.getText();
        
        try
        {
            File DATA = new File("CaptureData.txt");
            FileOutputStream DATASTREAM = new FileOutputStream(DATA);
            PrintStream OUT = new PrintStream(DATASTREAM);
            
            OUT.print(CaptureData);
            
            OUT.close();
            DATASTREAM.close();
            JOptionPane.showMessageDialog(null,"Data SAVED successfully");
            
        
        
        }
        
    catch (Exception X)
    {JOptionPane.showMessageDialog(null,"File Access Error ! Could not save data.");}
    
  
    }
    
    //---------------------------------------------------------
    
    public static void LoadCaptureData()
    {
       String CaptureData= "";
       try
       {
          File DATA = new File("CaptureData.txt");
          FileInputStream DATASTREAM = new FileInputStream(DATA);
          InputStreamReader INPUT = new InputStreamReader(DATASTREAM);
          BufferedReader IN = new BufferedReader(INPUT);
          
          
          while(IN.read() != -1)
          {
              CaptureData = CaptureData + IN.readLine();
          
          
       
          }
       
         IN.close();
         INPUT.close();
         DATASTREAM.close();
         
         TA_OUTPUT.setText(CaptureData);
         
         JOptionPane.showMessageDialog(null,"Data LOADED successfully !");
       
       
       }
    
    catch(IOException X)
    {
        JOptionPane.showMessageDialog(null,"File Access Error ! couldn't load data");
    
    }
      
    
    }    
    
  
    
    
//System.out.println("IP Address: " + INIF.address);
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
   
    
/*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        B_CAPTURE = new javax.swing.JButton();
        B_STOP = new javax.swing.JButton();
        B_SAVE = new javax.swing.JButton();
        B_LOAD = new javax.swing.JButton();
        B_EXIT = new javax.swing.JButton();
        B_SELECT = new javax.swing.JButton();
        B_LIST = new javax.swing.JButton();
        TF_SelectInterface = new javax.swing.JTextField();
        L_FilterStatus = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        RB_Filter_Enable = new javax.swing.JRadioButton();
        RB_Filter_Disable = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        TF_SpecialPort = new javax.swing.JTextField();
        RB_Port_Special = new javax.swing.JRadioButton();
        B_FILTER = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        RB_Port_HTTP = new javax.swing.JRadioButton();
        RB_Port_SSL = new javax.swing.JRadioButton();
        RB_Port_FTP = new javax.swing.JRadioButton();
        RB_Port_SSH = new javax.swing.JRadioButton();
        RB_Port_Telnet = new javax.swing.JRadioButton();
        RB_Port_SMTP = new javax.swing.JRadioButton();
        RB_Port_POP3 = new javax.swing.JRadioButton();
        RB_Port_IMAP = new javax.swing.JRadioButton();
        RB_Port_IMAPS = new javax.swing.JRadioButton();
        RB_Port_DNS = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        RB_Port_NetBIOS = new javax.swing.JRadioButton();
        RB_Port_SAMBA = new javax.swing.JRadioButton();
        RB_Port_AD = new javax.swing.JRadioButton();
        RB_Port_SQL = new javax.swing.JRadioButton();
        RB_Port_LDAP = new javax.swing.JRadioButton();
        SP_OUTPUT = new javax.swing.JScrollPane();
        TA_OUTPUT = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        B_CAPTURE.setText("CAPTURE");
        B_CAPTURE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_CAPTUREActionPerformed(evt);
            }
        });

        B_STOP.setText("STOP");
        B_STOP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_STOPActionPerformed(evt);
            }
        });

        B_SAVE.setText("SAVE");
        B_SAVE.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_SAVEActionPerformed(evt);
            }
        });

        B_LOAD.setText("LOAD");
        B_LOAD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_LOADActionPerformed(evt);
            }
        });

        B_EXIT.setText("EXIT");
        B_EXIT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_EXITActionPerformed(evt);
            }
        });

        B_SELECT.setText("SELECT");
        B_SELECT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_SELECTActionPerformed(evt);
            }
        });

        B_LIST.setText("LIST");
        B_LIST.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_LISTActionPerformed(evt);
            }
        });

        jButton15.setText("DISABLED (ALL PORTS)");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        RB_Filter_Enable.setText("Enable");
        RB_Filter_Enable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_Filter_EnableActionPerformed(evt);
            }
        });

        RB_Filter_Disable.setText("Disable");
        RB_Filter_Disable.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_Filter_DisableActionPerformed(evt);
            }
        });

        jLabel1.setText("Special Port #");

        TF_SpecialPort.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TF_SpecialPortActionPerformed(evt);
            }
        });

        RB_Port_Special.setText("Special Port");

        B_FILTER.setText("FILTER");
        B_FILTER.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_FILTERActionPerformed(evt);
            }
        });

        jLabel2.setText("Port Filter Presents ");

        RB_Port_HTTP.setText("HTTP (80)");

        RB_Port_SSL.setText("HTTP SSL (443)");

        RB_Port_FTP.setText("FTP (20+21)");

        RB_Port_SSH.setText("SSH (22)");

        RB_Port_Telnet.setText("Telnet (23)");
        RB_Port_Telnet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_Port_TelnetActionPerformed(evt);
            }
        });

        RB_Port_SMTP.setText("SMTP (25)");
        RB_Port_SMTP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_Port_SMTPActionPerformed(evt);
            }
        });

        RB_Port_POP3.setText("POP 3 (110)");
        RB_Port_POP3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_Port_POP3ActionPerformed(evt);
            }
        });

        RB_Port_IMAP.setText("IMAP (143)");

        RB_Port_IMAPS.setText("IMAPS (993)");

        RB_Port_DNS.setText("DNS (53)");
        RB_Port_DNS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_Port_DNSActionPerformed(evt);
            }
        });

        jLabel3.setText("Interface");

        RB_Port_NetBIOS.setText("NetBios (139)");
        RB_Port_NetBIOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_Port_NetBIOSActionPerformed(evt);
            }
        });

        RB_Port_SAMBA.setText("Samba (137)");

        RB_Port_AD.setText("AD (445)");

        RB_Port_SQL.setText("SQL (118)");

        RB_Port_LDAP.setText("LDAP (389)");
        RB_Port_LDAP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_Port_LDAPActionPerformed(evt);
            }
        });

        TA_OUTPUT.setColumns(20);
        TA_OUTPUT.setRows(5);
        SP_OUTPUT.setViewportView(TA_OUTPUT);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SP_OUTPUT)
                        .addGap(709, 709, 709))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TF_SelectInterface, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(B_CAPTURE, javax.swing.GroupLayout.PREFERRED_SIZE, 179, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(B_SELECT)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(B_LIST)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 170, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(B_FILTER)
                                .addComponent(RB_Port_Special))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(B_SAVE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(5, 5, 5)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(TF_SpecialPort, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(B_EXIT)
                                                .addGap(95, 95, 95)
                                                .addComponent(jLabel1))))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(261, 261, 261)
                                        .addComponent(L_FilterStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(B_LOAD)
                                                .addGap(140, 140, 140))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addComponent(B_STOP)
                                                .addGap(187, 187, 187)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(RB_Filter_Enable)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(RB_Filter_Disable))
                                            .addComponent(jButton15))))
                                .addGap(19, 19, 19)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 266, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RB_Port_HTTP)
                                    .addComponent(RB_Port_SSL)
                                    .addComponent(RB_Port_FTP)
                                    .addComponent(RB_Port_SSH)
                                    .addComponent(RB_Port_Telnet))
                                .addGap(29, 29, 29)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(RB_Port_DNS)
                                    .addComponent(RB_Port_IMAPS)
                                    .addComponent(RB_Port_IMAP)
                                    .addComponent(RB_Port_POP3)
                                    .addComponent(RB_Port_SMTP)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(12, 12, 12)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(RB_Port_SQL)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(19, 19, 19)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(RB_Port_NetBIOS)
                                        .addComponent(RB_Port_SAMBA)
                                        .addComponent(RB_Port_AD)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(24, 24, 24)
                                    .addComponent(RB_Port_LDAP))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(SP_OUTPUT, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(B_CAPTURE)
                            .addComponent(L_FilterStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6))
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(B_STOP)
                                    .addComponent(TF_SelectInterface, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(B_SAVE)
                                    .addComponent(B_LOAD)
                                    .addComponent(jButton15))
                                .addGap(41, 41, 41)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(B_EXIT)
                                    .addComponent(jLabel1)
                                    .addComponent(RB_Port_Special))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(TF_SpecialPort, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(B_FILTER)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(B_SELECT)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(B_LIST))))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RB_Port_HTTP)
                            .addComponent(RB_Port_SMTP)
                            .addComponent(RB_Port_NetBIOS))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RB_Port_SSL)
                            .addComponent(RB_Port_POP3)
                            .addComponent(RB_Filter_Disable)
                            .addComponent(RB_Filter_Enable)
                            .addComponent(RB_Port_SAMBA))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RB_Port_FTP)
                            .addComponent(RB_Port_IMAP)
                            .addComponent(RB_Port_AD))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RB_Port_SSH)
                            .addComponent(RB_Port_IMAPS)
                            .addComponent(RB_Port_SQL))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RB_Port_Telnet)
                            .addComponent(RB_Port_DNS)
                            .addComponent(RB_Port_LDAP))))
                .addContainerGap(350, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
  */

 @SuppressWarnings("unchecked")
 
    private void B_STOPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_STOPActionPerformed
        CaptureState = false;
        CAPTAIN.finished();
    }//GEN-LAST:event_B_STOPActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton15ActionPerformed

    private void RB_Port_TelnetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_Port_TelnetActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_Port_TelnetActionPerformed

    private void RB_Port_POP3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_Port_POP3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_Port_POP3ActionPerformed

    private void RB_Port_DNSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_Port_DNSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_Port_DNSActionPerformed

    private void RB_Port_NetBIOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_Port_NetBIOSActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_Port_NetBIOSActionPerformed

    private void RB_Port_LDAPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_Port_LDAPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_Port_LDAPActionPerformed

    private void B_SELECTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_SELECTActionPerformed
        ChooseInterface();
    }//GEN-LAST:event_B_SELECTActionPerformed

    private void B_CAPTUREActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_CAPTUREActionPerformed
        TA_OUTPUT.setText("");
        CaptureState = true;
        CapturePackets();
    }//GEN-LAST:event_B_CAPTUREActionPerformed

    private void B_SAVEActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_SAVEActionPerformed
        SaveCaptureData();
    }//GEN-LAST:event_B_SAVEActionPerformed

    private void B_LOADActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_LOADActionPerformed
        LoadCaptureData();
    }//GEN-LAST:event_B_LOADActionPerformed

    private void B_EXITActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_EXITActionPerformed
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_B_EXITActionPerformed

    private void B_FILTERActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_FILTERActionPerformed
        try
        {
if(RB_Filter_Enable.isSelected())
{
if(RB_Port_Special.isSelected())
{
  String PORT = TF_SpecialPort.getText();
CAP.setFilter("port" + PORT , true);



}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 80",true);}

else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 443",true);}

else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 21",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 22",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 23",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 25",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 110",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 143",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 993",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 53",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 139",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 137",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 445",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 118",true);}
else if(RB_Port_HTTP.isSelected())
{CAP.setFilter("port 389",true);}
}
else
{JOptionPane.showMessageDialog(null,"Filtering is disabled");
}


}
catch(Exception X ){System.out.print(X);}
        
    }//GEN-LAST:event_B_FILTERActionPerformed

    private void RB_Filter_EnableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_Filter_EnableActionPerformed
       
    }//GEN-LAST:event_RB_Filter_EnableActionPerformed

    private void RB_Filter_DisableActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_Filter_DisableActionPerformed
       
    }//GEN-LAST:event_RB_Filter_DisableActionPerformed

    private void RB_Port_SMTPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_Port_SMTPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_Port_SMTPActionPerformed

    private void TF_SpecialPortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TF_SpecialPortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TF_SpecialPortActionPerformed

    private void B_LISTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_LISTActionPerformed
        ListNetworkInterfaces();
        B_SELECT.setEnabled(true);
        TF_SelectInterface.requestFocus();
    }//GEN-LAST:event_B_LISTActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        java.awt.EventQueue.invokeLater(new Runnable() {

public void run()
{

new PkPirate_Interface().setVisible(true);


}
});
}


/////////////////###############%%%%%%%%%%%%%%%%%%%%%%%%

      

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_CAPTURE;
    private javax.swing.JButton B_EXIT;
    private javax.swing.JButton B_FILTER;
    private javax.swing.JButton B_LIST;
    private javax.swing.JButton B_LOAD;
    private javax.swing.JButton B_SAVE;
    private javax.swing.JButton B_SELECT;
    private javax.swing.JButton B_STOP;
    private javax.swing.JTextField L_FilterStatus;
    private javax.swing.JRadioButton RB_Filter_Disable;
    private javax.swing.JRadioButton RB_Filter_Enable;
    private javax.swing.JRadioButton RB_Port_AD;
    private javax.swing.JRadioButton RB_Port_DNS;
    private javax.swing.JRadioButton RB_Port_FTP;
    private javax.swing.JRadioButton RB_Port_HTTP;
    private javax.swing.JRadioButton RB_Port_IMAP;
    private javax.swing.JRadioButton RB_Port_IMAPS;
    private javax.swing.JRadioButton RB_Port_LDAP;
    private javax.swing.JRadioButton RB_Port_NetBIOS;
    private javax.swing.JRadioButton RB_Port_POP3;
    private javax.swing.JRadioButton RB_Port_SAMBA;
    private javax.swing.JRadioButton RB_Port_SMTP;
    private javax.swing.JRadioButton RB_Port_SQL;
    private javax.swing.JRadioButton RB_Port_SSH;
    private javax.swing.JRadioButton RB_Port_SSL;
    private javax.swing.JRadioButton RB_Port_Special;
    private javax.swing.JRadioButton RB_Port_Telnet;
    private javax.swing.JScrollPane SP_OUTPUT;
    public static javax.swing.JTextArea TA_OUTPUT;
    private javax.swing.JTextField TF_SelectInterface;
    private javax.swing.JTextField TF_SpecialPort;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton15;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables

    private void initComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
