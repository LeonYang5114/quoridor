package yang.leon.quoridor.mode;

import java.util.HashMap;

import javax.swing.JFrame;

public class ModeController extends AbstractModeController {

    private JFrame frame;
    private HashMap<String, AbstractModeWizard> modeWizards;
    private AbstractModeWizard modeWizard;

    public ModeController(JFrame frame) {
	this.frame = frame;
	modeWizards = new HashMap<String, AbstractModeWizard>();
	setModeWizard("LocalGameWizard");
    }

    @Override
    public JFrame getFrame() {
	return frame;
    }

    @Override
    public AbstractModeWizard getCurrModeWizard() {
	return modeWizard;
    }

    @Override
    public void setModeWizard(String wizard) {
	if (modeWizards.get(wizard) != null) {
	    modeWizard = modeWizards.get(wizard);
	} else {
	    String name = "yang.leon.quoridor.mode." + wizard;
	    try {
		modeWizard = (AbstractModeWizard) Class.forName(name)
			.getConstructor().newInstance();
	    } catch (Exception e) {
		e.printStackTrace();
		return;
	    }
	    modeWizards.put(wizard, modeWizard);
	    modeWizard.setModeController(this);
	}
	getCurrModeWizard().showWizard(getFrame());
    }

    @Override
    public void launch(AbstractLauncher launcher) {
	launcher.launch(getFrame());
    }

    @Override
    public void viewRegisterNotify(String clientName) {
	if (modeWizard instanceof HostGameWizard) {
	    ((HostGameWizard) modeWizard).clientAdded(clientName);
	}
    }

    @Override
    public void modelRegisterNotify(String serverName) {
	if (modeWizard instanceof ClientGameWizard) {
	    ((ClientGameWizard) modeWizard).serverConnected(serverName);
	}
    }

}
