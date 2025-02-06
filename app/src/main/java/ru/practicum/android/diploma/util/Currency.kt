package ru.practicum.android.diploma.util

enum class Currency(val currencyName: String, val symbol: String) {
    RUB(currencyName = "RUB", symbol = "₽"),
    RUR(currencyName = "RUR", symbol = "₽"),
    USD(currencyName = "USD", symbol = "$"),
    EUR(currencyName = "EUR", symbol = "€"),
    BYR(currencyName = "BYR", symbol = "Br"),
    KZT(currencyName = "KZT", symbol = "₸"),
    UAH(currencyName = "UAH", symbol = "₴"),
    AZN(currencyName = "AZN", symbol = "₼"),
    UZS(currencyName = "UZS", symbol = "Soʻm"),
    GEL(currencyName = "GEL", symbol = "₾"),
    KGT(currencyName = "KGT", symbol = "с•с"),
    CURRENCY_UNSPECIFIED("", "")
}
