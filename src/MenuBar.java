package src;

import java.awt.event.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MenuBar extends JMenuBar implements ActionListener {

    JMenuBar menuBar = this;

    public MenuBar() {
        // ====================== FILE MENU ====================== //
        JMenu fileMenu = new JMenu("File");

        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem importMenuItem = new JMenuItem("Import");

        newMenuItem.setActionCommand("New");
        openMenuItem.setActionCommand("Open");
        saveMenuItem.setActionCommand("Save");
        importMenuItem.setActionCommand("Import");

        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        importMenuItem.addActionListener(this);
        
        fileMenu.add(newMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(importMenuItem);
        menuBar.add(fileMenu);
        // ====================== EDIT MENU ====================== //
        JMenu editMenu = new JMenu("Edit");

        JMenuItem cutMenuItem = new JMenuItem("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");

        cutMenuItem.setActionCommand("Cut");
        copyMenuItem.setActionCommand("Copy");
        pasteMenuItem.setActionCommand("Paste");

        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        menuBar.add(editMenu);
        // ====================== HELP MENU ====================== //
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        System.out.println(event.getActionCommand().toString());
        switch(event.getActionCommand().toString()) {
            case "Open":
                System.out.println("x");
                JFileChooser fc = new JFileChooser();
                int returnVal = fc.showOpenDialog(MenuBar.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fc.getSelectedFile();
                    String filePath = file.getAbsolutePath();
                    Engine.LoadFile(filePath);
                }
                break;
            case "New":
                NewFile();
                break;
            default:
                break;
        }
    }
    public static void NewFile() {
        System.out.println("New File Bitches");
    }
}
