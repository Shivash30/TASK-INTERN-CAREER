/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package main.teacher;

import connect.ConnectDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Shivash Jurakan
 */
public class tch_Menu extends javax.swing.JFrame {
    int updateStudentID;
    /**
     * Creates new form tch_Menu
     */
    public tch_Menu() {
        initComponents();
        populateStuTable(stu_tblStudent);

        populateResultsTable(res_tblResult);

        populateExamTable(exam_tblExam);
        popExamBox(exam_cmbEC);
        popExamBox(eu_cmbEC);
        popExamBox(ed_cmbEC);

        populateQTable(q_tblQuestion);
        popExamBox(q_cmbSearch);
        popExamBox(qa_cmbEC);
        popExamBox(qd_cmbEC);
        
        populateTchTable(tch_tblTeacher);

    }

    //**************************************************************************
    //                  Student Form Methods
    //**************************************************************************
    public static void populateStuTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT student_no, CONCAT(first_name, ' ',last_name) AS full_name,email, contact_no FROM student")) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("student_no"),
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("contact_no")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {

        }
    }

    public void popSearchStuTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "SELECT student_no, CONCAT(first_name, ' ',last_name) AS full_name,email, contact_no FROM student WHERE last_name LIKE ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, "%" + stu_txtSearch.getText() + "%");
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Object[] row = {
                            rs.getString("student_no"),
                            rs.getString("full_name"),
                            rs.getString("email"),
                            rs.getString("contact_no")
                        };
                        model.addRow(row);
                    }
                }
            }
        } catch (SQLException e) {

        }
    }

    //**************************************************************************
    //                  Results Form Methods
    //**************************************************************************
    public static void populateResultsTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT CONCAT(s.first_name, ' ', s.last_name) AS full_name, e.exam_code, g.grade FROM student s INNER JOIN grade g ON s.student_no = g.student_id INNER JOIN exam e ON g.exam_id = e.exam_id")) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("full_name"),
                    rs.getString("exam_code"),
                    rs.getInt("grade")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Print the exception stack trace for debugging
        }
    }

    public void popSearchResultsTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "SELECT CONCAT(s.first_name, ' ', s.last_name) AS full_name, e.exam_code, g.grade FROM student s INNER JOIN grade g ON s.student_no = g.student_id INNER JOIN exam e ON g.exam_id = e.exam_id WHERE s.last_name LIKE ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, "%" + res_txtSearch.getText() + "%");
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Object[] row = {
                            rs.getString("full_name"),
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
    //                  Exam Form Methods
    //**************************************************************************
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

    public static void populateExamTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT e.exam_code, e.duration, CONCAT(t.first_name, ' ',t.last_name) AS full_name FROM exam e INNER JOIN teacher t ON e.teacher_id = t.teacher_id")) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("exam_code"),
                    rs.getString("duration"),
                    rs.getString("full_name")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {

        }
    }

    public void popSearchExamTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "SELECT e.exam_code, e.duration, CONCAT(t.first_name, ' ',t.last_name) AS full_name FROM exam e INNER JOIN teacher t ON e.teacher_id = t.teacher_id WHERE e.exam_code = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, (String) exam_cmbEC.getSelectedItem());
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Object[] row = {
                            rs.getString("exam_code"),
                            rs.getString("duration"),
                            rs.getString("full_name")
                        };
                        model.addRow(row);
                    }
                }
            }
        } catch (SQLException e) {

        }
    }

    public void addExam() {
        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "INSERT INTO exam (teacher_id, exam_code, duration) VALUES (?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, Integer.parseInt(ea_txtTeacherID.getText()));
                pstmt.setString(2, ea_txtExamCode.getText());
                pstmt.setInt(3, Integer.parseInt(ea_txtDuration.getText()));
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record added successfully.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExam() {
        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "DELETE FROM exam WHERE exam_code = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, (String) ed_cmbEC.getSelectedItem());
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, ed_cmbEC.getSelectedItem() + " exam was deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, ed_cmbEC.getSelectedItem() + " exam was not found!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateExamDuration() {
        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "UPDATE exam SET duration = ? WHERE exam_code = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, eu_txtDuration.getText());
                pstmt.setString(2, (String) eu_cmbEC.getSelectedItem());
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, eu_cmbEC.getSelectedItem() + " exam duration was updated successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, eu_cmbEC.getSelectedItem() + " exam was not found!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //**************************************************************************
    //                  Question Form Methods
    //**************************************************************************
    public static void populateQTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT q.question_id, q.question, q.a_choice, q.b_choice, q.c_choice, q.d_choice, q.answer FROM exam e INNER JOIN question_bank q ON e.exam_id = q.exam_id")) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("question_id"),
                    rs.getString("question"),
                    rs.getString("a_choice"),
                    rs.getString("b_choice"),
                    rs.getString("c_choice"),
                    rs.getString("d_choice"),
                    rs.getString("answer")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {

        }
    }

    public void popSearchQTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "SELECT q.question_id, q.question, q.a_choice, q.b_choice, q.c_choice, q.d_choice, q.answer FROM exam e INNER JOIN question_bank q ON e.exam_id = q.exam_id WHERE exam_code = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, (String) q_cmbSearch.getSelectedItem());
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        Object[] row = {
                            rs.getString("question_id"),
                            rs.getString("question"),
                            rs.getString("a_choice"),
                            rs.getString("b_choice"),
                            rs.getString("c_choice"),
                            rs.getString("d_choice"),
                            rs.getString("answer")
                        };
                        model.addRow(row);
                    }
                }
            }
        } catch (SQLException e) {

        }
    }

    public void addQ() {
        try (Connection con = new ConnectDB().getConnection()) {
            int examId = getExamIdFromCode(con, (String) qa_cmbEC.getSelectedItem());
            String sql = "INSERT INTO question_bank (exam_id, question, a_choice, b_choice, c_choice, d_choice, answer) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, examId);
                pstmt.setString(2, qa_txtQuestion.getText());
                pstmt.setString(3, qa_txtA.getText());
                pstmt.setString(4, qa_txtB.getText());
                pstmt.setString(5, qa_txtC.getText());
                pstmt.setString(6, qa_txtD.getText());
                pstmt.setString(7, qa_txtAns.getText());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Record added successfully.");
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

    private void populateQuestionIdComboBox(int examId, JComboBox cmb) {
        cmb.removeAllItems(); // Clear existing items
        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "SELECT question_id FROM question_bank WHERE exam_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setInt(1, examId);
                try (ResultSet rs = pstmt.executeQuery()) {
                    while (rs.next()) {
                        int questionId = rs.getInt("question_id");
                        cmb.addItem(String.valueOf(questionId)); // Add question ID to combo box
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteQ() {
        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "DELETE FROM question_bank WHERE question_id = ?";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, (String) qd_cmbQI.getSelectedItem());
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(null, "Question was deleted successfully.");
                } else {
                    JOptionPane.showMessageDialog(null, "Question was not found!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    //**************************************************************************
    //                  Teacher Form Methods
    //**************************************************************************
    public static void populateTchTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        try (Connection con = new ConnectDB().getConnection(); Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery("SELECT CONCAT(first_name, ' ',last_name) AS full_name,email, contact_no, subject FROM teacher")) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("full_name"),
                    rs.getString("email"),
                    rs.getString("contact_no"),
                    rs.getString("subject")
                };
                model.addRow(row);
            }
        } catch (SQLException e) {

        }
    }
    
    public void registerStudent(){
        try (Connection con = new ConnectDB().getConnection()) {
            String sql = "INSERT INTO teacher (first_name,last_name,email,password,contact_no, subject) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                pstmt.setString(1, tch_txtFirstName.getText());
                pstmt.setString(2, tch_txtLastName.getText());
                pstmt.setString(3, tch_txtEmail.getText());
                pstmt.setString(4, tch_txtPassword.getText());
                pstmt.setString(5, tch_txtContactNo.getText());
                pstmt.setString(6, tch_txtSubject.getText());
                pstmt.executeUpdate();
                
                JOptionPane.showMessageDialog(null, "New teacher registered");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void clearTeacherForm(){
        tch_txtFirstName.setText("");
        tch_txtLastName.setText("");
        tch_txtEmail.setText("");
        tch_txtPassword.setText("");
        tch_txtContactNo.setText("");
        tch_txtSubject.setText("");
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
        pnlMenu1 = new javax.swing.JPanel();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        pnlNavQuestions = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        pnlNavExams = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        pnlNavResults = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator4 = new javax.swing.JSeparator();
        pnlNavStudents = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jSeparator5 = new javax.swing.JSeparator();
        pnlNavTeacher = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jSeparator6 = new javax.swing.JSeparator();
        tabMenuNav = new javax.swing.JTabbedPane();
        pnlExam = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        exam_tblExam = new javax.swing.JTable();
        exam_cmbEC = new javax.swing.JComboBox<>();
        exam_btnRefresh = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        exam_pnlAdd = new javax.swing.JPanel();
        ea_txtTeacherID = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ea_txtExamCode = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        ea_txtDuration = new javax.swing.JTextField();
        ea_btnAddExam = new javax.swing.JButton();
        exam_pnlUpdate = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        eu_cmbEC = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();
        eu_txtDuration = new javax.swing.JTextField();
        eu_btnUpdate = new javax.swing.JButton();
        exam_pnlDelete = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        ed_cmbEC = new javax.swing.JComboBox<>();
        ed_btnDelete = new javax.swing.JButton();
        pnlQuestions = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        q_cmbSearch = new javax.swing.JComboBox<>();
        q_btnRefresh = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        q_tblQuestion = new javax.swing.JTable();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        pnlAddQ = new javax.swing.JPanel();
        qa_btnAdd = new javax.swing.JButton();
        qa_txtAns = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        qa_cmbEC = new javax.swing.JComboBox<>();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        qa_txtD = new javax.swing.JTextField();
        qa_txtC = new javax.swing.JTextField();
        qa_txtB = new javax.swing.JTextField();
        qa_txtA = new javax.swing.JTextField();
        jScrollPane5 = new javax.swing.JScrollPane();
        qa_txtQuestion = new javax.swing.JTextArea();
        pnlDeleteQ = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        qd_cmbEC = new javax.swing.JComboBox<>();
        qd_btnDelete = new javax.swing.JButton();
        jLabel34 = new javax.swing.JLabel();
        qd_cmbQI = new javax.swing.JComboBox<>();
        pnlResults = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        res_tblResult = new javax.swing.JTable();
        res_txtSearch = new javax.swing.JTextField();
        res_btnSearch = new javax.swing.JButton();
        res_btnClear = new javax.swing.JButton();
        pnlStudents = new javax.swing.JPanel();
        stu_txtSearch = new javax.swing.JTextField();
        stu_btnSearch = new javax.swing.JButton();
        stu_btnClear = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        stu_tblStudent = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        pnlTeacher = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        tch_tblTeacher = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        tch_txtFirstName = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        tch_txtLastName = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        tch_txtEmail = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        tch_txtPassword = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        tch_txtContactNo = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        tch_txtSubject = new javax.swing.JTextField();
        tch_btnRegister = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
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
                .addGap(47, 47, 47)
                .addComponent(lblExamName, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTrial, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 619, Short.MAX_VALUE)
                .addComponent(btnLogOut)
                .addContainerGap())
        );
        pnlHeaderLayout.setVerticalGroup(
            pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeaderLayout.createSequentialGroup()
                .addGap(13, 13, 13)
                .addGroup(pnlHeaderLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTrial, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblExamName, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLogOut))
                .addGap(0, 10, Short.MAX_VALUE))
        );

        getContentPane().add(pnlHeader, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 970, 60));

        pnlMenu.setBackground(new java.awt.Color(239, 183, 0));

        pnlMenu1.setBackground(new java.awt.Color(239, 183, 0));

        pnlNavQuestions.setBackground(new java.awt.Color(239, 183, 0));
        pnlNavQuestions.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlNavQuestionsMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel13.setText("Questions");

        javax.swing.GroupLayout pnlNavQuestionsLayout = new javax.swing.GroupLayout(pnlNavQuestions);
        pnlNavQuestions.setLayout(pnlNavQuestionsLayout);
        pnlNavQuestionsLayout.setHorizontalGroup(
            pnlNavQuestionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavQuestionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNavQuestionsLayout.setVerticalGroup(
            pnlNavQuestionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavQuestionsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlNavExams.setBackground(new java.awt.Color(239, 183, 0));
        pnlNavExams.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlNavExamsMouseClicked(evt);
            }
        });

        jLabel14.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel14.setText("Exams");

        javax.swing.GroupLayout pnlNavExamsLayout = new javax.swing.GroupLayout(pnlNavExams);
        pnlNavExams.setLayout(pnlNavExamsLayout);
        pnlNavExamsLayout.setHorizontalGroup(
            pnlNavExamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavExamsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNavExamsLayout.setVerticalGroup(
            pnlNavExamsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavExamsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlNavResults.setBackground(new java.awt.Color(239, 183, 0));
        pnlNavResults.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlNavResultsMouseClicked(evt);
            }
        });

        jLabel15.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel15.setText("Results");

        javax.swing.GroupLayout pnlNavResultsLayout = new javax.swing.GroupLayout(pnlNavResults);
        pnlNavResults.setLayout(pnlNavResultsLayout);
        pnlNavResultsLayout.setHorizontalGroup(
            pnlNavResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNavResultsLayout.setVerticalGroup(
            pnlNavResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNavResultsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addContainerGap())
        );

        pnlNavStudents.setBackground(new java.awt.Color(239, 183, 0));
        pnlNavStudents.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlNavStudentsMouseClicked(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel16.setText("Students");

        javax.swing.GroupLayout pnlNavStudentsLayout = new javax.swing.GroupLayout(pnlNavStudents);
        pnlNavStudents.setLayout(pnlNavStudentsLayout);
        pnlNavStudentsLayout.setHorizontalGroup(
            pnlNavStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavStudentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNavStudentsLayout.setVerticalGroup(
            pnlNavStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavStudentsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlNavTeacher.setBackground(new java.awt.Color(239, 183, 0));
        pnlNavTeacher.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pnlNavTeacherMouseClicked(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel17.setText("Teachers");

        javax.swing.GroupLayout pnlNavTeacherLayout = new javax.swing.GroupLayout(pnlNavTeacher);
        pnlNavTeacher.setLayout(pnlNavTeacherLayout);
        pnlNavTeacherLayout.setHorizontalGroup(
            pnlNavTeacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavTeacherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlNavTeacherLayout.setVerticalGroup(
            pnlNavTeacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNavTeacherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel17)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMenu1Layout = new javax.swing.GroupLayout(pnlMenu1);
        pnlMenu1.setLayout(pnlMenu1Layout);
        pnlMenu1Layout.setHorizontalGroup(
            pnlMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator2)
            .addComponent(jSeparator3, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(pnlNavExams, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlNavQuestions, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(pnlNavResults, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator1)
            .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(pnlNavStudents, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator5)
            .addComponent(pnlNavTeacher, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSeparator6)
        );
        pnlMenu1Layout.setVerticalGroup(
            pnlMenu1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlMenu1Layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addComponent(jSeparator4, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNavExams, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNavQuestions, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNavResults, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNavStudents, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlNavTeacher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator6, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(264, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlMenuLayout = new javax.swing.GroupLayout(pnlMenu);
        pnlMenu.setLayout(pnlMenuLayout);
        pnlMenuLayout.setHorizontalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 150, Short.MAX_VALUE)
            .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlMenuLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        pnlMenuLayout.setVerticalGroup(
            pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 674, Short.MAX_VALUE)
            .addGroup(pnlMenuLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(pnlMenuLayout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(pnlMenu1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        getContentPane().add(pnlMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 23, -1, 550));

        pnlExam.setBackground(new java.awt.Color(255, 255, 255));

        exam_tblExam.setBackground(new java.awt.Color(102, 153, 255));
        exam_tblExam.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        exam_tblExam.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Exam Code", "Duration", "Lecturer"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Integer.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        exam_tblExam.setGridColor(new java.awt.Color(102, 153, 255));
        jScrollPane2.setViewportView(exam_tblExam);

        exam_cmbEC.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        exam_cmbEC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                exam_cmbECItemStateChanged(evt);
            }
        });

        exam_btnRefresh.setBackground(new java.awt.Color(239, 183, 0));
        exam_btnRefresh.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        exam_btnRefresh.setText("Refresh");
        exam_btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exam_btnRefreshActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel1.setText("Exam Code");

        exam_pnlAdd.setBackground(new java.awt.Color(102, 153, 255));
        exam_pnlAdd.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Add Exam", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 14))); // NOI18N

        ea_txtTeacherID.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel5.setText("Teacher ID");

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel6.setText("Exam Code");

        ea_txtExamCode.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel7.setText("Duration");

        ea_txtDuration.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N

        ea_btnAddExam.setBackground(new java.awt.Color(239, 183, 0));
        ea_btnAddExam.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        ea_btnAddExam.setText("Add Exam");
        ea_btnAddExam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ea_btnAddExamActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout exam_pnlAddLayout = new javax.swing.GroupLayout(exam_pnlAdd);
        exam_pnlAdd.setLayout(exam_pnlAddLayout);
        exam_pnlAddLayout.setHorizontalGroup(
            exam_pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exam_pnlAddLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exam_pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(ea_btnAddExam)
                    .addGroup(exam_pnlAddLayout.createSequentialGroup()
                        .addGroup(exam_pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addGroup(exam_pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ea_txtExamCode, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ea_txtTeacherID, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ea_txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, 178, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        exam_pnlAddLayout.setVerticalGroup(
            exam_pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exam_pnlAddLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exam_pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(ea_txtTeacherID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(exam_pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ea_txtExamCode, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(exam_pnlAddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(ea_txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ea_btnAddExam)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        exam_pnlUpdate.setBackground(new java.awt.Color(102, 153, 255));
        exam_pnlUpdate.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Update Duration", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 14))); // NOI18N

        jLabel8.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel8.setText("Exam Code");

        jLabel9.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel9.setText("Duration");

        eu_btnUpdate.setBackground(new java.awt.Color(239, 183, 0));
        eu_btnUpdate.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        eu_btnUpdate.setText("Update");
        eu_btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                eu_btnUpdateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout exam_pnlUpdateLayout = new javax.swing.GroupLayout(exam_pnlUpdate);
        exam_pnlUpdate.setLayout(exam_pnlUpdateLayout);
        exam_pnlUpdateLayout.setHorizontalGroup(
            exam_pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exam_pnlUpdateLayout.createSequentialGroup()
                .addGroup(exam_pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(exam_pnlUpdateLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(exam_pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(exam_pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(eu_txtDuration, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                            .addComponent(eu_cmbEC, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(exam_pnlUpdateLayout.createSequentialGroup()
                        .addGap(202, 202, 202)
                        .addComponent(eu_btnUpdate)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        exam_pnlUpdateLayout.setVerticalGroup(
            exam_pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exam_pnlUpdateLayout.createSequentialGroup()
                .addGroup(exam_pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eu_cmbEC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(exam_pnlUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(eu_txtDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(eu_btnUpdate)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        exam_pnlDelete.setBackground(new java.awt.Color(102, 153, 255));
        exam_pnlDelete.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Delete Exam", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 14))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel10.setText("Exam Code");

        ed_btnDelete.setBackground(new java.awt.Color(239, 183, 0));
        ed_btnDelete.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        ed_btnDelete.setText("Delete");
        ed_btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ed_btnDeleteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout exam_pnlDeleteLayout = new javax.swing.GroupLayout(exam_pnlDelete);
        exam_pnlDelete.setLayout(exam_pnlDeleteLayout);
        exam_pnlDeleteLayout.setHorizontalGroup(
            exam_pnlDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, exam_pnlDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exam_pnlDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(exam_pnlDeleteLayout.createSequentialGroup()
                        .addGap(0, 203, Short.MAX_VALUE)
                        .addComponent(ed_btnDelete))
                    .addGroup(exam_pnlDeleteLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ed_cmbEC, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(44, 44, 44))
        );
        exam_pnlDeleteLayout.setVerticalGroup(
            exam_pnlDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(exam_pnlDeleteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(exam_pnlDeleteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ed_cmbEC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                .addComponent(ed_btnDelete)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlExamLayout = new javax.swing.GroupLayout(pnlExam);
        pnlExam.setLayout(pnlExamLayout);
        pnlExamLayout.setHorizontalGroup(
            pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlExamLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlExamLayout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exam_cmbEC, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exam_btnRefresh))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 412, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(exam_pnlAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exam_pnlUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exam_pnlDelete, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        pnlExamLayout.setVerticalGroup(
            pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlExamLayout.createSequentialGroup()
                .addContainerGap(38, Short.MAX_VALUE)
                .addGroup(pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlExamLayout.createSequentialGroup()
                        .addComponent(exam_pnlAdd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exam_pnlUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(exam_pnlDelete, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlExamLayout.createSequentialGroup()
                        .addGroup(pnlExamLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(exam_cmbEC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(exam_btnRefresh)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 421, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        tabMenuNav.addTab("Exams", pnlExam);

        pnlQuestions.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel2.setText("Exam Code");

        q_cmbSearch.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        q_cmbSearch.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                q_cmbSearchItemStateChanged(evt);
            }
        });

        q_btnRefresh.setBackground(new java.awt.Color(239, 183, 0));
        q_btnRefresh.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        q_btnRefresh.setText("Refresh");
        q_btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                q_btnRefreshActionPerformed(evt);
            }
        });

        q_tblQuestion.setBackground(new java.awt.Color(102, 153, 255));
        q_tblQuestion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        q_tblQuestion.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Question ID", "Question", "Choice A", "Choice B", "Choice C", "Choice D", "Answer"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        q_tblQuestion.setGridColor(new java.awt.Color(102, 153, 255));
        q_tblQuestion.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        q_tblQuestion.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        q_tblQuestion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                q_tblQuestionMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(q_tblQuestion);

        pnlAddQ.setBackground(new java.awt.Color(102, 153, 255));

        qa_btnAdd.setBackground(new java.awt.Color(239, 183, 0));
        qa_btnAdd.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        qa_btnAdd.setText("Add");
        qa_btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qa_btnAddActionPerformed(evt);
            }
        });

        qa_txtAns.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        jLabel26.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel26.setText("Question");

        jLabel27.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel27.setText("Exam Code");

        qa_cmbEC.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        jLabel28.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel28.setText("Choice A");

        jLabel29.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel29.setText("Choice B");

        jLabel30.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel30.setText("Choice C");

        jLabel31.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel31.setText("Choice D");

        jLabel32.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel32.setText("Answer");

        qa_txtD.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        qa_txtC.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        qa_txtB.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        qa_txtA.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        qa_txtQuestion.setColumns(20);
        qa_txtQuestion.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        qa_txtQuestion.setRows(5);
        qa_txtQuestion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                qa_txtQuestionMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(qa_txtQuestion);

        javax.swing.GroupLayout pnlAddQLayout = new javax.swing.GroupLayout(pnlAddQ);
        pnlAddQ.setLayout(pnlAddQLayout);
        pnlAddQLayout.setHorizontalGroup(
            pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddQLayout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(pnlAddQLayout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddQLayout.createSequentialGroup()
                        .addComponent(jLabel27)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(qa_cmbEC, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel29)
                    .addComponent(jLabel30)
                    .addComponent(jLabel31)
                    .addComponent(jLabel32))
                .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlAddQLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(qa_txtAns, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(pnlAddQLayout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(qa_txtB, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(qa_txtA, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(qa_txtC, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(qa_txtD, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(31, 31, 31)
                .addComponent(qa_btnAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(103, Short.MAX_VALUE))
        );
        pnlAddQLayout.setVerticalGroup(
            pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlAddQLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(qa_cmbEC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel28)
                    .addComponent(qa_txtA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel27))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlAddQLayout.createSequentialGroup()
                        .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(qa_txtB, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(qa_txtC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel30)
                            .addComponent(qa_btnAdd))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(qa_txtD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel31))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(qa_txtAns, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel32)))
                    .addGroup(pnlAddQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel26)
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Add Question", pnlAddQ);

        pnlDeleteQ.setBackground(new java.awt.Color(102, 153, 255));

        jLabel11.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel11.setText("Exam Code");

        qd_cmbEC.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        qd_cmbEC.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                qd_cmbECItemStateChanged(evt);
            }
        });

        qd_btnDelete.setBackground(new java.awt.Color(239, 183, 0));
        qd_btnDelete.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        qd_btnDelete.setText("Delete");
        qd_btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qd_btnDeleteActionPerformed(evt);
            }
        });

        jLabel34.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel34.setText("Question ID");

        qd_cmbQI.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        qd_cmbQI.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                qd_cmbQIItemStateChanged(evt);
            }
        });
        qd_cmbQI.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                qd_cmbQIMouseClicked(evt);
            }
        });
        qd_cmbQI.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                qd_cmbQIPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout pnlDeleteQLayout = new javax.swing.GroupLayout(pnlDeleteQ);
        pnlDeleteQ.setLayout(pnlDeleteQLayout);
        pnlDeleteQLayout.setHorizontalGroup(
            pnlDeleteQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDeleteQLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDeleteQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlDeleteQLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(qd_btnDelete))
                    .addGroup(pnlDeleteQLayout.createSequentialGroup()
                        .addGroup(pnlDeleteQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel11)
                            .addComponent(jLabel34))
                        .addGap(75, 75, 75)
                        .addGroup(pnlDeleteQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(qd_cmbEC, 0, 343, Short.MAX_VALUE)
                            .addComponent(qd_cmbQI, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(264, 264, 264))
        );
        pnlDeleteQLayout.setVerticalGroup(
            pnlDeleteQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDeleteQLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDeleteQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(qd_cmbEC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDeleteQLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel34)
                    .addComponent(qd_cmbQI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(36, 36, 36)
                .addComponent(qd_btnDelete)
                .addContainerGap(94, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Delete Question", pnlDeleteQ);

        javax.swing.GroupLayout pnlQuestionsLayout = new javax.swing.GroupLayout(pnlQuestions);
        pnlQuestions.setLayout(pnlQuestionsLayout);
        pnlQuestionsLayout.setHorizontalGroup(
            pnlQuestionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlQuestionsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlQuestionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlQuestionsLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(q_cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 202, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(q_btnRefresh)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1)
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );
        pnlQuestionsLayout.setVerticalGroup(
            pnlQuestionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlQuestionsLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlQuestionsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(q_cmbSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(q_btnRefresh)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        tabMenuNav.addTab("Question", pnlQuestions);

        pnlResults.setBackground(new java.awt.Color(255, 255, 255));

        jLabel12.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel12.setText("Search By Surname");

        res_tblResult.setBackground(new java.awt.Color(102, 153, 255));
        res_tblResult.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        res_tblResult.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Name", "Exam Code", "Result"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        res_tblResult.setGridColor(new java.awt.Color(102, 153, 255));
        jScrollPane1.setViewportView(res_tblResult);

        res_txtSearch.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N

        res_btnSearch.setBackground(new java.awt.Color(239, 183, 0));
        res_btnSearch.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        res_btnSearch.setText("Search");

        res_btnClear.setBackground(new java.awt.Color(239, 183, 0));
        res_btnClear.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        res_btnClear.setText("Clear");
        res_btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                res_btnClearActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlResultsLayout = new javax.swing.GroupLayout(pnlResults);
        pnlResults.setLayout(pnlResultsLayout);
        pnlResultsLayout.setHorizontalGroup(
            pnlResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResultsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlResultsLayout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(res_txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(res_btnSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(res_btnClear)
                        .addGap(0, 214, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlResultsLayout.setVerticalGroup(
            pnlResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlResultsLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(pnlResultsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(res_txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(res_btnSearch)
                    .addComponent(res_btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 438, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabMenuNav.addTab("Result", pnlResults);

        pnlStudents.setBackground(new java.awt.Color(255, 255, 255));

        stu_txtSearch.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        stu_txtSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_txtSearchActionPerformed(evt);
            }
        });

        stu_btnSearch.setBackground(new java.awt.Color(239, 183, 0));
        stu_btnSearch.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        stu_btnSearch.setText("Search");
        stu_btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_btnSearchActionPerformed(evt);
            }
        });

        stu_btnClear.setBackground(new java.awt.Color(239, 183, 0));
        stu_btnClear.setFont(new java.awt.Font("Roboto", 1, 18)); // NOI18N
        stu_btnClear.setText("Clear");
        stu_btnClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stu_btnClearActionPerformed(evt);
            }
        });

        stu_tblStudent.setBackground(new java.awt.Color(102, 153, 255));
        stu_tblStudent.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        stu_tblStudent.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student Number", "Student Name", "Email", "Contact No"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        stu_tblStudent.setGridColor(new java.awt.Color(102, 153, 255));
        jScrollPane4.setViewportView(stu_tblStudent);

        jLabel3.setFont(new java.awt.Font("Roboto", 0, 18)); // NOI18N
        jLabel3.setText("Search By Surname");

        javax.swing.GroupLayout pnlStudentsLayout = new javax.swing.GroupLayout(pnlStudents);
        pnlStudents.setLayout(pnlStudentsLayout);
        pnlStudentsLayout.setHorizontalGroup(
            pnlStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlStudentsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
                    .addGroup(pnlStudentsLayout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stu_txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(stu_btnSearch)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(stu_btnClear)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlStudentsLayout.setVerticalGroup(
            pnlStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlStudentsLayout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(pnlStudentsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(stu_txtSearch, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(stu_btnSearch)
                    .addComponent(stu_btnClear))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 439, Short.MAX_VALUE)
                .addContainerGap())
        );

        tabMenuNav.addTab("Student", pnlStudents);

        pnlTeacher.setBackground(new java.awt.Color(255, 255, 255));

        tch_tblTeacher.setBackground(new java.awt.Color(102, 153, 255));
        tch_tblTeacher.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Teacher Name", "Email", "Contact No", "Subject"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tch_tblTeacher.setGridColor(new java.awt.Color(102, 153, 255));
        jScrollPane6.setViewportView(tch_tblTeacher);

        jPanel2.setBackground(new java.awt.Color(102, 153, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Register Teacher", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Roboto", 0, 18))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel4.setText("First Name");

        tch_txtFirstName.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        tch_txtFirstName.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel18.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel18.setText("Last Name");

        tch_txtLastName.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        tch_txtLastName.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel19.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel19.setText("Email");

        tch_txtEmail.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        tch_txtEmail.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel20.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel20.setText("Password");

        tch_txtPassword.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        tch_txtPassword.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel21.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel21.setText("Contact No");

        tch_txtContactNo.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        tch_txtContactNo.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jLabel22.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        jLabel22.setText("Subject");

        tch_txtSubject.setFont(new java.awt.Font("Roboto", 0, 16)); // NOI18N
        tch_txtSubject.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        tch_btnRegister.setBackground(new java.awt.Color(239, 183, 0));
        tch_btnRegister.setFont(new java.awt.Font("Roboto Black", 1, 16)); // NOI18N
        tch_btnRegister.setText("Register");
        tch_btnRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tch_btnRegisterActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 361, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(tch_btnRegister, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tch_txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tch_txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4)
                        .addComponent(jLabel20)
                        .addComponent(jLabel19)
                        .addComponent(tch_txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel18)
                        .addComponent(tch_txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(tch_txtContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel21)
                        .addComponent(tch_txtSubject, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel22))
                    .addContainerGap(37, Short.MAX_VALUE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addGap(36, 36, 36)
                    .addComponent(jLabel4)
                    .addGap(4, 4, 4)
                    .addComponent(tch_txtFirstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel18)
                    .addGap(4, 4, 4)
                    .addComponent(tch_txtLastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel19)
                    .addGap(4, 4, 4)
                    .addComponent(tch_txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel20)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tch_txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel21)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tch_txtContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel22)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tch_txtSubject, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(29, 29, 29)
                    .addComponent(tch_btnRegister)
                    .addGap(36, 36, 36)))
        );

        javax.swing.GroupLayout pnlTeacherLayout = new javax.swing.GroupLayout(pnlTeacher);
        pnlTeacher.setLayout(pnlTeacherLayout);
        pnlTeacherLayout.setHorizontalGroup(
            pnlTeacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTeacherLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlTeacherLayout.setVerticalGroup(
            pnlTeacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTeacherLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTeacherLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE))
                .addContainerGap())
        );

        tabMenuNav.addTab("Teacher", pnlTeacher);

        getContentPane().add(tabMenuNav, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 30, 820, 540));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void stu_btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_btnClearActionPerformed
        // TODO add your handling code here:
        populateStuTable(stu_tblStudent);
        stu_txtSearch.setText("");
    }//GEN-LAST:event_stu_btnClearActionPerformed

    private void stu_btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_btnSearchActionPerformed
        // TODO add your handling code here:
        popSearchStuTable(stu_tblStudent);
    }//GEN-LAST:event_stu_btnSearchActionPerformed

    private void exam_cmbECItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_exam_cmbECItemStateChanged
        // TODO add your handling code here:
        popSearchExamTable(exam_tblExam);
    }//GEN-LAST:event_exam_cmbECItemStateChanged

    private void exam_btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exam_btnRefreshActionPerformed
        // TODO add your handling code here:
        populateExamTable(exam_tblExam);
    }//GEN-LAST:event_exam_btnRefreshActionPerformed

    private void ea_btnAddExamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ea_btnAddExamActionPerformed
        // TODO add your handling code here:
        if ((!ea_txtTeacherID.getText().equals("")) || (!ea_txtExamCode.getText().equals("")) || (!ea_txtDuration.getText().equals(""))) {
            addExam();

            populateExamTable(exam_tblExam);
            popExamBox(exam_cmbEC);
            popExamBox(eu_cmbEC);
            popExamBox(ed_cmbEC);
        } else {
            JOptionPane.showMessageDialog(null, "Missing details");
        }


    }//GEN-LAST:event_ea_btnAddExamActionPerformed

    private void ed_btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ed_btnDeleteActionPerformed
        // TODO add your handling code here:
        //Add confirmation

        deleteExam();
        populateExamTable(exam_tblExam);
        popExamBox(exam_cmbEC);
        popExamBox(eu_cmbEC);
        popExamBox(ed_cmbEC);
    }//GEN-LAST:event_ed_btnDeleteActionPerformed

    private void eu_btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_eu_btnUpdateActionPerformed
        // TODO add your handling code here:

        updateExamDuration();
        populateExamTable(exam_tblExam);
        popExamBox(exam_cmbEC);
        popExamBox(eu_cmbEC);
        popExamBox(ed_cmbEC);
    }//GEN-LAST:event_eu_btnUpdateActionPerformed

    private void q_cmbSearchItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_q_cmbSearchItemStateChanged
        // TODO add your handling code here:

        popSearchQTable(q_tblQuestion);
    }//GEN-LAST:event_q_cmbSearchItemStateChanged

    private void q_btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_q_btnRefreshActionPerformed
        // TODO add your handling code here:

        populateQTable(q_tblQuestion);
    }//GEN-LAST:event_q_btnRefreshActionPerformed

    private void qa_txtQuestionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_qa_txtQuestionMouseClicked
        // TODO add your handling code here:
        String text = (String) qa_txtQuestion.getText();
        String input = (String) JOptionPane.showInputDialog(null, "Enter question:", "Question input", JOptionPane.PLAIN_MESSAGE, null, null, text);

        qa_txtQuestion.setText(input);

    }//GEN-LAST:event_qa_txtQuestionMouseClicked

    private void qa_btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qa_btnAddActionPerformed
        // TODO add your handling code here:
        addQ();
        populateQTable(q_tblQuestion);
    }//GEN-LAST:event_qa_btnAddActionPerformed

    private void qd_btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qd_btnDeleteActionPerformed
        // TODO add your handling code here:
        deleteQ();
        populateQTable(q_tblQuestion);
    }//GEN-LAST:event_qd_btnDeleteActionPerformed

    private void qd_cmbQIItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_qd_cmbQIItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_qd_cmbQIItemStateChanged

    private void qd_cmbQIMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_qd_cmbQIMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_qd_cmbQIMouseClicked

    private void qd_cmbQIPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_qd_cmbQIPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_qd_cmbQIPropertyChange

    private void qd_cmbECItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_qd_cmbECItemStateChanged
        // TODO add your handling code here:
        String examCode = (String) qd_cmbEC.getSelectedItem();

        try (Connection con = new ConnectDB().getConnection()) {
            populateQuestionIdComboBox(getExamIdFromCode(con, examCode), qd_cmbQI);
        } catch (Exception e) {

        }
    }//GEN-LAST:event_qd_cmbECItemStateChanged

    private void q_tblQuestionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_q_tblQuestionMouseClicked
        // TODO add your handling code here:
      
    }//GEN-LAST:event_q_tblQuestionMouseClicked

    private void btnLogOutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLogOutActionPerformed
        // TODO add your handling code here:
        setVisible(false);
      
    }//GEN-LAST:event_btnLogOutActionPerformed

    private void stu_txtSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stu_txtSearchActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_stu_txtSearchActionPerformed

    private void res_btnClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_res_btnClearActionPerformed
        // TODO add your handling code here:
        populateResultsTable(res_tblResult);
        res_txtSearch.setText("");
    }//GEN-LAST:event_res_btnClearActionPerformed

    private void pnlNavStudentsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNavStudentsMouseClicked
        // TODO add your handling code here:
        tabMenuNav.setSelectedIndex(3);
        populateStuTable(stu_tblStudent);

        populateResultsTable(res_tblResult);

        populateExamTable(exam_tblExam);
        popExamBox(exam_cmbEC);
        popExamBox(eu_cmbEC);
        popExamBox(ed_cmbEC);

        populateQTable(q_tblQuestion);
        popExamBox(q_cmbSearch);
        popExamBox(qa_cmbEC);
        popExamBox(qd_cmbEC);
        
        populateTchTable(tch_tblTeacher);
    }//GEN-LAST:event_pnlNavStudentsMouseClicked

    private void pnlNavResultsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNavResultsMouseClicked
        // TODO add your handling code here:
        tabMenuNav.setSelectedIndex(2);
        populateStuTable(stu_tblStudent);

        populateResultsTable(res_tblResult);

        populateExamTable(exam_tblExam);
        popExamBox(exam_cmbEC);
        popExamBox(eu_cmbEC);
        popExamBox(ed_cmbEC);

        populateQTable(q_tblQuestion);
        popExamBox(q_cmbSearch);
        popExamBox(qa_cmbEC);
        popExamBox(qd_cmbEC);
        
        populateTchTable(tch_tblTeacher);
    }//GEN-LAST:event_pnlNavResultsMouseClicked

    private void pnlNavExamsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNavExamsMouseClicked
        // TODO add your handling code here:
        tabMenuNav.setSelectedIndex(0);
        populateStuTable(stu_tblStudent);

        populateResultsTable(res_tblResult);

        populateExamTable(exam_tblExam);
        popExamBox(exam_cmbEC);
        popExamBox(eu_cmbEC);
        popExamBox(ed_cmbEC);

        populateQTable(q_tblQuestion);
        popExamBox(q_cmbSearch);
        popExamBox(qa_cmbEC);
        popExamBox(qd_cmbEC);
        
        populateTchTable(tch_tblTeacher);
    }//GEN-LAST:event_pnlNavExamsMouseClicked

    private void pnlNavQuestionsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNavQuestionsMouseClicked
        // TODO add your handling code here:
        tabMenuNav.setSelectedIndex(1);
        populateStuTable(stu_tblStudent);

        populateResultsTable(res_tblResult);

        populateExamTable(exam_tblExam);
        popExamBox(exam_cmbEC);
        popExamBox(eu_cmbEC);
        popExamBox(ed_cmbEC);

        populateQTable(q_tblQuestion);
        popExamBox(q_cmbSearch);
        popExamBox(qa_cmbEC);
        popExamBox(qd_cmbEC);
        
        populateTchTable(tch_tblTeacher);
    }//GEN-LAST:event_pnlNavQuestionsMouseClicked

    private void pnlNavTeacherMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pnlNavTeacherMouseClicked
        // TODO add your handling code here:
        tabMenuNav.setSelectedIndex(4);
        populateStuTable(stu_tblStudent);

        populateResultsTable(res_tblResult);

        populateExamTable(exam_tblExam);
        popExamBox(exam_cmbEC);
        popExamBox(eu_cmbEC);
        popExamBox(ed_cmbEC);

        populateQTable(q_tblQuestion);
        popExamBox(q_cmbSearch);
        popExamBox(qa_cmbEC);
        popExamBox(qd_cmbEC);
        
        populateTchTable(tch_tblTeacher);
    }//GEN-LAST:event_pnlNavTeacherMouseClicked

    private void tch_btnRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tch_btnRegisterActionPerformed
        // TODO add your handling code here:
        if((!tch_txtFirstName.getText().equals("")) || (!tch_txtLastName.getText().equals("")) || (!tch_txtEmail.getText().equals("")) || (!tch_txtPassword.getText().equals("")) || (!tch_txtContactNo.getText().equals("")) || (!tch_txtSubject.getText().equals(""))){
            registerStudent();
            populateTchTable(tch_tblTeacher);
            clearTeacherForm();
        }
        else{
            JOptionPane.showMessageDialog(null, "Missing information!");
        }
    }//GEN-LAST:event_tch_btnRegisterActionPerformed

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
            java.util.logging.Logger.getLogger(tch_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(tch_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(tch_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(tch_Menu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new tch_Menu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogOut;
    private javax.swing.JButton ea_btnAddExam;
    private javax.swing.JTextField ea_txtDuration;
    private javax.swing.JTextField ea_txtExamCode;
    private javax.swing.JTextField ea_txtTeacherID;
    private javax.swing.JButton ed_btnDelete;
    private javax.swing.JComboBox<String> ed_cmbEC;
    private javax.swing.JButton eu_btnUpdate;
    private javax.swing.JComboBox<String> eu_cmbEC;
    private javax.swing.JTextField eu_txtDuration;
    private javax.swing.JButton exam_btnRefresh;
    private javax.swing.JComboBox<String> exam_cmbEC;
    private javax.swing.JPanel exam_pnlAdd;
    private javax.swing.JPanel exam_pnlDelete;
    private javax.swing.JPanel exam_pnlUpdate;
    private javax.swing.JTable exam_tblExam;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JSeparator jSeparator6;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblExamName;
    private javax.swing.JLabel lblTrial;
    private javax.swing.JPanel pnlAddQ;
    private javax.swing.JPanel pnlDeleteQ;
    private javax.swing.JPanel pnlExam;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlMenu;
    private javax.swing.JPanel pnlMenu1;
    private javax.swing.JPanel pnlNavExams;
    private javax.swing.JPanel pnlNavQuestions;
    private javax.swing.JPanel pnlNavResults;
    private javax.swing.JPanel pnlNavStudents;
    private javax.swing.JPanel pnlNavTeacher;
    private javax.swing.JPanel pnlQuestions;
    private javax.swing.JPanel pnlResults;
    private javax.swing.JPanel pnlStudents;
    private javax.swing.JPanel pnlTeacher;
    private javax.swing.JButton q_btnRefresh;
    private javax.swing.JComboBox<String> q_cmbSearch;
    private javax.swing.JTable q_tblQuestion;
    private javax.swing.JButton qa_btnAdd;
    private javax.swing.JComboBox<String> qa_cmbEC;
    private javax.swing.JTextField qa_txtA;
    private javax.swing.JTextField qa_txtAns;
    private javax.swing.JTextField qa_txtB;
    private javax.swing.JTextField qa_txtC;
    private javax.swing.JTextField qa_txtD;
    private javax.swing.JTextArea qa_txtQuestion;
    private javax.swing.JButton qd_btnDelete;
    private javax.swing.JComboBox<String> qd_cmbEC;
    private javax.swing.JComboBox<String> qd_cmbQI;
    private javax.swing.JButton res_btnClear;
    private javax.swing.JButton res_btnSearch;
    private javax.swing.JTable res_tblResult;
    private javax.swing.JTextField res_txtSearch;
    private javax.swing.JButton stu_btnClear;
    private javax.swing.JButton stu_btnSearch;
    private javax.swing.JTable stu_tblStudent;
    private javax.swing.JTextField stu_txtSearch;
    private javax.swing.JTabbedPane tabMenuNav;
    private javax.swing.JButton tch_btnRegister;
    private javax.swing.JTable tch_tblTeacher;
    private javax.swing.JTextField tch_txtContactNo;
    private javax.swing.JTextField tch_txtEmail;
    private javax.swing.JTextField tch_txtFirstName;
    private javax.swing.JTextField tch_txtLastName;
    private javax.swing.JTextField tch_txtPassword;
    private javax.swing.JTextField tch_txtSubject;
    // End of variables declaration//GEN-END:variables
}
