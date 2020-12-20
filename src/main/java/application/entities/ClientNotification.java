package application.entities;

public class ClientNotification {
    private String notificationTitle = null;
    private String notificationText = null;

    public ClientNotification(String notificationTitle, String notificationText)
    {
        this.notificationTitle = notificationTitle;
        this.notificationText = notificationText;
    }
    
   public String getTitle()
   {
	   return notificationTitle;
   }
   
   public String getText()
   {
	   return notificationText;
   }
}
