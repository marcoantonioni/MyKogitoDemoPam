package marco.demos.kogito;

public class RequestedData {
    private String requestorName;
    private int requestorAge;
    private int amount;
    private boolean accepted;

    public String getRequestorName() {
        return requestorName;
    }

    public void setRequestorName(String requestorName) {
        this.requestorName = requestorName;
    }

    public int getRequestorAge() {
        return requestorAge;
    }

    public void setRequestorAge(int requestorAge) {
        this.requestorAge = requestorAge;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    @Override
    public String toString() {
        return "RequestData: requestorName["+requestorName+"] requestorAge["+requestorAge+"] amount["+amount+"] accepted["+accepted+"]";
    }    
    
}
