package com.example.cpuperfdemo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private val items = mutableListOf<Item>()
    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("Sample1Activity", "onCreate")
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(items)

        swipeRefreshLayout.setOnRefreshListener(::refreshData)
    }

    private fun refreshData() {
        items.run {
            clear()
            addAll(generateItems())
        }
        recyclerView.adapter?.notifyDataSetChanged()
        swipeRefreshLayout.isRefreshing = false
    }
}

private fun generateItems(): List<Item> {
    val now = LocalDateTime.now()
    return List(500_000) { createItem(now, it + 1) }
}

private fun createItem(now: LocalDateTime, offset: Int): Item {
    val date = now.plusDays(offset.toLong()).toLocalDate().atStartOfDay()
    return Item(
        formattedDate = date.format(DateTimeFormatter.ISO_LOCAL_DATE),
        remainingTime = getRemainingTime(now, date)
    )
}

private fun getRemainingTime(start: LocalDateTime, end: LocalDateTime): String {
    val duration = Duration.between(start, end)
    val days = duration.toDays()
    val hours = duration.minusDays(days).toHours()
    val minutes = duration.minusDays(days).minusHours(hours).toMinutes()
    val seconds = duration.minusDays(days).minusHours(hours).minusMinutes(minutes).seconds
    return buildString {
        if (days > 0) append("$days d")
        if (hours > 0) append(" $hours h")
        if (minutes > 0) append(" $minutes min")
        if (seconds > 0) append(" $seconds s")
    }.trim()
}
