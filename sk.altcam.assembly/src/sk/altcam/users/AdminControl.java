package sk.altcam.users;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;
import org.eclipse.swt.widgets.Text;

import sk.altcam.assembly.AssemblyMonitoring;

public class AdminControl extends Composite{
  private static int MIN_TEXT_WIDTH = 300;
  private static int MIN_LIST_WIDTH = 400;
  private static int MIN_LIST_HEIGHT = 400;
  private static int MIN_GROUP_WIDTH = 600;
  private static int MIN_BUTTON_WIDTH = 100;
  private List users;
  private Text userID;
  private Text userName;
  private Text password;
  private Button chbIsAdmin;
  private Button btnAdd;
  public Button getBtnAdd() {
    return btnAdd;
  }

  public Button getBtnSave() {
    return btnSave;
  }

  public Button getBtnReset() {
    return btnReset;
  }

  public Button getBtnDelete() {
    return btnDelete;
  }

  private Button btnSave;
  private Button btnReset;
  private Button btnDelete;  
  
  public Text getUserID() {
    return userID;
  }

  public Text getUserName() {
    return userName;
  }

  public Text getPassword() {
    return password;
  }

  public Button getChbIsAdmin() {
    return chbIsAdmin;
  }

  public AdminControl(Composite parent, int style , AssemblyMonitoring assemblyMonitoring){
    super(parent,style);
    this.setLayout(new GridLayout(1,false));
    Group grUsers = new Group (this, SWT.BORDER);
    GridData gdGrUsers = new GridData(GridData.FILL_BOTH);
    gdGrUsers.widthHint = AdminControl.MIN_GROUP_WIDTH;
    grUsers.setText(" Admin: ");
    grUsers.setLayout(new GridLayout(6,false));
    grUsers.setLayoutData(gdGrUsers);
    GridData gdUsersLabel = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
    gdUsersLabel.verticalSpan = 5;
    Label label = new Label(grUsers, SWT.NONE);
    label.setText("Uzivatelia:");
    label.setLayoutData(gdUsersLabel);
    users = new List(grUsers, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
    GridData gdUsers = new GridData(GridData.FILL_VERTICAL);
    gdUsers.widthHint = AdminControl.MIN_LIST_WIDTH;
    gdUsers.heightHint = AdminControl.MIN_LIST_HEIGHT;
    gdUsers.verticalSpan = 5;
    users.setLayoutData(gdUsers);
    label = new Label(grUsers, SWT.NONE);
    label.setText("ID Uzivatela:");
    userID = new Text(grUsers, SWT.BORDER);
    GridData gdText = new GridData();
    gdText.widthHint = AdminControl.MIN_TEXT_WIDTH;
    gdText.horizontalSpan = 3;
    userID.setLayoutData(gdText);
    label = new Label(grUsers, SWT.NONE);
    label.setText("Meno uzivatela:");
    userName = new Text(grUsers, SWT.BORDER);
    userName.setLayoutData(gdText);
    label = new Label(grUsers, SWT.NONE);
    label.setText("Heslo:");
    password = new Text(grUsers, SWT.BORDER | SWT.PASSWORD);
    password.setLayoutData(gdText);
    label = new Label(grUsers, SWT.NONE);
    chbIsAdmin = new Button(grUsers, SWT.CHECK);
    chbIsAdmin.setText("Administrator");
    GridData gdChbIsAdminText = new GridData();
    gdChbIsAdminText.horizontalSpan = 3;
    chbIsAdmin.setLayoutData(gdChbIsAdminText);
    GridData gdButton = new GridData(GridData.VERTICAL_ALIGN_BEGINNING);
    gdButton.widthHint = AdminControl.MIN_BUTTON_WIDTH;
    btnAdd = new Button(grUsers, SWT.PUSH | SWT.CENTER);
    btnAdd.setText("Novy");
    btnAdd.setLayoutData(gdButton);
    btnSave = new Button(grUsers, SWT.PUSH | SWT.CENTER);
    btnSave.setText("Uloz");
    btnSave.setLayoutData(gdButton);
    btnReset = new Button(grUsers, SWT.PUSH | SWT.CENTER);
    btnReset.setText("Reset");
    btnReset.setLayoutData(gdButton);
    btnDelete = new Button(grUsers, SWT.PUSH | SWT.CENTER);
    btnDelete.setText("Zmaz");
    btnDelete.setLayoutData(gdButton);

  }
  
  public List getUsers(){
    return users;
  }
  
}
