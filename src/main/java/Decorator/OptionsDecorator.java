package Decorator;

public class OptionsDecorator implements AdditionalOptions{

    AdditionalOptions additionalOptions;

    public OptionsDecorator(AdditionalOptions additionalOptions) {
        this.additionalOptions = additionalOptions;
    }

    @Override
    public String addBaggage() {
        return additionalOptions.addBaggage();
    }

    @Override
    public String economyClass() {
        return additionalOptions.economyClass();
    }

    @Override
    public String businessClass() {
        return additionalOptions.businessClass();
    }
}
