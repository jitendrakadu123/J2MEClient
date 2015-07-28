import javax.microedition.midlet.MIDlet;
import javax.microedition.lcdui.*;

public class LogIn implements CommandListener{
  private Display display;
  private TextField userName,password;
  public Form form;
  private Command login,cancel;
  private Image img, imge, img2;
  List searchdevice;
  
  public LogIn(MIDlet m,List search) {
  form = new Form("Sign in");
  userName = new TextField("LoginID:", "", 30, TextField.ANY);
  password = new TextField("Password:", "", 30, TextField.PASSWORD);
  cancel = new Command("Cancel", Command.CANCEL, 2);
  login = new Command("Login", Command.OK, 2);  
  display = Display.getDisplay(m);
  searchdevice=search;
  try{form.append(img);}catch(Exception e){}
  form.append(userName);
  form.append(password);
  form.addCommand(cancel);
  form.addCommand(login);
  form.setCommandListener(this);
  display.setCurrent(form);
  }

  public void validateUser(String name, String password) {
  if (name.equals("ritesh") && password.equals("abc123")) {
  display.setCurrent(searchdevice);
  } else {
  tryAgain();
  }
  }  

  public void showMsg() {
   
  }

  public void tryAgain() {
  Alert error = new Alert("Login Incorrect", "Please try again", imge, AlertType.ERROR);
  error.setTimeout(900);
  userName.setString("");
  password.setString("");
  display.setCurrent(error, form);
  }
  
  public void commandAction(Command c, Displayable d) {
  String label = c.getLabel();
  if(label.equals("Login")) {
  validateUser(userName.getString(), password.getString());
  } 
  }
}