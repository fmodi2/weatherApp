import weather.Period;

class weatherAdapt{
    private Period period;

    //get the specific day
    public  weatherAdapt (Period period) {
        this.period = period;
    }

    //converts the temperature to Celsius and returns as a int
    public int getTemp() {
        if (period != null) {
            //formula to convert fahrenheit to celsius
            int temp = (period.temperature - 32) * 5 / 9;
            return temp;
        }
        else {
            throw new RuntimeException("The temp is null or empty couldn't be calculated");
        }

    }}