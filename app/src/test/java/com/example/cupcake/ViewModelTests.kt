package com.example.cupcake;


import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import org.junit.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;

class ViewModelTests {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun quantity_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.orderQuantity.observeForever {
        }
        viewModel.setOrderQuantity(12)
        assertEquals(12, viewModel.orderQuantity.value)
    }

    @Test
    fun price_twelve_cupcakes() {
        val viewModel = OrderViewModel()
        viewModel.setOrderQuantity(12)
        viewModel.price.observeForever {  }
        assertEquals("AED27.00", viewModel.price.value)
    }
}