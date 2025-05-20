package com.example.avaliacaomobile.adapter

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Filter
import com.example.avaliacaomobile.model.CarModel

class CarModelAdapter(context: Context, private val allCars: MutableList<CarModel>) :
    ArrayAdapter<CarModel>(context, android.R.layout.simple_list_item_1, allCars) {

    private var filteredCars = allCars.toMutableList()

    override fun getCount(): Int = filteredCars.size

    override fun getItem(position: Int): CarModel? = filteredCars[position]

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterResults = FilterResults()
                val query = constraint?.toString()?.lowercase()?.trim()

                val filteredList = if (query.isNullOrEmpty()) {
                    allCars
                } else {
                    allCars.filter {
                        it.modelo.lowercase().contains(query)
                    }
                }

                filterResults.values = filteredList
                filterResults.count = filteredList.size
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                filteredCars = (results.values as List<CarModel>).toMutableList()
                notifyDataSetChanged()
            }
        }
    }
}