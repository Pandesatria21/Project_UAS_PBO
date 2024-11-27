import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

// Model Pengguna
class Pengguna {
    private int id;
    private String nama;
    private String email;
    private String password;
    private List<Tugas> daftarTugas;

    public Pengguna(int id, String nama, String email, String password) {
        this.id = id;
        this.nama = nama;
        this.email = email;
        this.password = password;
        this.daftarTugas = new ArrayList<>();
    }

    public void tambahTugas(Tugas tugas) {
        daftarTugas.add(tugas);
    }

    public void hapusTugas(Tugas tugas) {
        daftarTugas.remove(tugas);
    }

    public List<Tugas> getDaftarTugas() {
        return daftarTugas;
    }

    public String getNama() {
        return nama;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

// Model Tugas
class Tugas {
    private int id;
    private String judul;
    private String deskripsi;
    private String status;
    private LocalDate deadline;

    public Tugas(int id, String judul, String deskripsi, LocalDate deadline) {
        this.id = id;
        this.judul = judul;
        this.deskripsi = deskripsi;
        this.status = "Belum Selesai";
        this.deadline = deadline;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJudul() {
        return judul;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getDeadline() {
        return deadline;
    }

    @Override
    public String toString() {
        return judul + " (Deadline: " + deadline + ", Status: " + status + ")";
    }
}

// Aplikasi Utama
public class ManajamenTugas {
    private static List<Pengguna> penggunaList = new ArrayList<>();

    public static void main(String[] args) {
        // Menambahkan pengguna dummy
        penggunaList.add(new Pengguna(1, "PandeSatria", "pandesatria@gmail.com", "pandesatria21"));

        // Memulai aplikasi dengan halaman login
        SwingUtilities.invokeLater(HalamanLogin::new);
    }

    public static List<Pengguna> getPenggunaList() {
        return penggunaList;
    }
}

// Halaman Login
class HalamanLogin extends JFrame {
    public HalamanLogin() {
        setTitle("Login");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama dengan gradien
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(72, 61, 139);
                Color color2 = new Color(123, 104, 238);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

        JLabel titleLabel = new JLabel("MANAJEMEN TUGAS");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("LOGIN");
        subtitleLabel.setForeground(Color.WHITE);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));

        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setForeground(Color.WHITE);
        loginButton.setBackground(new Color(72, 61, 139));
        loginButton.setFocusPainted(false);
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton daftarButton = new JButton("DAFTAR");
        daftarButton.setFont(new Font("Arial", Font.BOLD, 16));
        daftarButton.setForeground(Color.WHITE);
        daftarButton.setBackground(new Color(123, 104, 238));
        daftarButton.setFocusPainted(false);
        daftarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            Pengguna pengguna = ManajamenTugas.getPenggunaList().stream()
                    .filter(p -> p.getNama().equals(username) && p.getEmail().equals(email) && p.getPassword().equals(password))
                    .findFirst()
                    .orElse(null);

            if (pengguna != null) {
                dispose();
                SwingUtilities.invokeLater(() -> new HalamanUtama(pengguna));
            } else {
                JOptionPane.showMessageDialog(this, "Username, email, atau password salah!", "Login Gagal", JOptionPane.ERROR_MESSAGE);
            }
        });

        daftarButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(HalamanDaftar::new);
        });

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(emailField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(loginButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(daftarButton);

        add(mainPanel);
        setVisible(true);
    }
}

// Halaman Daftar Pengguna
class HalamanDaftar extends JFrame {
    public HalamanDaftar() {
        setTitle("Daftar Pengguna Baru");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));

        JLabel titleLabel = new JLabel("DAFTAR PENGGUNA BARU");
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Isi Data");
        subtitleLabel.setForeground(Color.BLACK);
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JTextField usernameField = new JTextField();
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));

        JTextField emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        emailField.setBorder(BorderFactory.createTitledBorder("Email"));

        JPasswordField passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));

        JButton daftarButton = new JButton("DAFTAR");
        daftarButton.setFont(new Font("Arial", Font.BOLD, 18));
        daftarButton.setForeground(Color.BLACK);
        daftarButton.setBackground(new Color(72, 61, 139));
        daftarButton.setFocusPainted(false);
        daftarButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        daftarButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            // Cek apakah pengguna sudah ada
            if (ManajamenTugas.getPenggunaList().stream().anyMatch(p -> p.getEmail().equals(email))) {
                JOptionPane.showMessageDialog(this, "Email sudah digunakan!", "Pendaftaran Gagal", JOptionPane.ERROR_MESSAGE);
            } else {
                // Daftar pengguna baru
                Pengguna penggunaBaru = new Pengguna(ManajamenTugas.getPenggunaList().size() + 1, username, email, password);
                ManajamenTugas.getPenggunaList().add(penggunaBaru);
                JOptionPane.showMessageDialog(this, "Pendaftaran berhasil! Silakan login.", "Berhasil", JOptionPane.INFORMATION_MESSAGE);
                dispose();
                SwingUtilities.invokeLater(HalamanLogin::new);
            }
        });

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(subtitleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(usernameField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(emailField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        mainPanel.add(passwordField);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
        mainPanel.add(daftarButton);

        add(mainPanel);
        setVisible(true);
    }
}

//HalamanUtama
class HalamanUtama extends JFrame {
    private Pengguna pengguna;
    private DefaultListModel<Tugas> listModel;

    public HalamanUtama(Pengguna pengguna) {
        this.pengguna = pengguna;
        setTitle("Dashboard - Manajemen Tugas");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel utama dengan gradien
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(72, 61, 139);
                Color color2 = new Color(123, 104, 238);
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Header dengan nama pengguna
        JLabel headerLabel = new JLabel("Selamat Datang, " + pengguna.getNama(), JLabel.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 20));
        headerLabel.setForeground(Color.WHITE);
        mainPanel.add(headerLabel, BorderLayout.NORTH);

        // Daftar tugas
        listModel = new DefaultListModel<>();
        pengguna.getDaftarTugas().forEach(listModel::addElement);
        JList<Tugas> tugasList = new JList<>(listModel);
        tugasList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Tugas) {
                    Tugas tugas = (Tugas) value;
                    label.setText(tugas.getJudul() + " (Deadline: " + tugas.getDeadline() + ", Status: " + tugas.getStatus() + ")");
                }
                label.setFont(new Font("Arial", Font.PLAIN, 14));
                return label;
            }
        });
        tugasList.setBackground(new Color(240, 240, 255));
        tugasList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tugasList.setFixedCellHeight(40);
        mainPanel.add(new JScrollPane(tugasList), BorderLayout.CENTER);

        // Panel tombol
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        // Tombol "Tambah Tugas"
        JButton addButton = new JButton("Tambah Tugas");
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setBackground(new Color(100, 149, 237));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);
        addButton.setPreferredSize(new Dimension(120, 40));
        addButton.addActionListener(e -> {
            String judul = JOptionPane.showInputDialog(this, "Masukkan Judul:");
            String deskripsi = JOptionPane.showInputDialog(this, "Masukkan Deskripsi:");
            String deadlineInput = JOptionPane.showInputDialog(this, "Masukkan Deadline (format: YYYY-MM-DD):");
            try {
                LocalDate deadline = LocalDate.parse(deadlineInput);
                Tugas tugas = new Tugas(listModel.getSize() + 1, judul, deskripsi, deadline);
                pengguna.tambahTugas(tugas);
                listModel.addElement(tugas);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Format tanggal salah!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Tombol "Hapus Tugas"
        JButton deleteButton = new JButton("Hapus Tugas");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.setBackground(new Color(255, 99, 71));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);
        deleteButton.setPreferredSize(new Dimension(120, 40));
        deleteButton.addActionListener(e -> {
            Tugas selectedTugas = tugasList.getSelectedValue();
            if (selectedTugas != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus tugas ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    pengguna.hapusTugas(selectedTugas);
                    listModel.removeElement(selectedTugas);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Pilih tugas yang ingin dihapus!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Tombol "Edit Status Tugas"
        JButton editButton = new JButton("Edit Status Tugas");
        editButton.setFont(new Font("Arial", Font.BOLD, 14));
        editButton.setBackground(new Color(255, 215, 0));
        editButton.setForeground(Color.WHITE);
        editButton.setFocusPainted(false);
        editButton.setPreferredSize(new Dimension(150, 40));
        editButton.addActionListener(e -> {
            Tugas selectedTugas = tugasList.getSelectedValue();
            if (selectedTugas != null) {
                String newStatus = JOptionPane.showInputDialog(this, "Masukkan status baru (Belum Selesai/Selesai):");
                selectedTugas.setStatus(newStatus);
                tugasList.repaint();
            } else {
                JOptionPane.showMessageDialog(this, "Pilih tugas yang ingin diedit!", "Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // Tombol "Logout"
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setBackground(new Color(255, 69, 0));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFocusPainted(false);
        logoutButton.setPreferredSize(new Dimension(120, 40));
        logoutButton.addActionListener(e -> {
            dispose(); // Menutup halaman utama
            SwingUtilities.invokeLater(HalamanLogin::new); // Kembali ke halaman login
        });

        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);
        buttonPanel.add(logoutButton);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }
}