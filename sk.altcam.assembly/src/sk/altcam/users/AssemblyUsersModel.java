package sk.altcam.users;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;

import sk.altcam.assembly.AssemblyMonitoring;
import sk.altcam.assembly.MessageBox;
import sk.altcam.assembly.entity.User;

public class AssemblyUsersModel {
  
  private Properties properties = new Properties();
  private final AdminControl adminControl;
  private static final String USERS_FILE_NAME = "AssemblyUsers.properties";
  private static final String NEW_ID = "##noveid##";
  private User loggedUser = null;
  
  public AssemblyUsersModel (AdminControl adminControl){
    this.adminControl = adminControl;
    List<User> users = getUsers();
    org.eclipse.swt.widgets.List lsUsers = adminControl.getUsers();
    lsUsers.setItems(getUserLabels(users));
    
    lsUsers.addSelectionListener(new SelectionListener() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        String selectedUserId = getAdminControl().getUsers().getSelection()[0].split("-")[0].trim();
        User selectedUser = getUser(selectedUserId);
        getAdminControl().getUserID().setText(selectedUser.getUserId());
        getAdminControl().getUserName().setText(selectedUser.getUserName());
        getAdminControl().getPassword().setText("");
        getAdminControl().getChbIsAdmin().setSelection(selectedUser.isAdmin());
      }
      @Override
      public void widgetDefaultSelected(SelectionEvent arg0) {
      }
    });
    
    adminControl.getBtnAdd().addSelectionListener(new SelectionListener() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        addUser();
      }
      @Override
      public void widgetDefaultSelected(SelectionEvent arg0) {
      }
    });
    adminControl.getBtnSave().addSelectionListener(new SelectionListener() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        saveUser();
      }
      @Override
      public void widgetDefaultSelected(SelectionEvent arg0) {
      }
    });
    adminControl.getBtnReset().addSelectionListener(new SelectionListener() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        resetUser();
      }
      @Override
      public void widgetDefaultSelected(SelectionEvent arg0) {
      }
    });
    adminControl.getBtnDelete().addSelectionListener(new SelectionListener() {
      @Override
      public void widgetSelected(SelectionEvent arg0) {
        deleteUser();
      }
      @Override
      public void widgetDefaultSelected(SelectionEvent arg0) {
      }
    });
  }
  
  public void loadProperties (){
    try {
      properties.load(new FileInputStream(AssemblyMonitoring.PROPERTIES_LOCATION + File.separator + USERS_FILE_NAME));
    } catch (FileNotFoundException e) {
    } catch (IOException e) {
    }
  }
  
  public void saveUsers(){
    try {
      properties.store(new FileOutputStream(AssemblyMonitoring.PROPERTIES_LOCATION + File.separator + USERS_FILE_NAME),
          "Users file for Assembly Monitoring");
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public String getProperty(String propertyName){
    return properties.getProperty (propertyName,"<null>");
  }
  
  public void setProperty(String key , String value){
    properties.setProperty (key,value);
  }

  public List<User> getUsers(){
    loadProperties();
    Set<Object> userIds = properties.keySet();
    LinkedList<User> users = new LinkedList<User>();
    for (Object userId : userIds){
      users.add(parseUserData(userId.toString(), getProperty(userId.toString())));
    }
    
    Collections.sort(users);
    
    return users;
  }
  
  public User getUser (String userId){
    return parseUserData(userId, getProperty(userId));
  }
  
  private User parseUserData (String userId, String userData){
    String[] splitUserData = userData.split("\\|");
    return new User(userId, splitUserData[0], splitUserData[1].equalsIgnoreCase("true"),splitUserData[2]);
  }
  
  private String encodeUserData (User user){
    return user.getUserName() + "|"
           + user.isAdmin() + "|"
           + user.getPasswordHash();
  
  }
  
  private AdminControl getAdminControl(){
    return this.adminControl;
  }
  
  public String[] getUserLabels(List<User> users){
    String[] userLabels = new String[users.size()];
    int index = 0; 
    for (User user : users){
      userLabels[index] = formatUserlabel(user);
          index++;
    }
    return userLabels;
  }
  
  public String formatUserlabel (User user){
    return user.getUserId() + " - " + user.getUserName(); 
  }
  
  private void addUser(){
    org.eclipse.swt.widgets.List lsUsers = adminControl.getUsers();
    lsUsers.add(AssemblyUsersModel.NEW_ID,0);
    lsUsers.select(0);
    adminControl.getUserID().setText("");
    adminControl.getUserName().setText("");
    adminControl.getPassword().setText("");
    adminControl.getChbIsAdmin().setSelection(false);
  }
  private void saveUser(){
    if (adminControl.getUserID().getText().length() > 0 
        && adminControl.getPassword().getText().length() > 0){
      if (adminControl.getUsers().getItemCount() > 0 
          && adminControl.getUsers().getSelection()[0].equals(AssemblyUsersModel.NEW_ID)){
        adminControl.getUsers().remove(0);
      }
      
      User user = new User(adminControl.getUserID().getText(),
          adminControl.getUserName().getText(),
          adminControl.getChbIsAdmin().getSelection(),
          AssemblyUsersModel.calculatePasswordHash(adminControl.getPassword().getText()));
      
      properties.put(user.getUserId(), encodeUserData(user));
      
      saveUsers();
      adminControl.getUsers().setItems(getUserLabels(getUsers()));
    }
    else{
      MessageBox.displayMessageBox(adminControl.getUserID().getShell(), SWT.ERROR, "Chyba !",
          "ID uzivatela a heslo nemozu byt prazdne.");
    }
  }
  private void resetUser(){
    String currentUserId = adminControl.getUsers().getSelection()[0].split("-")[0].trim();
    User user = getUser(currentUserId);
    adminControl.getUserID().setText(user.getUserId());
    adminControl.getUserName().setText(user.getUserName());
    adminControl.getChbIsAdmin().setSelection(user.isAdmin());
    adminControl.getPassword().setText("");
  }
  private void deleteUser(){
    properties.remove(adminControl.getUserID().getText());
    saveUsers();
    adminControl.getUsers().setItems(getUserLabels(getUsers()));
    adminControl.getUserID().setText("");
    adminControl.getUserName().setText("");
    adminControl.getPassword().setText("");
    adminControl.getChbIsAdmin().setSelection(false);
  }
  
  public boolean checkAndSetLoggedUser(String userId, String password){
    boolean isAllowed = false;
    if (properties.containsKey(userId)){
      User user = getUser(userId);
      if (user.getPasswordHash().equals(AssemblyUsersModel.calculatePasswordHash(password))){
        loggedUser = user;
        isAllowed = true;
      }
    }
    return isAllowed;
  }
  
  public static String calculatePasswordHash(String password){
    return getMD5Sum(password);
  }
  
  public User getLoggedUser() {
    return loggedUser;
  }
  
  private static String getMD5Sum(String stringToHash){
    String md5Sum = null;
    MessageDigest md;
    ByteArrayInputStream bais = null;
    DigestInputStream dis = null;
    try {
      md = MessageDigest.getInstance("MD5");
      md.reset();
      bais = new ByteArrayInputStream(stringToHash.getBytes());
      dis = new DigestInputStream(bais, md);
      while (dis.read() != -1) {
        // just go trough bytes in file
      }
      byte[] digested = md.digest();
      StringBuffer sb = new StringBuffer();
      for (int i = 0; i < digested.length; i++) {
        // sb.append(Integer.toHexString(0xff & digested[i]));
        sb.append(Integer.toHexString((digested[i] & 0xFF) | 0x100).substring(1, 3));
      }
      md5Sum = sb.toString();
    } catch (NoSuchAlgorithmException nsae) {
      MessageBox.displayMessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR,
          "Chyba !",
          "Unable to calculate MD5 sum for " + stringToHash  + nsae);
    } catch (IOException ioe ){
      
    }finally {
      if (bais != null) {
        try {
          bais.close();
        } catch (IOException ioe) {
          MessageBox.displayMessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR,
              "Chyba !",
              "Unable to close Input Stream " + ioe);
          System.out.println();
        }
      }
      if (dis != null) {
        try {
          dis.close();
        } catch (IOException ioe) {
          MessageBox.displayMessageBox(Display.getCurrent().getActiveShell(), SWT.ERROR,
              "Chyba !",
              "Unable to close Digest Input Stream " + ioe);
        }
      }
    }
    return md5Sum;
  }
}
