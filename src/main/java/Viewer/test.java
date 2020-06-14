package Viewer;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;

public class test {
    public static void main (String [] args) {
        test_Frame tf = new test_Frame();
    }
}

class test_Frame extends JFrame implements ActionListener{

    private JFileChooser jfc = new JFileChooser();
    private JButton jbt_open = new JButton("열기");
    private JButton jbt_save = new JButton("저장");
    private JLabel jlb = new JLabel(" ");
    public test_Frame(){
        super("test");
        this.init();
        this.start();
        this.setSize(400,200);
        this.setVisible(true);
    }
    public void init(){
        getContentPane().setLayout(new FlowLayout());
        add(jbt_open);
        add(jbt_save);
        add(jlb);
    }
    public void start(){
        jbt_open.addActionListener(this);
        jbt_save.addActionListener(this);

        jfc.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
        // 파일 필터
        jfc.setMultiSelectionEnabled(false);//다중 선택 불가
    }

    @Override
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        if(arg0.getSource() == jbt_open){
            if(jfc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION){
                // showopendialog 열기 창을 열고 확인 버튼을 눌렀는지 확인
                jlb.setText("열기 경로 : " + jfc.getSelectedFile().toString());
            }
        }else if(arg0.getSource() == jbt_save){
            if(jfc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION){

                // 가정 : 우리는 포트폴리오 파일 File 객체로

                // showSaveDialog 저장 창을 열고 확인 버튼을 눌렀는지 확인
                jlb.setText("저장 경로 : " + jfc.getSelectedFile().toString() + "." + jfc.getFileFilter().getDescription());
            }
        }
    }
}

