package shop.notification;

public abstract class AbstractChannel implements NotificationChannel {
    private int sentCount = 0;
    private String lastDelivered = "";

    protected abstract boolean isValidRecipient(CustomerContact contact);
    protected abstract String resolveRecipient(CustomerContact contact);
    protected abstract String prepareMessage(String message);
    protected abstract void deliver(String recipient, String preparedMessage);

    @Override 
    public final boolean send(CustomerContact contact, String message) {
        if (message == null || message.isBlank()) {
            return false;
        }

        if (!isValidRecipient(contact)) {
            return false;
        }

        String recipient = resolveRecipient(contact);
        String prepared = prepareMessage(message);

        if (recipient == null || recipient.isBlank() || prepared == null || prepared.isBlank()) {
            return false;
        }

        deliver(recipient, prepared);
        lastDelivered = prepared;
        sentCount++;
        return true;
    }

    public int getSentCount() {
        return sentCount;
    }
    public String getLastDelivered() {
        return lastDelivered;
    }

}
