package com.example.calendarapp.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import android.os.Build
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import com.example.calendarapp.R
import android.view.ViewGroup
import android.widget.Button
import java.util.*

class SimpleCalendar : LinearLayout {
    private var currentDate: TextView? = null
    private var currentMonth: TextView? = null
    private var selectedDayButton: Button? = null
    private lateinit var days: Array<Button?>
    var weekOneLayout: LinearLayout? = null
    var weekTwoLayout: LinearLayout? = null
    var weekThreeLayout: LinearLayout? = null
    var weekFourLayout: LinearLayout? = null
    var weekFiveLayout: LinearLayout? = null
    var weekSixLayout: LinearLayout? = null
    private lateinit var weeks: Array<LinearLayout?>
    private var currentDateDay = 0
    private var chosenDateDay = 0
    private var currentDateMonth = 0
    private var chosenDateMonth = 0
    private var currentDateYear = 0
    private var chosenDateYear = 0
    private var pickedDateDay = 0
    private var pickedDateMonth = 0
    private var pickedDateYear = 0
    var userMonth = 0
    var userYear = 0
    private var mListener: DayClickListener? = null
    private var userDrawable: Drawable? = null
    private var calendar: Calendar? = null
    var defaultButtonParams: LayoutParams? = null
    private var userButtonParams: LayoutParams? = null

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
        init(context)
    }

    private fun init(context: Context) {
        val metrics = resources.displayMetrics
        val view = LayoutInflater.from(context).inflate(R.layout.calender_layout, this, true)
        calendar = Calendar.getInstance()
        weekOneLayout = view.findViewById<View>(R.id.calendar_week_1) as LinearLayout
        weekTwoLayout = view.findViewById<View>(R.id.calendar_week_2) as LinearLayout
        weekThreeLayout = view.findViewById<View>(R.id.calendar_week_3) as LinearLayout
        weekFourLayout = view.findViewById<View>(R.id.calendar_week_4) as LinearLayout
        weekFiveLayout = view.findViewById<View>(R.id.calendar_week_5) as LinearLayout
        weekSixLayout = view.findViewById<View>(R.id.calendar_week_6) as LinearLayout
        currentDate = view.findViewById<View>(R.id.current_date) as TextView
        currentMonth = view.findViewById<View>(R.id.current_month) as TextView
        chosenDateDay = calendar!!.get(Calendar.DAY_OF_MONTH)
        currentDateDay = chosenDateDay
        if (userMonth != 0 && userYear != 0) {
            chosenDateMonth = userMonth
            currentDateMonth = chosenDateMonth
            chosenDateYear = userYear
            currentDateYear = chosenDateYear
        } else {
            chosenDateMonth = calendar!!.get(Calendar.MONTH)
            currentDateMonth = chosenDateMonth
            chosenDateYear = calendar!!.get(Calendar.YEAR)
            currentDateYear = chosenDateYear
        }
        currentDate!!.text = "" + currentDateDay
        currentMonth!!.text = ENG_MONTH_NAMES[currentDateMonth]
        initializeDaysWeeks()
        defaultButtonParams = if (userButtonParams != null) {
            userButtonParams
        } else {
            getdaysLayoutParams()
        }
        addDaysinCalendar(defaultButtonParams, context, metrics)
        initCalendarWithDate(chosenDateYear, chosenDateMonth, chosenDateDay)
    }

    private fun initializeDaysWeeks() {
        weeks = arrayOfNulls(6)
        days = arrayOfNulls(6 * 7)
        weeks[0] = weekOneLayout
        weeks[1] = weekTwoLayout
        weeks[2] = weekThreeLayout
        weeks[3] = weekFourLayout
        weeks[4] = weekFiveLayout
        weeks[5] = weekSixLayout
    }

    private fun initCalendarWithDate(year: Int, month: Int, day: Int) {
        if (calendar == null) calendar = Calendar.getInstance()
        calendar!![year, month] = day
        val daysInCurrentMonth = calendar!!.getActualMaximum(Calendar.DAY_OF_MONTH)
        chosenDateYear = year
        chosenDateYear = year
        chosenDateMonth = month
        chosenDateDay = day
        calendar!![year, month] = 1
        val firstDayOfCurrentMonth = calendar!![Calendar.DAY_OF_WEEK]
        calendar!![year, month] = calendar!!.getActualMaximum(Calendar.DAY_OF_MONTH)
        var dayNumber = 1
        var daysLeftInFirstWeek = 0
        var indexOfDayAfterLastDayOfMonth = 0
        if (firstDayOfCurrentMonth != 1) {
            daysLeftInFirstWeek = firstDayOfCurrentMonth
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth
            for (i in firstDayOfCurrentMonth until firstDayOfCurrentMonth + daysInCurrentMonth) {
                if (currentDateMonth == chosenDateMonth && currentDateYear == chosenDateYear && dayNumber == currentDateDay) {
                    days[i]!!.setBackgroundColor(resources.getColor(R.color.green))
                    days[i]!!.setTextColor(Color.WHITE)
                } else {
                    days[i]!!.setTextColor(Color.BLACK)
                    days[i]!!.setBackgroundColor(Color.TRANSPARENT)
                }
                val dateArr = IntArray(3)
                dateArr[0] = dayNumber
                dateArr[1] = chosenDateMonth
                dateArr[2] = chosenDateYear
                days[i]!!.tag = dateArr
                days[i]!!.text = dayNumber.toString()
                days[i]!!.setOnClickListener { v -> onDayClick(v) }
                ++dayNumber
            }
        } else {
            daysLeftInFirstWeek = 8
            indexOfDayAfterLastDayOfMonth = daysLeftInFirstWeek + daysInCurrentMonth
            for (i in 8 until 8 + daysInCurrentMonth) {
                if (currentDateMonth == chosenDateMonth && currentDateYear == chosenDateYear && dayNumber == currentDateDay) {
                    days[i]!!.setBackgroundColor(resources.getColor(R.color.green))
                    days[i]!!.setTextColor(Color.WHITE)
                } else {
                    days[i]!!.setTextColor(Color.BLACK)
                    days[i]!!.setBackgroundColor(Color.TRANSPARENT)
                }
                val dateArr = IntArray(3)
                dateArr[0] = dayNumber
                dateArr[1] = chosenDateMonth
                dateArr[2] = chosenDateYear
                days[i]!!.tag = dateArr
                days[i]!!.text = dayNumber.toString()
                days[i]!!.setOnClickListener { v -> onDayClick(v) }
                ++dayNumber
            }
        }
        if (month > 0) calendar!![year, month - 1] = 1 else calendar!![year - 1, 11] = 1
        var daysInPreviousMonth = calendar!!.getActualMaximum(Calendar.DAY_OF_MONTH)
        for (i in daysLeftInFirstWeek - 1 downTo 0) {
            val dateArr = IntArray(3)
            if (chosenDateMonth > 0) {
                if (currentDateMonth == chosenDateMonth - 1 && currentDateYear == chosenDateYear && daysInPreviousMonth == currentDateDay) {
                } else {
                    days[i]!!.setBackgroundColor(Color.TRANSPARENT)
                }
                dateArr[0] = daysInPreviousMonth
                dateArr[1] = chosenDateMonth - 1
                dateArr[2] = chosenDateYear
            } else {
                if (currentDateMonth == 11 && currentDateYear == chosenDateYear - 1 && daysInPreviousMonth == currentDateDay) {
                } else {
                    days[i]!!.setBackgroundColor(Color.TRANSPARENT)
                }
                dateArr[0] = daysInPreviousMonth
                dateArr[1] = 11
                dateArr[2] = chosenDateYear - 1
            }
            days[i]!!.tag = dateArr
            days[i]!!.text = daysInPreviousMonth--.toString()
            days[i]!!.setOnClickListener { v -> onDayClick(v) }
        }
        var nextMonthDaysCounter = 1
        for (i in indexOfDayAfterLastDayOfMonth until days.size) {
            val dateArr = IntArray(3)
            if (chosenDateMonth < 11) {
                if (currentDateMonth == chosenDateMonth + 1 && currentDateYear == chosenDateYear && nextMonthDaysCounter == currentDateDay) {
                    days[i]!!.setBackgroundColor(resources.getColor(R.color.green))
                } else {
                    days[i]!!.setBackgroundColor(Color.TRANSPARENT)
                }
                dateArr[0] = nextMonthDaysCounter
                dateArr[1] = chosenDateMonth + 1
                dateArr[2] = chosenDateYear
            } else {
                if (currentDateMonth == 0 && currentDateYear == chosenDateYear + 1 && nextMonthDaysCounter == currentDateDay) {
                    days[i]!!.setBackgroundColor(resources.getColor(R.color.green))
                } else {
                    days[i]!!.setBackgroundColor(Color.TRANSPARENT)
                }
                dateArr[0] = nextMonthDaysCounter
                dateArr[1] = 0
                dateArr[2] = chosenDateYear + 1
            }
            days[i]!!.tag = dateArr
            days[i]!!.setTextColor(Color.parseColor(CUSTOM_GREY))
            days[i]!!.text = nextMonthDaysCounter++.toString()
            days[i]!!.setOnClickListener { v -> onDayClick(v) }
        }
        calendar!![chosenDateYear, chosenDateMonth] = chosenDateDay
    }

    fun onDayClick(view: View?) {
        mListener!!.onDayClick(view)
        if (selectedDayButton != null) {
            if (chosenDateYear == currentDateYear && chosenDateMonth == currentDateMonth && pickedDateDay == currentDateDay) {
                selectedDayButton!!.setBackgroundColor(resources.getColor(R.color.green))
                selectedDayButton!!.setTextColor(Color.WHITE)
            } else {
                selectedDayButton!!.setBackgroundColor(Color.TRANSPARENT)
                if (selectedDayButton!!.currentTextColor != Color.RED) {
                    selectedDayButton!!.setTextColor(
                        resources
                            .getColor(R.color.green)
                    )
                }
            }
        }
        selectedDayButton = view as Button?
        if (selectedDayButton!!.tag != null) {
            val dateArray = selectedDayButton!!.tag as IntArray
            pickedDateDay = dateArray[0]
            pickedDateMonth = dateArray[1]
            pickedDateYear = dateArray[2]
        }
        if (pickedDateYear == currentDateYear && pickedDateMonth == currentDateMonth && pickedDateDay == currentDateDay) {
            selectedDayButton!!.setBackgroundColor(resources.getColor(R.color.green))
            selectedDayButton!!.setTextColor(Color.WHITE)
        } else {
            selectedDayButton!!.setBackgroundColor(resources.getColor(R.color.green))
            if (selectedDayButton!!.currentTextColor != Color.RED) {
                selectedDayButton!!.setTextColor(Color.WHITE)
            }
        }
    }

    private fun addDaysinCalendar(
        buttonParams: LayoutParams?, context: Context,
        metrics: DisplayMetrics
    ) {
        var engDaysArrayCounter = 0
        for (weekNumber in 0..5) {
            for (dayInWeek in 0..6) {
                val day = Button(context)
                day.setTextColor(Color.parseColor(CUSTOM_GREY))
                day.setBackgroundColor(Color.TRANSPARENT)
                day.layoutParams = buttonParams
                day.textSize = (metrics.density.toInt() * 8).toFloat()
                day.setSingleLine()
                days[engDaysArrayCounter] = day
                weeks[weekNumber]!!.addView(day)
                ++engDaysArrayCounter
            }
        }
    }

    private fun getdaysLayoutParams(): LayoutParams {
        val buttonParams = LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        buttonParams.weight = 1f
        return buttonParams
    }

    fun setUserDaysLayoutParams(userButtonParams: LayoutParams?) {
        this.userButtonParams = userButtonParams
    }

    fun setUserCurrentMonthYear(userMonth: Int, userYear: Int) {
        this.userMonth = userMonth
        this.userYear = userYear
    }

    fun setDayBackground(userDrawable: Drawable?) {
        this.userDrawable = userDrawable
    }

    interface DayClickListener {
        fun onDayClick(view: View?)
    }

    fun setCallBack(mListener: DayClickListener?) {
        this.mListener = mListener
    }

    companion object {
        private const val CUSTOM_GREY = "#a0a0a0"
        private val ENG_MONTH_NAMES = arrayOf(
            "January", "February", "March", "April",
            "May", "June", "July", "August",
            "September", "October", "November", "December"
        )
    }
}