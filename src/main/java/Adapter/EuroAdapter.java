package Adapter;

public class EuroAdapter extends EuroToTenge implements Transfer{
    @Override
    public void transferValute() {
        TransferTenge();
    }
}
