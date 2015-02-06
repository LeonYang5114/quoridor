package yang.leon.quoridor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import yang.leon.quoridor.mode.AbstractModeController;
import yang.leon.quoridor.mode.HostGameWizard;
import yang.leon.quoridor.mode.ModeController;

public class QuoridorMain extends JFrame {

    private AbstractModeController controller;

    public QuoridorMain() {
	super();
	setTitle("Quoridor");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setJMenuBar(new QuoridorMenuBar());
	controller = new ModeController(this);
    }

    public AbstractModeController getModeController() {
	return controller;
    }

    class QuoridorMenuBar extends JMenuBar {

	private JMenu m_mode = new JMenu("Mode");

	private JMenuItem mi_localMode = new JMenuItem("Local Mode");
	private JMenuItem mi_hostMode = new JMenuItem("Host Mode");
	private JMenuItem mi_clientMode = new JMenuItem("Client Mode");

	public QuoridorMenuBar() {
	    add(m_mode);
	    m_mode.add(mi_localMode);
	    m_mode.add(mi_hostMode);
	    m_mode.add(mi_clientMode);

	    mi_localMode.setName("LocalGameWizard");
	    mi_hostMode.setName("HostGameWizard");
	    mi_clientMode.setName("ClientGameWizard");

	    ActionListener changeGameMode = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
		    getModeController().setModeWizard(
			    ((JComponent) e.getSource()).getName());
		}

	    };
	    
	    mi_localMode.addActionListener(changeGameMode);
	    mi_hostMode.addActionListener(changeGameMode);
	    mi_clientMode.addActionListener(changeGameMode);
	}
    }

    public static void main(String[] args) {
	QuoridorMain main = new QuoridorMain();
    }

}
