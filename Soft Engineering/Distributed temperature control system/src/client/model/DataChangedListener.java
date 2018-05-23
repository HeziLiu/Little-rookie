package client.model;

public interface DataChangedListener {
    public void temperatureChanged(float temp);
    public void paymentChanged(float pay);
    public void windChanged(int wind);
    public void workModeChanged(int workMode);
    public void onException(Exception e);
}
