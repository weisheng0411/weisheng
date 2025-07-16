
import java.awt.BorderLayout;
import javax.swing.BorderFactory;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author wei sheng
 */
public class textsystem extends JFrame implements ActionListener{

    // 数据模型类
    static class User {
        String username;
        String password;
        String email;
        String phone;

        User(String username, String password, String email, String phone) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.phone = phone;
        }
    }

    static class ClassInfo {
        String classId;
        String subject;
        String date;
        String time;
        String location;
        String tutorName;
        double fee;

        ClassInfo(String classId, String subject, String date, String time, String location, String tutorName, double fee) {
            this.classId = classId;
            this.subject = subject;
            this.date = date;
            this.time = time;
            this.location = location;
            this.tutorName = tutorName;
            this.fee = fee;
        }
    }

    static class StudentEnrollment {
        String studentId;
        String studentName;
        String classId;

        StudentEnrollment(String studentId, String studentName, String classId) {
            this.studentId = studentId;
            this.studentName = studentName;
            this.classId = classId;
        }
    }

    // 全局变量
    private User currentUser;
    private List<ClassInfo> classList = new ArrayList<>();
    private List<StudentEnrollment> enrollments = new ArrayList<>();
    private JTable classTable; // 班级列表表格
    private JTable studentTable; // 学生名单表格
    private ClassTableModel classTableModel;
    private StudentTableModel studentTableModel;

    // 文件路径
    private static final String USER_FILE = "user.txt";
    private static final String CLASS_FILE = "classinfo.txt";
    private static final String ENROLL_FILE = "student_enroll.txt";

    // 登录界面组件
    private JTextField loginUsernameField;
    private JPasswordField loginPasswordField;

    // 主界面组件
    private JPanel mainPanel;
    private JTextField classIdField, subjectField, dateField, timeField, locationField, feeField;
    private JButton addButton, editButton, deleteButton, viewStudentsButton, editProfileButton, viewScheduleButton;

    public textsystem() {
        super("Tutor管理系统");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // 加载数据
        loadUsers();
        loadClasses();
        loadEnrollments();

        // 显示登录界面
        showLoginScreen();
    }

    // 登录界面
    private void showLoginScreen() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("用户名:"), gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        loginUsernameField = new JTextField(20);
        panel.add(loginUsernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("密码:"), gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        loginPasswordField = new JPasswordField(20);
        panel.add(loginPasswordField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("登录");
        loginButton.addActionListener(e -> authenticateUser());
        panel.add(loginButton, gbc);

        setContentPane(panel);
        setVisible(true);
    }

    // 用户身份验证
    private void authenticateUser() {
        String username = loginUsernameField.getText().trim();
        String password = new String(loginPasswordField.getPassword());

        // 简单验证 - 在实际应用中应该从user.txt加载并验证
        // 这里假设存在一个默认管理员账户用于演示
        if ((username.equals("tutor") && password.equals("password")) || 
            (currentUser != null && username.equals(currentUser.username) && password.equals(currentUser.password))) {
            // 登录成功，显示主界面
            currentUser = new User(username, password, "tutor@example.com", "123456789");
            showMainScreen();
        } else {
            JOptionPane.showMessageDialog(this, "用户名或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 主界面
    private void showMainScreen() {
        mainPanel = new JPanel(new BorderLayout());

        // 顶部输入区域
        JPanel inputPanel = new JPanel(new GridLayout(2, 6, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("班级信息"));

        inputPanel.add(new JLabel("班级ID:"));
        classIdField = new JTextField();
        inputPanel.add(classIdField);

        inputPanel.add(new JLabel("科目:"));
        subjectField = new JTextField();
        inputPanel.add(subjectField);

        inputPanel.add(new JLabel("日期:"));
        dateField = new JTextField();
        inputPanel.add(dateField);

        inputPanel.add(new JLabel("时间:"));
        timeField = new JTextField();
        inputPanel.add(timeField);

        inputPanel.add(new JLabel("地点:"));
        locationField = new JTextField();
        inputPanel.add(locationField);

        inputPanel.add(new JLabel("费用:"));
        feeField = new JTextField();
        inputPanel.add(feeField);

        // 按钮区域
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("添加班级");
        addButton.addActionListener(this);
        editButton = new JButton("修改班级");
        editButton.addActionListener(this);
        deleteButton = new JButton("删除班级");
        deleteButton.addActionListener(this);
        viewStudentsButton = new JButton("查看学生名单");
        viewStudentsButton.addActionListener(this);
        editProfileButton = new JButton("修改个人资料");
        editProfileButton.addActionListener(this);
        viewScheduleButton = new JButton("查看时间表");
        viewScheduleButton.addActionListener(this);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewStudentsButton);
        buttonPanel.add(editProfileButton);
        buttonPanel.add(viewScheduleButton);

        // 表格区域 - classTable（班级列表表格）
        classTableModel = new ClassTableModel();
        classTable = new JTable(classTableModel);
        JScrollPane scrollPane = new JScrollPane(classTable);

        // 选择行监听器
        classTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && classTable.getSelectedRow() != -1) {
                int selectedRow = classTable.getSelectedRow();
                ClassInfo selectedClass = classList.get(selectedRow);
                populateClassFields(selectedClass);
            }
        });

        // 组装主界面
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(scrollPane, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    // 填充班级信息到输入框
    private void populateClassFields(ClassInfo classInfo) {
        classIdField.setText(classInfo.classId);
        subjectField.setText(classInfo.subject);
        dateField.setText(classInfo.date);
        timeField.setText(classInfo.time);
        locationField.setText(classInfo.location);
        feeField.setText(String.valueOf(classInfo.fee));
    }

    // 清空输入框
    private void clearClassFields() {
        classIdField.setText("");
        subjectField.setText("");
        dateField.setText("");
        timeField.setText("");
        locationField.setText("");
        feeField.setText("");
    }

    // 事件处理
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            handleAddClass();
        } else if (e.getSource() == editButton) {
            handleEditClass();
        } else if (e.getSource() == deleteButton) {
            handleDeleteClass();
        } else if (e.getSource() == viewStudentsButton) {
            handleViewStudents();
        } else if (e.getSource() == editProfileButton) {
            handleEditProfile();
        } else if (e.getSource() == viewScheduleButton) {
            handleViewSchedule();
        }
    }

    // 添加班级
    private void handleAddClass() {
        try {
            String classId = classIdField.getText().trim();
            String subject = subjectField.getText().trim();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();
            String location = locationField.getText().trim();
            double fee = Double.parseDouble(feeField.getText().trim());

            // 验证必填字段
            if (classId.isEmpty() || subject.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "班级ID、科目、日期和时间为必填项", "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // 检查班级ID是否已存在
            for (ClassInfo c : classList) {
                if (c.classId.equals(classId)) {
                    JOptionPane.showMessageDialog(this, "班级ID已存在", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            ClassInfo newClass = new ClassInfo(classId, subject, date, time, location, currentUser.username, fee);
            classList.add(newClass);
            classTableModel.fireTableDataChanged();
            saveClasses();
            clearClassFields();
            JOptionPane.showMessageDialog(this, "班级添加成功");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "费用必须是数字", "输入错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 修改班级
    private void handleEditClass() {
        int selectedRow = classTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一个班级", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        try {
            String classId = classIdField.getText().trim();
            String subject = subjectField.getText().trim();
            String date = dateField.getText().trim();
            String time = timeField.getText().trim();
            String location = locationField.getText().trim();
            double fee = Double.parseDouble(feeField.getText().trim());

            // 验证必填字段
            if (classId.isEmpty() || subject.isEmpty() || date.isEmpty() || time.isEmpty()) {
                JOptionPane.showMessageDialog(this, "班级ID、科目、日期和时间为必填项", "输入错误", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ClassInfo selectedClass = classList.get(selectedRow);
            // 如果修改了班级ID，检查新ID是否已存在
            if (!selectedClass.classId.equals(classId)) {
                for (ClassInfo c : classList) {
                    if (c.classId.equals(classId)) {
                        JOptionPane.showMessageDialog(this, "班级ID已存在", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // 更新班级信息
            selectedClass.classId = classId;
            selectedClass.subject = subject;
            selectedClass.date = date;
            selectedClass.time = time;
            selectedClass.location = location;
            selectedClass.fee = fee;

            classTableModel.fireTableDataChanged();
            saveClasses();
            JOptionPane.showMessageDialog(this, "班级修改成功");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "费用必须是数字", "输入错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 删除班级
    private void handleDeleteClass() {
        int selectedRow = classTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一个班级", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "确定要删除选中的班级吗?", "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            classList.remove(selectedRow);
            classTableModel.fireTableDataChanged();
            saveClasses();
            clearClassFields();
            JOptionPane.showMessageDialog(this, "班级删除成功");
        }
    }

    // 查看学生名单
    private void handleViewStudents() {
        int selectedRow = classTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "请先选择一个班级", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String selectedClassId = classList.get(selectedRow).classId;
        List<StudentEnrollment> classStudents = new ArrayList<>();
        for (StudentEnrollment se : enrollments) {
            if (se.classId.equals(selectedClassId)) {
                classStudents.add(se);
            }
        }

        JDialog dialog = new JDialog(this, "班级学生名单 - " + selectedClassId, true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        // 学生名单表格 - studentTable
        studentTableModel = new StudentTableModel(classStudents);
        studentTable = new JTable(studentTableModel);
        JScrollPane scrollPane = new JScrollPane(studentTable);

        dialog.add(scrollPane);
        dialog.setVisible(true);
    }

    // 修改个人资料
    private void handleEditProfile() {
        JDialog dialog = new JDialog(this, "修改个人资料", true);
        dialog.setSize(300, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new GridLayout(4, 2, 10, 10));
//        dialog.getContentPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        dialog.add(new JLabel("用户名:"));
        JTextField usernameField = new JTextField(currentUser.username);
        usernameField.setEditable(false);
        dialog.add(usernameField);

        dialog.add(new JLabel("密码:"));
        JPasswordField passwordField = new JPasswordField(currentUser.password);
        dialog.add(passwordField);

        dialog.add(new JLabel("邮箱:"));
        JTextField emailField = new JTextField(currentUser.email);
        dialog.add(emailField);

        dialog.add(new JLabel("电话:"));
        JTextField phoneField = new JTextField(currentUser.phone);
        dialog.add(phoneField);

        JButton saveButton = new JButton("保存");
        saveButton.addActionListener(e -> {
            currentUser.password = new String(passwordField.getPassword());
            currentUser.email = emailField.getText().trim();
            currentUser.phone = phoneField.getText().trim();
            saveUsers();
            JOptionPane.showMessageDialog(dialog, "个人资料修改成功");
            dialog.dispose();
        });
        dialog.add(saveButton);

        JButton cancelButton = new JButton("取消");
        cancelButton.addActionListener(e -> dialog.dispose());
        dialog.add(cancelButton);

        dialog.setVisible(true);
    }

    // 查看时间表
    private void handleViewSchedule() {
        // 创建时间表对话框
        JDialog dialog = new JDialog(this, "我的教学时间表", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        
        // 复制并排序班级列表（按日期和时间）
        List<ClassInfo> sortedClasses = new ArrayList<>(classList);
        sortedClasses.sort((c1, c2) -> {
            // 先按日期排序
            int dateCompare = c1.date.compareTo(c2.date);
            if (dateCompare != 0) {
                return dateCompare;
            }
            // 日期相同则按时间排序
            return c1.time.compareTo(c2.time);
        });
        
        // 创建时间表表格模型
        String[] columnNames = {"日期", "时间", "科目", "班级ID", "地点", "费用"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // 填充数据
        for (ClassInfo cls : sortedClasses) {
            Object[] row = {
                cls.date,
                cls.time,
                cls.subject,
                cls.classId,
                cls.location,
                cls.fee
            };
            model.addRow(row);
        }
        
        // 时间表表格 - scheduleTable
        JTable scheduleTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(scheduleTable);
        dialog.add(scrollPane);
        
        dialog.setVisible(true);
    }

    // 加载用户数据
    private void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(USER_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    currentUser = new User(parts[0], parts[1], parts[2], parts[3]);
                }
            }
        } catch (FileNotFoundException e) {
            // 文件不存在，使用默认用户
            currentUser = new User("tutor", "password", "tutor@example.com", "123456789");
            saveUsers();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 加载班级数据
    private void loadClasses() {
        classList.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(CLASS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 7) {
                    try {
                        classList.add(new ClassInfo(
                            parts[0], parts[1], parts[2], parts[3], parts[4], parts[5], Double.parseDouble(parts[6])));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // 文件不存在，将在保存时创建
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 加载学生报名数据
    private void loadEnrollments() {
        enrollments.clear();
        try (BufferedReader br = new BufferedReader(new FileReader(ENROLL_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    enrollments.add(new StudentEnrollment(parts[0], parts[1], parts[2]));
                }
            }
        } catch (FileNotFoundException e) {
            // 创建示例学生数据文件
            try (PrintWriter pw = new PrintWriter(new FileWriter(ENROLL_FILE))) {
                pw.println("s001,张三,class101");
                pw.println("s002,李四,class101");
                pw.println("s003,王五,class102");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            loadEnrollments(); // 重新加载
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 保存用户数据
    private void saveUsers() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(USER_FILE))) {
            pw.println(currentUser.username + "," + currentUser.password + "," + currentUser.email + "," + currentUser.phone);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "保存用户数据失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 保存班级数据
    private void saveClasses() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(CLASS_FILE))) {
            for (ClassInfo ci : classList) {
                pw.println(ci.classId + "," + ci.subject + "," + ci.date + "," + ci.time + "," + 
                           ci.location + "," + ci.tutorName + "," + ci.fee);
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "保存班级数据失败", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    // 班级表格模型
    class ClassTableModel extends AbstractTableModel {
        private String[] columnNames = {"班级ID", "科目", "日期", "时间", "地点", "费用"};

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return classList.size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            ClassInfo ci = classList.get(row);
            switch (col) {
                case 0: return ci.classId;
                case 1: return ci.subject;
                case 2: return ci.date;
                case 3: return ci.time;
                case 4: return ci.location;
                case 5: return ci.fee;
                default: return null;
            }
        }
    }

    // 学生表格模型
    class StudentTableModel extends AbstractTableModel {
        private String[] columnNames = {"学生ID", "学生姓名"};
        private List<StudentEnrollment> data;

        public StudentTableModel(List<StudentEnrollment> data) {
            this.data = data;
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public int getRowCount() {
            return data.size();
        }

        @Override
        public String getColumnName(int col) {
            return columnNames[col];
        }

        @Override
        public Object getValueAt(int row, int col) {
            StudentEnrollment se = data.get(row);
            switch (col) {
                case 0: return se.studentId;
                case 1: return se.studentName;
                default: return null;
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(textsystem::new);
    }
}
