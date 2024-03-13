/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main.student;

import java.sql.Connection;
import connect.ConnectDB;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Shivash Jurakan
 */
public class stu_Menu extends javax.swing.JFrame {

    int studentNumber;
    String examCode;
    int duration;

    /**
     * Creates new form stu_Menu
     */
    public stu_Menu(int studentNumber) {
        initComponents();
        this.studentNumber = studentNumber;
        showMyDetails();
        showMyResults();
        showExams();
        popExamBox(exam_cmbExam);

        exam_txtStudentNo.setText(String.valueOf(studentNumber));
    }

    public stu_Menu() {
        initComponents();
    }

    //**************************************************************************
    //                  Exam Form Methods
    //**************************************************************************
    public void showExams() {
        DefaultTableModel model = (DefaultTableModel) exam_tblExam.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT exam_code,duration FROM exam")) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("exam_code"),
                    rs.getInt("duration")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {

        }
    }

    public void popExamBox(JComboBox cmb) {
        try (Connection con = new ConnectDB().getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT DISTINCT exam_code FROM exam")) {

            Vector<String> items = new Vector<>();
            while (rs.next()) {
                String examCode = rs.getString("exam_code");
                items.add(examCode);
            }
            cmb.setModel(new DefaultComboBoxModel<>(items));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static int getDuration(Connection con, String examCode) throws SQLException {
        String query = "SELECT duration FROM exam WHERE exam_code = ?";
        try (PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, examCode);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("duration");
                } else {
                    return -1; // Return -1 if no exam found with the given code
                }
            }
        }
    }
        //**************************************************************************
        //                  My Results Form Methods
        //**************************************************************************
    public void showMyResults() {
        DefaultTableModel model = (DefaultTableModel) res_tblResult.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "SELECT exam_code, grade FROM exam INNER JOIN grade ON exam.exam_id = grade.exam_id WHERE student_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, studentNumber);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Object[] row = {
                            rs.getString("exam_code"),
                            rs.getInt("grade")
                        };
                        model.addRow(row);
                    }
                }
            }
        } catch (SQLException e) {

        }
    }

    //**************************************************************************
    //                  My Details Form Methods
    //**************************************************************************
    public void showMyDetails() {
        String sql = "SELECT * FROM student WHERE student_no = ?";
        try (Connection con = new ConnectDB().getConnection();) {
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, studentNumber);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        det_txtStuNo.setText(rs.getString("student_no"));
                        det_txtFName.setText(rs.getString("first_name"));
                        det_txtLName.setText(rs.getString("last_name"));
                        det_txtEmail.setText(rs.getString("email"));
                        det_txtPassword.setText(rs.getString("password"));
                        det_txtContactNo.setText(rs.getString("contact_no"));
                    }
                }
            }
        } catch (Exception e) {

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

        pnlHeader = new javax.swing.JPanel();
        lblTrial = new javax.swing.JLabel();
        lblExamName = new javax.swing.JLabel();
        btnLogOut = new javax.swing.JButton();
        pnlMenu = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        pnlNavResults = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        pnlNavExams = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        pnlNavMyDetails = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        tabMenuNav = new javax.swing.JTabbedPane();
        pnlExam = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        exam_tblExam = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        exam_cmbExam = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        exam_txtStudentNo = new javax.swing.JTextField();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        exam_chkRules = new javax.swing.JCheckBox();
        btnTakeExam = new javax.swing.JButton();
        pnlMyResults = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        res_tblResult = new javax.swing.JTable();
        pnlMyDetails = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        det_txtStuNo = new javax.swing.JTextField();
        det_txtFName = new javax.swing.JTextField();
        det_txtLName = new javax.swing.JTextField();
        det_txtEmail = new javax.swing.JTextField();
        det_txtPassword = new javax.swing.JTextField();
        det_txtContactNo = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        pnlHeader.setBackground(new java.awt.Color(0, 0, 0));

        lblTrial.setFont(new java.awt.Font("Roboto Black", 1, 28)); // NOI18N
        lblTrial.setForeground(new java.awt.Color(255, 255, 255));
        lblTrial.setText("TRIAL");

        lblExamName.setFont(new java.awt.Font("Roboto Black", 1, 28)); // NOI18N
        lblExamName.setForeground(new java.awt.Color(102, 153, 255));
        lblExamName.setText("EXAM");

        btnLogOut.setBackground(new java.awt.Color(102, 153, 255));
        btnLogOut.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btnLogOut.setForeground(new java.awt.Color(255, 255, 255));
        btnLogOut.setText("Log Out");
        btnLogOut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLogOutActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlHeaderLayout = new javax.swing.GroupLayout(pnlHeader);
        pnlHeader.setLayout(pnlHeaderLayout);
        pnlHeaderLayout.setHorizontalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addComponent(lblExamName, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTrial, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 622, Short.MAX_VALUE)
                .addComponent(btnLogOut)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTrial, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                    .addComponent(btnLogOut)
                    .addComponent(lblExamName, javax.swing.GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE))
                .addContainerGap())
        );

        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 60));

        pnlMenu.setBackground(new java.awt.Color(239, 183, 0));

        pnlNavResults.setBackground(new java.awt.Color(239, 183, 0));
        pnlNavResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlNavResultsMouseClicked(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel10.setText("My Results");

        javax.swing.GroupLayout pnlNavResultsLayout = new javax.swing.GroupLayout(pnlNavResults);
        pnlNavResults.setLayout(pnlNavResultsLayout);
        pnlNavResultsLayout.setHorizontalGroup(
            pnlNavResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNavResultsLayout.setVerticalGroup(
            pnlNavResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNavResultsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel10)
                .addContainerGap())
        );

        pnlNavExams.setBackground(new java.awt.Color(239, 183, 0));
        pnlNavExams.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlNavExamsMouseClicked(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel9.setText("Exams");

        javax.swing.GroupLayout pnlNavExamsLayout = new javax.swing.GroupLayout(pnlNavExams);
        pnlNavExams.setLayout(pnlNavExamsLayout);
        pnlNavExamsLayout.setHorizontalGroup(
            pnlNavExamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavExamsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNavExamsLayout.setVerticalGroup(
            pnlNavExamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNavExamsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addContainerGap())
        );

        pnlNavMyDetails.setBackground(new java.awt.Color(239, 183, 0));
        pnlNavMyDetails.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlNavMyDetailsMouseClicked(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel11.setText("My Details");

        javax.swing.GroupLayout pnlNavMyDetailsLayout = new javax.swing.GroupLayout(pnlNavMyDetails);
        pnlNavMyDetails.setLayout(pnlNavMyDetailsLayout);
        pnlNavMyDetailsLayout.setHorizontalGroup(
            pnlNavMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavMyDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNavMyDetailsLayout.setVerticalGroup(
            pnlNavMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavMyDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(pnlNavExams, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlNavResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlNavMyDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenuLayout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNavExams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNavResults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNavMyDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(264, Short.MAX_VALUE))
        );

        getContentPane().add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 23, 150, 530));

        pnlExam.setBackground(new java.awt.Color(255, 255, 255));

        exam_tblExam.setBackground(new java.awt.Color(102, 153, 255));
        exam_tblExam.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        exam_tblExam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Exam Code", "Duration"
            }
        ));
        exam_tblExam.setGridColor(new java.awt.Color(102, 153, 255));
        jScrollPane2.setViewportView(exam_tblExam);

        jPanel1.setBackground(new java.awt.Color(102, 153, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Exam Registration", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 18))); // NOI18N

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel7.setText("Select Exam:");

        exam_cmbExam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exam_cmbExamActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel8.setText("Student Number: ");

        exam_txtStudentNo.setEditable(false);

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText("1. Each test has a time limit of 30 minutes.\n2. The test consists of 5 questions.\n3. Questions are structured as multiple-choice questions.\n4. Each correct answer earns 1 point. There is no negative marking for incorrect answer.\n5. Students must submit the test before the time limit expires. Unsubmitted answers won't be considered.\n6. Instant feedback is provided after submitting the test, showing the score.");
        jScrollPane3.setViewportView(jTextArea1);

        exam_chkRules.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        exam_chkRules.setText("Accept Rules");

        btnTakeExam.setBackground(new java.awt.Color(239, 183, 0));
        btnTakeExam.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        btnTakeExam.setText("Take Exam");
        btnTakeExam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTakeExamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
                    .addComponent(exam_chkRules)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(btnTakeExam)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel7)
                                .addComponent(jLabel8))
                            .addGap(47, 47, 47)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(exam_txtStudentNo)
                                .addComponent(exam_cmbExam, 0, 168, Short.MAX_VALUE)))))
                .addContainerGap(37, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(exam_cmbExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(exam_txtStudentNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(exam_chkRules)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnTakeExam)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlExamLayout = new javax.swing.GroupLayout(pnlExam);
        pnlExam.setLayout(pnlExamLayout);
        pnlExamLayout.setHorizontalGroup(
            pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExamLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlExamLayout.setVerticalGroup(
            pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addContainerGap())
        );

        tabMenuNav.addTab("Exams", pnlExam);

        pnlMyResults.setBackground(new java.awt.Color(255, 255, 255));

        res_tblResult.setBackground(new java.awt.Color(102, 153, 255));
        res_tblResult.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        res_tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Exam_Code", "Grade"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        res_tblResult.setGridColor(new java.awt.Color(102, 153, 255));
        jScrollPane1.setViewportView(res_tblResult);

        javax.swing.GroupLayout pnlMyResultsLayout = new javax.swing.GroupLayout(pnlMyResults);
        pnlMyResults.setLayout(pnlMyResultsLayout);
        pnlMyResultsLayout.setHorizontalGroup(
            pnlMyResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMyResultsLayout.createSequentialGroup()
                .addContainerGap(135, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 570, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(110, 110, 110))
        );
        pnlMyResultsLayout.setVerticalGroup(
            pnlMyResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMyResultsLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23))
        );

        tabMenuNav.addTab("Result", pnlMyResults);

        pnlMyDetails.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel1.setText("Student Number");

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel2.setText("First Name");

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel3.setText("Last Name");

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel4.setText("Email");

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel5.setText("Password");

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel6.setText("Contact Number");

        det_txtStuNo.setEditable(false);
        det_txtStuNo.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        det_txtStuNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                det_txtStuNoActionPerformed(evt);
            }
        });

        det_txtFName.setEditable(false);
        det_txtFName.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        det_txtLName.setEditable(false);
        det_txtLName.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        det_txtLName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                det_txtLNameActionPerformed(evt);
            }
        });

        det_txtEmail.setEditable(false);
        det_txtEmail.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        det_txtPassword.setEditable(false);
        det_txtPassword.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        det_txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                det_txtPasswordActionPerformed(evt);
            }
        });

        det_txtContactNo.setEditable(false);
        det_txtContactNo.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        det_txtContactNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                det_txtContactNoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlMyDetailsLayout = new javax.swing.GroupLayout(pnlMyDetails);
        pnlMyDetails.setLayout(pnlMyDetailsLayout);
        pnlMyDetailsLayout.setHorizontalGroup(
            pnlMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMyDetailsLayout.createSequentialGroup()
                .addGap(226, 226, 226)
                .addGroup(pnlMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addGap(51, 51, 51)
                .addGroup(pnlMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(det_txtEmail)
                    .addComponent(det_txtPassword)
                    .addComponent(det_txtContactNo)
                    .addComponent(det_txtStuNo, javax.swing.GroupLayout.PREFERRED_SIZE, 368, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(det_txtFName)
                    .addComponent(det_txtLName))
                .addGap(38, 38, 38))
        );
        pnlMyDetailsLayout.setVerticalGroup(
            pnlMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMyDetailsLayout.createSequentialGroup()
                .addContainerGap(63, Short.MAX_VALUE)
                .addGroup(pnlMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(det_txtStuNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(39, 39, 39)
                .addGroup(pnlMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(det_txtFName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addGroup(pnlMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(det_txtLName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(pnlMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(det_txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(pnlMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(det_txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(pnlMyDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(det_txtContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(69, 69, 69))
        );

        tabMenuNav.addTab("Student", pnlMyDetails);

        getContentPane().add(tabMenuNav, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 820, 520));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void det_txtStuNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_det_txtStuNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_det_txtStuNoActionPerformed

    private void det_txtLNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_det_txtLNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_det_txtLNameActionPerformed

    private void det_txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_det_txtPasswordActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_det_txtPasswordActionPerformed

    private void det_txtContactNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_det_txtContactNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_det_txtContactNoActionPerformed

    private void exam_cmbExamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exam_cmbExamActionPerformed
        // TODO add your handling code here:
        examCode = (String) exam_cmbExam.getSelectedItem();
        try(Connection con = new ConnectDB().getConnection()){
            duration = getDuration(con, examCode);
        }
        catch(Exception e){
            
        }
        
    }//GEN-LAST:event_exam_cmbExamActionPerformed

    private void btnTakeExamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTakeExamActionPerformed
        // TODO add your handling code here:
        if ((exam_chkRules.isSelected()) && (!exam_cmbExam.equals(""))) {
            new Exam(studentNumber, examCode, duration).setVisible(true);
            setVisible(false);
        }
    }//GEN-LAST:event_btnTakeExamActionPerformed

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        setVisible(false);
        
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void pnlNavExamsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNavExamsMouseClicked
        // TODO add your handling code here:
        tabMenuNav.setSelectedIndex(0);
        
    }//GEN-LAST:event_pnlNavExamsMouseClicked

    private void pnlNavResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNavResultsMouseClicked
        // TODO add your handling code here:
        tabMenuNav.setSelectedIndex(1);
    }//GEN-LAST:event_pnlNavResultsMouseClicked

    private void pnlNavMyDetailsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNavMyDetailsMouseClicked
        // TODO add your handling code here:
        tabMenuNav.setSelectedIndex(2);
    }//GEN-LAST:event_pnlNavMyDetailsMouseClicked

    /**
     * int questionId = (int) qu_cmbQI.getSelectedItem();
     *
     * try (Connection con = new ConnectDB().getConnection()) { String sql =
     * "SELECT question, a_choice, b_choice, c_choice, d_choice, answer FROM
     * question_bank WHERE question_id = ?"; try (PreparedStatement pstmt =
     * con.prepareStatement(sql)) { pstmt.setInt(1, questionId); try (ResultSet
     * rs = pstmt.executeQuery()) { if (rs.next()) {
     * qu_txtQuestion.setText(rs.getString("question"));
     * qu_txtA.setText(rs.getString("a_choice"));
     * qu_txtB.setText(rs.getString("b_choice"));
     * qu_txtC.setText(rs.getString("c_choice"));
     * qu_txtD.setText(rs.getString("d_choice"));
     * qu_txtAns.setText(rs.getString("answer")); } } }
     *
     * } catch (Exception e) {
     *
     * } *
     */
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
            java.util.logging.Logger.getLogger(stu_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(stu_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(stu_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(stu_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new stu_Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton btnTakeExam;
    private javax.swing.JTextField det_txtContactNo;
    private javax.swing.JTextField det_txtEmail;
    private javax.swing.JTextField det_txtFName;
    private javax.swing.JTextField det_txtLName;
    private javax.swing.JTextField det_txtPassword;
    private javax.swing.JTextField det_txtStuNo;
    private javax.swing.JCheckBox exam_chkRules;
    private javax.swing.JComboBox<String> exam_cmbExam;
    private javax.swing.JTable exam_tblExam;
    private javax.swing.JTextField exam_txtStudentNo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JLabel lblExamName;
    private javax.swing.JLabel lblTrial;
    private javax.swing.JPanel pnlExam;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlMyDetails;
    private javax.swing.JPanel pnlMyResults;
    private javax.swing.JPanel pnlNavExams;
    private javax.swing.JPanel pnlNavMyDetails;
    private javax.swing.JPanel pnlNavResults;
    private javax.swing.JTable res_tblResult;
    private javax.swing.JTabbedPane tabMenuNav;
    // End of variables declaration//GEN-END:variables
}
