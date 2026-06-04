/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Component;

import View.Admin.DashboardAdmin;
import View.Admin.InputBarang;
import View.Admin.ViewBarang;
import View.User.Login;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author karina
 */
public class Sidebar extends JPanel {

    public Sidebar(JFrame currentFrame) {

        setBackground(new Color(15,23,42));

        setPreferredSize(new Dimension(220,0));

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(25));

        JLabel title = new JLabel("Lost & Found");

        title.setForeground(Color.WHITE);

        title.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(title);

        add(Box.createVerticalStrut(40));

        // DASHBOARD
        JButton btnDashboard =
                menuButton("Dashboard");

        btnDashboard.addActionListener(e -> {

            new DashboardAdmin().setVisible(true);

            currentFrame.dispose();
        });

        add(btnDashboard);

        // VIEW BARANG
        JButton btnBarang =
                menuButton("Daftar Barang");

        btnBarang.addActionListener(e -> {

            new ViewBarang().setVisible(true);

            currentFrame.dispose();
        });

        add(btnBarang);

        // INPUT BARANG
        JButton btnInput =
                menuButton("Tambah Barang");

        btnInput.addActionListener(e -> {

            new InputBarang().setVisible(true);

            currentFrame.dispose();
        });

        add(btnInput);

        // LOGOUT
        JButton btnLogout =
                menuButton("Logout");

        btnLogout.addActionListener(e -> {

            new Login().setVisible(true);

            currentFrame.dispose();
        });

        add(btnLogout);
    }

    private JButton menuButton(String text){

        JButton btn = new JButton(text);

        btn.setMaximumSize(
                new Dimension(Integer.MAX_VALUE,45)
        );

        btn.setBackground(new Color(15,23,42));

        btn.setForeground(Color.WHITE);

        btn.setFocusPainted(false);

        btn.setHorizontalAlignment(SwingConstants.LEFT);

        btn.setBorder(
                BorderFactory.createEmptyBorder(
                        10,20,10,10
                )
        );

        btn.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );

        return btn;
    }
}