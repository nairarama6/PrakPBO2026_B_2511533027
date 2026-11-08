package shop.notification;

public class WhatsAppChannel extends AbstractChannel {
    @Override 
    public String channelName() {
        return "WA";
    }

    @Override 
    protected boolean isValidRecipient(CustomerContact contact) {
        String phone = contact.getPhone();
        if (phone == null || phone.length() < 10)
            return false;
        return phone.startsWith("08") || phone.startsWith("+62");
    }

    @Override 
    protected String resolveRecipient(CustomerContact contact) {
        return contact.getPhone();
    }

    @Override 
    protected String prepareMessage(String message) {
        return "[Toko UMKM] " + message;
    }

    @Override 
    protected void deliver(String recipient, String preparedMessage) {
        System.out.println("[WA to " + recipient + "] " + preparedMessage);
    }

}
