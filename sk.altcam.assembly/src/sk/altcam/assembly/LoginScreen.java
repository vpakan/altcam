package sk.altcam.assembly;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import sk.altcam.users.AssemblyUsersModel;

public class LoginScreen {
  public static void logIn(Display display,
      final AssemblyUsersModel assemblyUsersModel) {
    // Set layout for shell
    final Shell shell = new Shell(display);
    shell.setText("Monitoring montaze - Prihlasenie");
    GridLayout layout = new GridLayout(1, false);
    shell.setLayout(layout);
    Composite composite = new Composite(shell, SWT.BORDER);
    composite.setLayout(new GridLayout(2, false));
    new Label(composite, SWT.None);
    new Label(composite, SWT.None);
    Label label = new Label(composite, SWT.None);
    label.setText("Zadajte prihlasovacie udaje:");
    GridData gdLabel = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
    gdLabel.horizontalSpan = 2;
    label.setLayoutData(gdLabel);
    new Label(composite, SWT.None);
    new Label(composite, SWT.None);
    label = new Label(composite, SWT.None);
    label.setText("ID uzivatela:");
    final Text txUserId = new Text(composite, SWT.BORDER);
    GridData gdText = new GridData();
    gdText.widthHint = 250;
    txUserId.setLayoutData(gdText);
    label = new Label(composite, SWT.None);
    label.setText("Heslo:");
    final Text txPassword = new Text(composite, SWT.PASSWORD | SWT.BORDER);
    txPassword.setLayoutData(gdText);
    new Label(composite, SWT.None);
    new Label(composite, SWT.None);
    Composite btnComposite = new Composite(composite, SWT.None);
    btnComposite.setLayout(new GridLayout(2, true));
    GridData gdBtnComposite = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
    gdBtnComposite.horizontalSpan = 2;
    btnComposite.setLayoutData(gdBtnComposite);
    Button btnLogIn = new Button(btnComposite, SWT.BORDER | SWT.FLAT);
    btnLogIn.setText("Prihlas");
    GridData gdLoginButton = new GridData(GridData.HORIZONTAL_ALIGN_END);
    gdLoginButton.widthHint = 160;
    btnLogIn.setLayoutData(gdLoginButton);
    Button btnCancel = new Button(btnComposite, SWT.BORDER | SWT.FLAT);
    btnCancel.setText("Zrus");
    GridData gdCancelButton = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
    gdCancelButton.widthHint = 160;
    btnCancel.setLayoutData(gdCancelButton);
    btnCancel.addSelectionListener(new SelectionListener() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        shell.dispose();
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent arg0) {
      }
    });
    btnLogIn.addSelectionListener(new SelectionListener() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        if (assemblyUsersModel.checkAndSetLoggedUser(txUserId.getText(),
            txPassword.getText())) {
          shell.dispose();
        } else {
          MessageBox.displayMessageBox(shell, SWT.ERROR,
              "Monitoring montaze - chyba prihlasenia",
              "Zadane prihlasovacie udaje su nespravne.\nZadajte znovu prosim.");
          txPassword.setText("");
          txPassword.setFocus();
        }
      }

      @Override
      public void widgetDefaultSelected(SelectionEvent arg0) {
      }
    });
    // Ask the shell to display its content
    shell.pack();
    Point shellSize = shell.getSize();
    shell.setLocation((display.getBounds().width - shellSize.x) / 2,
        (display.getBounds().height - shellSize.y) / 2);
    shell.open();
    while (!shell.isDisposed()) {
      if (!display.readAndDispatch())
        display.sleep();
    }
  }
}
