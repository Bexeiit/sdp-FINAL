package Adapter;

public class RubleAdapter extends RubleToTenge implements Transfer{
    @Override
    public void transferValute() {
        TransferTenge();
    }
}
