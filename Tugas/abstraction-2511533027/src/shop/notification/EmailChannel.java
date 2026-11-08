package shop.notification;

public class EmailChannel extends AbstractChannel {
    @Override
    public String channelName() {
        return "Email";
    }

    @Override 
    protected boolean isValidRecipient(CustomerContact contact) {
        return contact.getEmail() != null && contact.getEmail().contains("@");
    }

    @Override
    protected String resolveRecipient(CustomerContact contact) {
        return contact.getEmail();
    }

    @Override 
    protected String prepareMessage(String message) {
        return message;
    }

    @Override 
    protected void deliver(String recipient, String preparedMessage) {
        System.out.println("[Email to " + recipient + "] " + preparedMessage);
    }

}
