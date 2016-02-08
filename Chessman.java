class Chessman
{
  Country country;
  Unit unit;

  Chessman(Country country, Unit unit)
  {
    this.country = country;
    this.unit = unit;
  }

  Country getCountry()
  {
    return this.country;
  }

  Unit getUnit()
  {
    return this.unit;
  }
}
