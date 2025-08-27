import weather.Period;

public abstract class weatherTemplate
{
    Period period;

    public weatherTemplate(Period period)
    {
        this.period = period;
    }
    protected abstract int showTemp();
}
