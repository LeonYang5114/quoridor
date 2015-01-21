package yang.leon.quoridor;

import javax.swing.JFrame;

public abstract class AbstractModeController {
    
    public abstract JFrame getFrame();

    public abstract AbstractModeWizard getCurrModeWizard();

    public abstract void setModeWizard(AbstractModeWizard wizard);
    
    public abstract void showWizard();

    public abstract void setGameController(AbstractGameController controller);
    
    public abstract AbstractGameController getGameController();
    
    public abstract void launch(AbstractLauncher launcher);
    
    public abstract void viewRegisterNotify(String clientName);

    public abstract void modelRegisterNotify(String clientHost);
}
