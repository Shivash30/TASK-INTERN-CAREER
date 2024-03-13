/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main.student;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import connect.ConnectDB;

/**
 *
 * @author Shivash Jurakan
 */
public class Exam extends javax.swing.JFrame {
    int studentNumber;
    String examCode;
    int duration;
    
    int maxQ;
    int qCount = 0;
    double score = 0;
    double grade = 0;
            
    String userAns;
    ButtonGroup grp = new ButtonGroup();

    String question, choiceA, choiceB, choiceC, choiceD, answer;
    ResultSet rsQ;
    PreparedStatement pstmtQ;
    
    ArrayList<Object> question_bank = new ArrayList<>();

    /**
     * Creates new form ExamForm
     */
    public Exam() {
        initComponents();
        /**getContentPane().setBackground(Color.BLACK);

        grp.add(rbtn1);
        grp.add(rbtn2);
        grp.add(rbtn3);
        grp.add(rbtn4);
        setUpUI();
        getQuestions();
        displayQ(); **/
    }

    public Exam(int studentNumber, String examCode, int duration) {
        initComponents();
        
        getContentPane().setBackground(Color.BLACK);
        this.studentNumber = studentNumber;
        this.examCode = examCode;
        this.duration = duration;
        
        grp.add(rbtn1);
        grp.add(rbtn2);
        grp.add(rbtn3);
        grp.add(rbtn4);
        setUpUI();
        getQuestions();
        displayQ();
    }

    public void setUpUI() {
        txtStuNo.setText(String.valueOf(studentNumber));

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = today.format(formatter);
        lblDate.setText(formattedDate);

        getNoQuestions();
        txtTotalQ.setText(String.valueOf(maxQ));
        
        lblExamCode.setText(examCode);
        
        lblTotalTimer.setText(String.valueOf(duration));
        
        new Timerclass1(lblTimeTaken, Integer.parseInt(lblTotalTimer.getText()));
        
        //txtQNo.setText(String.valueOf(qCount + 1));
    }
    
    public void recordResult() {
        grade = (score/maxQ)*100;
        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "INSERT INTO grade (exam_id, student_id, grade) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, getExamIdFromCode(con, examCode));
                pstmt.setInt(2, studentNumber);
                pstmt.setInt(3, (int)grade);
                pstmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private static int getExamIdFromCode(Connection con, String examCode) throws SQLException {
        String query = "SELECT exam_id FROM exam WHERE exam_code = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, examCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("exam_id");
                } else {
                    return -1; // Return -1 if no exam found with the given code
                }
            }
        }
    }
    
    public void getQuestions() {
        /**
         * try (Connection con = getConnection()) { String sql = "SELECT
         * q.question, q.a_choice, q.b_choice, q.c_choice, q.d_choice, q.answer
         * FROM question_bank q INNER JOIN exam e ON q.exam_id = e.exam_id WHERE
         * e.exam_code = ?"; PreparedStatement pstmt =
         * con.prepareStatement(sql); pstmt.setString(1, examCode); rsQ =
         * pstmt.executeQuery(); } catch (SQLException e) {
         *
         * } *
         */

        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "SELECT q.question, q.a_choice, q.b_choice, q.c_choice, q.d_choice, q.answer FROM question_bank q INNER JOIN exam e ON q.exam_id = e.exam_id WHERE e.exam_code = ?";
            pstmtQ = con.prepareStatement(sql);
            pstmtQ.setString(1, examCode);
            rsQ = pstmtQ.executeQuery();
            // PreparedStatement and ResultSet are closed here
            while(rsQ.next()){
                question = rsQ.getString("question");
                choiceA = rsQ.getString("a_choice");
                choiceB = rsQ.getString("b_choice");
                choiceC = rsQ.getString("c_choice");
                choiceD = rsQ.getString("d_choice");
                answer = rsQ.getString("answer");
                String[] arr = new String[]{question, choiceA, choiceB, choiceC, choiceD, answer};
                
                question_bank.add(arr);
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    
    public void displayQ(){
            userAns = "";
            String[] temp = (String[])question_bank.get(qCount);
            
            lblQuestion.setText(temp[0]);
            rbtn1.setText(temp[1]);
            rbtn2.setText(temp[2]);
            rbtn3.setText(temp[3]);
            rbtn4.setText(temp[4]);
            
            answer = temp[5];
        
            txtQNo.setText(String.valueOf(qCount + 1));    
    }

    public void getNoQuestions() {
        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "SELECT COUNT(*) AS record_count FROM question_bank q INNER JOIN exam e ON q.exam_id = e.exam_id WHERE e.exam_code = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, examCode);
                try (ResultSet rs = pstmt.executeQuery()) {
                    if (rs.next()) {
                        maxQ = rs.getInt("record_count");
                    }
                }
            }
        } catch (SQLException e) {

        }
    }
 
    //**************************************************************************
    //                  Inner Timer Class
    //**************************************************************************
    public class Timerclass1 {

        Timer timer;
        int timeLeftInSeconds; // Change this value to set the countdown duration
        //30 is the time in minutes

        public Timerclass1(JLabel lbl, int timeMin) {
            this.timeLeftInSeconds = timeMin * 60;
            timer = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Code to be executed when the timer fires
                    if (timeLeftInSeconds > 0) {
                        updateTimerLabel(lbl);
                        timeLeftInSeconds--;
                    } else {
                        timer.stop();
                        setVisible(false);
                        recordResult();
                        new stu_Menu(studentNumber).setVisible(true);
                    }
                }
            });

            // Start the timer
            timer.start();
        }

        private void updateTimerLabel(JLabel lbl) {
            int minutes = timeLeftInSeconds / 60;
            int seconds = timeLeftInSeconds % 60;
            lbl.setText(String.format("%02d:%02d", minutes, seconds));
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        lblQuestion = new javax.swing.JLabel();
        rbtn1 = new javax.swing.JRadioButton();
        rbtn2 = new javax.swing.JRadioButton();
        rbtn3 = new javax.swing.JRadioButton();
        rbtn4 = new javax.swing.JRadioButton();
        btnNext = new javax.swing.JButton();
        btnSubmit = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtStuNo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtTotalQ = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtQNo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        lblTotalTimer = new javax.swing.JLabel();
        lblTimeTaken = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        lblExamCode = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(239, 183, 0));

        lblQuestion.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblQuestion.setText("Question");

        rbtn1.setBackground(new java.awt.Color(239, 183, 0));
        rbtn1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        rbtn1.setText("Option 1");
        rbtn1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbtn1MouseClicked(evt);
            }
        });

        rbtn2.setBackground(new java.awt.Color(239, 183, 0));
        rbtn2.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        rbtn2.setText("Option 2");
        rbtn2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbtn2MouseClicked(evt);
            }
        });

        rbtn3.setBackground(new java.awt.Color(239, 183, 0));
        rbtn3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        rbtn3.setText("Option 3");
        rbtn3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbtn3MouseClicked(evt);
            }
        });
        rbtn3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbtn3ActionPerformed(evt);
            }
        });

        rbtn4.setBackground(new java.awt.Color(239, 183, 0));
        rbtn4.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        rbtn4.setText("Option 4");
        rbtn4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                rbtn4MouseClicked(evt);
            }
        });

        btnNext.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btnNext.setText("Next");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        btnSubmit.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btnSubmit.setText("Submit");
        btnSubmit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSubmitActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnNext)
                        .addGap(18, 18, 18)
                        .addComponent(btnSubmit))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rbtn1)
                            .addComponent(lblQuestion)
                            .addComponent(rbtn2)
                            .addComponent(rbtn3)
                            .addComponent(rbtn4))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblQuestion)
                .addGap(46, 46, 46)
                .addComponent(rbtn1)
                .addGap(47, 47, 47)
                .addComponent(rbtn2)
                .addGap(47, 47, 47)
                .addComponent(rbtn3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(rbtn4)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSubmit)
                            .addComponent(btnNext))
                        .addContainerGap())))
        );

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Student Number:");

        txtStuNo.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Total Question");

        txtTotalQ.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Question Number");

        txtQNo.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Date:");

        lblDate.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("DD/MM/YYYY");

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Total Time:");

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Time Taken:");

        lblTotalTimer.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblTotalTimer.setForeground(new java.awt.Color(255, 255, 255));
        lblTotalTimer.setText("tt Minutes");

        lblTimeTaken.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblTimeTaken.setForeground(new java.awt.Color(255, 255, 255));
        lblTimeTaken.setText("M SS");

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Exam Code:");

        lblExamCode.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        lblExamCode.setForeground(new java.awt.Color(255, 255, 255));
        lblExamCode.setText("CCXXX");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtTotalQ, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(txtStuNo, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(txtQNo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblExamCode)
                        .addGap(125, 125, 125)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lblDate)
                        .addGap(128, 128, 128)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblTotalTimer))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8)
                                .addGap(2, 2, 2)
                                .addComponent(lblTimeTaken)))
                        .addGap(49, 49, 49))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(lblTotalTimer)
                    .addComponent(jLabel12)
                    .addComponent(lblExamCode)
                    .addComponent(jLabel5)
                    .addComponent(lblDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTimeTaken)
                    .addComponent(jLabel8))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtStuNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTotalQ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtQNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(205, 205, 205))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void rbtn3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbtn3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rbtn3ActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        // TODO add your handling code here:
        if((rbtn1.isSelected()) || (rbtn2.isSelected()) || (rbtn3.isSelected()) || (rbtn4.isSelected())){
            if(userAns.equals(answer)){
                score++;
            }
           
           grp.clearSelection();
            
           qCount++;
           displayQ();
        }
        else{
            JOptionPane.showMessageDialog(null, "No answer was selected!");
        }
        
        if(qCount == maxQ-1){
            btnNext.setVisible(false);
        }
        
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnSubmitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSubmitActionPerformed
        // TODO add your handling code here:
        if((rbtn1.isSelected()) || (rbtn2.isSelected()) || (rbtn3.isSelected()) || (rbtn4.isSelected())){
            if(userAns.equals(answer)){
                score++;
            }
            recordResult();
            JOptionPane.showMessageDialog(null, "Total Score is " + grade);
            setVisible(false);
            new stu_Menu(studentNumber).setVisible(true);
        }
        else{
            JOptionPane.showMessageDialog(null, "No answer was selected!");
        }
   
    }//GEN-LAST:event_btnSubmitActionPerformed

    private void rbtn1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbtn1MouseClicked
        // TODO add your handling code here:
        userAns = rbtn1.getText();
    }//GEN-LAST:event_rbtn1MouseClicked

    private void rbtn2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbtn2MouseClicked
        // TODO add your handling code here:
        userAns = rbtn2.getText();
    }//GEN-LAST:event_rbtn2MouseClicked

    private void rbtn3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbtn3MouseClicked
        // TODO add your handling code here:
        userAns = rbtn3.getText();
    }//GEN-LAST:event_rbtn3MouseClicked

    private void rbtn4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_rbtn4MouseClicked
        // TODO add your handling code here:
        userAns = rbtn4.getText();
    }//GEN-LAST:event_rbtn4MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Exam.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Exam().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnSubmit;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblExamCode;
    private javax.swing.JLabel lblQuestion;
    private javax.swing.JLabel lblTimeTaken;
    private javax.swing.JLabel lblTotalTimer;
    private javax.swing.JRadioButton rbtn1;
    private javax.swing.JRadioButton rbtn2;
    private javax.swing.JRadioButton rbtn3;
    private javax.swing.JRadioButton rbtn4;
    private javax.swing.JTextField txtQNo;
    private javax.swing.JTextField txtStuNo;
    private javax.swing.JTextField txtTotalQ;
    // End of variables declaration//GEN-END:variables
}
