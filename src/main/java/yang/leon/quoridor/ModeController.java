package yang.leon.quoridor;

import javax.swing.JFrame;

public class ModeController extends AbstractModeController {

    private JFrame frame;
    private AbstractModeWizard modeWizard;
    private AbstractGameController gameController;

    public ModeController(JFrame frame) {
	this.frame = frame;
	setModeWizard(new LocalGameWizard());
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
    public void setModeWizard(AbstractModeWizard wizard) {
	modeWizard = wizard;
	modeWizard.setModeController(this);
    }
    
    public void showWizard() {
	getCurrModeWizard().showWizard(getFrame());
    }

    @Override
    public void setGameController(AbstractGameController controller) {
	gameController = controller;
	gameController.setModeController(this);
    }
    
    @Override
    public AbstractGameController getGameController() {
	return gameController;
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
	if (modeWizard instanceof FindGameWizard) {
	    ((FindGameWizard) modeWizard).serverConnected(serverName);
	}
    }

}
