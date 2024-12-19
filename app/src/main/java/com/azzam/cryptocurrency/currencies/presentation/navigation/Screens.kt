package com.azzam.cryptocurrency.currencies.presentation.navigation

sealed class Screens(val route: String, val title: String) {
    data object CurrenciesListScreen : Screens("/currencies_list/", "Currencies List")
    data object CurrencyDetailsScreen : Screens("/currency_details/", "Currency Details")

    fun withArgs(vararg args: String?): String {
        return buildString {
            append(route)
            args.forEach { arg ->
                append("$arg/")
            }
        }
    }
}