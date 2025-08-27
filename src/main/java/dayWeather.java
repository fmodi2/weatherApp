import weather.Period;

public class dayWeather extends weatherTemplate{
    public dayWeather(Period p) {
        super(p);
    }

    @Override
    //fetch the temp for todays weather using period
    protected int showTemp() {
        if(period == null)
        {
            throw new RuntimeException("Forecast did not load");
        }
        int temp;
        temp = period.temperature;
        return temp;

    }
}
