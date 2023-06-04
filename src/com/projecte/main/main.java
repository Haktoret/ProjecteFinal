package com.projecte.main;

import com.projectefinal.menuprincipal.menu;
import javax.swing.UIManager;

public class main {

    public static void main(String[] args) {
        
        try {
            // Establecer el tema FlatDarkLaf
            UIManager.setLookAndFeel(new com.formdev.flatlaf.FlatDarkLaf());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        menu m = new menu();
    }
}
