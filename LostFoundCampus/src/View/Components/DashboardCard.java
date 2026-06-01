/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Components;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author karina
 */
public class DashboardCard extends JPanel {

    public DashboardCard(String total, String title, Color color) {

        setBackground(Color.WHITE);

        setPreferredSize(new Dimension(180,100));

        setBorder(
                BorderFactory.createLineBorder(
                        new Color(230,230,230)
                )
        );

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel lblTotal = new JLabel(total);

        lblTotal.setFont(
                new Font("SansSerif", Font.BOLD, 28)
        );

        lblTotal.setForeground(color);

        lblTotal.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel lblTitle = new JLabel(title);

        lblTitle.setFont(
                new Font("SansSerif", Font.PLAIN, 14)
        );

        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        add(Box.createVerticalGlue());

        add(lblTotal);

        add(Box.createVerticalStrut(8));

        add(lblTitle);

        add(Box.createVerticalGlue());
    }
}