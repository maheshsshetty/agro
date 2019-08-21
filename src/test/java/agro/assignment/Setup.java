package agro.assignment;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeSuite;

import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.testng.annotations.AfterSuite;

public class Setup {
  
  @Test()
  public void getInput()
  {
	    JTextField username = new JTextField();
	    JTextField password = new JPasswordField();
	    JTextField reponame = new JTextField();
	    Object[] message = {
	        "Git Username:", username,
	        "Git Password:", password,
	        "Git New Reponame:", reponame,
	    };
	    int option = JOptionPane.showConfirmDialog(null, message, "Input is mandatory", JOptionPane.OK_CANCEL_OPTION);
	    if (option == JOptionPane.OK_OPTION) {
	    	Constants obj=new Constants();
	       Constants.username=username.getText();
	       Constants.password=password.getText();
	       Constants.repoName=reponame.getText();
	    } else {
	        System.exit(0);
	    }
  }

  @AfterSuite
  public void afterSuite() {
  }

}
