package com.example.cupcake

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

private const val PRICE_PER_CUPCAKE = 2.00
private const val PRICE_FOR_SAME_DAY_PICKUP = 3.00

class OrderViewModel:ViewModel() {
    private var _orderQuantity = MutableLiveData<Int>()
    private var _cupcakeFlavor = MutableLiveData<String>()
    private var _pickupDate =  MutableLiveData<String>()
    private var _price = MutableLiveData<Double>()

    val cupcakeFlavor: LiveData<String> = _cupcakeFlavor
    val orderQuantity: LiveData<Int> = _orderQuantity
    val price: LiveData<String> = Transformations.map(_price) {
        NumberFormat.getCurrencyInstance().format(it)
    }
    val dateOptions = getPickupOptions()
    val pickupDate: LiveData<String> = _pickupDate

    init {
        resetOrder()
    }

    fun setOrderQuantity(numCupcakes: Int) {
        _orderQuantity.value = numCupcakes
        updatePrice()
    }
    fun setFlavor(flavor: String) {
        _cupcakeFlavor.value = flavor
    }
    fun setPickupDate(newDate: String) {
        _pickupDate.value = newDate
        updatePrice()
    }
    fun hasFlavor() : Boolean {
        return !_cupcakeFlavor.value.isNullOrEmpty()
    }
    fun getPickupOptions(): List<String> {
        val options = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        repeat(4) {
            options.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return options
    }
    fun resetOrder() {
        _pickupDate.value = dateOptions[0]
        _orderQuantity.value = 0
        _price.value = 0.0
        _cupcakeFlavor.value = ""
    }
   fun updatePrice() {
       var calculatedPrice = (_orderQuantity.value?:0) * PRICE_PER_CUPCAKE
       if (dateOptions[0] == _pickupDate.value) {
           calculatedPrice += PRICE_FOR_SAME_DAY_PICKUP
       }
       _price.value = calculatedPrice
   }
}