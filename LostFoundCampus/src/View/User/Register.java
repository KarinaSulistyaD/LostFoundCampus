/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package View.User;

import Controller.ControllerUser;
import Model.User.ModelUser;
import View.Component.AppButtonFactory;
import View.Component.AppContentPanel;
import View.Component.AppFrame;
import View.Component.AppTheme;
import View.Component.LabeledInput;

import javax.swing.*;
import java.awt.*;

public class Register extends AppFrame {

    private final LabeledInput namaInput;
    private final LabeledInput usernameInput;
    private final LabeledInput passwordInput;

    public Register() {
        this(null);
    }

    public Register(JFrame parentFrame){
        super("Register", AppTheme.WINDOW_AUTH_REGISTER, parentFrame);

        JPanel panel = createScreenPanel();

        JPanel form = new AppContentPanel(new GridBagLayout());
        form.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 14, 8, 14);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        JLabel title = new JLabel("REGISTER");
        title.setFont(AppTheme.TITLE_FONT);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setForeground(AppTheme.PRIMARY);

        namaInput = LabeledInput.text("Nama", 16);
        usernameInput = LabeledInput.text("Username", 16);
        passwordInput = LabeledInput.password("Password", 16);

        JButton btnRegister = AppButtonFactory.success("REGISTER");
        JButton btnLogin = AppButtonFactory.primary("LOGIN");
        JButton btnCancel = hasParentFrame() ? AppButtonFactory.danger("CANCEL") : null;

        JPanel buttonPanel = new JPanel(new GridLayout(1, hasParentFrame() ? 3 : 2, 12, 0));
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnLogin);
        if (btnCancel != null) {
            buttonPanel.add(btnCancel);
        }

        gbc.gridy = 0;
        form.add(title, gbc);

        gbc.gridy = 1;
        form.add(namaInput, gbc);

        gbc.gridy = 2;
        form.add(usernameInput, gbc);

        gbc.gridy = 3;
        form.add(passwordInput, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(14, 14, 8, 14);
        form.add(buttonPanel, gbc);

        panel.add(form, BorderLayout.CENTER);
        setScreenContent(panel);

        btnRegister.addActionListener(e -> register());

        btnLogin.addActionListener(e -> {
            openLoginScreen();
        });

        if (btnCancel != null) {
            btnCancel.addActionListener(e -> backToParent());
        }
    }

    private void register(){

        if(namaInput.getText().isEmpty()
                || usernameInput.getText().isEmpty()
                || passwordInput.getPassword().length == 0){

            JOptionPane.showMessageDialog(
                    this,
                    "Data tidak boleh kosong"
            );

            return;
        }

        ModelUser user =
                new ModelUser();

        user.setNama(
                namaInput.getText()
        );

        user.setUsername(
                usernameInput.getText()
        );

        user.setPassword(
                new String(passwordInput.getPassword())
        );

        ControllerUser controller =
                new ControllerUser();

        controller.insert(user);

        JOptionPane.showMessageDialog(
                this,
                "Register berhasil"
        );

        openLoginScreen();
    }

    private void openLoginScreen() {
        if (hasParentFrame() && getParentFrame() instanceof Login) {
            backToParent();
            return;
        }

        dispose();
        if (hasParentFrame()) {
            new Login(getParentFrame()).setVisible(true);
            return;
        }

        new Login().setVisible(true);
    }
}