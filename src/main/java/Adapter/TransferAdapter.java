package Adapter;

public class TransferAdapter extends DollarToTenge implements Transfer{
    @Override
    public void transferValute() {
        transferTenge();
    }
}
