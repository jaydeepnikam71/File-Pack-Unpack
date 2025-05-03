import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;

public class MarvellousPackFront extends Template implements ActionListener {
    JButton SUBMIT, PREVIOUS;
    JLabel label1, label2, title;
    final JTextField text1, text2;

    public MarvellousPackFront() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        title = new JLabel("Marvellous Packing Portal");
        Dimension size = title.getPreferredSize();
        title.setBounds(40, 50, size.width + 60, size.height);
        title.setFont(new Font("Century", Font.BOLD, 17));
        title.setForeground(Color.blue);

        label1 = new JLabel("Directory name:");
        label1.setForeground(Color.white);
        label1.setBounds(350, 50, size.width, size.height);

        text1 = new JTextField(15);
        Dimension tsize = text1.getPreferredSize();
        text1.setBounds(500, 50, tsize.width, tsize.height);
        text1.setToolTipText("Enter directory to pack");

        label2 = new JLabel("Destination file name:");
        label2.setForeground(Color.white);
        label2.setBounds(350, 100, size.width + 60, size.height);

        text2 = new JTextField(15);
        text2.setBounds(500, 100, tsize.width, tsize.height);
        text2.setToolTipText("Enter destination packed file name");

        SUBMIT = new JButton("SUBMIT");
        Dimension bsize = SUBMIT.getPreferredSize();
        SUBMIT.setBounds(350, 200, bsize.width, bsize.height);
        SUBMIT.addActionListener(this);

        PREVIOUS = new JButton("PREVIOUS");
        Dimension b2size = PREVIOUS.getPreferredSize();
        PREVIOUS.setBounds(500, 200, b2size.width, b2size.height);
        PREVIOUS.addActionListener(this);

        _header.add(title);
        _content.add(label1);
        _content.add(label2);
        _content.add(text1);
        _content.add(text2);
        _content.add(SUBMIT);
        _content.add(PREVIOUS);

        this.setSize(1000, 400);
        this.setResizable(false);
        this.setVisible(true);
        text1.requestFocusInWindow();
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == exit) {
            this.setVisible(false);
            System.exit(0);
        }

        if (ae.getSource() == minimize) {
            this.setState(this.ICONIFIED);
        }

        if (ae.getSource() == SUBMIT) {
            String sourceDir = text1.getText().trim();
            String destFile = text2.getText().trim();

            if (sourceDir.isEmpty() || destFile.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in both fields.", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            File dir = new File(sourceDir);
            if (!dir.exists() || !dir.isDirectory()) {
                JOptionPane.showMessageDialog(this, "Invalid source directory.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                MarvellousPacker obj = new MarvellousPacker(sourceDir, destFile);
                JOptionPane.showMessageDialog(this, "Packing completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
                new NextPage("MarvellousAdmin");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error: " + e.getMessage(), "Packing Failed", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }

        if (ae.getSource() == PREVIOUS) {
            this.setVisible(false);
            this.dispose();
            new NextPage("MarvellousAdmin");
        }
    }
}
