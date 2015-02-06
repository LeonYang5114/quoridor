package yang.leon.quoridor.mode;

import javax.swing.JFrame;

import yang.leon.quoridor.AbstractGameController;

public abstract class AbstractModeController {
    
    public abstract JFrame getFrame();

    public abstract AbstractModeWizard getCurrModeWizard();

    public abstract void setModeWizard(String wizard);
    
    public abstract void launch(AbstractLauncher launcher);
    
    public abstract void viewRegisterNotify(String clientName);

    public abstract void modelRegisterNotify(String clientHost);
}
