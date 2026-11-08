package shop.notification;

import shop.StudentInfo;

public class SmsChannel extends AbstractChannel {
    private final int maxLength;

    public SmsChannel(int maxLength) {
        if (maxLength < 3) {
            throw new IllegalArgumentException("Panjang SMS minimal 3 karakter");
        }
        this.maxLength = maxLength;
    }

    public SmsChannel() {
        this.maxLength = StudentInfo.SMS_MAX_LENGTH;
    }

    @Override 
    public String channelName() {
        return "SMS";
    }

    @Override 
    protected boolean isValidRecipient(CustomerContact contact) {
        return contact.getPhone() != null && contact.getPhone().startsWith("08");
    }

    @Override 
    protected String resolveRecipient(CustomerContact contact) {
        return contact.getPhone();
    }

    @Override 
    protected String prepareMessage(String message) {
    if (message.length() > maxLength) {
        return message.substring(0, maxLength - 3) + "...";
    }
    return message;
    }

    @Override 
    protected void deliver(String recipient, String preparedMessage) {
        System.out.println("[SMS to " + recipient + "] " + preparedMessage);
    }
}