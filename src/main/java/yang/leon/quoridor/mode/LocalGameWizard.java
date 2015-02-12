package yang.leon.quoridor.mode;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import yang.leon.quoridor.AbstractGameController;
import yang.leon.quoridor.AbstractGameView;
import yang.leon.quoridor.DefaultController;
import yang.leon.quoridor.DefaultModel;
import yang.leon.quoridor.DefaultView;

public class LocalGameWizard extends AbstractModeWizard {

    private JComboBox<Integer> cb_numPlayers;

    private AbstractModeController controller;

    public LocalGameWizard() {
	super();
	setLayout(new GridBagLayout());
	setMinimumSize(new Dimension(300, 250));

	GridBagConstraints c = new GridBagConstraints();
	c.gridx = 0;
	c.gridy = 0;
	c.anchor = GridBagConstraints.SOUTHEAST;
	c.weightx = 0.5;
	c.weighty = 0.5;
	c.insets = new Insets(0, 5, 15, 5);
	add(new JLabel("Number Players: "), c);

	cb_numPlayers = new JComboBox<Integer>();
	for (int i = 2; i <= 4; i++) {
	    cb_numPlayers.addItem(i);
	}
	cb_numPlayers.setSelectedIndex(0);
	c.gridx = 1;
	c.gridy = 0;
	c.anchor = GridBagConstraints.SOUTHWEST;
	add(cb_numPlayers, c);

	JButton btn_launch = new JButton("Launch");
	btn_launch.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		int numPlayers = cb_numPlayers.getItemAt(cb_numPlayers
			.getSelectedIndex());
		AbstractGameController controller = new DefaultController(
			new DefaultModel(numPlayers));
		final AbstractGameView view = new DefaultView(controller);
		view.setViewState("InitialState");
		
		AbstractLauncher launcher = new AbstractLauncher() {
		    @Override
		    public void launch(JFrame frame) {
			frame.getContentPane().removeAll();
			frame.getContentPane().add(view);
			setPreferredSize(null);
			frame.pack();
			frame.setLocationRelativeTo(null);
		    }
		};
		
		getModeController().launch(launcher);
	    }
	});
	c.gridx = 0;
	c.gridy = 1;
	c.gridwidth = 2;
	c.anchor = GridBagConstraints.NORTH;
	c.weightx = 1;
	add(btn_launch, c);
	c.gridwidth = 1;

	setBorder(BorderFactory.createTitledBorder("Local Game"));
    }
    public void setModeController(AbstractModeController controller) {
	this.controller = controller;
    }
    
    public AbstractModeController getModeController() {
	return controller;
    }

}
