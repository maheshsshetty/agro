package agro.assignment;

/**
 * Hello world!
 *
 */
public class Constants 
{
public static  String username="";
public static  String password  ="";
public static  String repoName="";

private static Constants instance;

public Constants(){}

public static synchronized Constants getInstance(){
    if(instance == null){
        instance = new Constants();
    }
    return instance;
}
   
}
