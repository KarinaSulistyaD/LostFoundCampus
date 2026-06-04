/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.Component;

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author karina
 */
public class CustomButton extends JButton {

    public CustomButton(String text) {

        super(text);

        setFont(new Font("SansSerif", Font.BOLD, 14));

        setForeground(Color.WHITE);

        setBackground(new Color(37, 99, 235));

        setFocusPainted(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));

        setBorder(BorderFactory.createEmptyBorder(12,20,12,20));

        setOpaque(true);

        setContentAreaFilled(true);
    }
}