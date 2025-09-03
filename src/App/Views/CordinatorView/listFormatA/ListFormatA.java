/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package App.Views.CordinatorView.listFormatA;

import App.Models.FormatA;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.SwingUtilities;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Usuario
 */
public class ListFormatA extends JComponent {
    
    private MigLayout layout;
    
    public ListFormatA(){
        init();
    }
    private void init(){
        int idListFormatA[] = new int[]{
            1,2,3
        };
        
        layout = new MigLayout("wrap 1, fillx, gapy 0, inset 2", "fill");
        setLayout(layout);
        setOpaque(true);
        //poniendo box con datos de FormatA basicos
        for (int i = 0; i < idListFormatA.length; i++) {
            addBox(idListFormatA[i], i);
        }
    }
    
    private void addBox(int id, int index) {
        BoxFormatA item = new BoxFormatA(id, index);
        
        item.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //pasar id de formatoA para visualizar formatoA en el dialog
                FormatAView content = new FormatAView();
                JDialog dialog = new JDialog();
                dialog.setContentPane(content);
                dialog.pack();
                dialog.setLocationRelativeTo(null);
                dialog.setVisible(true);
            }
        });
        add(item);
        revalidate();
        repaint();
    }
    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setColor(new Color(222,222,222));//color del menu
        g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
        super.paintComponent(grphcs);
    }
}
